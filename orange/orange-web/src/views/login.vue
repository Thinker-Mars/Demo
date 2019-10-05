<template>
    <div class="index">
        <div class="index_pc_bj">
            <i-form ref="loginForm" :model="loginForm" :rules="ruleLogin">
                <div class="wrap_conter">
                    <ul style="list-style: none;box-shadow:10px 10px 20px rgba(0,0,0,.5);">
                        <li style="border-bottom: 1px solid #e9eaec;">
                            <div class="content">
                                
                                <span style="float:right;font-size: 15px"><Icon type="ios-log-in"></Icon>欢迎登录</span>
                            </div>
                        </li>
                        <li>
                            <dl>
                                <Form-item prop="email">
                                    <i-input type="text" v-model="loginForm.email" placeholder="Email">
                                        <Icon type="ios-person-outline" slot="prepend"></Icon>
                                    </i-input>
                                </Form-item>
                                <Form-item prop="password">
                                    <i-input type="password" v-model="loginForm.password" placeholder="Password">
                                        <Icon type="ios-locked-outline" slot="prepend"></Icon>
                                    </i-input>
                                </Form-item>
                                <Form-item>
                                    <i-button type="primary" @click="handleSubmit('loginForm')">登录</i-button>
                                </Form-item>
                            </dl>
                        </li>
                    </ul>
                </div>
            </i-form>
        </div>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                loginForm: {
                    email: '',
                    password: ''
                },
                ruleLogin: {
                    email: [
                        { required: true, message: '请填写邮箱', trigger: 'change' }
                    ],
                    password: [
                        { required: true, message: '请填写密码', trigger: 'blur' },
                        { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'change' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.http.post('/login', this.loginForm)
                        .then((res) => {
                            if (res.flag) {
                                this.$Message.success('登录成功!');
                                this.$store.dispatch('setToken', res.data.token);
                                this.$store.dispatch('changeLoginFlag', true);
                                this.$router.push({path: '/home'});
                            } else {
                                this.$Message.error(res.info);
                            }
                        });
                    } else {
                        return;
                    }
                })
            }
        }
    }
</script>


<style scoped>

.index {
  align-items: center;
  display: flex;
  width: 100%;
  min-width: 300px;
  min-height: 300px;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  text-align: center;
  /*background: rebeccapurple;*/
  background-image: url(../images/login.jpg);
}

#index_pc_bj {
  width: 100%;
  height: auto;
  background-size: cover;
  overflow: hidden;
  background-position: center center; /*box-shadow: 0 0px 3px rgba(0,0,0,.5);*/
  text-align: center;
}

.wrap_conter ul {
  position: relative;
  width: 300px;
  border-radius: 5px;
  background: #fff;
  margin: 0 auto;
}
.wrap_conter li {
  text-align: center;
  color: #fff;
  font-size: 12px;
  line-height: 30px;
  padding: 0 25px 0px 25px;
  width: 100%;
}

.content {
  color: #1c2438;
  line-height: 40px;
  display: block;
  text-align: left;
  padding: 5px 0 0 0;
  margin: 0 80px 0 20px;
}

</style>