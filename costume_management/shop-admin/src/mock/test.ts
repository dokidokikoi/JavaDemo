import { MockMethod } from 'vite-plugin-mock'
// const fs = require('fs')
// const path = require('path')
// const JSON5 = require('json5')

// const info = fs.readFileSync(path.join(__dirname, 'api/login/info.json5'), 'utf-8')

export default [
  {
    url: '/login/info', // 注意，这里只能是string格式
    method: 'get',
    response: () => {
      return 'sdsada'
    }
  }
] as MockMethod[] // 这里其实就是定义数据格式的，不了解的同学可以参考typescript的官方文档
