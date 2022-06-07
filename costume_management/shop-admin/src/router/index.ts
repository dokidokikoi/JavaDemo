import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import AppLayout from '@/layout/AppLayout.vue'
import productRoute from './modules/product'
import permisssionRoute from './modules/permission'
import mediaRoute from './modules/media'
import wareRoute from './modules/ware'
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import { store } from '@/store'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: AppLayout,
    meta: {
      requiresAuth: true
    },
    children: [
      {
        path: '', // 默认子路由
        name: 'home',
        meta: {
          title: '首页'
        },
        component: () => import('@/views/home/index.vue')
      },
      productRoute,
      permisssionRoute,
      mediaRoute,
      wareRoute
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login/index.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(), // 路由模式
  routes // 路由规则
})

router.beforeEach((to, from) => {
  // 开始加载进度条
  nprogress.start()
  if (to.meta.requiresAuth && !store.state.user) {
    // 如果跳转到的页面需要授权，且未登录，重定向到登录页面
    return {
      path: '/login',
      // 保存我们所在的位置，以便登录后跳转
      query: { redirect: to.fullPath }
    }
  }
})

router.afterEach(() => {
  // 加载完毕
  nprogress.done()
})

export default router
