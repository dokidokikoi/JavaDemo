export interface IPermission {
  id: string
  path: string
  name: string
}

export interface IPermissionTree {
  id: string
  name: string
  children: IPermissionTree[]
}
