<template>
  <h1>{{ $store.state.user?.user_info.nickname }} 欢迎您</h1>
  <app-card>
    <template #header>
      修改密码
    </template>
    <el-form
      :model="param"
      :rules="formRules"
      label-width="80px"
      ref="form"
    >
      <el-form-item
        prop="password"
        label="密码"
      >
        <el-input
          type="password"
          v-model="param.password"
        />
      </el-form-item>
      <el-form-item
        prop="newPassword"
        label="新密码"
      >
        <el-input
          type="password"
          v-model="param.newPassword"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="editPass"
        >
          修改
        </el-button>
      </el-form-item>
    </el-form>
  </app-card>
</template>

<script lang="ts" setup>
import { IElForm } from '@/types/element-plus'
import { reactive, ref } from 'vue'
import { changePass, logout } from '@/api/common'
import { ElMessage } from 'element-plus'
import { useStore } from '@/store'
import { useRouter } from 'vue-router'

const store = useStore()
const param = reactive({
  id: store.state.user?.user_info.id,
  password: '',
  newPassword: ''
})

const form = ref<IElForm | null>(null)
const formRules = reactive({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' }
  ]
})

const router = useRouter()
const editPass = async () => {
  // 表单验证
  const valid = await form.value?.validate()
  if (!valid) {
    return false
  }

  const data = await changePass(param)

  if (data.code === 200) {
    ElMessage.success('修改密码成功')
    await logout()
    router.push('login')
  }
}
</script>

<style lang='scss' scoped>

</style>
