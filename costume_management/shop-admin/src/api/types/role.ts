import { IPermission } from './permission'

export interface IRole {
  id: string
  roleName: string
  roleDesc: string
}

export interface IRoleVo {
  id: string
  roleName: string
  roleDesc: string
  permissions: IPermission[]
}

export interface IRoleParam {
  id: string
  roleName: string
  roleDesc: string
  permission: string[]
}
