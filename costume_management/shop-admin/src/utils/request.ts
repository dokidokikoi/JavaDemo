import axios, { AxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { store } from '@/store'
import router from '@/router/'

const request = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL
})

// 请求拦截器
request.interceptors.request.use(function (config) {
  // 统一设置用户身份 token
  const user = store.state.user
  if (config && config.headers) {
    config.headers['Content-Type'] = 'application/json'
    if (user && user.token) {
      config.headers.Authorization = user.token
    }
  }
  return config
}, function (error) {
  return Promise.reject(error)
})

let isRefresh = false

// 响应拦截器
request.interceptors.response.use(function (response) {
  // 统一处理接口响应数据，比如 token 过期无效、服务端异常等
  if (response.status !== 200) {
    console.log(response.status)
    ElMessage.error('请求失败，请稍后重试')
    return Promise.reject(response)
  }
  // 请求正常，返回数据
  const status = response.data.code
  if ((!status || status === 200)) {
    return response
  }

  // 错误情况
  // 登录过期
  if (status === 410000) {
    if (isRefresh) return Promise.reject(response)
    isRefresh = true
    ElMessage.error('您的登录已过期')
    // 清空用户信息
    store.commit('setUser', null)
    // 跳转到登录界面
    router.push({
      name: 'login',
      query: {
        redirect: router.currentRoute.value.fullPath
      }
    })
    isRefresh = false
    // 在内部消化掉这个业务异常
    return Promise.reject(response)
  }

  // 其它错误情况
  ElMessage.error(response.data.msg || '请求失败，请稍后重试')
  // 手动返回一个 Promise 异常
  return Promise.reject(response.data)
}, function (error) {
  return Promise.reject(error)
})

export default <T = any>(config: AxiosRequestConfig) => {
  return request(config).then(
    res => {
      return {
        msg: res.data.msg,
        data: res.data.data as T,
        code: res.data.code
      }
    }
  )
}
