import request from '@/utils/request'
import { IPage } from './types/common'
import { IStock, IWare } from './types/ware'

export const getWareList = (params: any) => {
  return request<IPage<IWare>>({
    method: 'GET',
    url: 'ware/list',
    params
  })
}

export const saveWare = (params: any) => {
  return request({
    method: 'POST',
    url: 'ware/save',
    params
  })
}

export const updateWare = (params: any) => {
  return request({
    method: 'PUT',
    url: 'ware/update',
    params
  })
}

export const getStockList = (params: any) => {
  return request<IPage<IStock>>({
    method: 'GET',
    url: 'waregoods/list',
    params
  })
}

export const updateStock = (params: any) => {
  return request({
    method: 'PUT',
    url: 'waregoods/update',
    params
  })
}

export const saveStock = (params: any) => {
  return request({
    method: 'POST',
    url: 'waregoods/save',
    params
  })
}
