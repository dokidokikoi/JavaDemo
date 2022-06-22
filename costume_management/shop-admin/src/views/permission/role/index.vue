<template>
  <page-container>
    <app-card>
      <template #header>
        数据筛选
      </template>
      <el-form
        ref="form"
        :model="searchParam"
        :disabled="listLoading"
        label-width="70px"
      >
        <el-form-item label="角色名">
          <el-input
            v-model="searchParam.key"
            placeholder="请输入角色名关键字"
            clearable
            style="width: 300px;"
            @keydown.enter="loadList"
          >
            <template #append>
              <el-button
                :icon="Search"
                @click="loadList"
              />
            </template>
          </el-input>
        </el-form-item>
      </el-form>
    </app-card>

    <app-card>
      <template #header>
        <el-button
          type="primary"
          @click="roleDialogVisible = true"
        >
          新增角色
        </el-button>
      </template>
      <el-table
        :data="roleList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="ID"
        />
        <el-table-column
          prop="roleName"
          label="角色名"
        />
        <el-table-column
          prop="roleDesc"
          label="角色简介"
        />
        <el-table-column
          label="操作"
          fixed="right"
          align="center"
        >
          <template #default="{ row }">
            <el-tooltip
              effect="dark"
              content="编辑角色"
              placement="top-end"
            >
              <el-button
                type="primary"
                @click="editRole(row)"
              >
                <el-icon><Edit /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="分配权限"
              placement="top"
            >
              <el-button
                type="success"
                @click="disRole(row)"
              >
                <el-icon><Document /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="删除角色"
              placement="top-start"
            >
              <el-button
                type="danger"
                @click="delRole(row.id)"
              >
                <el-icon><Delete /></el-icon>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <app-pagination
        v-model:page="searchParam.page"
        v-model:limit="searchParam.limit"
        :list-count="listCount"
        :load-list="loadList"
      />
    </app-card>

    <app-dialog-form
      :confirm="isUpdate ? handleUpdateRole : handleAddRole"
      v-model="roleDialogVisible"
      @close="handleClose"
    >
      <el-form
        :model="roleParam"
        :rules="roleFormRules"
        ref="roleForm"
        label-width="100px"
      >
        <el-form-item
          label="角色名"
          prop="roleName"
        >
          <el-input
            style="width=50%"
            v-model="roleParam.roleName"
            placeholder="请输入角色名"
          />
        </el-form-item>
        <el-form-item
          label="友链描述"
          prop="roleDesc"
        >
          <el-input
            style="width=50%"
            type="textarea"
            v-model="roleParam.roleDesc"
            placeholder="请输入角色描述"
          />
        </el-form-item>
      </el-form>
    </app-dialog-form>

    <app-dialog-form
      v-model="disDialogVisible"
      :confirm="handleDis"
    >
      <el-tree
        :props="props"
        node-key="name"
        :data="permissionTree"
        :default-checked-keys="checkedKeys"
        ref="treeRef"
        show-checkbox
      />
    </app-dialog-form>
  </page-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { IRole, IRoleVo } from '@/api/types/role'
import * as roleApi from '@/api/role'
import * as permissionApi from '@/api/permission'
import { IElForm, IElTree } from '@/types/element-plus'
import { ElMessage, ElMessageBox } from 'element-plus'
import { IPermissionTree } from '@/api/types/permission'

const searchParam = reactive({
  key: '',
  page: 1,
  limit: 5
})

const roleParam = reactive<IRole>({
  id: '',
  roleName: '',
  roleDesc: ''
})

const listCount = ref(0)
const roleList = ref<IRoleVo[]>([])
const listLoading = ref(false)
const loadList = async () => {
  listLoading.value = true
  const { data } = await roleApi.getRoleList(searchParam).finally(() => {
    listLoading.value = false
  })

  roleList.value = data.list
  listCount.value = data.totalCount
}

onMounted(() => {
  loadList()
})

const editRole = (row: any) => {
  isUpdate.value = true
  roleParam.id = row.id
  roleParam.roleName = row.roleName
  roleParam.roleDesc = row.roleDesc

  roleDialogVisible.value = true
}

const permissionTree = ref<IPermissionTree[]>([])
const checkedKeys = ref<string[]>([])
const props = {
  label: 'name',
  children: 'children'
}
const disId = ref('0')
const disRole = (row: IRoleVo) => {
  getPermissoinList()
  disDialogVisible.value = true

  // 默认展示权限
  checkedKeys.value = row.permissions.map(e => e.name)
  disId.value = row.id
}
const disDialogVisible = ref(false)
const getPermissoinList = async () => {
  const { data } = await permissionApi.getPermissionTree()

  permissionTree.value = data
}
const treeRef = ref<IElTree | null>(null)
const handleDis = async () => {
  const ids = treeRef.value!.getCheckedNodes(true, false).map(e => e.id)

  const data = await roleApi.setPermission(disId.value, ids).finally(() => {
    disDialogVisible.value = false
  })

  if (data.code === 200) {
    ElMessage.success('分配权限成功')
    loadList()
  }
}

const delRole = (id: string) => {
  ElMessageBox.confirm(
    '该操作不可逆，是否删除?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning'
    }
  )
    .then(() => {
      roleApi.delRole(id).then(data => {
        if (data.code === 200) {
          ElMessage.success('删除成功')
          loadList()
        }
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}

const roleDialogVisible = ref(false)
const isUpdate = ref(false)
const handleUpdateRole = async () => {
  // 表单验证
  const valid = await roleForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await roleApi.editRole(roleParam)

  if (data.code === 200) {
    ElMessage.success('新增角色成功')
    loadList()
    roleDialogVisible.value = false
  }
}

const roleFormRules = reactive({
  roleName: [
    { required: true, message: '请输入角色名称', trigger: 'blur' }
  ]
})
const roleForm = ref<IElForm | null>(null)
const handleAddRole = async () => {
  // 表单验证
  const valid = await roleForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await roleApi.addRole(roleParam)

  if (data.code === 200) {
    ElMessage.success('新增角色成功')
    loadList()
    roleDialogVisible.value = false
  }
}

const handleClose = () => {
  roleParam.id = ''
  roleParam.roleName = ''
  roleParam.roleDesc = ''

  isUpdate.value = false
}

</script>

<style lang='scss' scoped>

</style>
