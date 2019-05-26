import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state={//当前登录用户的员工id，姓名，职位（即role字段），登陆状态
    id: '',
    name: "",
    role: "",
    islogin: false,
    token: ""
};
const getters={
    getId() {
        return state.id
    },
    getName() {
        return state.name
    },
    getRole() {
        return state.role
    },
    getLoginState() {
        return state.islogin
    }

};
const mutations={//注销登陆则清空所有的状态
    clear(state) {
        state.id = '';
        state.name = "";
        state.role = "";
        state.islogin = false,
        state.token = ""
    },
    setName(state, nameValue) {
        state.name = nameValue;
    }
};
const actions={
    logout(context) {
        context.commit('clear');
    },
    setNewName(context, nameValue) {
        context.commit('setName', nameValue);

    }

}

const store = new Vuex.Store({
    state,
    getters,
    mutations,
    actions
 });

//创建vuex中的store对象
export default store;