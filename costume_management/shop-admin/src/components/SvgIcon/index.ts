import { App } from 'vue'
import Component from './index.vue'
import 'virtual:svg-icons-register'

export default {
  install (app: App) {
    app.component('SvgIcon', Component)
  }
}
