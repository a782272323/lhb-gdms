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
            <el-radio-group class="item-top" v-model="sysUserParams.sysUserSex" @change="updateSex">
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
            <h4 style="float: left">用户昵称</h4>
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
            <h4 style="float:left;">手机号码</h4>
          </el-col>
          <el-col :span="6" :offset="1">
            <div style="color: #909399;margin-top: 20px;">
              <span v-html="desensitizationPhone(sysUserParams.sysUserPhone)"></span>
            </div>
          </el-col>
          <el-col :span="6" :offset="7">
            <el-link class="item-top" icon="el-icon-edit" type="success" :underline="false" @click="openDialogOldPhone">换绑</el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 修改邮箱 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float:left;">邮箱</h3>
          </el-col>
          <el-col :span="6" :offset="1">
            <div style="color: #909399;margin-top: 20px;">
              <span v-html="desensitizationEmail(sysUserParams.sysUserEmail)"></span>
            </div>
          </el-col>
          <el-col v-if="sysUserParams.sysUserEmail !== null" :span="6" :offset="7">
            <el-link class="item-top" icon="el-icon-edit" type="success" :underline="false" @click="openDialogOldEmail">换绑</el-link>
          </el-col>
          <el-col v-if="sysUserParams.sysUserEmail === null" :span="6" :offset="7">
            <el-link class="item-top" type="success" :underline="false" @click="openDialogEmail">
              <svg-icon icon-class="lockEmail01"></svg-icon> 绑定
            </el-link>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="24">
          <el-col :span="4">
            <h3 style="float:left;">密码</h3>
          </el-col>
          <el-col :span="6" :offset="14">
            <el-link style="margin-top: 30px" type="success" :underline="false" @click="openDialogResetPassword">
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
    <!-- 手机换绑-旧手机验证弹窗 -->
    <div align="center">
      <el-dialog
        title="手机换绑"
        :visible.sync="dialogOldPhone"
        width="350px"
        :close-on-click-modal="false"
        >
        <div style="margin-top: -40px;">
          <h3>原手机号码 : <span v-html="desensitizationPhone(sysUserParams.sysUserPhone)"></span></h3>
        </div>
        <el-row :gutter="24">
          <el-col :span="13">
            <!-- 手机验证码输入框 -->
            <el-input
              ref="oldPhoneCode"
              v-model.trim="oldPhoneCodeInput"
              placeholder="请输入验证码"
              name="oldPhoneCode"
              autocomplete="on"
              style="width: 130px"
              clearable
            />
          </el-col>
          <el-col :span="8">
            <el-button
              :loading="oldPhoneCodeLoading"
              style="margin-left: 15px;"
              type="success"
              size="small"
              plain
              @click="getOldPhoneCode"
            >
              {{ codeOldPhoneMsg }}
            </el-button>
          </el-col>
        </el-row>
        <el-button
          type="success"
          style="margin-top: 10px;width: 100%"
          :disabled="oldPhoneCodeInput.length != 6"
          @click="openDialogNewPhone"
          >
          下一步
        </el-button>
      </el-dialog>
    </div>
    <!-- 手机换绑-新手机验证弹窗 -->
    <div align="center">
      <el-dialog
        title="新手机绑定"
        :visible.sync="dialogNewPhone"
        width="350px"
        :close-on-click-modal="false"
        >
        <div style="margin-top: -30px;">
          <!-- 手机号码输入框 -->
          <el-input
            ref="newPhone"
            v-model.trim="newPhoneInput"
            placeholder="请输入手机号码"
            name="newPhone"
            autocomplete="on"
            style="width: 100%"
            clearable
          />
          <el-row :gutter="24">
            <el-col :span="13">
              <!-- 手机验证码输入框 -->
              <el-input
                ref="newPhoneCode"
                v-model.trim="newPhoneCodeInput"
                placeholder="请输入验证码"
                name="newPhoneCode"
                autocomplete="on"
                style="width: 130px"
                clearable
              />
            </el-col>
            <el-col :span="8">
              <el-button
                :loading="newPhoneCodeLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                :disabled="newPhoneInput.length != 11"
                @click="getNewPhoneCode"
              >
                {{ codeNewPhoneMsg }}
              </el-button>
            </el-col>
          </el-row>
          <el-button
            type="success"
            style="margin-top: 10px;width: 100%"
            :disabled="newPhoneCodeInput.length != 6"
            @click="submitNewPhone"
            >
            确认
          </el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 邮箱绑定弹窗 -->
    <div
      v-if="sysUserParams.sysUserEmail === null"
      align="center">
      <el-dialog
        title="邮箱绑定"
        :visible.sync="dialogEmail"
        width="350px"
        :close-on-click-modal="false"
      >
        <div style="margin-top: -30px;">
          <!-- 邮箱输入框 -->
          <el-input
            ref="email"
            v-model.trim="emailInput"
            placeholder="请输入邮箱"
            name="email"
            autocomplete="on"
            style="width: 100%"
            clearable
          />
          <el-row :gutter="24">
            <el-col :span="13">
              <!-- 邮箱验证码输入框 -->
              <el-input
                ref="emailCode"
                v-model.trim="emailCodeInput"
                placeholder="请输入验证码"
                name="emailCode"
                autocomplete="on"
                style="width: 130px"
                clearable
              />
            </el-col>
            <el-col :span="8">
              <el-button
                v-if="emailInput === ''"
                :loading="emailCodeLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                disabled
              >
                获取验证码
              </el-button>
              <el-button
                v-if="emailInput !== ''"
                :loading="emailCodeLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                @click="getEmailCode"
              >
                {{ codeEmailMsg }}
              </el-button>
            </el-col>
          </el-row>
          <el-button
            type="success"
            style="margin-top: 10px;width: 100%"
            :disabled="emailCodeInput.length !== 6"
            @click="submitEmail"
          >
            确认
          </el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 邮箱换绑-旧邮箱验证弹窗 -->
    <div align="center">
      <el-dialog
        title="邮箱换绑"
        :visible.sync="dialogOldEmail"
        width="350px"
        :close-on-click-modal="false"
      >
        <div style="margin-top: -40px;">
          <h3>原邮箱号码 : <span v-html="desensitizationEmail(sysUserParams.sysUserEmail)"></span></h3>
        </div>
        <el-row :gutter="24">
          <el-col :span="13">
            <!-- 邮箱验证码输入框 -->
            <el-input
              ref="oldEmailCode"
              v-model.trim="oldEmailCodeInput"
              placeholder="请输入验证码"
              name="oldEmailCode"
              autocomplete="on"
              style="width: 130px"
              clearable
            />
          </el-col>
          <el-col :span="8">
            <el-button
              :loading="oldEmailCodeLoading"
              style="margin-left: 15px;"
              type="success"
              size="small"
              plain
              @click="getOldEmailCode"
            >
              {{ codeOldEmailMsg }}
            </el-button>
          </el-col>
        </el-row>
        <el-button
          type="success"
          style="margin-top: 10px;width: 100%"
          :disabled="oldEmailCodeInput.length !== 6"
          @click="openDialogNewEmail"
          >
          下一步
        </el-button>
      </el-dialog>
    </div>
    <!-- 邮箱换绑-新邮箱验证弹窗 -->
    <div align="center">
      <el-dialog
        title="新邮箱绑定"
        :visible.sync="dialogNewEmail"
        width="350px"
        :close-on-click-modal="false"
      >
        <div style="margin-top: -30px;">
          <!-- 邮箱号码输入框 -->
          <el-input
            ref="newEmail"
            v-model.trim="newEmailInput"
            placeholder="请输入邮箱号码"
            name="newEmail"
            autocomplete="on"
            style="width: 100%"
            clearable
          />
          <el-row :gutter="24">
            <el-col :span="13">
              <!-- 邮箱验证码输入框 -->
              <el-input
                ref="newEmailCode"
                v-model.trim="newEmailCodeInput"
                placeholder="请输入验证码"
                name="newEmailCode"
                autocomplete="on"
                style="width: 130px"
                clearable
              />
            </el-col>
            <el-col :span="8">
              <el-button
                v-if="newEmailInput === ''"
                :loading="newEmailCodeLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                disabled
              >
                获取验证码
              </el-button>
              <el-button
                v-if="newEmailInput !== ''"
                :loading="newEmailCodeLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                @click="getNewEmailCode"
                >
                {{ codeNewPhoneMsg }}
              </el-button>
            </el-col>
          </el-row>
          <el-button
            type="success"
            style="margin-top: 10px;width: 100%"
            :disabled="newEmailCodeInput.length != 6"
            @click="submitNewEmail"
          >
            确认
          </el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 重置密码弹窗 -->
    <div align="center">
      <el-dialog
        title="重置密码"
        :visible.sync="dialogResetPassword"
        width="350px"
        :close-on-click-modal="false"
        >
        <div style="margin-top: -25px">
          <el-button-group>
            <el-button v-if="resetPasswordType === 1" type="success" round @click="chooseResetPasswordType">
              手机重置密码
            </el-button>
            <el-button v-if="resetPasswordType !== 1" type="success" round plain @click="chooseResetPasswordType">
              手机重置密码
            </el-button>
            <el-button v-if="resetPasswordType === 2" type="success" round @click="chooseResetPasswordType">
              邮箱重置密码
            </el-button>
            <el-button v-if="resetPasswordType !== 2" type="success" round plain @click="chooseResetPasswordType">
              邮箱重置密码
            </el-button>
          </el-button-group>
        </div>
        <!-- 手机重置密码 -->
        <div v-if="resetPasswordType === 1">
          <h3>手机号码 : <span v-html="desensitizationPhone(sysUserParams.sysUserPhone)"></span></h3>
          <el-row :gutter="24">
            <el-col :span="13">
              <!-- 手机验证码输入框 -->
              <el-input
                ref="phoneCodeReset"
                v-model.trim="phoneCodeResetInput"
                placeholder="请输入验证码"
                name="phoneCodeReset"
                autocomplete="on"
                style="width: 130px"
                clearable
              />
            </el-col>
            <el-col :span="8">
              <el-button
                :loading="phoneCodeResetLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                @click="getPhoneResetCode"
              >
                {{ codePhoneResetMsg }}
              </el-button>
            </el-col>
          </el-row>
        </div>
        <!-- 邮箱重置密码 -->
        <div v-if="resetPasswordType === 2">
          <h3>邮箱号码 : <span v-html="desensitizationEmail(sysUserParams.sysUserEmail)"></span></h3>
          <el-row :gutter="24">
            <el-col :span="13">
              <!-- 邮箱验证码输入框 -->
              <el-input
                ref="emailCodeReset"
                v-model.trim="emailCodeResetInput"
                placeholder="请输入验证码"
                name="emailCodeReset"
                autocomplete="on"
                style="width: 130px"
                clearable
              />
            </el-col>
            <el-col :span="8">
              <el-button
                :loading="emailCodeResetLoading"
                style="margin-left: 15px;"
                type="success"
                size="small"
                plain
                @click="getEmailResetCode"
              >
                {{ codeEmailResetMsg }}
              </el-button>
            </el-col>
          </el-row>
        </div>
        <div>
          <!-- 输入密码输入框 -->
          <el-input
            ref="emailCodeReset"
            v-model.trim="resetPassword"
            placeholder="请输入新的密码"
            name="emailCodeReset"
            autocomplete="on"
            style="width: 100%;"
            type="password"
            show-password
            clearable
          />
          <!-- 再次输入密码输入框 -->
          <el-input
            ref="emailCodeReset"
            v-model.trim="resetPasswordAgain"
            placeholder="请再次输入新的密码"
            name="emailCodeReset"
            autocomplete="on"
            style="width: 100%;"
            type="password"
            show-password
            clearable
          />
          <el-button
            v-if="resetPasswordAgain === ''"
            type="success"
            style="margin-top: 10px;width: 100%"
            disabled
            >
            重置
          </el-button>
          <el-button
            v-if="resetPasswordAgain !== ''"
            type="success"
            style="margin-top: 10px;width: 100%"
            @click="submitResetPassword"
            >
            重置
          </el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 查看大图标组件 -->
    <PictureCheck ref="pictureCheck"/>
  </div>
