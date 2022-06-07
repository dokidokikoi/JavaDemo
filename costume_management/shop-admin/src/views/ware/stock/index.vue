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
        label-width="100px"
      >
        <el-form-item
          label="商品关键字"
        >
          <el-input
            v-model="listParams.key"
            placeholder="请输入商品关键字"
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
        <el-form-item
          label="仓库地址"
        >
          <el-input
            v-model="listParams.ware"
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
      </el-form>
    </app-card>

    <app-card>
      <template #header>
        数据展示
      </template>
      <el-table
        :data="stockList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="库存ID"
        />
        <el-table-column
          prop="goodsname"
          label="商品名"
        >
          <template #default="{ row }">
            {{ row.goods.name }}
          </template>
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间"
          min-width="120"
        >
          <template #default="{ row }">
            {{ formatDate(row.createDate) }}
          </template>
        </el-table-column>
        <el-table-column
          prop="warename"
          label="仓库地址"
        >
          <template #default="{ row }">
            {{ row.ware.address }}
          </template>
        </el-table-column>
        <el-table-column
          prop="amount"
          label="库存"
        />
        <el-table-column
          prop="amount"
          label="操作数"
        >
          <template #default="{ row }">
            <el-input
              type="number"
              v-model="row.num"
            />
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          fixed="right"
          align="center"
          min-width="160"
        >
          <template #default="{ row }">
            <el-button
              :loading="isCheckIn == row.id"
              @click="checkIn(row)"
            >
              入库
            </el-button>
            <el-button
              :loading="isCheckOut == row.id"
              @click="checkOut(row)"
            >
              出库
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
  </page-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { IStock } from '@/api/types/ware'
import * as wareApi from '@/api/ware'
import { formatDate } from '@/utils/time'

const listLoading = ref(false)
const listParams = reactive({
  key: '',
  ware: '',
  orderBy: '',
  limit: 5,
  page: 1
})

const stockList = ref<IStock[]>([])

const listCount = ref(0)

const isCheckIn = ref('0')
const isCheckOut = ref('0')

onMounted(() => {
  loadList()
})

const loadList = async () => {
  listLoading.value = true
  const { data } = await wareApi.getStockList(listParams).finally(() => {
    listLoading.value = false
  })
  stockList.value = data.list
  stockList.value.forEach(e => {
    e.num = 0
  })
  listCount.value = data.totalCount
}

const checkIn = async (row: any) => {
  isCheckIn.value = row.id
  const params = {
    type: 'checkIn',
    id: row.id,
    num: row.num
  }
  const data = await wareApi.updateStock(params).finally(() => {
    isCheckIn.value = '0'
    loadList()
  })

  if (data.code === 200) {
    ElMessage.success('入库成功')
  }
}

const checkOut = async (row: any) => {
  isCheckIn.value = row.id
  const params = {
    type: 'checkOut',
    id: row.id,
    num: row.num
  }
  const data = await wareApi.updateStock(params).finally(() => {
    isCheckIn.value = '0'
    loadList()
  })

  if (data.code === 200) {
    ElMessage.success('入库成功')
  }
}

</script>

<style lang='scss' scoped>

</style>
