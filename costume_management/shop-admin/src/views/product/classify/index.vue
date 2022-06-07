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
      >
        <el-form-item label="分类名称">
          <el-input
            v-model="listParams.key"
            placeholder="请输入商品名称关键字"
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
      <!-- 表格 -->
      <template #header>
        <el-button
          type="primary"
          @click="dialogTableVisible = true"
        >
          添加分类
        </el-button>
      </template>
      <el-table
        :data="categoryList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="分类ID"
        />
        <el-table-column
          prop="categoryName"
          label="分类名称"
        />
        <el-table-column
          min-width="120px"
          label="操作"
          fixed="right"
          align="center"
        >
          <template #default="{ row }">
            <el-button
              @click="showUpdateDialog(row)"
            >
              编辑
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
      :confirm="isUpdate ? updateGoods : saveGoods"
      v-model="dialogTableVisible"
      @close="handleClose"
    >
      <el-form
        :model="category"
        :rules="formRules"
        ref="categoryForm"
      >
        <el-form-item
          label-width="100px"
          label="分类名称"
          prop="name"
        >
          <el-input v-model="category.categoryName" />
        </el-form-item>
      </el-form>
    </app-dialog-form>
  </page-container>
</template>

<script lang="ts" setup>
import { ICategory } from '@/api/types/category'
import { onMounted, reactive, ref } from 'vue'
import * as categoryApi from '@/api/category'
import { Search } from '@element-plus/icons-vue'
import type { IElForm } from '@/types/element-plus'
import { ElMessage } from 'element-plus'

const categoryList = ref<ICategory[]>([])
const category = reactive<ICategory>({
  id: '',
  categoryName: ''
})
const listParams = reactive({
  key: '',
  categoryId: '',
  page: 1,
  limit: 5
})

const formRules = reactive({
  categoryName: [
    { required: true, message: '请输入商品名称', trigger: 'blur' }
  ]
})
const listCount = ref(0)
const listLoading = ref(false)

const dialogTableVisible = ref(false)
const isUpdate = ref(false)
const categoryForm = ref<IElForm | null>(null)

onMounted(() => {
  loadList()
})

const loadList = async () => {
  listLoading.value = true
  const { data } = await categoryApi.getCategoryList(listParams).finally(() => {
    listLoading.value = false
  })
  categoryList.value = data.list
  listCount.value = data.totalCount
}

const showUpdateDialog = (params: ICategory) => {
  dialogTableVisible.value = true
  isUpdate.value = true

  category.id = params.id
  category.categoryName = params.categoryName
}

const updateGoods = async () => {
  // 表单验证
  const valid = await categoryForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await categoryApi.updateCategory(category).finally(() => {
    dialogTableVisible.value = false
    loadList()
  })
  if (data.code === 200) {
    ElMessage.success('修改成功')
  }
}

const saveGoods = async () => {
  // 表单验证
  const valid = await categoryForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await categoryApi.saveCategory(category).finally(() => {
    dialogTableVisible.value = false
    loadList()
  })
  if (data.code === 200) {
    ElMessage.success('保存成功')
  }
}

const handleClose = () => {
  category.id = ''
  category.categoryName = ''

  isUpdate.value = false
}

</script>

<style lang='scss' scoped>

</style>
