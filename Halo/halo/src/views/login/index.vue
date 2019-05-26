<template>
  <div>
     <div class="video-container">
      <video :style="fixStyle" autoplay loop  v-on:canplay="canplay">
        <source src="@/videos/Sunset-Desert-Run.mp4" type="video/mp4"/>
  
      </video> 
        <div class="loginCon">
          <el-card shadow="hover" class="box-card" >
            <div slot="header" class="clearfix">
              <span>登录</span>
            </div>
            <el-form :model="loginForm" status-icon  class="demo-ruleForm">
              <el-form-item>
                <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入登录账号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-input type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入登录密码"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button class="subBtn" type="primary" @click="submitForm">登录</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
      <div class="poster hidden" v-if="!vedioCanPlay">
        <img :style="fixStyle" src="@/videos/Sunset-Desert-Run.jpg" alt="">
      </div>
     </div> 
  </div>
</template>


<script>
export default {
  name: 'App',
    data() {
      return {
        vedioCanPlay: false,
        fixStyle: '',
        loginForm: {
          username: '',
          password: ''
        }
      }
    },
    methods: {
      canplay() {
        this.vedioCanPlay = true
      },
      submitForm: function() {
          let that = this
          if (this.loginForm.username === '' || this.loginForm.password === '') {
            this.$message({
              showClose: true,
              message: '账号或密码不能为空',
              type: 'error'
            })
            return false
          } else {
            this.$axios.post("http://localhost:8088/userlogin/login",{loginForm: this.loginForm})
            .then((response) => {
              if (response.data.flag == '00') {
                  this.$message({
                    showClose: true,
                    message: '账号或密码错误!',
                    type: 'error'
                  })
                  return false
              }
              else {
                this.$store.state.id = response.data.id;
                this.$store.state.name = response.data.name;
                this.$store.state.role = response.data.role;
                this.$store.state.islogin = true;
                //登陆信息存入sessionStorage,防止刷新时状态丢失
                sessionStorage.setItem("id", response.data.id);
                sessionStorage.setItem("name", response.data.name);
                sessionStorage.setItem("role", response.data.role);
                sessionStorage.setItem("isLogin", true);
                sessionStorage.setItem("token", response.data.token);
                that.$router.push({path: '/index/weekdata'})
              }

            })
              
          }
        },
        message: function() {
          const h = this.$createElement;
          this.$notify({
            title: 'Welcome!',
            message: h('i', { style: 'color: teal'}, ''),
            duration: 2500
          });
        }
    },
    mounted: function() {
      window.onresize = () => {
        const windowWidth = document.body.clientWidth
        const windowHeight = document.body.clientHeight
        const windowAspectRatio = windowHeight / windowWidth
        let videoWidth
        let videoHeight
        if (windowAspectRatio < 0.5625) {
          videoWidth = windowWidth
          videoHeight = videoWidth * 0.5625
          this.fixStyle = {
            height: windowWidth * 0.5625 + 'px',
            width: windowWidth + 'px',
            'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
            'margin-left': 'initial'
          }
        } else {
          videoHeight = windowHeight
          videoWidth = videoHeight / 0.5625
          this.fixStyle = {
            height: windowHeight + 'px',
            width: windowHeight / 0.5625 + 'px',
            'margin-left': (windowWidth - videoWidth) / 2 + 'px',
            'margin-bottom': 'initial'
          }
        }
      }
      window.onresize()

  },
  created() {
    this.vedioCanPlay = true;
    this.message();

  }
  
}
</script>

<style scoped>


  .video-container {
    position: relative;
    height: 100vh;

  }
  .login-box {
    height: 300px;
    width: 500px;
    
  }

  .video-container .poster img,
  .video-container video {
    z-index: 0;
    position: absolute;
  }

  .video-container .filter {
    z-index: 1;
  
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  /* .clearfix:after {
    clear: both
  } */
  .box-card {
    width: 480px;
    
  }
  .el-input__inner {
    width: 80px;
    height: 20px

  }
    .text {
    text-align:center;
  }
  .el-card {
    color: #00b7ff;
    margin: auto;
    width: 50%;
    padding: 50px
  }
  .my-input {
    position: absolute;
    width: 200px;
    height: 30px
  }
  .loginCon {
    position: absolute;
    height: 100px;
    width: 500px;
    right: 0px;
    margin-top: 150px;
    opacity: 0.85;
  }
  
   .box-card {
    width: 230px;
    height: 230%;
  }
        
</style>

  



