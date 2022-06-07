<template>
  <page-container>
    <app-card>
      <template #header>
        数据筛选
      </template>
      <el-form
        ref="form"
        :model="listParams"
        :disabled="listLoading"
        label-width="70px"
      />
      <el-form-item
        label="仓库地址"
      >
        <el-input
          v-model="listParams.key"
          placeholder="请输入仓库地址关键字"
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
    </app-card>

    <app-card>
      <template #header>
        <el-button
          type="primary"
          @click="dialogTableVisible = true"
        >
          新增仓库
        </el-button>
      </template>
      <el-table
        :data="wareList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="仓库ID"
        />
        <el-table-column
          prop="address"
          label="仓库地址"
        />
        <el-table-column
          prop="createDate"
          label="创建时间"
        />
        <el-table-column
          prop="manager"
          label="负责人"
        />
        <el-table-column
          label="操作"
          fixed="right"
          align="center"
          min-width="150"
        >
          <template #default="{ row }">
            <el-button
              @click="showUpdateDialog(row)"
            >
              编辑
            </el-button>
            <el-button>
              分配管理员
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <app-pagination
        v-model:page="listParams.page"
        v-model:limit="listParams.limit"
        :list-count="listCount"
        :load-list="loadList"
      />
    </app-card>

    <app-dialog-form
      :confirm="isUpdate ? updateWare : saveWare"
      v-model="dialogTableVisible"
      @close="handleClose"
    >
      <el-form
        :model="ware"
        :rules="formRules"
        ref="wareForm"
      >
        <el-form-item
          label-width="100px"
          label="仓库地址"
          prop="categoryId"
        >
          <el-input v-model="ware.address" />
        </el-form-item>
      </el-form>
    </app-dialog-form>
  </page-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import type { IElForm } from '@/types/element-plus'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { IWare } from '@/api/types/ware'
import * as wareApi from '@/api/ware'

const listLoading = ref(false)
const listParams = reactive({
  key: '',
  limit: 5,
  page: 1
})

const wareList = ref<IWare[]>([])

const listCount = ref(0)

const dialogTableVisible = ref(false)
const isUpdate = ref(false)
const wareForm = ref<IElForm | null>(null)
const ware = reactive({
  id: '',
  address: '',
  userId: ''
})

const formRules = reactive({
  address: [
    { required: true, message: '请输入仓库地址', trigger: 'blur' }
  ]
})

onMounted(() => {
  loadList()
})

const loadList = async () => {
  listLoading.value = false
  const { data } = await wareApi.getWareList(listParams).finally(() => {
    listLoading.value = false
  })
  wareList.value = data.list
  listCount.value = data.totalCount
}

const showUpdateDialog = (params: any) => {
  ware.id = params.id
  ware.address = params.address
  ware.userId = params.userId

  isUpdate.value = true
  dialogTableVisible.value = true
}

const updateWare = async () => {
  // 表单验证
  const valid = await wareForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await wareApi.updateWare(ware).finally(() => {
    dialogTableVisible.value = false
    loadList()
  })
  if (data.code === 200) {
    ElMessage.success('保存成功')
  }
}

const saveWare = async () => {
  // 表单验证
  const valid = await wareForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await wareApi.saveWare(ware).finally(() => {
    dialogTableVisible.value = false
    loadList()
  })
  if (data.code === 200) {
    ElMessage.success('保存成功')
  }
}

const handleClose = () => {
  ware.id = ''
  ware.address = ''
  ware.userId = ''

  isUpdate.value = false
}
</script>

<style lang='scss' scoped>

</style>
