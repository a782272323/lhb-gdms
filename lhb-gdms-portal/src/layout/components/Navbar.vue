<template>
  <el-row class="navbar">
    <el-col :span="4">
      <!-- logo区域 -->
      <div class="logo">
        <img class="logo-img" src="../../img/Blog02.png" @click="linkToHome"/>
        <p style="float: right" @click="linkToHome">Bin 博客平台</p>
      </div>
    </el-col>
    <el-col :span="14">
      <!-- 导航栏功能模块区域 -->
      <div class="switch">
        <div style="float: left">
          <el-menu
            class="switch-menu"
            mode="horizontal"
            :default-active="this.$route.path"
            active-text-color="#2ECC71"
            >
            <el-menu-item index="/home" @click="linkToHome">
              <i class="el-icon-s-home"></i> 首页
            </el-menu-item>
            <el-menu-item index="/blog" @click="linkToBlog">
              <div v-if="this.$route.path === '/blog' ">
                <svg-icon icon-class="Blog03-#2ECC71"></svg-icon> 我的主页
              </div>
              <div v-if="this.$route.path != '/blog' ">
                <svg-icon icon-class="Blog03"></svg-icon> 我的主页
              </div>
            </el-menu-item>
            <el-menu-item index="/message" @click="linkToMessage">
              <i class="el-icon-bell"></i> 消息
            </el-menu-item>
          </el-menu>
        </div>
        <div class="switch-input">
          <el-input
            placeholder="探索Bin博客(文章/标签/用户)"
            clearable
            >
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
        </div>
        <!-- 个人中心区域 -->
        <!--    <div class="person-center" >-->
        <div class="right-menu" >
<!--          <el-button  class="right-menu-button" type="success" plain round>写文章</el-button>-->
          <el-button class="right-menu-button" type="success" plain round @click="linkToArticle">写文章</el-button>
          <!-- 未登录 -->
          <div v-if="!login" style="float:left;">
            <el-button class="right-menu-button" @click="linkToLogin" type="success" plain round>登录</el-button>
            <el-button class="right-menu-button" @click="linkToRegistered" type="success" plain round>注册</el-button>
          </div>
          <!-- 已经登录 -->
          <el-dropdown v-if="login" class="avatar-container right-menu-item hover-effect" slot="dropdown">
            <!-- 头像 -->
            <div class="avatar-wrapper">
<!--              <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">-->
              <img :src="this.imageUrl+'?imageView2/1/w/80/h/80'" class="user-avatar">
              <span style="float: right;margin-left: 10px;margin-top: -5px;color: #2ECC71">个人中心
              </span>
            </div>
            <el-dropdown-menu slot="dropdown">
              <router-link to="/">
                <el-dropdown-item><i class="el-icon-s-home"></i>首页</el-dropdown-item>
              </router-link>
              <router-link to="/blog">
                <el-dropdown-item><svg-icon icon-class="Blog03"></svg-icon> 我的主页</el-dropdown-item>
              </router-link>
              <router-link to="/message">
                <el-dropdown-item><i class="el-icon-bell"></i>消息</el-dropdown-item>
              </router-link>
              <router-link to="/article">
                <el-dropdown-item divided><i class="el-icon-edit"></i>写文章</el-dropdown-item>
              </router-link>
              <router-link to="/caoGao">
                <el-dropdown-item><svg-icon icon-class="caoGao01"></svg-icon> 草稿箱</el-dropdown-item>
              </router-link>
              <router-link to="/focus">
                <el-dropdown-item><svg-icon icon-class="focus00"></svg-icon> 关注</el-dropdown-item>
              </router-link>
              <router-link to="/collection">
                <el-dropdown-item><svg-icon icon-class="collection00"></svg-icon> 收藏集</el-dropdown-item>
              </router-link>
              <router-link to="/labelFocus">
                <el-dropdown-item><svg-icon icon-class="label00"></svg-icon> 标签管理</el-dropdown-item>
              </router-link>
              <router-link to="/profile/index">
                <el-dropdown-item divided><svg-icon icon-class="system00"></svg-icon> 设置</el-dropdown-item>
              </router-link>
              <el-dropdown-item @click.native="logout">
                <span style="display:block;"><svg-icon icon-class="logout00"></svg-icon> 注销</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-col>
<!--    <el-col :span="5">-->

<!--    </el-col>-->
  </el-row>
</template>

<script>
import { mapGetters } from 'vuex'
// eslint-disable-next-line no-unused-vars
import { getToken } from '@/utils/auth' // get token from cookie
// import '@/src/icons/my/Blog2.svg'
import { getUserDetails } from '@/api/system'

export default {
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ])
  },
  data() {
    return {
      login: '',
      // 当前激活菜单
      activeIndex: '1',
      imageUrl: '',
      userList: []
    }
  },
  created() {
    this.isLogin()
    // 如果已经登录系统则使用刷新详情的数据
    if (this.login) {
      this.refreshPage()
    }
  },
  methods: {
    refreshPage() {
      getUserDetails().then(res => {
        this.userList = res.data.getList
        this.imageUrl = res.data.getList.sysUserIcon
      })
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    isLogin() {
      const hashToken = getToken()
      if (hashToken) {
        this.login = true
      } else {
        console.log('未登录')
        this.login = false
      }
    },
    // 跳转到我的主页
    linkToBlog() {
      console.log('我的主页 activeIndex = ' + this.activeIndex)
      this.activeIndex = '2'
      this.$router.push({ path: '/blog' })
    },
    // 跳转到首页
    linkToHome() {
      console.log('首页 activeIndex = ' + this.activeIndex)
      this.activeIndex = '1'
      this.$router.push({ path: '/home' })
    },
    // 跳转到消息
    linkToMessage() {
      console.log('消息 activeIndex = ' + this.$route.path)
      this.activeIndex = '3'
      this.$router.push({ path: '/message' })
    },
    // 跳转到登录
    linkToLogin() {
      this.$router.push({ path: '/login' })
    },
    // 跳转到注册
    linkToRegistered() {
      this.$router.push({ path: '/registered' })
    },
    // 跳转到写文章页面
    linkToArticle() {
      this.$router.push({ path: '/article' })
    }
  }
}
</script>

<style lang="scss" scoped>
  .navbar {
    height: 70px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);

    .logo {
      float: right;
      width: 150px;
      height: 50px;
      margin-top: 15px;
      margin-right: 10px;
      font-weight: bold;
      color: #2ECC71;
      /*background-color: #623615;*/

      .logo-img {
        width: 40px;
        height: 40px;
        margin-right: 10px;
      }
    }

    .switch {
      margin-top: 10px;
      /*background-color: #C03639;*/
      width: 980px;
      height: 60px;

      .switch-menu {
        /*background-color: #ECF0F1;*/
        margin-right: 20px;
        max-width: 330px;
      }

      .switch-input {
        float: left;
        margin-top: 13px;
        width: 250px;
      }

    }

    .right-menu {
      margin-left: 60px;
      float: left;
      height: 70px;
      width: auto;
      line-height: 50px;
      /*background-color: #2c3e50;*/

      &:focus {
        outline: none;
      }

      .right-menu-button {
        float: left;
        margin-top: 15px;
        margin-left: 15px;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          float: right;
          margin-top: 15px;
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }

  /*修改下拉框*/
  .el-dropdown-menu__item:not(.is-disabled):hover {
    background-color: #E3F9ED !important;
    color: #333333 !important;
  }

  /*修改输入框*/
  /deep/.el-input__inner {
    background-color: transparent !important;
    background-image: none;
    border: 1px solid #2ECC71;
  }

</style>
