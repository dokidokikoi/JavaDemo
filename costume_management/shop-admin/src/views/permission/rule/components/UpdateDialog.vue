<template>
  <app-dialog-form
    :confirm="isUpdate ? updateWare : saveWare"
    @close="handleClose"
  >
    <el-form
      model="permission"
      :rules="formRules"
      ref="form"
    >
      <el-form-item
        prop="name"
        label="权限名"
      >
        <el-input v-model="permission.name" />
      </el-form-item>
    </el-form>
  </app-dialog-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { IElForm } from '@/types/element-plus'

const permission = reactive({
  id: '',
  name: ''
})

const form = ref<IElForm | null>(null)
const formRules = reactive({
  name: [
    { required: true, message: '请输入权限名', trigger: 'blur' }
  ]
})

const isUpdate = ref(false)

const updateWare = () => {

}

const saveWare = () => {
  
}

const handleClose = () => {

}
</script>

<style lang='scss' scoped>

</style>
