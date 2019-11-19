import axios from 'axios';
import QS from 'qs';
import router from '../router';
import { Notice } from 'iview';
/**
 * 必须要引入这个文件，并通过store.state.token获取到token值
 * 无法通过this.$store.getters.getToken获取
 * 估计和js文件加载顺序有关
 * 现在是早上4:40，不然我早就睡觉了
 * 引以为戒
 */
import store from '../store/index'


/**
 * 设置post请求头
 */
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';


/**
 * 请求超时时间(10s)
 */
axios.defaults.timeout = 1000 * 10;


/**
 * 根据环境变量配置设置默认的接口url前缀
 * development：本地开发模式
 * production：生产模式
 */
if (process.env.NODE_ENV == 'development') {    
    axios.defaults.baseURL = 'http://localhost:8090';}  
else if (process.env.NODE_ENV == 'production') {    
    axios.defaults.baseURL = 'https://www.cnblogs.com/cone/';
}


/**
 * 拦截状态码说明：
 * 1、401
 * 未登录，跳转到登录页，登陆成功后返回要访问的页面
 * 2、403
 * token过期，清除本地token，其余逻辑同上
 * 3、404
 * 资源不存在
 * 4、其他状态码
 * 直接抛出错误原因
 */

 /**
  * 请求拦截器
  * 每次发送请求前，先判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
  * 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
  */
 axios.interceptors.request.use(
    config => {
        const token = store.state.token;
        token && (config.headers.Authorization = token);
        return config;
    },
    error => {
        return Promise.error(error);
    }
);


/**
 * 响应拦截器
 */
axios.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },

    /**
     * 状态码不是200的处理
     */
    error => {
        
        if (error.response) {
            switch (error.response.status) {

                case 401:
                    router.replace({
                        path: '/login',
                        query: { redirect: router.currentRoute.fullPath }
                    });
                    break;

                case 403:
                    Notice.warning({
                        title: '过期啦！',
                        desc: '身份验证过期，即将重新登陆'
                    });
                    //清除token
                    localStorage.removeItem('token');
                    this.$store.dispatch('clearToken');

                    setTimeout(() => {
                        router.replace({
                            path: '/login',
                            query: { redirect: router.currentRoute.fullPath }
                        });
                    }, 1000);
                    break;

                case 404:
                    Notice.error({
                        title: '出错啦！',
                        desc: '本星球找不到你想要的，建议你去别的星球逛逛~'
                    });
                    break;
                    
                default:
                    Notice.error({
                        title: '出错啦！',
                        desc: error.response.data.message
                    });

            }
            return Promise.reject(error.response);

        }
    }

);


export default {
    /**
     * 封装的get方法
     * @param {String} url 请求地址
     * @param {Object} params 请求参数
     */
    get(url, params) {
        // debugger;
        // console.log(params)
        return new Promise((resolve, reject) => {
            axios.get(url, {
                params: params
            })
            .then(res => {
                
                resolve(res.data);
            })
            .catch(error => {
                
                reject(error);
            })
        });
    },

    /**
     * 封装的post方法
     * @param {String} url 
     * @param {*} params 
     */
    post(url, params) {
        
        return new Promise((resolve, reject) => {
            axios.post(url, QS.stringify(params))
            .then(res => {
                
                resolve(res.data);
            })
            .catch(error => {
                
                reject(error);
            })
        });
    }
}



