/* eslint-disable import/no-duplicates */
<template>
  <div class="login-container">
    <el-form
      ref="form"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
      @submit.prevent="handleLogin"
    >
      <div class="title-container">
        <h3 class="title">
          Login Form
        </h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon
            class="icon usericon"
            name="user"
          />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-tooltip
        v-model="capsTooltip"
        content="Caps lock is On"
        placement="right"
        manual="true"
      >
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon
              class="icon"
              name="password"
            />
          </span>
          <el-input
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            placeholder="Password"
            name="password"
            tabindex="2"
            autocomplete="on"
            @keyup="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter="handleLogin()"
          />
          <span
            class="show-pwd"
            @click="showPwd"
          >
            <svg-icon
              class="icon"
              :name="passwordType === 'password' ? 'no_eye' : 'eye'"
            />
          </span>
        </el-form-item>
      </el-tooltip>

      <el-form-item prop="code">
        <div class="imgcode-wrap">
          <el-input
            v-model="loginForm.code"
            placeholder="请输入验证码"
          >
            <template #prefix>
              <i class="el-input__icon el-icon-key" />
            </template>
          </el-input>
          <img
            class="imgcode"
            alt="验证码"
            :src="captchaSrc"
            @click="loadCaptcha"
            title="看不清？单击换一张"
          >
        </div>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        native-type="submit"
        style="width:100%;margin-bottom:30px;"
        @click="handleLogin"
      >
        Login
      </el-button>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, nextTick } from 'vue'
import { getCaptcha, login } from '@/api/common'
import { ElMessage, FormItemRule } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { store } from '@/store'

const loginForm = reactive({
  username: 'admin',
  password: '123456',
  key: '',
  code: ''
})

const router = useRouter()
const route = useRoute()

const passwordType = ref('password')
const capsTooltip = ref(false)
const password = ref<HTMLInputElement|null>(null)
const form = ref<HTMLFormElement|null>(null)
const loading = ref(false)
const captchaSrc = ref('')

const loginRules = ref<Record<string, FormItemRule[]>>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' }
  ]
})

const showPwd = () => {
  if (passwordType.value === 'password') {
    passwordType.value = ''
  } else {
    passwordType.value = 'password'
  }
  nextTick(() => {
    if (password.value) {
      password.value.focus()
    }
  })
}

const checkCapslock = (e: { key: any; }) => {
  const { key } = e
  capsTooltip.value = key && key.length === 1 && (key >= 'A' && key <= 'Z')
}

// 获取验证码
const loadCaptcha = () => {
  getCaptcha().then((res: { imgCode: string; key: string }) => {
    captchaSrc.value = res.imgCode
    loginForm.key = res.key
  })
}

onMounted(() => {
  getCaptcha().then((res: { imgCode: string; key: string }) => {
    captchaSrc.value = res.imgCode
    loginForm.key = res.key
  })
})

// 登录
const handleLogin = async () => {
  // 表单验证
  const valid = await form.value?.validate()
  if (!valid) {
    return false
  }

  // 验证通过，展示 loading
  loading.value = true

  // 请求提交
  const data = await login(loginForm).catch(() => {
    loadCaptcha() // 刷新验证码
  }).finally(() => {
    loading.value = false
  })

  console.log(data)
  if (!data) return

  ElMessage.success('登录成功')

  // 存储登录用户信息
  store.commit('setUser', {
    user_info: { ...data.user_info },
    token: data.token
  })

  // store.commit('setMenus', data.menus)

  // 跳转回原来页面
  let redirect = route.query.redirect
  if (typeof redirect !== 'string') {
    redirect = '/'
  }
  router.replace(redirect)
}

</script>

<style lang='scss' scoped>
$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 40px;
    width: 85%;

    ::v-deep .el-input__wrapper {
      width: 100%;
      background-color: $bg;
      border-radius: 0;
      box-shadow: none;
    }

    ::v-deep input {
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 40px !important;
      caret-color: $cursor;

      ::v-deep &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}

$bg: #2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 500px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    height: 40px;
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .icon {
    width: 25px;
    height: 25px;
    margin-top: 7px;
    margin-left: 10px;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .imgcode-wrap {
    width: 98%;
    display: flex;
    align-items: center;
    padding: 6px 0px !important;
    .imgcode {
      height: 37px;
      float: right;
      margin-left: 20px;
      cursor: pointer;
    }
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }
}
</style>
