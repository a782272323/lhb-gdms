<template>
  <div class="main">
    <!-- 登录框 -->
    <div class="login">
      <!-- 图片区域 -->
      <div class="login-logo">
        <img class="login-logo-img" src="../../img/Blog02.png" />
        <p class="login-logo-font">Bin 博客平台欢迎您!</p>
      </div>
      <div class="login-form">
        <!-- 切换登录或者注册 -->
        <div class="login-form-login-registered">
          <el-button-group>
            <el-button type="success" @click="linkToLogin" plain round>登录</el-button>
            <el-button type="success" @click="linkToRegistered" round>注册</el-button>
          </el-button-group>
        </div>
        <!-- 注册表单 -->
        <div class="registered-form">
          <el-form
            ref="registeredFormParams"
            :model="registeredFormParams"
            autocomplete="on"
            label-position="left"
            :rules="registeredFormRules"
            >
            <!-- 用户输入框 -->
            <el-form-item prop="username">
              <el-row :gutter="24">
                <el-col :span="21">
                  <el-input
                    ref="username"
                    name="username"
                    :key="passwordType"
                    placeholder="请输入用户名"
                    v-model="registeredFormParams.username"
                    type="text"
                    tabindex="1"
                    autocomplete="on"
                    clearable
                  >
                  </el-input>
                </el-col>
                <el-col :span="2">
                  <el-tooltip
                    effect="light"
                    placement="top-end"
                    >
                    <svg-icon icon-class="xiaowenhao00-#ffffff">
                    </svg-icon>
                    <div slot="content">
                      用户名确定后不可修改,<br/>
                      请勿随意填写!
                    </div>
                  </el-tooltip>
                </el-col>
              </el-row>
            </el-form-item>
            <!-- 密码输入框 -->
            <el-form-item prop="password" style="margin-top: 40px">
              <el-input
                ref="password"
                v-model="registeredFormParams.password"
                placeholder="请输入密码"
                :type="passwordType"
                name="password"
                tabindex="2"
                autocomplete="on"
                >
              </el-input>
              <span class="show-pwd" @click="showPwd">
                <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open' "/>
              </span>
            </el-form-item>
            <!-- 再次输入密码输入框 -->
            <el-form-item prop="passwordAgain" style="margin-top: 40px">
              <el-input
                ref="passwordAgain"
                v-model="registeredFormParams.passwordAgain"
                placeholder="请再次输入密码"
                :type="passwordTypeAgain"
                name="passwordAgain"
                tabindex="3"
                autocomplete="on"
                >
              </el-input>
              <span class="show-pwd" @click="showPwdAgain">
                <svg-icon :icon-class="passwordTypeAgain === 'password' ? 'eye' : 'eye-open' "/>
              </span>
            </el-form-item>
            <!-- 手机号码输入框 -->
            <el-form-item prop="phone" style="margin-top: 40px">
              <el-input
                ref="phone"
                v-model="registeredFormParams.phone"
                placeholder="请输入手机号码"
                name="phone"
                tabindex="4"
                autocomplete="on"
                clearable
                >
              </el-input>
            </el-form-item>
            <!-- 手机验证码输入框 -->
            <el-form-item prop="phoneCode" style="margin-top: 40px;">
              <div style="float: left">
                <el-input
                  ref="phoneCode"
                  v-model="registeredFormParams.phoneCode"
                  placeholder="输入验证码"
                  name="phoneCode"
                  type="text"
                  tabindex="5"
                  style="width: 130px"
                  clearable
                  >
                </el-input>
                <el-button :loading="phoneCodeLoading" style="margin-left: 25px;width: 140px;" type="success" plain @click="getRegisteredCode">
                  {{ codePhoneMsg }}
                </el-button>
              </div>
            </el-form-item>
            <!-- 拼图验证码 -->
            <Vcode :show="codeShow" @success="codeSuccess" :imgs="[Img2, Img1]"></Vcode>
            <!-- 注册按钮 -->
            <el-button :loading="loading" style="width: 300px;margin-top: 20px" type="success" round @click="handleRegistered">
              注册
            </el-button>
          </el-form>
        </div>
        <div align="center">
          <p style="color: #fff;margin-top: 20px">已有账号?  <el-link @click="linkToLogin" type="success"> 立即登录</el-link> </p>
        </div>
        <div align="center">
          <el-link type="success" :underline="false" @click="linkToHome">暂不登录,进入博客平台</el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vcode from 'vue-puzzle-vcode'
  import Img1 from '../../../src/img/bg01.jpg'
  import Img2 from '../../../src/img/bg05.jpg'
  import { registered, getRegisteredPhoneCode } from '@/api/user'

  export default {
    name: 'Registered',
    components: { Vcode },
    data() {
      return {
        registeredFormParams: {
          username: '',
          password: '',
          passwordAgain: '',
          phone: '',
          phoneCode: ''
        },
        // 倒计时初始秒数
        phoneCountDown: 60,
        phoneCodeLoading: false,
        codePhoneMsg: '获取验证码',
        passwordType: 'password',
        passwordTypeAgain: 'password',
        loading: false,
        // 验证码模态框
        codeShow: false,
        Img1,
        Img2,
        registeredFormRules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          passwordAgain: [
            { required: true, message: '请再次输入密码', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { min: 11, max: 11, message: '请输入11位的手机号', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      // 进入页面时获取之前是否有手机验证码倒计时
      const phoneEndTime = localStorage.getItem('phoneEndTime')
      if (phoneEndTime) {
        this.phoneCodeCountDown(phoneEndTime)
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
      showPwdAgain() {
        if (this.passwordTypeAgain === 'password') {
          this.passwordTypeAgain = ''
        } else {
          this.passwordTypeAgain = 'password'
        }
        this.$nextTick(() => {
          this.$refs.passwordAgain.focus()
        })
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
      // 获取注册手机验证码
      getRegisteredCode() {
        if (this.registeredFormParams.phone === '') {
          this.$message.error('手机号不能为空')
        } else if (this.registeredFormParams.phone.length !== 11) {
          this.$message.error('请输入11位数的手机号')
        } else {
          this.phoneCodeLoading = true
          getRegisteredPhoneCode(this.registeredFormParams.phone).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              this.phoneCodeLoading = false
              const endPhoneMessageRes = (new Date()).getTime() + 60000
              localStorage.setItem('phoneEndTime', JSON.stringify(endPhoneMessageRes))
              this.phoneCodeCountDown(endPhoneMessageRes)
            } else {
              this.$message.success(res.message)
              this.phoneCodeLoading = false
            }
          }).catch(() => {
            this.phoneCodeLoading = false
          })
        }
      },
      // 注册按钮
      handleRegistered() {
        this.$refs.registeredFormParams.validate((valid) => {
          if (valid) {
            if (this.registeredFormParams.password === this.registeredFormParams.passwordAgain) {
              this.codeShow = true
            } else {
              this.$message.error('两次输入的密码不匹配，请重新输入')
              this.registeredFormParams.password = ''
              this.registeredFormParams.passwordAgain = ''
            }
          } else {
            return false
          }
        })
      },
      // 图片验证码验证成功后的回调函数
      codeSuccess() {
        this.$message.success('校验成功')
        this.codeShow = false
        this.loading = true
        console.log(JSON.stringify(this.registeredFormParams))
        registered(this.registeredFormParams.phoneCode, this.registeredFormParams).then(res => {
          if (res.code === 200) {
            this.loading = false
            this.$message.success('注册成功！请登录系统')
            this.$router.push({ path: '/login' })
          } else {
            this.$message.error(res.message)
            this.loading = false
          }
        }).catch(() => {
          this.loading = false
          this.$message.error('网络异常，请稍后重试!')
        })
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

      .login-logo {
        float: left;
        max-width: 400px;
        width: 400px;
        height: 700px;
        background-image: url("../../img/bg06.jpg");
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

        .login-form-login-registered {
          margin-top: 20px;
          margin-left: 240px;
        }
      }

      .registered-form {
        margin-left: 50px;
        margin-top: 70px;
        width: 300px;
        max-width: 350px;
        background-color: transparent;;
      }

      .show-pwd {
        position: absolute;
        right: 10px;
        top: 0px;
        font-size: 16px;
        color: #889aa4;
        cursor: pointer;
        user-select: none;
      }
    }
  }
</style>
