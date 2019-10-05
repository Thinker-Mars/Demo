import Vuex from 'vuex';
import Vue from 'vue';
Vue.use(Vuex);

/**
 * 定义全局可用的变量
 */
const state = {
    token : '',
    loginFlag: false
};

/**
 * 取得state中变量的值
 */
const getters = {

    /**
     * 取得token值
     */
    getToken() {
        return state.token;
    },

    /**
     * 取得登录状态flag
     */
    getLoginFlag() {
        return state.loginFlag;
    }
};

/**
 * 修改state中变量的值
 * mutations里面的方法都是同步事务
 */
const mutations = {
    /**
     * 设置token
     * @param {String} str 
     */
    setToken(state, str) {
        state.token = str;
    },

    /**
     * 清空token
     * @param {*} state 
     */
    clearToken(state) {
        state.token = null;
    },

    /**
     * 更改登录状态
     * @param {*} state 
     * @param {boolean} flag 
     */
    changeLoginFlag(state, flag) {
        state.loginFlag = flag;
    }
};

/**
 * 异步触发mutations里面的方法
 * 调用方法：this.$store.dispatch('setToken'，your token)
 */
const actions = {
    /**
     * 异步修改token
     * @param {*} context 
     * @param {*} str 
     */
    setToken(context, str) {
        context.commit('setToken', str);
    },

    /**
     * 异步清空token
     * @param {*} context 
     */
    clearToken(context) {
        context.commit('clearToken');
    },

    /**
     * 异步修改登录状态
     * @param {*} context 
     * @param {boolean} flag 
     */
    changeLoginFlag(context, flag) {
        context.commit('changeLoginFlag', flag);
    }
};

const store = new Vuex.Store({
    state,
    getters,
    mutations,
    actions
})

export default store;
