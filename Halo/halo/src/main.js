// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

import router from './router'
import ElementUI from 'element-ui'
import echarts from 'echarts'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import qs from 'qs'
import store from './store/store'
import App from './App'



Vue.config.productionTip = false

Vue.use(ElementUI)

Vue.prototype.$echarts = echarts
Vue.prototype.$axios = axios
Vue.prototype.qs = qs

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
