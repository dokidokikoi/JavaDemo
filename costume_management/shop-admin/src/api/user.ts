import request from '@/utils/request'
import { IPage } from './types/common'
import { IUser, IUserVo } from './types/user'

export const getUserlist = (params: any) => {
  return request<IPage<IUserVo>>({
    method: 'GET',
    url: 'user/list',
    params
  })
}

export const addUser = (data: IUser) => {
  return request({
    method: 'POST',
    url: 'user/save',
    data
  })
}

export const editUser = (data: IUser) => {
  return request({
    method: 'PUT',
    url: 'user/update',
    data
  })
}

export const setUserState = (params: {id: string, flag: boolean}) => {
  return request({
    method: 'PUT',
    url: 'user/set_state',
    params
  })
}

export const setUserRole = (id: string, data: string[]) => {
  return request({
    method: 'PUT',
    url: 'user/set_role/' + id,
    data
  })
}
