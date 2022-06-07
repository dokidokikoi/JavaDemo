<template>
  <page-container>
    <app-card>
      <template #header>
        数据筛选
      </template>
      <el-form
        :model="listParam"
        :disabled="listLoading"
        ref="searchform"
      >
        <el-form-item
          label="权限名"
        >
          <el-input
            placeholder="请输入权限名关键字"
            style="width: 300px;"
            v-model="listParam.key"
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
          @click="dialogTableVisible = true"
        >
          新增权限
        </el-button>
      </template>
      <el-table
        :data="permissionList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="权限ID"
        />
        <el-table-column
          prop="name"
          label="权限名"
        />
        <el-table-column
          label="操作"
          fixed="right"
          align="center"
          min-width="150"
        >
          <template #default="{ row }">
            <el-button>
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <app-pagination
        v-model:page="listParam.page"
        v-model:limit="listParam.limit"
        :list-count="listCount"
        :load-list="loadList"
      />
    </app-card>

    <update-dialog v-model="dialogTableVisible" />
  </page-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import type { IElForm } from '@/types/element-plus'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import * as permissionApi from '@/api/permission'
import { IPermission } from '@/api/types/permission'
import UpdateDialog from './components/UpdateDialog.vue'

const listLoading = ref(false)
const permissionList = ref<IPermission[]>([])
const listParam = reactive({
  key: '',
  limit: 5,
  page: 1
})

const listCount = ref(0)

const dialogTableVisible = ref(false)

onMounted(() => {
  loadList()
})

const loadList = async () => {
  listLoading.value = true
  const { data } = await permissionApi.getPermissionList(listParam).finally(() => {
    listLoading.value = false
  })
  permissionList.value = data.list
  listCount.value = data.totalCount
}
</script>

<style lang='scss' scoped>

</style>
