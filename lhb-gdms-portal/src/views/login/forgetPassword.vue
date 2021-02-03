<template>
    <div class="forget">
      <!-- 忘记密码内容 -->
      <div class="forget-main">
        <!-- logo -->
        <div class="forget-main-logo">
          <img class="logo-img" src="../../../src/img/Blog02.png" />
          <p class="logo-font">Bin 博客平台欢迎您!</p>
        </div>
        <!-- 表单处理区域 -->
        <div class="forget-form">
          <div style="color: #2ECC71;" align="center">
            <h3>重置密码</h3>
          </div>
          <!-- 切换手机或者邮箱重置密码 -->
          <div class="forget-form-menu">
            <el-menu
              :default-active="activeIndex"
              background-color="#2C3E50"
              text-color="#BDC3C7"
              active-text-color="#2ECC71"
              mode="horizontal"
              >
              <el-menu-item @click="chooseResetType" index="1">
                通过手机重置密码
              </el-menu-item>
              <el-menu-item @click="chooseResetType" index="2">
                通过邮箱重置密码
              </el-menu-item>
            </el-menu>
          </div>
          <!-- 重置密码表单 -->
          <div class="form-reset">
            <!-- 手机重置 -->
            <div>
              <el-form
                ref="phoneFormParams"
                :model="phoneFormParams"
                autocomplete="on"
                v-if="resetType === 1"
                label-position="left"
                >
                <!-- 手机号码输入框 -->
                <el-form-item prop="phone">
                  <el-input
                    ref="phone"
                    v-model="phoneFormParams.phone"
                    placeholder="请输入手机号码"
                    name="phone"
                    type="text"
                    tabindex="1"
                    autocomplete="on"
                    clearable
                    />
                </el-form-item>
                <!-- 手机验证码输入框 -->
                <el-form-item style="margin-top: 40px;" prop="phoneCode">
                  <el-input
                    ref="phoneCode"
                    v-model="phoneFormParams.phoneCode"
                    placeholder="请输入验证码"
                    name="phoneCode"
                    type="text"
                    tabindex="2"
                    autocomplete="on"
                    style="width: 130px;"
                    clearable
                  />
                  <el-button
                    :loading="phoneCodeLoading"
                    style="margin-left: 25px;width: 140px"
                    type="success"
                    plain
                    :disabled="phoneFormParams.phone.length != 11"
                    @click="sendPhoneCode"
                    >
                    {{ codePhoneMsg }}
                  </el-button>
                </el-form-item>
                <!-- 输入密码输入框 -->
                <el-form-item style="margin-top: 40px;" prop="password">
                  <el-input
                    ref="password"
                    v-model="phoneFormParams.password"
                    placeholder="请输入新的密码"
                    name="password"
                    type="password"
                    tabindex="3"
                    autocomplete="on"
                    clearable
                    show-password
                  />
                </el-form-item>
                <!-- 再次输入密码输入框 -->
                <el-form-item style="margin-top: 40px;" prop="passwordAgain">
                  <el-input
                    ref="passwordAgain"
                    v-model="phoneFormParams.passwordAgain"
                    placeholder="请再次输入新的密码"
                    name="passwordAgain"
                    type="password"
                    tabindex="4"
                    autocomplete="on"
                    clearable
                    show-password
                  />
                </el-form-item>
                <!-- 重置密码按钮 -->
                <el-form-item>
                  <el-button
                    :loading="loading"
                    style="width: 300px;margin-top: 20px"
                    type="success"
                    round
                    :disabled="phoneFormParams.passwordAgain === ''"
                    @click="submitResetPassword(phoneFormParams.phone, phoneFormParams.password, phoneFormParams.phoneCode, 'phone', phoneFormParams.passwordAgain)"
                    >重置密码</el-button>
                </el-form-item>
              </el-form>
            </div>
            <!-- 邮箱重置 -->
            <div>
              <el-form
                ref="emailFormParams"
                :model="emailFormParams"
                autocomplete="on"
                v-if="resetType === 2"
                label-position="left"
              >
                <!-- 邮箱号码输入框 -->
                <el-form-item prop="email">
                  <el-input
                    ref="email"
                    v-model="emailFormParams.email"
                    placeholder="请输入邮箱号码"
                    name="email"
                    type="text"
                    tabindex="1"
                    autocomplete="on"
                    clearable
                  />
                </el-form-item>
                <!-- 邮箱验证码输入框 -->
                <el-form-item style="margin-top: 40px;" prop="phoneCode">
                  <el-input
                    ref="emailCode"
                    v-model="emailFormParams.emailCode"
                    placeholder="请输入验证码"
                    name="emailCode"
                    type="text"
                    tabindex="2"
                    autocomplete="on"
                    style="width: 130px;"
                    clearable
                  />
                  <el-button
                    :loading="emailCodeLoading"
                    style="margin-left: 25px;width: 140px"
                    type="success"
                    plain
                    :disabled="emailFormParams.email === ''"
                    @click="sendEmailCode"
                    >
                    {{ codeEmailMsg }}
                  </el-button>
                </el-form-item>
                <!-- 输入密码输入框 -->
                <el-form-item style="margin-top: 40px;" prop="password">
                  <el-input
                    ref="password"
                    v-model="emailFormParams.password"
                    placeholder="请输入新的密码"
                    name="password"
                    type="password"
                    tabindex="3"
                    autocomplete="on"
                    clearable
                    show-password
                  />
                </el-form-item>
                <!-- 再次输入密码输入框 -->
                <el-form-item style="margin-top: 40px;" prop="passwordAgain">
                  <el-input
                    ref="passwordAgain"
                    v-model="emailFormParams.passwordAgain"
                    placeholder="请再次输入新的密码"
                    name="passwordAgain"
                    type="password"
                    tabindex="4"
                    autocomplete="on"
                    clearable
                    show-password
                  />
                </el-form-item>
                <!-- 重置密码按钮 -->
                <el-form-item>
                  <el-button
                    :loading="loading"
                    style="width: 300px;margin-top: 20px"
                    type="success"
                    round
                    :disabled="emailFormParams.passwordAgain === ''"
                    @click="submitResetPassword(emailFormParams.email, emailFormParams.password, emailFormParams.emailCode, 'email', emailFormParams.passwordAgain)"
                    >
                    重置密码
                  </el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
          <!-- 返回登录注册或者首页 -->
          <div style="color: #2ECC71;margin-top: -10px;margin-left: -40px;" align="center">
            <svg-icon style="margin-top: 5px;" icon-class="back00"></svg-icon>
            <el-link style="color: #2ECC71;" type="info" :underline="false" @click="linkToLogin">
              <h3>
                返回登录/注册
              </h3>
            </el-link>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  import { getPhoneCode, getEmailCode } from '@/api/user'
  import { resetPasswordWithoutLogin } from '@/api/system'
  export default {
    name: 'ForgetPassword',
    data() {
      return {
        loading: false,
        activeIndex: '1',
        // 重置密码类型 1-手机 2-邮箱
        resetType: 1,
        // 手机表单参数
        phoneFormParams: {
          phone: '',
          phoneCode: '',
          password: '',
          passwordAgain: ''
        },
        // 邮箱表单参数
        emailFormParams: {
          email: '',
          emailCode: '',
          password: '',
          passwordAgain: ''
        },
        phoneCodeLoading: false,
        emailCodeLoading: false,
        codePhoneMsg: '获取验证码',
        codeEmailMsg: '获取验证码',
        phoneCountDown: 60,
        emailCountDown: 60
      }
    },
    created() {
      // 进入页面时获取之前是否有手机验证码倒计时
      const phoneEndTime = localStorage.getItem('phoneEndTime')
      if (phoneEndTime) {
        console.log('已经有倒计时')
        this.phoneCodeCountDown(phoneEndTime)
      }
      // 进入页面时获取之前是否有邮箱验证码倒计时
      const emailEndTime = localStorage.getItem('emailEndTime')
      if (emailEndTime) {
        console.log('已经有倒计时')
        this.emailCodeCountDown(emailEndTime)
      }
    },
    methods: {
      // 选择重置密码类型
      chooseResetType() {
        if (this.resetType === 1) {
          this.activeIndex = '2'
          this.resetType = 2
        } else {
          this.activeIndex = '1'
          this.resetType = 1
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
            localStorage.removeItem('phoneEndTime')
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
            localStorage.removeItem('emailEndTime')
            clearTimeout(timer)
          } else {
            this.emailCodeCountDown(endEmailMessageRes)
          }
        }, 1000)
      },
      // 获取手机验证码
      sendPhoneCode() {
        this.phoneCodeLoading = true
        getPhoneCode(this.phoneFormParams.phone).then(res => {
          if (res.code === 200) {
            this.$message.success(res.message)
            this.phoneCodeLoading = false
            const endPhoneMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('phoneEndTime', JSON.stringify(endPhoneMessageRes))
            this.phoneCodeCountDown(endPhoneMessageRes)
          } else {
            this.$message.error(res.message)
          }
        }).catch(() => {
          this.phoneCodeLoading = false
        })
      },
      // 获取邮箱验证码
      sendEmailCode() {
        this.emailCodeLoading = true
        getEmailCode(this.emailFormParams.email).then(res => {
          if (res.code === 200) {
            this.$message.success(res.message)
            this.emailCodeLoading = false
            const endEmailMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('emailEndTime', JSON.stringify(endEmailMessageRes))
            this.emailCodeCountDown(endEmailMessageRes)
          } else {
            this.$message.error(res.message)
          }
        }).catch(() => {
          this.emailCodeLoading = false
        })
      },
      // 重置密码
      submitResetPassword(keyword, password, code, type, passwordAgain) {
        if (password !== passwordAgain) {
          this.$message.error('两次输入的密码不一致!')
        } else {
          this.loading = true
          resetPasswordWithoutLogin(keyword, password, code, type).then(res => {
            if (res.code === 200) {
              this.$message.success('重置密码成功!请重新登录博客')
              this.loading = false
              setTimeout(() => {
                this.linkToLogin()
              }, 2000)
            } else {
              this.$message.error(res.message)
            }
          }).catch(() => {
            this.loading = false
          })
        }
      },
      // 跳转登录
      linkToLogin() {
        this.$router.push({
          path: '/login'
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .forget {
    background-color: #ECF0F1;
    min-height: 100%;
    width: 100%;

    .forget-main {
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

      .forget-main-logo {
        float: left;
        max-width: 400px;
        width: 400px;
        height: 700px;
        background-image: url("../../../src/img/bg06.jpg");
        background-size: 100% 100%;
        background-repeat: no-repeat;

        .logo-img {
          margin-left: 60px;
          margin-top: 50px;
          float: left;
          width: 40px;
          height: 40px;
          margin-right: 10px;
        }

        .logo-font {
          margin-top: 55px;
          margin-left: 10px;
          float: left;
          font-weight: bold;
          font-size: 25px;
          color: #2ECC71;
        }
      }

      .forget-form {
        float: left;
        max-width: 400px;
        width: 400px;
        height: 700px;
        background-color: #2C3E50;

        .forget-form-menu {
          margin-left: 42px;
          width: 305px;
        }

        .form-reset {
          margin-left: 40px;
          margin-top: 40px;
          width: 300px;
          background-color: transparent;
        }
      }
    }
  }
</style>
