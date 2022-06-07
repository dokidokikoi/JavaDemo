import { IPermission } from './permission'

export interface IRole {
  id: string
  roleName: string
  roleDesc: string
  permission: IPermission
}
