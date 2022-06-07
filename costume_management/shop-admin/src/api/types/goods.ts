import { ICategory } from './category'
import { IWare } from './ware'

export interface IGoods {
  id: string
  name: string
  cover: string
  price: number
  createDate: string
  creatorName: string
  category: ICategory
}

export interface IWareGoods {
  id: string
  amount: number
  creatDate: string
  updateDate: string
  ware: IWare
  goods: IGoods[]
}

export interface IGoodsParams {
  key: string
  categoryId: string,
  page: number,
  limit: number,
}
