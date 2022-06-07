import request from '@/utils/request'
import { ICategory } from './types/category'
import { IPage } from './types/common'

export const getCategoryList = (params: any) => {
  return request<IPage<ICategory>>({
    method: 'GET',
    url: 'category/list',
    params
  })
}

export const updateCategory = (params: any) => {
  return request({
    method: 'PUT',
    url: 'category/update',
    params
  })
}

export const saveCategory = (params: any) => {
  return request({
    method: 'POST',
    url: 'category/save',
    params
  })
}
