import { RouteRecordRaw, RouterView } from 'vue-router'

const routes: RouteRecordRaw = {
  path: 'media',
  name: 'media',
  meta: {
    title: '媒体'
  },
  component: RouterView,
  children: [
    {
      path: '',
      component: () => import('@/views/media/index.vue')
    }
  ]
}

export default routes
