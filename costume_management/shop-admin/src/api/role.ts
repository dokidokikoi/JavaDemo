import request from '@/utils/request'
import { IPage } from './types/common'
import { IRole, IRoleVo } from './types/role'

export const getRoleList = (params: any) => {
  return request<IPage<IRoleVo>>({
    method: 'GET',
    url: 'role/list',
    params
  })
}

export const addRole = (data: IRole) => {
  return request({
    method: 'POST',
    url: 'role/save',
    data
  })
}

export const editRole = (data: IRole) => {
  return request({
    method: 'PUT',
    url: 'role/update',
    data
  })
}

export const delRole = (id: string) => {
  return request({
    method: 'DELETE',
    url: 'role/delete/' + id
  })
}

export const setPermission = (id: string, data: string[]) => {
  return request({
    method: 'PUT',
    url: 'role/set_permission/' + id,
    data
  })
}
