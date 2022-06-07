import request from '@/utils/request'
import { IGoodsParams } from './types/goods'

export const getGoodsList = (params: IGoodsParams) => {
  return request({
    method: 'GET',
    url: 'goods/list',
    params
  })
}

export const saveGoods = (params: any) => {
  return request({
    method: 'POST',
    url: 'goods/save',
    params
  })
}

export const updateGoods = (params: any) => {
  return request({
    method: 'PUT',
    url: 'goods/update',
    params
  })
}

export const updateGoodsState = (params: any) => {
  return request({
    method: 'POST',
    url: 'goods/set_state',
    params
  })
}
