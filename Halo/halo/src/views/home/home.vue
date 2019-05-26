<template>
<div  style="margin-top:-45px; overflow:-Scroll;overflow-y:hidden">
<el-container style="height: 701px; border: 1px solid #eee ">
  <el-aside  style="background-color: rgb(238, 241, 246)" id="asideNav">
    <el-menu :default-openeds="['1', '3']">
      <el-submenu index="1">
        <template slot="title"><i class="el-icon-message"></i>数据资产</template>
        <el-menu-item index="1-1" @click="bookWeekData">周数据活跃度</el-menu-item>
        <el-menu-item index="1-2" @click="property">资产总览</el-menu-item>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title"><i class="el-icon-menu"></i>元数据管理</template>
          <el-menu-item index="2-1" @click="bookinfo">图书信息管理</el-menu-item>
          <el-menu-item index="2-2" @click="collect">数据采集</el-menu-item>
          <el-menu-item index="2-3" @click="excel">Excel导入</el-menu-item>
      </el-submenu>
      <el-submenu index="3" v-show="this.show =='管理员'">
        <template slot="title"><i class="el-icon-menu"></i>待办任务</template>
        <el-menu-item index="3-1" @click="audit">新增图书信息审核</el-menu-item>
        <el-menu-item index="3-2" @click="del">删除图书信息审核</el-menu-item>
        <el-menu-item index="3-3" @click="uppdate">变更图书信息审核</el-menu-item>
        <el-menu-item index="3-4" @click="uppdateExcel">Excel导入审核</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title"><i class="el-icon-menu"></i>我的申请</template>
        <el-menu-item index="4-1" @click="myapply">我的申请</el-menu-item>
      </el-submenu>
    </el-menu>
  </el-aside>
  
  <el-container>
    <el-header style="text-align: right; font-size: 18px;margin-top: 30px">
      <!-- <el-submenu index="4">
        <template slot="title"><i class="el-icon-setting" style="margin-right: 15px"></i></i><span>{{this.$store.getters.getName}}</span></template>
        <el-menu-item index="4-1" @click="logout">退出</el-menu-item>

      </el-submenu> -->
      <el-dropdown @command="handleCommand">
        <i class="el-icon-setting" style="margin-right: 15px; margin-top: 45px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>{{this.$store.getters.getName}}</span>
    </el-header>
    
    <el-main id="elmain">
      <router-view> </router-view>
    </el-main>
  </el-container>

</el-container>
</div> 
</template>

<script>
  export default {
    data() {
      return {
        show: ""
        
      }
    },
    methods: {
      bookWeekData: function() {
        this.$router.push("/index/weekdata");
      },
      bookinfo: function() {
        this.$router.push("/index/bookinfo")
      },
      audit: function() {
        this.$router.push("/index/audit")
      },
      myapply: function() {
        this.$router.push("/index/myapply")
      },
      excel: function() {
        this.$router.push("/index/excel")
      },
      property: function() {
        this.$router.push("/index/property")
      },
      del: function() {
        this.$router.push("/index/del")
      },
      uppdate: function() {
        this.$router.push("/index/uppdate")
      },
      uppdateExcel: function() {
        this.$router.push("/index/updateExcel")
      },
      collect: function() {
        this.$router.push("/index/dataCol")
      },
      handleCommand(command) {
        this.$confirm('确定退出系统?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {

            this.$router.push("/login");
            sessionStorage.clear();//清空所有缓存

          })

      }
    },
    created() {
      this.show = sessionStorage.getItem("role");
    }
  };
</script>

<style lang="scss">
  * {
    margin: 0px;
    padding: 0px;
  }

  body {
    background-color: #f2f2f2;
    font-size: 14px;
    color: #333333;
  }

  li {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  $top: top;
  $bottom: bottom;
  $left: left;
  $right: right;
  $leftright: ($left, $right);
  %w100 {
    width: 100%;
  }

  %h100 {
    height: 140%;
  }

  %cursor {
    cursor: pointer;
  }

   body, #loyout, .el-container,  ul.el-menu {
    @extend %h100;
  }

  @mixin set-value($side, $value) {
    @each $prop in $leftright {
      #{$side}-#{$prop}: $value;
    }
  }

  #elmain {
    background-color: #f0f2f5;
  }

  
#asideNav {
    width: auto !important;
    display: flex;
    flex-direction: column;
    border-right: solid 1px #e6e6e6;
    height: auto;
    margin-top: 50px;
    .logo-name {
      color: rgba(255, 255, 255, .8);
      background-color: #03152A !important;
      @extend %w100;
      font-weight: 300;
      z-index: 999;
      p {
        height: 50px;
        line-height: 50px;
        text-align: center;
        font-size: 16px;
      }
    }
  }

</style>
