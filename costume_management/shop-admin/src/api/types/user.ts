import { IPermission } from './permission'
import { IRole } from './role'

export interface IUser {
  id: string
  account: string
  createDate: string
  nickname: string
  password: string
  state: number
}

export interface IUserVo {
  id: string
  account: string
  createDate: string
  nickname: string
  state: number
  roles: IRole[]
  permissions: IPermission[]
}
