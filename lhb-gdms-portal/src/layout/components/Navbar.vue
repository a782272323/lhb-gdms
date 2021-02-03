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
<!--            <el-menu-item v-if="login === true" index="/blog" @click="linkToBlog">-->
<!--              <div v-if="this.$route.path === '/blog'">-->
<!--                <svg-icon icon-class="Blog03-#2ECC71"></svg-icon> 我的主页-->
<!--              </div>-->
<!--              <div v-if="this.$route.path !== '/blog' ">-->
<!--                <svg-icon icon-class="Blog03"></svg-icon> 我的主页-->
<!--              </div>-->
<!--            </el-menu-item>-->
            <el-menu-item v-if="login === true" index="/message" @click="linkToMessage">
              <i class="el-icon-bell"></i> 消息
            </el-menu-item>
          </el-menu>
        </div>
        <div class="switch-input">
          <el-popover
            width="300"
            v-if="isInputHistory === true"
            placement="bottom"
            trigger="click"
            >
            <div
              v-for="(item, index) in inputHistoryLists"
              :key="index"
              >
              <el-card style="width: 270px;height: 50px; float: left;margin-bottom: 10px;" shadow="hover">
                <el-row :gutter="24">
                  <el-col :span="2">
                    <svg-icon
                      style="float: left;height: 30px;width: 30px;margin-top: -10px;margin-left: -15px;"
                      icon-class="history00">
                    </svg-icon>
                  </el-col>
                  <el-col :span="16">
                    <el-link
                      style="float: left;margin-top: -25px;margin-left: 10px;color: black;"
                      type="info"
                      :underline="false"
                      @click="linkToHomeSearch(item)"
                      >
                      <h2>{{ item.sysInputHistoryContent }}</h2>
                    </el-link>
                  </el-col>
                  <el-col :span="2" :offset="1">
                    <el-button
                      style="float: left;margin-top: -10px;margin-bottom: 20px;"
                      size="mini"
                      type="danger"
                      icon="el-icon-delete"
                      circle
                      plain
                      @click="removeInputHistory(item)">
                    </el-button>
                  </el-col>
                </el-row>
              </el-card>
            </div>
            <el-input
              slot="reference"
              style="width: 250px;"
              placeholder="探索Bin博客(文章/标签/用户)"
              v-model.trim="searchContent"
              @keyup.enter.native="linkToHomeSort"
              clearable
            >
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </el-popover>
          <el-input
            slot="reference"
            v-if="isInputHistory === false"
            style="width: 250px;"
            placeholder="探索Bin博客(文章/标签/用户)"
            v-model.trim="searchContent"
            @keyup.enter.native="linkToHomeSort"
            clearable
          >
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
          <el-button
            type="success"
            icon="el-icon-search"
            plain
            circle
            @click="linkToHomeSort"
            :disabled="searchContent === ''"
            ></el-button>
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
              <router-link to="/message">
                <el-dropdown-item><i class="el-icon-bell"></i>消息</el-dropdown-item>
              </router-link>
<!--              <div @click="linkToBlog">-->
<!--                <el-dropdown-item divided><svg-icon icon-class="Blog03"></svg-icon> 我的主页</el-dropdown-item>-->
<!--              </div>-->
              <router-link to="/blog">
                <el-dropdown-item divided><svg-icon icon-class="Blog03"></svg-icon> 我的主页</el-dropdown-item>
              </router-link>
              <router-link to="/article">
                <el-dropdown-item><i class="el-icon-edit"></i>写文章</el-dropdown-item>
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
import { getUserDetails, sendPhoneCode, checkPhoneCode } from '@/api/system'
import { insertInputHistory, getInputHistory, updateInputHistory, deleteInputHistory } from '@/api/homeSearch'
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
      userList: [],
      searchContent: '',
      isInputHistory: false,
      // 当前登录用户id
      userId: '',
      // 输入框历史记录
      inputHistoryLists: [
        {
          created: '',
          updated: '',
          sysInputHistoryId: '',
          sysInputHistoryContent: '',
          sysUserId: ''
        }
      ]
    }
  },
  created() {
    this.isLogin()
    // 如果已经登录系统则使用刷新详情的数据
    if (this.login === true) {
      this.refreshPage()
    }
    if (this.$route.query.search !== undefined) {
      this.searchContent = this.$route.query.search
    }
  },
  methods: {
    refreshPage() {
      // 获取用户数据详情
      getUserDetails().then(res => {
        this.userList = res.data.getList
        this.imageUrl = res.data.getList.sysUserIcon
        this.userId = res.data.getList.sysUserId
        localStorage.setItem('sysUserId', this.userId)
      })
      // 获取输入框历史记录
      getInputHistory().then(res => {
        if (res.code === 200) {
          this.inputHistoryLists = res.data.inputHistoryLists
          if (this.inputHistoryLists.length > 0) {
            this.isInputHistory = true
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      localStorage.removeItem('sysUserId')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    isLogin() {
      const hashToken = getToken()
      if (hashToken) {
        this.login = true
      } else {
        this.login = false
      }
    },
    // 跳转到我的主页
    linkToBlog() {
      this.activeIndex = '2'
      this.$router.push({
        path: '/blog',
        query: {
          sysUserId: this.userId,
          blogType: 'article'
        }
      })
      window.location.reload()
    },
    // 跳转到首页
    linkToHome() {
      this.activeIndex = '1'
      this.$router.push({ path: '/home' })
    },
    // 跳转到消息
    linkToMessage() {
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
    },
    // 搜索按钮跳转搜索页面
    linkToHomeSort() {
      if (this.login === true) {
        insertInputHistory(this.searchContent).then(res => {
          if (res.code === 200) {
            this.refreshPage()
            const routeUrl = this.$router.resolve({
              name: 'HomeSearch',
              query: {
                search: this.searchContent
              }
            })
            window.open(routeUrl.href, '_blank')
          } else {
            this.$message.error(res.message)
          }
        })
      } else {
        const routeUrl = this.$router.resolve({
          name: 'HomeSearch',
          query: {
            search: this.searchContent
          }
        })
        window.open(routeUrl.href, '_blank')
      }
    },
    // 历史记录内容点击跳转搜索页面
    linkToHomeSearch(item) {
      updateInputHistory(item.sysInputHistoryContent).then(res => {
        if (res.code === 200) {
          const routeUrl = this.$router.resolve({
            name: 'HomeSearch',
            query: {
              search: item.sysInputHistoryContent
            }
          })
          window.open(routeUrl.href, '_blank')
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 删除搜索框历史记录
    removeInputHistory(item) {
      deleteInputHistory(item.sysInputHistoryId).then(res => {
        if (res.code === 200) {
          this.refreshPage()
          this.$message.success(res.message)
          getInputHistory().then(res => {
            if (res.data.inputHistoryLists.length <= 0) {
              setTimeout(() => {
                window.location.reload()
              }, 1000)
            }
          })
        } else {
          this.$message.error(res.message)
        }
      })
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
        width: 300px;
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
