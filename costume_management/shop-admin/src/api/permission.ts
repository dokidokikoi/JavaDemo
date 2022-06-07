import request from '@/utils/request'
import { IPage } from './types/common'
import { IPermission } from './types/permission'

export const getPermissionList = (params: any) => {
  return request<IPage<IPermission>>({
    method: 'GET',
    url: 'permission/list',
    params
  })
}
