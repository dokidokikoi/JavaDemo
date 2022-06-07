import { RouteRecordRaw, RouterView } from 'vue-router'
const routes: RouteRecordRaw = {
  path: 'ware',
  component: RouterView,
  meta: {
    title: '库存'
  },
  children: [
    {
      path: 'ware_list',
      name: 'ware_list',
      meta: {
        title: '仓库列表'
      },
      component: () => import('@/views/ware/ware/index.vue')
    },
    {
      path: 'stock',
      name: 'stock',
      meta: {
        title: '库存列表'
      },
      component: () => import('@/views/ware/stock/index.vue')
    }
  ]
}

export default routes
