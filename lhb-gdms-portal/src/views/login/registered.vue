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
            <!-- 邮箱输入框 -->
            <el-form-item prop="email" style="margin-top: 40px">
              <el-input
                ref="email"
                v-model="registeredFormParams.email"
                placeholder="请输入邮箱"
                name="email"
                tabindex="5"
                autocomplete="on"
                clearable
              >
              </el-input>
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
      </div>
    </div>
  </div>
</template>

<script>
  import Vcode from 'vue-puzzle-vcode'
  import Img1 from '../../../src/img/bg01.jpg'
  import Img2 from '../../../src/img/bg05.jpg'
  import { registered } from '../../api/user'

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
          email: ''
        },
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
        registered(this.registeredFormParams).then(res => {
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
        margin-left: 30px;
        margin-top: 70px;
        width: 300px;
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
