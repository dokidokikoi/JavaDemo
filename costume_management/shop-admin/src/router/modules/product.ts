import { RouteRecordRaw, RouterView } from 'vue-router'

const routes: RouteRecordRaw = {
  path: 'product',
  component: RouterView,
  meta: {
    title: '商品'
  },
  children: [
    {
      path: 'product_list',
      name: 'product_list',
      meta: {
        title: '商品列表'
      },
      component: () => import('@/views/product/list/index.vue')
    },
    {
      path: 'product_classify',
      name: 'product_classify',
      meta: {
        title: '商品分类'
      },
      component: () => import('@/views/product/classify/index.vue')
    }
  ]
}

export default routes
