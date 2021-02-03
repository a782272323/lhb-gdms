<template>
  <div class="main">
    <!-- 登录框 -->
    <div class="login">
      <!-- 图片区域 -->
      <div class="login-logo">
        <img class="login-logo-img" src="../../../src/img/Blog02.png" />
        <p class="login-logo-font">Bin 博客平台欢迎您!</p>
      </div>
      <!-- 登录表单区域 -->
      <div class="login-form">
        <!-- 切换登录或者注册 -->
        <div class="login-form-login-registered">
          <el-button-group>
            <el-button type="success" @click="linkToLogin" round>登录</el-button>
            <el-button type="success" @click="linkToRegistered" plain round>注册</el-button>
          </el-button-group>
        </div>
        <!-- 切换登录方式 -->
        <div class="login-form-menu">
          <el-menu
            :default-active="activeIndex"
            background-color="#2C3E50"
            text-color="#BDC3C7"
            active-text-color="#2ECC71"
            mode="horizontal"
          >
            <el-menu-item @click="chooseUsernameLogin" index="1">
              账密登录
            </el-menu-item>
            <el-menu-item @click="choosePhoneLogin" index="2">
              手机登录
            </el-menu-item>
            <el-menu-item @click="chooseEmailLogin" index="3">
              邮箱登录
            </el-menu-item>
          </el-menu>
        </div>
        <!-- 登录表单 -->
        <div class="login-form-login">
          <!-- 账户密码登录表单 -->
          <div>
            <el-form
              ref="loginFormParams"
              :model="loginFormParams"
              :rules="loginRules"
              autocomplete="on"
              v-if="loginType === 1"
              label-position="left"
            >
              <!-- 用户名输入框 -->
              <el-form-item prop="username">
                <el-input
                  :key="passwordType"
                  ref="username"
                  v-model="loginFormParams.username"
                  placeholder="请输入用户名"
                  name="username"
                  type="text"
                  tabindex="1"
                  autocomplete="on"
                  clearable
                >
                </el-input>
              </el-form-item>
              <!-- 密码输入框 -->
              <el-tooltip v-model="capsTooltip" content="大写锁定打开" placement="right" manual>
                <el-form-item prop="password" style="margin-top: 40px">
                  <el-input
                    ref="password"
                    v-model="loginFormParams.password"
                    placeholder="请输入密码"
                    :type="passwordType"
                    name="password"
                    tabindex="2"
                    autocomplete="on"
                    @keyup.native="checkCapslock"
                    @blur="capsTooltip = false"
                    @keyup.enter.native="handleLogin"
                  >
                  </el-input>
                  <span class="show-pwd" @click="showPwd">
                <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open' "/>
              </span>
                </el-form-item>
              </el-tooltip>
              <!-- 滑动拼图验证码 -->
              <Vcode :show="codeShow" @success="codeSuccess" :imgs="[Img2, Img1]"></Vcode>
              <el-button :loading="loading" style="width: 300px;margin-top: 20px" type="success" round @click.native.prevent="handleLogin">登录</el-button>
            </el-form>
          </div>
          <!-- 手机登录表单 -->
          <div>
            <el-form
              ref="loginFormParams"
              :model="loginFormParams"
              v-if="loginType === 2"
              autocomplete="on"
              label-position="left"
            >
              <!-- 手机号输入框 -->
              <el-form-item prop="phone">
                <el-input
                  ref="phone"
                  v-model="loginFormParams.phone"
                  placeholder="请输入手机号"
                  name="phone"
                  type="text"
                  tabindex="1"
                  autocomplete="on"
                  clearable
                >
                </el-input>
              </el-form-item>
              <!-- 验证码输入框 -->
              <el-form-item prop="phoneCode" style="margin-top: 40px;">
                <div style="float: left">
                  <el-input
                    ref="phoneCode"
                    v-model="loginFormParams.phoneCode"
                    placeholder="输入验证码"
                    name="phoneCode"
                    type="text"
                    tabindex="2"
                    autocomplete="on"
                    style="width: 130px;"
                    clearable
                  >
                  </el-input>
                  <el-button :loading="phoneCodeLoading" style="margin-left: 25px;width: 140px" type="success" plain @click="getPhoneCode">
                    {{ codePhoneMsg }}
                  </el-button>
                </div>
              </el-form-item>
              <el-button :loading="loading" style="width: 300px;margin-top: 20px" type="success" round @click.native.prevent="handleLogin">登录</el-button>
            </el-form>
          </div>
          <!-- 邮箱登录表单 -->
          <div>
            <el-form
            ref="loginFormParams"
            :model="loginFormParams"
            v-if="loginType === 3"
            autocomplete="on"
            label-position="left"
            >
            <!-- 邮箱输入框 -->
            <el-form-item prop="phone">
              <el-input
                ref="phone"
                v-model="loginFormParams.email"
                placeholder="请输入邮箱"
                name="phone"
                type="text"
                tabindex="1"
                autocomplete="on"
                clearable
              >
              </el-input>
            </el-form-item>
            <!-- 验证码输入框 -->
            <el-form-item prop="phoneCode" style="margin-top: 40px">
              <div style="float: left">
                <el-input
                  ref="phoneCode"
                  v-model="loginFormParams.emailCode"
                  placeholder="输入验证码"
                  name="phoneCode"
                  type="text"
                  tabindex="2"
                  autocomplete="on"
                  style="width: 130px;"
                  clearable
                >
                </el-input>
                <el-button :loading="emailCodeLoading" style="margin-left: 25px;width: 140px" type="success" plain @click="getEmailCode">
                  {{ codeEmailMsg }}
                </el-button>
              </div>
            </el-form-item>
            <el-button :loading="loading" style="width: 300px;margin-top: 20px" type="success" round @click.native.prevent="handleLogin">登录</el-button>
          </el-form>
          </div>
        </div>
        <!-- 其他事项选择 -->
        <div class="login-form-button">
          <p style="float: left;margin-top: -20px; color: #fff;">
            还没有账号？
            <el-link @click="linkToRegistered" type="success" :underline="false">立即注册</el-link>
          </p>
          <p style="float: right;margin-top: -20px">
            <el-link @click="linkToForgetPassword" type="success" :underline="false">忘记密码?</el-link>
          </p>
          <div align="center">
            <el-link type="success" :underline="false" @click="linkToHome">暂不登录,进入博客平台</el-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vcode from 'vue-puzzle-vcode'
  import Img1 from '../../../src/img/bg01.jpg'
  import Img2 from '../../../src/img/bg05.jpg'
  import { getPhoneCode, getEmailCode } from '@/api/user'

  export default {
    name: 'Login',
    components: { Vcode },
    // components: { LoginUsername },
    data() {
      const validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('用户名不能为空'))
        }
      }
      const validatePassword = (rule, value, callback) => {
        if (value.length < 6) {
          callback(new Error('密码不能小于6位'))
        } else {
          callback()
        }
      }
      return {
        activeIndex: '1',
        // 登录状态 1-账号密码登录 2-手机登录 3-邮箱登录
        loginType: 1,
        labelPosition: 'left',
        // 账密登录时前端生成的验证码
        ruleFormLogin: {
          code_login: ''
        },
        // 账密登录时前端生成的验证码的校验
        checkCode: '',
        // 登录提交表单参数
        loginFormParams: {
          loginType: '',
          username: 'lhb123',
          password: '123456',
          phone: '',
          phoneCode: '',
          email: '',
          emailCode: ''
        },
        // 登录规则
        loginRules: {
          username: [{ required: true, trigger: 'blur', validator: validateUsername }],
          password: [{ required: true, trigger: 'blur', validator: validatePassword }]
        },
        // 验证码模态框
        codeShow: false,
        Img1,
        Img2,
        loading: false,
        capsTooltip: false,
        passwordType: 'password',
        redirect: undefined,
        otherQuery: {},
        // 倒计时秒数
        phoneCountDown: 60,
        emailCountDown: 60,
        codePhoneMsg: '获取验证码',
        codeEmailMsg: '获取验证码',
        phoneCodeLoading: false,
        emailCodeLoading: false,
        regexPhone: '^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$',
        regexEmail: '^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$'
      }
    },
    watch: {
      $route: {
        handler: function(route) {
          const query = route.query
          if (query) {
            this.redirect = query.redirect
            this.otherQuery = this.getOtherQuery(query)
          }
        },
        immediate: true
      }
    },
    created() {
      // 进入页面时获取之前是否有手机验证码倒计时
      const loginPhoneEndTime = localStorage.getItem('loginPhoneEndTime')
      if (loginPhoneEndTime) {
        console.log('已经有倒计时')
        this.phoneCodeCountDown(loginPhoneEndTime)
      }
      // 进入页面时获取之前是否有邮箱验证码倒计时
      const loginEmailEndTime = localStorage.getItem('loginEmailEndTime')
      if (loginEmailEndTime) {
        console.log('已经有倒计时')
        this.emailCodeCountDown(loginEmailEndTime)
      }
    },
    mounted() {
      if (this.loginFormParams.username === '') {
        this.$refs.username.focus()
      } else if (this.loginFormParams.password === '') {
        this.$refs.password.focus()
      }
    },
    methods: {
      // 跳转到登录
      linkToLogin() {
        this.$router.push({ path: '/login' })
      },
      // 跳转到注册
      linkToRegistered() {
        this.$router.push({ path: '/registered' })
      },
      // 跳转到忘记密码
      linkToForgetPassword() {
        this.$router.push({ path: '/forgetPassword' })
      },
      chooseUsernameLogin() {
        this.loginType = 1
      },
      choosePhoneLogin() {
        this.loginType = 2
      },
      chooseEmailLogin() {
        this.loginType = 3
      },
      // 验证码校验成功
      codeSuccess() {
        this.$message.success('校验成功')
        this.codeShow = false
        this.loading = true
        this.loginFormParams.loginType = this.loginType
        this.$store.dispatch('user/login', this.loginFormParams).then(() => {
          console.log('账密登录成功!')
          this.loading = false
          this.$message.success('登录成功!')
          this.checkLinkToWhere()
          // this.$router.push({ path: '/home' })
        }).catch(() => {
          this.loading = false
        })
      },
      checkCapslock(e) {
        const { key } = e
        this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
      },
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      // 判断是否需要返回原页面还是直接跳转首页
      checkLinkToWhere() {
        if (this.$route.query.redirect === 'yes') {
          console.log('登录成功，返回旧页面')
          this.$router.go(-1)
        } else {
          this.$router.push({ path: '/home' })
        }
      },
      // 登录按钮提交
      handleLogin() {
        if (this.loginType === 1) {
          console.log('通过账户密码登录')
          if (this.loginFormParams.username === '') {
            this.$message.error('用户名不能为空')
          } else if (this.loginFormParams.password.length < 6) {
            this.$message.error('密码大于6位')
          } else {
            this.codeShow = true
          }
        }

        if (this.loginType === 2) {
          console.log('通过手机登录')
          this.loading = true
          this.loginFormParams.loginType = this.loginType
          this.$store.dispatch('user/login', this.loginFormParams).then(() => {
            console.log('手机登录成功!')
            this.loading = false
            this.$message.success('登录成功!')
            this.checkLinkToWhere()
            // this.$router.push({ path: '/home' })
          }).catch(() => {
            this.loading = false
          })
        }

        if (this.loginType === 3) {
          console.log('通过邮箱登录')
          this.loading = true
          this.loginFormParams.loginType = this.loginType
          this.$store.dispatch('user/login', this.loginFormParams).then(() => {
            console.log('邮箱登录成功!')
            this.loading = false
            this.$message.success('登录成功!')
            this.checkLinkToWhere()
            // this.$router.push({ path: '/home' })
          }).catch(() => {
            this.loading = false
          })
        }
      },
      getOtherQuery(query) {
        return Object.keys(query).reduce((acc, cur) => {
          if (cur !== 'redirect') {
            acc[cur] = query[cur]
          }
          return acc
        }, {})
      },
      // 获取手机验证码/发送手机验证码
      getPhoneCode() {
        // 发送手机验证码
        console.log('发送手机验证码')
        if (this.loginFormParams.phone === '') {
          this.$message.error('手机号码不能为空,请输入手机号码!')
        } else {
          this.phoneCodeLoading = true
          getPhoneCode(this.loginFormParams.phone).then(result => {
            if (result.code === 200) {
              this.$message.success('验证码发送成功')
              this.phoneCodeLoading = false
              const endPhoneMessageRes = (new Date()).getTime() + 60000
              localStorage.setItem('loginPhoneEndTime', JSON.stringify(endPhoneMessageRes))
              this.phoneCodeCountDown(endPhoneMessageRes)
            } else {
              this.$message.success(result.message)
              this.phoneCodeLoading = false
            }
          })
        }
      },
      // 获取邮箱验证码/发送邮箱验证码
      getEmailCode() {
        console.log('发送邮箱验证码')
        if (this.loginFormParams.email === '') {
          this.$message.error('邮箱不能为空，请输入邮箱!')
        } else {
          this.emailCodeLoading = true
          getEmailCode(this.loginFormParams.email).then(result => {
            if (result.code === 200) {
              this.$message.success('验证码发送成功')
              this.emailCodeLoading = false
              const endEmailMessageRes = (new Date()).getTime() + 60000
              localStorage.setItem('loginEmailEndTime', JSON.stringify(endEmailMessageRes))
              this.emailCodeCountDown(endEmailMessageRes)
            } else {
              this.emailCodeLoading = false
              this.$message.error(result.message)
            }
            this.emailCodeLoading = false
          })
        }
      },
      // 手机验证码倒计时60秒，网页刷新后仍然存在
      phoneCodeCountDown(endPhoneMessageRes) {
        this.phoneCountDown = Math.ceil((endPhoneMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.phoneCountDown--
          if (this.phoneCountDown >= 1) {
            this.codePhoneMsg = '(' + this.phoneCountDown + ')秒后重试'
          }
          if (this.phoneCountDown < 1) {
            this.codePhoneMsg = '获取验证码'
            this.phoneCountDown = 60
            localStorage.removeItem('loginPhoneEndTime')
            clearTimeout(timer)
          } else {
            this.phoneCodeCountDown(endPhoneMessageRes)
          }
        }, 1000)
      },
      // 邮箱验证码倒计时60秒，网页刷新后仍然存在
      emailCodeCountDown(endEmailMessageRes) {
        this.emailCountDown = Math.ceil((endEmailMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.emailCountDown--
          if (this.emailCountDown >= 1) {
            this.codeEmailMsg = '(' + this.emailCountDown + ')秒后重试'
          }
          if (this.emailCountDown < 1) {
            this.codeEmailMsg = '获取验证码'
            this.emailCountDown = 60
            localStorage.removeItem('loginEmailEndTime')
            clearTimeout(timer)
          } else {
            this.emailCodeCountDown(endEmailMessageRes)
          }
        }, 1000)
      },
      linkToHome() {
        this.$router.push({ name: 'Home' })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .main {
    /*background-color: #DEDEDE;*/
    background-color: #ECF0F1;
    /*background-color: #BDC3C7;*/
    min-height: 100%;
    width: 100%;

    .login {
      width: 800px;
      height: 700px;
      max-height: 700px;
      /* 让页面居于正中间 */
      position: absolute;
      top: 50%;
      left: 50%;
      -webkit-transform: translate(-50%, -50%);
      -moz-transform: translate(-50%, -50%);
      -ms-transform: translate(-50%, -50%);
      -o-transform: translate(-50%, -50%);
      transform: translate(-50%, -50%);

      .show-pwd {
        position: absolute;
        right: 10px;
        top: 0px;
        font-size: 16px;
        color: #889aa4;
        cursor: pointer;
        user-select: none;
      }

      .login-logo {
        float: left;
        max-width: 400px;
        width: 400px;
        height: 700px;
        background-image: url("../../../src/img/bg06.jpg");
        background-size: 100% 100%;
        background-repeat: no-repeat;

        .login-logo-img {
          margin-left: 60px;
          margin-top: 50px;
          float: left;
          width: 40px;
          height: 40px;
          margin-right: 10px;
        }

        .login-logo-font {
          margin-top: 55px;
          margin-left: 10px;
          float: left;
          font-weight: bold;
          font-size: 25px;
          color: #2ECC71;
        }
      }

      .login-form {
        float: left;
        max-width: 400px;
        width: 400px;
        height: 700px;
        background-color: #2C3E50;
        /*background-color: #34495E;*/

        .login-form-menu {
          margin-left: 45px;
          margin-top: 80px;
          width: 300px;
        }

        .login-form-login {
          margin-left: 40px;
          margin-top: 40px;
          width: 300px;
          /*background-color: #623615;*/
          background-color: transparent;
        }

        .login-form-button {
          margin-left: 45px;
          margin-top: 50px;
          width: 300px;
        }

        .login-form-input {
          background-color: #2C3E50;
        }

        .login-form-login-registered {
          margin-top: 20px;
          margin-left: 240px;
        }
      }
    }
  }
</style>
