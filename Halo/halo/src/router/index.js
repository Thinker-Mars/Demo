import Vue from 'vue'
import Router from 'vue-router'
import home from '@/views/home/home'
import bookinfo from '@/views/bookMetaMange/bookinfo'
import audit from '@/views/audit/audit'
import weekDataActivity from '@/views/charts/weekDataActivity'
import bookproperty from '@/views/charts/property'
import myapply from '@/views/myApply/myApply'
import excel from '@/views/excel/excel'
import del from '@/views/audit/del'
import uppdate from '@/views/audit/uppdate'
import updateExcel from '@/views/audit/updateExcel'
import dataCollect from '@/views/collect/dataCollect'
 import login from '@/views/login/index'




Vue.use(Router)
let defaultRouter = [
  {
    path: '/login',
    component: login,
    name: '登陆',
  },
  {
    path: '/index',
    iconCls: 'fa fa-dashboard', // 图标样式class
    name: "主页",
    component: home,
    children:[
      {
        path: "/index/bookinfo",
        name: '图书信息',
        component: bookinfo
      },
      {
        path: "/index/audit",
        name: '图书新增审核',
        component: audit
      },
      {
        path: "/index/del",
        name: '图书删除审核',
        component: del
      },
      {
        path: "/index/uppdate",
        name: '图书变更审核',
        component: uppdate
      },
      {
        path: "/index/updateExcel",
        name: 'Excel导入审核',
        component: updateExcel
      },
      {
        path: '/index/weekdata',
        name: '周数据活跃度',
        component: weekDataActivity,
        
      },{
        path: '/index/myapply',
        component: myapply,
        name: '我的申请'
      },{
        path: '/index/excel',
        component: excel,
        name: 'excel导入'
      },{
        path: '/index/property',
        component: bookproperty,
        name: '资产总览'
      },{
        path: '/index/dataCol',
        component: dataCollect,
        name: '元数据采集'
      }
    ]
  }
]

export default new Router({
  routes: defaultRouter
})
