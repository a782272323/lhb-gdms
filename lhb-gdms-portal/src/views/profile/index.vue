<template>
  <div class="main" align="center">
    <div class="content">
      <div class="box">
        <h1>个人资料</h1>
        <el-divider></el-divider>
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float: left">头像</h3>
          </el-col>
          <el-col :span="4">
            <el-image class="imageToAvatar" :src="sysUserParams.sysUserIcon"></el-image>
          </el-col>
          <el-col :span="6" :offset="3">
            <el-button class="item-top" type="success" @click="userIconClick">上传自定义头像</el-button>
          </el-col>
          <el-col :span="6">
            <el-button class="item-top" type="success" @click="sysIconClick">选择系统头像</el-button>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 修改性别 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float: left">性别</h3>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-radio-group class="item-top" v-model="this.sysUserParams.sysUserSex" @change="updateSex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-col>
          <el-col v-if="!this.sexStatus" :span="6" :offset="7">
            <el-link class="item-top" icon="el-icon-edit" type="success" :underline="false" @focus="sexRadioClick">修改</el-link>
          </el-col>
          <el-col v-if="this.sexStatus" :span="2" :offset="8">
            <el-link class="item-top" type="success" :underline="false" @click="submitHandleSex">保存</el-link>
          </el-col>
          <el-col v-if="this.sexStatus" :span="2">
            <el-link class="item-top" type="info" :underline="false" @click="removeHandleSex">取消</el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 修改用户昵称 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float: left">用户昵称</h3>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-input
              v-model="sysUserParams.sysUserNickname"
              ref="nickName"
              placeholder="请输入用户昵称"
              class="item-input-top"
              type="text"
              clearable
              @focus="nickNameClick($event)"
              />
          </el-col>
          <el-col v-if="!this.nickNameStatus" :span="6" :offset="7">
            <el-link class="item-top" icon="el-icon-edit" type="success" :underline="false" @click="nickNameButtonClick">修改</el-link>
          </el-col>
          <el-col v-if="this.nickNameStatus" :span="2" :offset="8">
            <el-link class="item-top" type="success" :underline="false" @click="submitHandleNickname">保存</el-link>
          </el-col>
          <el-col v-if="this.nickNameStatus" :span="2">
            <el-link class="item-top" type="info" :underline="false" @click="removeHandleNickName">取消</el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 修改手机号码 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float:left;">手机号码</h3>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-input
              v-model="sysUserParams.sysUserPhone"
              ref="phone"
              placeholder="请输入手机号码"
              class="item-input-top"
              type="text"
              clearable
              @focus="phoneFocus($event)"
            />
          </el-col>
          <el-col v-if="!this.phoneStatus" :span="6" :offset="7">
            <el-link class="item-top" icon="el-icon-edit" type="success" :underline="false" @click="phoneButtonClick">修改</el-link>
          </el-col>
          <el-col v-if="this.phoneStatus" :span="2" :offset="8">
            <el-link class="item-top" type="success" :underline="false" @click="submitHandlePhone">保存</el-link>
          </el-col>
          <el-col v-if="this.phoneStatus" :span="2">
            <el-link class="item-top" type="info" :underline="false" @click="removeHandlePhone">取消</el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 修改邮箱 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float:left;">邮箱</h3>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-input
              v-model="sysUserParams.sysUserEmail"
              ref="email"
              placeholder="请输入邮箱"
              class="item-input-top"
              type="text"
              clearable
              @focus="emailFocus($event)"
            />
          </el-col>
          <el-col v-if="!this.emailStatus" :span="6" :offset="7">
            <el-link class="item-top" icon="el-icon-edit" type="success" :underline="false" @click="emailButtonClick">修改</el-link>
          </el-col>
          <el-col v-if="this.emailStatus" :span="2" :offset="8">
            <el-link class="item-top" type="success" :underline="false" @click="submitHandleEmail">保存</el-link>
          </el-col>
          <el-col v-if="this.emailStatus" :span="2">
            <el-link class="item-top" type="info" :underline="false" @click="removeHandleEmail">取消</el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float:left;">密码</h3>
          </el-col>
          <el-col :span="6" :offset="14">
            <el-link style="margin-top: 30px" type="success" :underline="false" @click="linkToResPassword">
              <svg-icon icon-class="password00-#2ECC71"></svg-icon> 重置密码
            </el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
      </div>
      <!-- 自定义头像弹窗 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogUserIcon"
          title="用户上传 DIY 头像"
          width="30%"
          center
          >
          <div align="center">
            <el-upload
              drag
              multiple
              action="1"
              list-type="picture"
              :auto-upload="false"
              :before-upload="handleBeforeUpload"
              :on-change="onChanges"
              accept="image/*"
              >
              <i class="el-icon-upload" style="color: #2ECC71"></i>
              <div class="el-upload__text">将文件拖到此处,或者<em style="color: #2ECC71">点击上传</em></div>
              <div class="el-upload__tip">只能上传jpg/png格式文件,且大小不超过5M</div>
            </el-upload>
            <div>
              <img class="showImg" v-if="uploadImgParams.url" :src="uploadImgParams.url" />
            </div>
            <el-button class="item-top" type="success" @click="removeUserIcon">取 消</el-button>
            <el-button type="success" @click="submitUserIcon">确 认</el-button>
          </div>
        </el-dialog>
      </div>
      <!-- 系统头像弹窗 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogSysIcon"
          title="更改头像"
          width="30%"
          center
          >
          <el-table
            v-loading="loading"
            element-loading-text="拼命加载中......"
            :data="iconList"
            style="width: 100%;border: 1px solid #2ECC71;"
            border
            fit
            stripe
            highlight-current-row
            >
            <el-table-column fixed label="头像预览" prop="userIconUrl" align="center">
              <template slot-scope="scope">
                <div class="block">
                  <el-image class="imgToAvatar" :src="scope.row.userIconUrl" @click="checkPicture(scope.row.userIconUrl)"></el-image>
                </div>
              </template>
            </el-table-column>
            <el-table-column fixed label="头像昵称" prop="userIconName" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.userIconName }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="选择" align="center">
              <template slot-scope="scope">
                <el-button type="success" plain @click="chooseNewSysIcon(scope.row)">使用它!</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div align="center" style="margin-top: 10px">
            <el-button style="width: 200px" type="success" @click="removeSysIcon">取 消</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
    <!-- 查看大图标组件 -->
    <PictureCheck ref="pictureCheck"/>
  </div>
