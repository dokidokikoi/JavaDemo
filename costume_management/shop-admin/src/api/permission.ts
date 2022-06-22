import request from '@/utils/request'
import { IPage } from './types/common'
import { IPermission, IPermissionTree } from './types/permission'

export const getPermissionList = (params: any) => {
  return request<IPage<IPermission>>({
    method: 'GET',
    url: 'permission/list',
    params
  })
}

export const addPermission = (data: IPermission) => {
  return request({
    method: 'POST',
    url: 'permission/save',
    data
  })
}

export const editPermission = (data: IPermission) => {
  return request({
    method: 'PUT',
    url: 'permission/update',
    data
  })
}

export const getPermissionTree = () => {
  return request<IPermissionTree[]>({
    method: 'GET',
    url: 'permission/permission_tree'
  })
}
