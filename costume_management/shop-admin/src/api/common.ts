/**
 * 公共基础接口封装
 */
import request from '@/utils/request'
import { ICaptcha } from './types/common'

// interface ResponseData<T = any> {
//   status: number
//   msg: string
//   data: T
// }

// export const getLoginInfo = () => {
//   return request.get<ResponseData<{
//     logo_square: string
//     logo_rectangle: string
//     login_logo: string
//     slide: string[]
//   }>>('login/info').then(res => {
//     return res.data.data
//   })
// }

export const login = (data: {
  username: string
  password: string
  key: string
  code: string
}) => {
  return request({
    method: 'Post',
    url: 'login',
    data
  })
}

export const getCaptcha = () => {
  return request<ICaptcha>({
    method: 'GET',
    url: 'login/captcha',
    params: {
      stamp: Date.now()
    }
  })
}

export const logout = () => {
  return request({
    method: 'POST',
    url: 'logout'
  })
}
