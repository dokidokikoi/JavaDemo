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
        <el-form-item label="用户名">
          <el-input
            v-model="searchParam.key"
            placeholder="请输入用户名关键字"
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
          @click="userDialogVisible = true"
        >
          新增用户
        </el-button>
      </template>
      <el-table
        :data="userList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="ID"
        />
        <el-table-column
          prop="account"
          label="账号"
        />
        <!-- <el-table-column
          prop="avatar"
          label="头像"
        >
          <template #default="{row}">
            <img
              v-show="row.avatar"
              :src="row.avatar"
              style="object-fit: cover; width: 100px; height: 100px"
            >
          </template>
        </el-table-column> -->
        <!-- <el-table-column
          prop="summary"
          label="简介"
        /> -->
        <el-table-column
          prop="createDate"
          label="创建日期"
        >
          <template #default="{row}">
            {{ formatDate(row.createDate) }}
          </template>
        </el-table-column>
        <!-- <el-table-column
          prop="lastLogin"
          label="最近操作日期"
        >
          <template #default="{row}">
            {{ formatDate(row.lastLogin) }}
          </template>
        </el-table-column> -->
        <el-table-column
          prop="state"
          label="状态"
        >
          <template #default="scope">
            <el-switch
              v-model="scope.row.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
              active-text="激活"
              inactive-text="下撤"
              @change="handleUpdateStatus(scope.row)"
              :loading="isLoad === scope.row.id"
            />
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          fixed="right"
          align="center"
        >
          <template #default="{ row }">
            <el-tooltip
              effect="dark"
              content="编辑用户"
              placement="top-end"
            >
              <el-button
                type="primary"
                @click="editUser(row)"
              >
                <el-icon><Edit /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="分配角色"
              placement="top-start"
            >
              <el-button
                type="success"
                @click="disRole(row)"
              >
                <el-icon><Edit /></el-icon>
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
      :confirm="isUpdate ? handleUpdateUser : handleAddUser"
      v-model="userDialogVisible"
      @close="handleClose"
    >
      <el-form
        :model="userParam"
        :rules="userFormRules"
        ref="userForm"
        label-width="100px"
      >
        <el-form-item
          label="账号"
          prop="account"
        >
          <el-input
            style="width=50%"
            v-model="userParam.account"
            placeholder="请输入账号"
          />
        </el-form-item>
        <el-form-item
          v-if="!isUpdate"
          label="密码"
          prop="password"
        >
          <el-input
            style="width=50%"
            v-model="userParam.password"
            placeholder="请输入密码"
          />
        </el-form-item>
        <el-form-item
          label="昵称"
          prop="nickname"
        >
          <el-input
            style="width=50%"
            v-model="userParam.nickname"
            placeholder="请输入昵称"
          />
        </el-form-item>
        <!-- <el-form-item
          label="头像"
          prop="avatar"
        >
          <img-upload
            :img="userParam.avatar"
            :app-width="'200'"
            :app-height="'200'"
            @get-img="getImg"
          />
        </el-form-item> -->
        <!-- <el-form-item
          label="邮箱"
          prop="email"
        >
          <el-input
            style="width=50%"
            v-model="userParam.email"
            placeholder="请输入邮箱"
          />
        </el-form-item> -->
        <!-- <el-form-item
          label="简介"
          prop="summary"
        >
          <el-input
            style="width=50%"
            type="textarea"
            v-model="userParam.summary"
            placeholder="请输入简介"
          />
        </el-form-item> -->
      </el-form>
    </app-dialog-form>

    <app-dialog-form
      :confirm="handleAddRole"
      v-model="roleDialogVisible"
    >
      <el-form
        :model="roleList"
      >
        <el-form-item
          label="角色"
        >
          <el-select
            v-model="roleids"
            multiple
            placeholder="请选择"
            clearable
            style="width: 50%;"
          >
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.roleName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
    </app-dialog-form>
  </page-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { IUser, IUserVo } from '@/api/types/user'
import * as userApi from '@/api/user'
import * as RoleApi from '@/api/role'
import { formatDate } from '@/utils/time'
import { IElForm } from '@/types/element-plus'
import { ElMessage } from 'element-plus'
import { IRoleVo } from '@/api/types/role'

const listLoading = ref(false)
const searchParam = reactive({
  key: '',
  limit: 5,
  page: 1
})
const userParam = reactive<IUser>({
  id: '',
  account: '',
  createDate: '',
  nickname: '',
  password: '',
  state: 0
})

const userList = ref<IUserVo[]>([])
const listCount = ref(0)

const loadList = async () => {
  listLoading.value = true
  const { data } = await userApi.getUserlist(searchParam).finally(() => {
    listLoading.value = false
  })

  userList.value = data.list
  listCount.value = data.totalCount
}
onMounted(() => {
  loadList()
})

const isLoad = ref('0')
const handleUpdateStatus = async (row: IUserVo) => {
  isLoad.value = row.id
  console.log(row.state)

  const data = await userApi.setUserState({ id: row.id, flag: row.state === 0 }).finally(() => {
    isLoad.value = '0'
  })

  if (data.code === 200) {
    ElMessage.success('修改状态成功')
    loadList()
  }
}

const userDialogVisible = ref(false)
const isUpdate = ref(false)

const editUser = (row: IUserVo) => {
  userParam.id = row.id
  userParam.account = row.account
  userParam.nickname = row.nickname

  isUpdate.value = true
  userDialogVisible.value = true
}
const handleUpdateUser = async () => {
  // 表单验证
  const valid = await userForm.value?.validate()
  if (!valid) {
    return false
  }
  const data = await userApi.editUser(userParam)

  if (data.code === 200) {
    ElMessage.success('修改成功')
    loadList()
    userDialogVisible.value = false
  }
}

const userFormRules = reactive({
  account: [
    { required: true, message: '请输入账户名', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})
const userForm = ref<IElForm | null>(null)
const handleAddUser = async () => {
  // 表单验证
  const valid = await userForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = userApi.addUser(userParam)

  if ((await data).code === 200) {
    ElMessage.success('添加用户成功')
    loadList()
    userDialogVisible.value = false
  }
}

// // 从上传组件获取图片url
// const getImg = (url: string) => {
//   // console.log(url)
//   userParam.avatar = url
// }

const handleClose = () => {
  userParam.id = ''
  userParam.account = ''
  userParam.nickname = ''
  userParam.password = ''

  isUpdate.value = false
}

const roleList = ref<IRoleVo[]>([])
const roleids = ref<string[]>([])
const editUserId = ref('0')
const roleDialogVisible = ref(false)
const disRole = (row: IUserVo) => {
  roleids.value = row.roles.map(e => e.id)
  editUserId.value = row.id
  getRoleList()
  roleDialogVisible.value = true
}

const getRoleList = async () => {
  const { data } = await RoleApi.getRoleList({ key: '', page: 1, limit: 1000 })

  roleList.value = data.list
}
const handleAddRole = async () => {
  const data = await userApi.setUserRole(editUserId.value, roleids.value)

  if (data.code === 200) {
    ElMessage.success('分配角色成功')
    loadList()
    roleDialogVisible.value = false
  }
}
</script>

<style lang='scss' scoped>

</style>
