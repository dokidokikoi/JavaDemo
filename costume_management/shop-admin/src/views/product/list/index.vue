/* eslint-disable vue/no-duplicate-attributes */
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
        <el-form-item label="商品分类">
          <el-select
            v-model="listParams.categoryId"
            placeholder="请选择"
            @change="loadList"
            clearable
          >
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称">
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
          添加商品
        </el-button>
      </template>
      <el-table
        :data="goodsList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="商品ID"
          :width="120"
        />
        <el-table-column
          prop="cover"
          label="商品图片"
          :width="150"
        >
          <template #default="scope">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="商品名称"
        />
        <el-table-column
          prop="price"
          label="商品售价"
        />
        <el-table-column
          label="状态"
          width="150"
        >
          <template #default="scope">
            <el-switch
              v-model="scope.row.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
              active-text="上架"
              inactive-text="下架"
              @change="handleUpdateStatus(scope.row)"
              :loading="isLoad == scope.row.id"
            />
          </template>
        </el-table-column>
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
            <el-button
              @click="showCheckInDialog(row)"
            >
              入库
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
        :model="goods"
        :rules="formRules"
        ref="goodsForm"
      >
        <el-form-item
          label-width="100px"
          label="商品名称"
          prop="name"
        >
          <el-input v-model="goods.name" />
        </el-form-item>
        <el-form-item
          label-width="100px"
          label="商品分类"
          prop="categoryId"
        >
          <el-select
            v-model="goods.categoryId"
            style="width: 50%;"
          >
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label-width="100px"
          label="商品封面图"
          prop="cover"
        >
          xxx
        </el-form-item>
        <el-form-item
          label-width="100px"
          label="商品单价"
          prop="price"
        >
          <el-input
            type="number"
            v-model="goods.price"
          />
        </el-form-item>
      </el-form>
    </app-dialog-form>

    <app-dialog-form
      :confirm="checkIn"
      v-model="checkIndialogTableVisible"
      @close="wareParam.wareId = ''"
    >
      <el-form
        :model="goods"
        :rules="formRules"
        ref="goodsForm"
      >
        <el-form-item
          label-width="100px"
          label="仓库地址"
          prop="ware"
        >
          <el-select
            v-model="wareParam.wareId"
            style="width: 50%;"
          >
            <el-option
              v-for="item in wareList"
              :key="item.id"
              :label="item.address"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
    </app-dialog-form>
  </page-container>
</template>

<script lang="ts" setup>
import PageContainer from '@/components/PageContainer/index.vue'
import * as goodsApi from '@/api/goods'
import * as categoryApi from '@/api/category'
import * as wareApi from '@/api/ware'
import { onMounted, reactive, ref } from 'vue'
import { IGoods, IGoodsParams } from '@/api/types/goods'
import { Search } from '@element-plus/icons-vue'
import { ICategory } from '@/api/types/category'
import type { IElForm } from '@/types/element-plus'
import { ElMessage } from 'element-plus'
import { IWare } from '@/api/types/ware'

const goodsList = ref<IGoods[]>([])
const categoryList = ref<ICategory[]>([])
const listLoading = ref(false)
const listParams = reactive<IGoodsParams>({
  key: '',
  categoryId: '',
  page: 1,
  limit: 5
})
const cateParams = {
  limit: 10000
}
const goods = reactive({
  id: '',
  name: '',
  price: 0,
  categoryId: '',
  cover: ''
})
const listCount = ref(0)
const isLoad = ref('0')

const isUpdate = ref(false)
const dialogTableVisible = ref(false)
const formRules = reactive({
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' }
  ],
  categoryId: [
    { required: true, message: '请选择商品分类', trigger: 'change' }
  ],
  price: [
    { required: true, message: '请输入单价', trigger: 'blur' }
  ]
})
const goodsForm = ref<IElForm | null>(null)

onMounted(() => {
  loadList()
})

const loadList = async () => {
  listLoading.value = true
  const { data } = await goodsApi.getGoodsList(listParams).finally(() => {
    listLoading.value = false
  })
  goodsList.value = data.list
  listCount.value = data.totalCount
  loadCategory()
  // console.log(data)
}

const loadCategory = async () => {
  const { data } = await categoryApi.getCategoryList(cateParams)
  categoryList.value = data.list
}

const saveGoods = async () => {
  // 表单验证
  const valid = await goodsForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await goodsApi.saveGoods(goods).finally(() => {
    dialogTableVisible.value = false
  })
  if (data.code === 200) {
    ElMessage.success('保存成功')
    loadList()
  }
}

const showUpdateDialog = (params: any) => {
  goods.id = params.id
  goods.cover = params.cover
  goods.name = params.name
  goods.price = params.price
  goods.categoryId = params.category.id

  isUpdate.value = true
  dialogTableVisible.value = true
}

const updateGoods = async () => {
  // 表单验证
  const valid = await goodsForm.value?.validate()
  if (!valid) {
    return false
  }

  const data = await goodsApi.updateGoods(goods).finally(() => {
    dialogTableVisible.value = false
    loadList()
  })
  if (data.code === 200) {
    ElMessage.success('保存成功')
  }
}

const handleClose = () => {
  goods.id = ''
  goods.cover = ''
  goods.name = ''
  goods.price = 0
  goods.categoryId = ''

  isUpdate.value = false
}

const handleUpdateStatus = async (params: { id: string; state: any }) => {
  isLoad.value = params.id
  const data = await goodsApi.updateGoodsState({
    id: params.id,
    state: params.state
  }).finally(() => {
    isLoad.value = '0'
  })
  if (data.code === 200) {
    ElMessage.success('修改成功')
  }
}

const checkIndialogTableVisible = ref(false)
const wareList = ref<IWare[]>([])
const wareParam = reactive({
  wareId: '',
  goodsId: ''
})

const loadWareList = async () => {
  listLoading.value = false
  const { data } = await wareApi.getWareList(listParams).finally(() => {
    listLoading.value = false
  })
  wareList.value = data.list
  listCount.value = data.totalCount
}

const showCheckInDialog = (row: any) => {
  checkIndialogTableVisible.value = true
  loadWareList()
  wareParam.goodsId = row.id
}

const checkIn = async () => {
  const data = await wareApi.saveStock(wareParam).finally(() => {
    checkIndialogTableVisible.value = false
  })
  if (data.code === 200) {
    ElMessage.success('入库成功')
  }
}

</script>

<style lang='scss' scoped>

</style>
