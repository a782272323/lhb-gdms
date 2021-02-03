<template>
    <div align="center">
      <div class="focus">
        <!-- 关注标题区域 -->
        <div class="focus-title">
          <el-row :gutter="24">
            <el-col :span="8">
              <svg-icon class="focus-title-svg" icon-class="focus01"></svg-icon>
              <h2 class="focus-title-h2">关注</h2>
            </el-col>
            <!-- 关注类型选择区域 -->
            <el-col :span="16">
              <el-link
                v-if="focusType !== 'fans'"
                class="focus-title-link"
                type="info"
                :underline="false"
                @click="jumpToFans"
                >
                <h3 style="color: black;">粉丝</h3>
              </el-link>
              <el-link
                v-if="focusType === 'fans'"
                class="focus-title-link"
                type="success"
                :underline="false"
                disabled
              >
                <h3>粉丝</h3>
              </el-link>
              <div class="focus-title-divToHr"></div>
              <el-link
                v-if="focusType !== 'label'"
                class="focus-title-link"
                type="info"
                :underline="false"
                @click="jumpToLabel"
                >
                <h3 style="color: black;">关注的标签</h3>
              </el-link>
              <el-link
                v-if="focusType === 'label'"
                class="focus-title-link"
                type="success"
                :underline="false"
                disabled
              >
                <h3>关注的标签</h3>
              </el-link>
              <div class="focus-title-divToHr"></div>
              <el-link
                v-if="focusType !== 'person'"
                class="focus-title-link"
                type="info"
                :underline="false"
                @click="jumpToPerson"
                >
                <h3 style="color: black;">你的关注</h3>
              </el-link>
              <el-link
                v-if="focusType === 'person'"
                class="focus-title-link"
                type="success"
                :underline="false"
                disabled
              >
                <h3>你的关注</h3>
              </el-link>
            </el-col>
          </el-row>
        </div>
        <el-divider></el-divider>
        <!-- 关注内容区域 -->
        <div
          v-loading="loading"
          element-loading-text="拼命加载中......"
          elment-loading-spinner="el-icon-loading"
          class="focus-content"
          >
          <!-- 你的关注 -->
          <div v-if="focusType === 'person'">
            <div v-if="focusPersonLists.length === 0">
              <div>
                <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
              </div>
              <br>
              <div>
                <h4>你的关注空空如也!</h4>
              </div>
            </div>
            <div
              v-for="(item, index) in focusPersonLists"
              :key="index"
              class="focus-content-person">
              <el-row :gutter="24">
                <hr style="margin-top: 10px;margin-bottom: 20px;border: 1px solid #ECF0F1;" v-if="index !== 0">
                <!-- 用户头像 -->
                <el-col :span="2">
                  <el-image class="focus-content-img" :src="item.sysUserIcon" @click="linkToBlog(item.formSysUserId)"></el-image>
                </el-col>
                <!-- 关注内容区域 -->
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <!-- 用户昵称 -->
                      <div>
                        <el-link class="focus-content-link-name" :underline="false" type="info" @click="linkToBlog(item.formSysUserId)">
                          <h4 class="focus-content-h4">{{ item.sysUserNickname }}</h4>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <!-- 关注内容描述 -->
                      <div>
                        <el-link class="focus-content-link-description" :underline="false" type="info">
                          <h4 class="focus-content-h4">
                            您于
                            {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日  {{ item.created | parseTime('{h}:{m}:{s}')}}
                            关注了ta
                          </h4>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
                <!-- 关注按钮区域 -->
                <el-col :span="4">
                  <el-button class="focus-content-button" type="success" @click="removeFocusPersonButton(item)">
                    已关注
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 关注的标签 -->
          <div v-if="focusType === 'label'">
            <div v-if="focusLabelLists.length === 0">
              <div>
                <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
              </div>
              <br>
              <div>
                <h4>关注的标签空空如也!</h4>
              </div>
            </div>
            <div
              v-for="(item, index) in focusLabelLists"
              :key="index"
              class="focus-content-person">
              <el-row :gutter="24">
                <hr style="margin-top: 10px;margin-bottom: 20px;border: 1px solid #ECF0F1;" v-if="index !== 0">
                <!-- 用户头像 -->
                <el-col :span="2">
                  <el-image class="focus-content-img" :src="focusUserLists.sysUserIcon" @click="linkToBlog(focusUserLists.sysUserId)"></el-image>
                </el-col>
                <!-- 关注内容区域 -->
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <!-- 用户昵称 -->
                      <div>
                        <el-link class="focus-content-link-name" :underline="false" type="info" @click="linkToBlog(focusUserLists.sysUserId)">
                          <h4 class="focus-content-h4">{{ focusUserLists.sysUserNickname }}</h4>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <!-- 关注内容描述 -->
                      <div style="margin-top: -25px;margin-left: 10px;">
                        <h4 style="color: black;float: left;">
                          您于
                          {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日  {{ item.created | parseTime('{h}:{m}:{s}')}}
                          关注了标签
                        </h4>
                        <el-link style="float: left;margin-left: 10px;margin-top: 5px;" type="success" :underline="false" @click="linkToLabelDetails(item)">
                          <h3> {{ item.labelName }} </h3>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
                <!-- 关注按钮区域 -->
                <el-col :span="4">
                  <el-button class="focus-content-button" type="success" @click="removeFocusLabelButton(item)">
                    已关注
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 粉丝 -->
          <div v-if="focusType === 'fans'">
            <div v-if="focusFansLists.length === 0">
              <div>
                <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
              </div>
              <br>
              <div>
                <h4>粉丝空空如也!</h4>
              </div>
            </div>
            <div
              v-for="(item, index) in focusFansLists"
              :key="index"
              class="focus-content-person">
              <el-row :gutter="24">
                <hr style="margin-top: 10px;margin-bottom: 20px;border: 1px solid #ECF0F1;" v-if="index !== 0">
                <!-- 用户头像 -->
                <el-col :span="2">
                  <el-image class="focus-content-img" :src="item.sysUserIcon" @click="linkToBlog(item.sysUserId)"></el-image>
                </el-col>
                <!-- 关注内容区域 -->
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <!-- 用户昵称 -->
                      <div>
                        <el-link class="focus-content-link-name" :underline="false" type="info" @click="linkToBlog(item.sysUserId)">
                          <h4 class="focus-content-h4">{{ item.sysUserNickname }}</h4>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <!-- 关注内容描述 -->
                      <div>
                        <el-link class="focus-content-link-description" :underline="false" type="info">
                          <h4 class="focus-content-h4">
                            于
                            {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日  {{ item.created | parseTime('{h}:{m}:{s}')}}
                            关注了您
                          </h4>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
                <!-- 关注按钮区域 -->
                <el-col :span="4">
                  <el-button v-if="item.isFocus === true" class="focus-content-button" type="success" @click="removeFocusFansButton(item)">
                    已关注
                  </el-button>
                  <el-button v-if="item.isFocus === false" class="focus-content-button" type="success" plain @click="submitFocusFansButton(item)">
                    未关注
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </div>
      <!-- 回到顶部 -->
      <el-tooltip content="返回顶部" placement="top">
        <vue-to-top
          type="12"
          right="100"
          bottom="50"
          size="60"
          color="#67C23A"
          duration="300"
        >
        </vue-to-top>
      </el-tooltip>
    </div>
</template>

<script>
  import vueToTop from 'vue-totop'
  import { getFocusPersonLists, getFocusUserLists, getFocusLabelLists, getFocusFansLists, deleteFocusPerson, insertFocusPerson } from '@/api/focus'
  import { deleteLabelFocusOne } from '@/api/label'
  export default {
    name: 'Focus',
    components: { vueToTop },
    data() {
      return {
        // 关注类型 person label fans
        focusType: 'person',
        loading: false,
        focusFansLists: [
          {
            sysUserIcon: '',
            sysUserFocusId: '',
            sysUserStatus: '',
            sysUserId: '',
            sysUserNickname: '',
            formSysUserId: '',
            updated: '',
            created: '',
            isFocus: '',
            trueUserFocusId: ''
          }
        ],
        focusLabelLists: [
          {
            labelId: '',
            sysLabelFocusId: '',
            created: '',
            updated: '',
            labelName: ''
          }
        ],
        focusPersonLists: [
          {
            sysUserIcon: '',
            sysUserFocusId: '',
            created: '',
            sysUserNickname: '',
            sysUserType: '',
            sysUserStatus: '',
            formSysUserId: '',
            updated: ''
          }
        ],
        focusUserLists: {
          updated: '',
          created: '',
          sysUserId: '',
          sysUserNickname: '',
          sysUserPhone: '',
          sysUserEmail: '',
          sysUserAreaCode: '',
          sysUserSex: '',
          sysUserStatus: '',
          sysUserPassword: '',
          sysUserType: '',
          sysUserKey: '',
          sysUserIcon: '',
          sysUserIntroduction: ''
        }
      }
    },
    created() {
      if (this.$route.query.focusType === undefined) {
        this.$router.push({
          name: 'Focus',
          query: {
            focusType: 'person'
          }
        })
      }
      this.focusType = this.$route.query.focusType
      setTimeout(() => {
        this.refreshPage()
      }, 500)
    },
    methods: {
      // 获取刷新数据
      refreshPage() {
        this.loading = true
        // 获取用户列表信息
        getFocusUserLists().then(res => {
          if (res.code === 200) {
            this.focusUserLists = res.data.focusUserLists
          } else {
            this.$message.error(res.message)
          }
        })
        if (this.focusType === 'person') {
          getFocusPersonLists().then(res => {
            if (res.code === 200) {
              this.loading = false
              this.focusPersonLists = res.data.focusPersonLists
            } else {
              this.loading = false
              this.$message.error(res.message)
            }
          })
        }
        if (this.focusType === 'label') {
          getFocusLabelLists().then(res => {
            if (res.code === 200) {
              this.loading = false
              this.focusLabelLists = res.data.focusLabelLists
            } else {
              this.loading = false
              this.$message.error(res.message)
            }
          })
        }
        if (this.focusType === 'fans') {
          getFocusFansLists().then(res => {
            if (res.code === 200) {
              this.loading = false
              this.focusFansLists = res.data.focusFansLists
            } else {
              this.loading = false
              this.$message.error(res.message)
            }
          })
        }
      },
      // 你的关注
      jumpToPerson() {
        this.$router.push({
          name: 'Focus',
          query: { focusType: 'person' }
        })
        window.location.reload()
      },
      // 关注的标签
      jumpToLabel() {
        this.$router.push({
          name: 'Focus',
          query: { focusType: 'label' }
        })
        window.location.reload()
      },
      // 粉丝
      jumpToFans() {
        this.$router.push({
          name: 'Focus',
          query: { focusType: 'fans' }
        })
        window.location.reload()
      },
      // 取消对用户的关注
      removeFocusPersonButton(item) {
        const confirm = '此操作将取消对该用户 ' + item.sysUserNickname + ' 的关注,是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteFocusPerson(item.sysUserFocusId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 关注用户
      submitFocusFansButton(item) {
        insertFocusPerson(item.sysUserId).then(res => {
          if (res.code === 200) {
            this.refreshPage()
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 取消对粉丝的关注
      removeFocusFansButton(item) {
        const confirm = '此操作将取消对粉丝 ' + item.sysUserNickname + ' 的关注,是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteFocusPerson(item.trueUserFocusId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 取消标签的关注
      removeFocusLabelButton(item) {
        const confirm = '此操作将取消对该标签 《' + item.labelName + '》 的关注,是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteLabelFocusOne(item.labelId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 跳转用户主页
      linkToBlog(id) {
        const routeUrl = this.$router.resolve({
          name: 'Blog',
          query: {
            sysUserId: id
          }
        })
        window.open(routeUrl.href, '_blank')
      },
      // 跳转标签详情页面
      linkToLabelDetails(item) {
        const routeUrl = this.$router.resolve({
          name: 'LabelDetails',
          query: { labelId: item.labelId, labelName: item.labelName }
        })
        window.open(routeUrl.href, '_blank')
      }
    }
  }
</script>

<style lang="scss" scoped>
  .focus {
    background-color: #fff;
    width: 760px;
    min-height: 700px;
    margin-top: 20px;
    padding: 20px;
    margin-bottom: 30px;

    .focus-title {
      background-color: #fff;
      width: 100%;
      min-height: 100px;
      margin-top: 20px;

      .focus-title-svg {
        float: left;
        height: 50px;
        width: 50px;
        margin-top: 25px;
      }

      .focus-title-h2 {
        float: left;
        margin-top: 37px;
        margin-left: 20px;
        color: #2ECC71;
      }

      .focus-title-link {
        float: right;
        margin-top: 25px;
      }

      .focus-title-divToHr {
        width: 1px;
        border: 1px solid black;
        float: right;
        height: 30px;
        margin-left: 10px;
        margin-right: 10px;
        margin-top: 35px;
      }
    }

    .focus-content {
      background-color: #fff;
      width: 100%;
      min-height: 500px;
      padding: 20px;

      .focus-content-img {
        height: 60px;
        width: 60px;
        border-radius: 50%;
      }

      .focus-content-link-name {
        float: left;
        margin-left: 10px;
        margin-top: -10px;
      }

      .focus-content-link-description {
        float: left;
        margin-left: 10px;
        margin-top: -25px;
      }

      .focus-content-button {
        margin-top: 15px;
      }

      .focus-content-h4 {
        color: black;
      }

      .focus-content-person {
        background-color: #fff;
        height: auto;
      }

      .focus-content-label {
        background-color: #fff;
        min-height: 100px;
      }

      .focus-content-fans {
        background-color: #fff;
        min-height: 100px;
      }
    }
  }
</style>
