import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { store, key } from './store'
import './styles/index.scss'
import elementPlus from './plugins/element-plus'
import 'virtual:svg-icons-register'
import svgIcon from './components/SvgIcon.vue' // 全局svg图标组件

createApp(App)
  .use(store, key)
  .use(router)
  .use(elementPlus)
  .component('svg-icon', svgIcon)
  .mount('#app')
