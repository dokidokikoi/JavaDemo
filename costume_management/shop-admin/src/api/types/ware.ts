import { IGoods } from './goods'

export interface IWare {
  id: string
  address: string
  createDate: string
  manager: string
}

export interface IStock {
  id: string,
  amount: number,
  createDate: string,
  ware: IWare,
  goods: IGoods
}