</template>

<script>
  import { getUserDetails, updateSysUserBase, updateUserIcon, updateUserSystemIcon, getIconLists } from '../../api/system'
  import PictureCheck from '../components/pictureCheck'
  export default {
    name: 'Profile',
    components: { PictureCheck },
    created() {
      this.refreshPage()
    },
    data() {
      return {
        userList: [],
        sysUserParams: {
          sysUserId: '',
          sysUserNickname: '',
          sysUserIcon: '',
          sysUserKey: '',
          sysUserSex: '',
          sysUserStatus: '',
          sysUserType: '',
          sysUserIntroduction: '',
          sysUserPhone: '',
          sysUserEmail: '',
          sysUserUsername: ''
        },
        sexStatus: false,
        nickNameStatus: false,
        phoneStatus: false,
        emailStatus: false,
        // 自定义头像弹窗
        dialogUserIcon: false,
        // 系统头像弹窗
        dialogSysIcon: false,
        // 图片上传相关参数
        uploadImgParams: {
          url: '',
          img: '',
          dialogCheck: false
        },
        loading: false,
        iconList: []
      }
    },
    methods: {
      refreshPage() {
        getUserDetails().then(res => {
          this.userList.sysUserSex = res.data.getList.sysUserSex
          this.sysUserParams = res.data.getList
        })
      },
      // 修改性别
      updateSex() {
        console.log('修改性别 = ' + this.sysUserParams.sysUserSex + ',' + this.userList.sysUserSex)
        if (this.sysUserParams.sysUserSex === this.userList.sysUserSex) {
          this.sexStatus = false
        } else {
          this.sexStatus = true
        }
      },
      // 性别单选按钮修改
      sexRadioClick() {
        this.sexStatus = true
        this.sysUserParams.sysUserSex = '男'
      },
      // 性别取消修改按钮
      removeHandleSex() {
        this.sexStatus = false
        this.sysUserParams.sysUserSex = this.userList.sysUserSex
        this.$message.info('已取消修改操作')
      },
      // 性别确认修改按钮
      submitHandleSex() {
        updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
          if (res.code === 200) {
            this.sexStatus = true
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 用户昵称修改按钮
      nickNameButtonClick() {
        this.nickNameStatus = true
        this.$nextTick(() => {
          // 触发输入框聚焦事件
          this.$refs.nickName.focus()
        })
      },
      // 用户昵称聚焦事件
      nickNameClick(e) {
        this.nickNameStatus = true
        console.log(e)
        e.currentTarget.select()
      },
      // 用户昵称取消修改按钮
      removeHandleNickName() {
        this.nickNameStatus = false
        this.$message.info('已取消修改操作')
      },
      // 用户昵称确认修改按钮
      submitHandleNickname() {
        updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
          if (res.code === 200) {
            this.nickNameStatus = true
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 手机号码修改按钮
      phoneButtonClick() {
        this.phoneStatus = true
        this.$nextTick(() => {
          // 触发输入框聚焦事件
          this.$refs.phone.focus()
        })
      },
      // 手机号码输入框聚焦事件
      phoneFocus(e) {
        this.phoneStatus = true
        e.currentTarget.select()
      },
      // 手机号码修改取消按钮
      removeHandlePhone() {
        this.phoneStatus = false
        this.$message.info('已取消修改')
      },
      // 手机号码修改确认按钮
      submitHandlePhone() {
        updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
          if (res.code === 200) {
            this.phoneStatus = false
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 邮箱修改按钮
      emailButtonClick() {
        this.emailStatus = true
        this.$nextTick(() => {
          // 触发输入框聚焦事件
          this.$refs.email.focus()
        })
      },
      // 邮箱输入框聚焦事件
      emailFocus(e) {
        this.emailStatus = true
        e.currentTarget.select()
      },
      // 邮箱修改取消按钮
      removeHandleEmail() {
        this.emailStatus = false
        this.$message.info('已取消修改')
      },
      // 邮箱修改确认按钮
      submitHandleEmail() {
        updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
          if (res.code === 200) {
            this.emailStatus = false
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 跳转重置密码页面
      linkToResPassword() {
        this.$router.push({path: '/resetPassword'})
      },
      // 打开用户自定义上传头像弹窗
      userIconClick() {
        this.dialogUserIcon = true
      },
      // 用户自定义上传头像取消按钮
      removeUserIcon() {
        this.dialogUserIcon = false
        this.$message.info('取消成功')
      },
      // 上传图片前校验格式
      handleBeforeUpload(file) {
        this.flag = 0
        const isJPG = file.type === 'image/jpeg'
        const isPNG = file.type === 'image/png'
        const isLt5M = file.size / 1024 / 1024 < 5
        this.size = (file.size / 1024 / 1024).toFixed(2) + 'M'
        if (isJPG) {
          this.flag = 1
          this.type = 'jpg'
        } else if (isPNG) {
          this.flag = 1
          this.type = 'png'
        } else {
          this.$message.error('文件格式只能为jpg或png类型文件式')
        }
        if (!isLt5M) {
          this.$message.error('上传图片大小不能超过5M')
          this.flag = 0
        }
        return false
      },
      onChanges(file, fileList) {
        var _this = this
        var event = event || window.event
        var reader = new FileReader()
        file = event.target.files[0]
        // 转 base64
        reader.onload = function(e) {
          if (_this.flag === 1) {
            _this.uploadImgParams.url = e.target.result
            const image = new Image()
            image.onload = function() {
              const width = image.width
              const height = image.height
              console.log('参数 宽高: ' + width + 'x' + height)
            }
            image.scr = e.target.result
          }
        }
        this.uploadImgParams.img = file
        reader.readAsDataURL(file)

      },
      // 用户自定义上传头像确认按钮
      submitUserIcon() {
        var formData = new FormData()
        formData.append('file', this.uploadImgParams.img)
        updateUserIcon(this.sysUserParams.sysUserId, formData).then(res => {
          if (res.code === 200) {
            this.dialogUserIcon = false
            this.$message.success(res.message)
            setTimeout(() => {
              location.reload()
            }, 2000)
          } else {
            this.dialogUserIcon = false
            this.$message.error(res.message)
          }
        })
      },
      // 打开系统头像页面
      sysIconClick() {
        this.dialogSysIcon = true
        this.loading = true
        getIconLists().then(res => {
          this.loading = false
          this.iconList = res.data.getLists
        })
      },
      // 查看大图标
      checkPicture(url) {
        this.$refs.pictureCheck.check(url)
      },
      // 取消系统头像按钮
      removeSysIcon() {
        this.loading = false
        this.dialogSysIcon = false
        this.$message.info('取消成功')
      },
      // 选择头像作为新头像
      chooseNewSysIcon(row) {
        this.loading = true
        console.log('用户id = ' + this.sysUserParams.sysUserId + ',头像id = ' + row.sysUserIconId)
        updateUserSystemIcon(this.sysUserParams.sysUserId, row.sysUserIconId).then(res => {
          if (res.code === 200) {
            this.loading = false
            this.dialogSysIcon = false
            this.$message.success(res.message)
          } else {
            this.loading = false
            this.dialogSysIcon = false
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .main {
    background-color: #ECF0F1;
    width: 100%;
    /*min-height: 1200px;*/
    padding-top: 25px;
    margin-bottom: 50px;

    /deep/.el-input__inner {
      background-color: transparent !important;
      background-image: none;
      border: 0px;
    }

    .content {
      background-color: #fff;
      width: 700px;
      height: 850px;
      padding: 30px;
    }

    .box {
      background-color: #fff;
      /*background-color: #2c3e50;*/
      width: 600px;
      height: 750px;
    }

    .imageToAvatar {
      height: 72px;
      width: 72px;
      margin-left: 40px;
    }

    .item-top {
      margin-top: 20px;
    }

    .item-input-top {
      margin-top: 10px;
      width: 300px;
    }

    .showImg {
      height: 140px;
      width: 280px;
    }

    .imgToAvatar {
      width: 60px;
      height: 60px;
      border-radius: 10px;
    }
  }
</style>