</template>

<script>
  import { getUserDetails, updateSysUserBase, updateUserIcon, updateUserSystemIcon, getIconLists } from '@/api/system'
  import { checkPhoneCode, sendPhoneCode, sendEmailCode, checkEmailCode, resetPassword } from '@/api/system'
  import PictureCheck from '../components/pictureCheck'
  export default {
    name: 'Profile',
    components: { PictureCheck },
    created() {
      this.refreshPage()
      // 进入页面时获取之前是否有旧手机验证码倒计时
      const oldPhoneEndTime = localStorage.getItem('oldPhoneEndTime')
      if (oldPhoneEndTime) {
        this.oldPhoneCodeCountDown(oldPhoneEndTime)
      }
      // 进入页面时获取之前是否有新手机验证码倒计时
      const newPhoneEndTime = localStorage.getItem('newPhoneEndTime')
      if (newPhoneEndTime) {
        this.newPhoneCodeCountDown(newPhoneEndTime)
      }
      // 进入页面时获取之前是否有邮箱绑定验证码倒计时
      const emailEndTime = localStorage.getItem('emailEndTime')
      if (emailEndTime) {
        this.emailCodeCountDown(emailEndTime)
      }
      // 进入页面时获取之前是否有旧邮箱验证码倒计时
      const oldEmailEndTime = localStorage.getItem('oldEmailEndTime')
      if (oldEmailEndTime) {
        this.oldEmailCodeCountDown(oldEmailEndTime)
      }
      // 进入页面时获取之前是否有新邮箱验证码倒计时
      const newEmailEndTime = localStorage.getItem('newEmailEndTime')
      if (newEmailEndTime) {
        this.newEmailCodeCountDown(newEmailEndTime)
      }
      // 进入页面时获取之前是否有手机重置密码验证码倒计时
      const resetPhoneEndTime = localStorage.getItem('resetPhoneEndTime')
      if (resetPhoneEndTime) {
        this.resetPhoneCodeCountDown(resetPhoneEndTime)
      }
      // 进入页面时获取之前是否有邮箱重置密码验证码倒计时
      const resetEmailEndTime = localStorage.getItem('resetEmailEndTime')
      if (resetEmailEndTime) {
        this.resetEmailCodeCountDown(resetEmailEndTime)
      }
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
        iconList: [],
        // 手机换绑-旧手机验证弹窗
        dialogOldPhone: false,
        // 手机换绑-新手机验证弹窗
        dialogNewPhone: false,
        // 手机验证码相关
        oldPhoneCodeInput: '',
        newPhoneCodeInput: '',
        newPhoneInput: '',
        codeOldPhoneMsg: '获取验证码',
        codeNewPhoneMsg: '获取验证码',
        oldPhoneCodeLoading: false,
        newPhoneCodeLoading: false,
        oldPhoneCountDown: 60,
        newPhoneCountDown: 60,
        // 邮箱验证码相关
        dialogEmail: false,
        dialogOldEmail: false,
        dialogNewEmail: false,
        emailCodeLoading: false,
        oldEmailCodeLoading: false,
        newEmailCodeLoading: false,
        codeEmailMsg: '获取验证码',
        codeOldEmailMsg: '获取验证码',
        codeNewEmailMsg: '获取验证码',
        emailInput: '',
        emailCodeInput: '',
        oldEmailCodeInput: '',
        newEmailInput: '',
        newEmailCodeInput: '',
        emailCountDown: 60,
        oldEmailCountDown: 60,
        newEmailCountDown: 60,
        // 重置密码弹窗
        dialogResetPassword: false,
        // 重置密码-手机输入框
        phoneResetInput: '',
        // 重置密码-手机验证码输入框
        phoneCodeResetInput: '',
        // 重置密码-输入密码
        resetPassword: '',
        // 重置密码-再次输入密码
        resetPasswordAgain: '',
        // 重置密码-邮箱输入框
        emailResetInput: '',
        // 重置密码-邮箱验证码输入框
        emailCodeResetInput: '',
        // 重置密码方式 1-手机,2-邮箱
        resetPasswordType: 1,
        phoneCodeResetLoading: false,
        emailCodeResetLoading: false,
        codePhoneResetMsg: '获取验证码',
        codeEmailResetMsg: '获取验证码',
        resetPhoneCountDown: 60,
        resetEmailCountDown: 60
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
        this.sexStatus = true
        updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
          if (res.code === 200) {
            this.sexStatus = false
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
        this.nickNameStatus = true
        updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
          if (res.code === 200) {
            this.nickNameStatus = false
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 跳转重置密码页面
      linkToResPassword() {
        const routeUrl = this.$router.resolve({
          name: 'ResetPassword'
        })
        window.open(routeUrl.href, '_blank')
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
            }, 1500)
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
            setTimeout(() => {
              location.reload()
            }, 1500)
          } else {
            this.loading = false
            this.dialogSysIcon = false
            this.$message.error(res.message)
          }
        })
      },
      // 手机号码脱敏处理
      desensitizationPhone(phone) {
        let phoneStr = phone
        phoneStr = phoneStr.replace(/^(.{3})(?:\d+)(.{4})$/,  '\$1****\$2')
        return phoneStr
      },
      // 邮箱号码脱敏处理
      desensitizationEmail(email) {
        let emailStr = email
        if (String(email).indexOf('@') > 0) {
          const str = email.split('@')
          let _s = ''
          // @前面多于3位
          if (str[0].length > 3) {
            for (let i = 3; i < str[0].length; i++) {
              _s += '*'
            }
            emailStr = str[0].substr(0, 3) + _s + '@' + str[1]
          } else {
            // @前面少于或者等于3位
            for (let i = 1; i < str[0].length; i++) {
              _s += '*'
            }
            emailStr = str[0].substr(0, 1) + _s + '@' + str[1]
          }
        }
        return emailStr
      },
      // 退出系统
      async logoutSystem() {
        await this.$store.dispatch('user/logout')
        localStorage.removeItem('sysUserId')
        this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      },
      // 手机换绑-旧手机验证弹窗
      openDialogOldPhone() {
        this.dialogOldPhone = true
      },
      // 下一步，校验完旧手机后打开新手机绑定弹窗
      openDialogNewPhone() {
        // 校验旧手机验证码是否正确
        checkPhoneCode(this.sysUserParams.sysUserPhone, this.oldPhoneCodeInput).then(res => {
          if (res.code === 200) {
            this.dialogOldPhone = false
            this.dialogNewPhone = true
            localStorage.removeItem('oldPhoneEndTime')
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 手机号码修改确认提示框
      submitNewPhone() {
        // 校验新手机验证码
        checkPhoneCode(this.newPhoneInput, this.newPhoneCodeInput).then(r => {
          if (r.code === 200) {
            this.sysUserParams.sysUserPhone = this.newPhoneInput
            // 修改手机号码
            updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
              if (res.code === 200) {
                this.$message.success('手机换绑成功!请重新登录系统')
                this.dialogNewPhone = false
                localStorage.removeItem('newPhoneEndTime')
                setTimeout(() => {
                  this.logoutSystem()
                }, 2000)
              } else {
                this.$message.error(res.message)
              }
            })
          } else {
            this.$message.error(r.message)
          }
        })
      },
      // 旧手机验证码倒计时
      oldPhoneCodeCountDown(endOldPhoneMessageRes) {
        this.oldPhoneCountDown = Math.ceil((endOldPhoneMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.oldPhoneCountDown--
          if (this.oldPhoneCountDown >= 1) {
            this.codeOldPhoneMsg = '(' + this.oldPhoneCountDown + ')秒后重试'
          }
          if (this.oldPhoneCountDown < 1) {
            this.codeOldPhoneMsg = '获取验证码'
            this.oldPhoneCountDown = 60
            localStorage.removeItem('oldPhoneEndTime')
            clearTimeout(timer)
          } else {
            this.oldPhoneCodeCountDown(endOldPhoneMessageRes)
          }
        }, 1000)
      },
      // 新手机验证码倒计时
      newPhoneCodeCountDown(endNewPhoneMessageRes) {
        this.newPhoneCountDown = Math.ceil((endNewPhoneMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.newPhoneCountDown--
          if (this.newPhoneCountDown >= 1) {
            this.codeNewPhoneMsg = '(' + this.newPhoneCountDown + ')秒后重试'
          }
          if (this.newPhoneCountDown < 1) {
            this.codeNewPhoneMsg = '获取验证码'
            this.newPhoneCountDown = 60
            localStorage.removeItem('newPhoneEndTime')
            clearTimeout(timer)
          } else {
            this.oldPhoneCodeCountDown(endNewPhoneMessageRes)
          }
        }, 1000)
      },
      // 获取旧手机验证码
      getOldPhoneCode() {
        this.oldPhoneCodeLoading = true
        sendPhoneCode(this.sysUserParams.sysUserPhone).then(res => {
          if (res.code === 200) {
            this.oldPhoneCodeLoading = false;
            this.$message.success(res.message);
            const endOldPhoneMessageRes = (new Date()).getTime() + 60000;
            localStorage.setItem('oldPhoneEndTime', JSON.stringify(endOldPhoneMessageRes));
            this.oldPhoneCodeCountDown(endOldPhoneMessageRes);
          } else {
            this.$message.error(res.message)
            this.oldPhoneCodeLoading = false
          }
        }).catch(() => {
          this.oldPhoneCodeLoading = false
        })
      },
      // 获取新手机验证码
      getNewPhoneCode() {
        this.newPhoneCodeLoading = true
        sendPhoneCode(this.newPhoneInput).then(res => {
          if (res.code === 200) {
            this.newPhoneCodeLoading = false
            this.$message.success(res.message)
            const endNewPhoneMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('newPhoneEndTime', JSON.stringify(endNewPhoneMessageRes))
            this.newPhoneCodeCountDown(endNewPhoneMessageRes)
          } else {
            this.$message.error(res.message)
            this.newPhoneCodeLoading = false
          }
        }).catch(() => {
          this.newPhoneCodeLoading = false
        })
      },
      // 打开邮箱绑定弹窗
      openDialogEmail() {
        this.dialogEmail = true
      },
      // 打开旧邮箱验证弹窗
      openDialogOldEmail() {
        this.dialogOldEmail = true
      },
      // 邮箱绑定验证码倒计时60秒
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
      // 旧邮箱验证码倒计时60秒
      oldEmailCodeCountDown(endOldEmailMessageRes) {
        this.oldEmailCountDown = Math.ceil((endOldEmailMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.oldEmailCountDown--
          if (this.oldEmailCountDown >= 1) {
            this.codeOldEmailMsg = '(' + this.oldEmailCountDown + ')秒后重试'
          }
          if (this.oldEmailCountDown < 1) {
            this.codeOldEmailMsg = '获取验证码'
            this.oldEmailCountDown = 60
            localStorage.removeItem('oldEmailEndTime')
            clearTimeout(timer)
          } else {
            this.oldEmailCodeCountDown(endOldEmailMessageRes)
          }
        }, 1000)
      },
      // 新邮箱验证码倒计时60秒
      newEmailCodeCountDown(endNewEmailMessageRes) {
        this.newEmailCountDown = Math.ceil((endNewEmailMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.newEmailCountDown--
          if (this.newEmailCountDown >= 1) {
            this.codeNewEmailMsg = '(' + this.newEmailCountDown + ')秒后重试'
          }
          if (this.newEmailCountDown < 1) {
            this.codeNewEmailMsg = '获取验证码'
            this.newEmailCountDown = 60
            localStorage.removeItem('newEmailEndTime')
            clearTimeout(timer)
          } else {
            this.newEmailCodeCountDown(endNewEmailMessageRes)
          }
        }, 1000)
      },
      // 获取邮箱绑定验证码
      getEmailCode() {
        this.emailCodeLoading = true
        sendEmailCode(this.emailInput).then(res => {
          if (res.code === 200) {
            this.emailCodeLoading = false
            this.$message.success(res.message)
            const endEmailMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('emailEndTime', JSON.stringify(endEmailMessageRes))
            this.emailCodeCountDown(endEmailMessageRes)
          } else {
            this.$message.error(res.message)
            this.emailCodeLoading = false
          }
        }).catch(() => {
          this.emailCodeLoading = false
        })
      },
      // 获取旧邮箱验证码
      getOldEmailCode() {
        this.oldEmailCodeLoading = true
        sendEmailCode(this.sysUserParams.sysUserEmail).then(res => {
          if (res.code === 200) {
            this.oldEmailCodeLoading = false
            this.$message.success(res.message)
            const endOldEmailMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('oldEmailEndTime', JSON.stringify(endOldEmailMessageRes))
            this.oldEmailCodeCountDown(endOldEmailMessageRes)
          } else {
            this.$message.error(res.message)
            this.oldEmailCodeLoading = false
          }
        }).catch(() => {
          this.oldEmailCodeLoading = false
        })
      },
      // 获取新邮箱验证码
      getNewEmailCode() {
        this.newEmailCodeLoading = true
        sendEmailCode(this.newEmailInput).then(res => {
          if (res.code === 200) {
            this.newEmailCodeLoading = false
            this.$message.success(res.message)
            const endNewEmailMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('newEmailEndTime', JSON.stringify(endNewEmailMessageRes))
            this.newEmailCodeCountDown(endNewEmailMessageRes)
          } else {
            this.$message.error(res.message)
            this.newEmailCodeLoading = false
          }
        }).catch(() => {
          this.newEmailCodeLoading = false
        })
      },
      // 下一步，新邮箱绑定
      openDialogNewEmail() {
        // 校验旧邮箱验证码正确之后
        this.dialogOldEmail = false
        this.dialogNewEmail = true
      },
      // 邮箱绑定确认按钮
      submitEmail() {
        // 校验邮箱验证码
        checkEmailCode(this.emailInput, this.emailCodeInput).then(r => {
          if (r.code === 200) {
            this.sysUserParams.sysUserEmail = this.emailInput
            // 修改邮箱
            updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
              if (res.code === 200) {
                this.$message.success('邮箱绑定成功!请重新登录系统')
                this.dialogEmail = false
                localStorage.removeItem('emailEndTime')
                setTimeout(() => {
                  this.logoutSystem()
                }, 2000)
              } else {
                this.$message.error(res.message)
              }
            })
          } else {
            this.$message.error(r.message)
          }
        })
      },
      // 新邮箱绑定确认按钮
      submitNewEmail() {
        // 校验新邮箱验证码
        checkEmailCode(this.newEmailInput, this.newEmailCodeInput).then(r => {
          if (r.code === 200) {
            this.sysUserParams.sysUserEmail = this.newEmailInput
            // 修改邮箱
            updateSysUserBase(this.sysUserParams.sysUserId, this.sysUserParams).then(res => {
              if (res.code === 200) {
                this.$message.success('邮箱换绑成功!请重新登录系统')
                this.dialogNewEmail = false
                localStorage.removeItem('newEmailEndTime')
                setTimeout(() => {
                  this.logoutSystem()
                }, 2000)
              } else {
                this.$message.error(res.message)
              }
            })
          } else {
            this.$message.error(r.message)
          }
        })
      },
      // 打开重置密码弹窗
      openDialogResetPassword() {
        this.dialogResetPassword = true
      },
      // 选择重置密码方式
      chooseResetPasswordType() {
        if (this.resetPasswordType === 1) {
          if (this.sysUserParams.sysUserEmail === null) {
            console.log('未绑定邮箱')
            this.dialogResetPassword = false
            this.$message.error('您还未绑定邮箱!请先绑定邮箱')
          } else {
            this.resetPasswordType = 2
          }
        } else {
          this.resetPasswordType = 1
        }
      },
      // 手机重置密码倒计时60秒
      resetPhoneCodeCountDown(endRestPhoneMessageRes) {
        this.resetPhoneCountDown = Math.ceil((endRestPhoneMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.resetPhoneCountDown--
          if (this.resetPhoneCountDown >= 1) {
            this.codePhoneResetMsg = '(' + this.resetPhoneCountDown + ')秒后重试'
          }
          if (this.resetPhoneCountDown < 1) {
            this.codePhoneResetMsg = '获取验证码'
            this.resetPhoneCountDown = 60
            localStorage.removeItem('resetPhoneEndTime')
            clearTimeout(timer)
          } else {
            this.resetPhoneCodeCountDown(endRestPhoneMessageRes)
          }
        }, 1000)
      },
      // 邮箱重置密码倒计时60秒
      resetEmailCodeCountDown(endRestEmailMessageRes) {
        this.resetEmailCountDown = Math.ceil((endRestEmailMessageRes - (new Date()).getTime()) / 1000)
        const timer = setTimeout(() => {
          this.resetEmailCountDown--
          if (this.resetEmailCountDown >= 1) {
            this.codeEmailResetMsg = '(' + this.resetEmailCountDown + ')秒后重试'
          }
          if (this.resetEmailCountDown < 1) {
            this.codePhoneResetMsg = '获取验证码'
            this.codeEmailResetMsg = 60
            localStorage.removeItem('resetEmailEndTime')
            clearTimeout(timer)
          } else {
            this.resetEmailCodeCountDown(endRestEmailMessageRes)
          }
        }, 1000)
      },
      // 获取手机重置密码验证码
      getPhoneResetCode() {
        this.phoneCodeResetLoading = true
        sendPhoneCode(this.sysUserParams.sysUserPhone).then(res => {
          if (res.code === 200) {
            this.phoneCodeResetLoading = false
            this.$message.success(res.message)
            const endRestPhoneMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('resetPhoneEndTime', JSON.stringify(endRestPhoneMessageRes))
            this.resetPhoneCodeCountDown(endRestPhoneMessageRes)
          } else {
            this.$message.error(res.message)
            this.phoneCodeResetLoading = false
          }
        }).catch(() => {
          this.phoneCodeResetLoading = false
        })
      },
      // 获取邮箱重置密码验证码
      getEmailResetCode() {
        this.emailCodeResetLoading = true
        sendEmailCode(this.sysUserParams.sysUserEmail).then(res => {
          if (res.code === 200) {
            this.emailCodeResetLoading = false
            this.$message.success(res.message)
            const endRestEmailMessageRes = (new Date()).getTime() + 60000
            localStorage.setItem('resetEmailEndTime', JSON.stringify(endRestEmailMessageRes))
            this.resetEmailCodeCountDown(endRestEmailMessageRes)
          } else {
            this.$message.error(res.message)
            this.emailCodeResetLoading = false
          }
        }).catch(() => {
          this.emailCodeResetLoading = false
        })
      },
      // 重置密码确认按钮
      submitResetPassword() {
        if (this.resetPassword !== this.resetPasswordAgain) {
          this.$message.error('两次输入的密码不一致!')
        } else {
          if (this.resetPasswordType === 1) {
            // 校验验证码
            checkPhoneCode(this.sysUserParams.sysUserPhone, this.phoneCodeResetInput).then(r => {
              if (r.code === 200) {
                // 重置密码
                resetPassword(this.resetPassword).then(res => {
                  if (res.code === 200) {
                    this.$message.success('密码重置成功!请重新登录系统')
                    this.dialogResetPassword = false
                    localStorage.removeItem('resetPhoneEndTime')
                    setTimeout(() => {
                      this.logoutSystem()
                    }, 2000)
                  } else {
                    this.$message.error(res.message)
                  }
                })
              } else {
                this.$message.error(r.message)
              }
            })
          }
          if (this.resetPasswordType === 2) {
            // 校验验证码
            checkEmailCode(this.sysUserParams.sysUserEmail, this.emailCodeResetInput).then(r => {
              if (r.code === 200) {
                // 重置密码
                resetPassword(this.resetPassword).then(res => {
                  if (res.code === 200) {
                    this.$message.success('密码重置成功!请重新登录系统')
                    this.dialogResetPassword = false
                    localStorage.removeItem('resetEmailEndTime')
                    setTimeout(() => {
                      this.logoutSystem()
                    }, 2000)
                  } else {
                    this.$message.error(res.message)
                  }
                })
              } else {
                this.$message.error(r.message)
              }
            })
          }
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .main {
    background-color: #ECF0F1;
    width: 100%;
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
      margin-top: 15px;
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
