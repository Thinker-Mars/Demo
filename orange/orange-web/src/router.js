import Vue from 'vue'
import VueRouter  from 'vue-router'

Vue.use(VueRouter)

const routers = [
  {
    path: '',
    redirect: '/login'
  },
  {
    path: '/login',
    meta: {
      title: "orange"
    },
    component: resolve => require(["./views/login.vue"], resolve)
  },
  {
    path: '/home',
    meta: {
      title: "home"
    },
    component: resolve => require(["./views/pc/home.vue"], resolve),
    children: [
      {
        path: 'news',
        name: 'news',
        component: resolve => require(["./views/pc/home/news.vue"], resolve)
      }
    
    ]
  }

]

const RouterConfig = {
  mode: "history",
  routes: routers
}

const router = new VueRouter(RouterConfig);

export default router;
