import Vue from 'vue'
import App from './App'//路由挂载
import router from './router'
import store from './store'
import iView from 'iview'
import http from './request/http'
import 'iview/dist/styles/iview.css'

Vue.use(iView);

Vue.config.productionTip = false

/**
 * 加载进度条
 */
router.beforeEach((to, from, next) => {
  iView.LoadingBar.start();
  next();
});

router.afterEach(route => {
  iView.LoadingBar.finish();
});


/**
 * notice通知全局配置
 */
Vue.prototype.$Notice.config({
  top: 50,
  duration: 4.5
})

/**
 * 把axios挂载到Vue原型上
 */
Vue.prototype.http=http


/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");

// new Vue({
//   el: '#app',
//   router,
//   components: { App },
//   template: '<App/>'
// })
