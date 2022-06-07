import { IPermission } from './permission'
import { IRole } from './role'

export interface ILoginInfo {
  logo_square: string
  logo_rectangle: string
  login_logo: string
  slide: string[]
}

export interface ICaptcha {
  imgCode: string
  key: string
  msg: string
}

export interface IUserInfo {
  id: string
  username: string
  nickname: string
  createDate: string
  state: string
  permissions: IPermission[]
  roles: IRole[]
}

export interface IPage<T> {
  currPage: number
  list: T[]
  pageSize: number
  totalCount: number
  totalPage: number
}
