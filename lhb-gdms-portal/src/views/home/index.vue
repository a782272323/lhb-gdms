<template>
  <div class="home" align="center">
    <hr style="margin-top: 0px;margin-bottom: 0px;border: 1px solid #ECF0F1;">
    <!-- 标签栏 -->
    <div class="label">
      <div class="label-content">
        <div
          v-loading="labelLoading"
          element-loading-text="拼命加载中......"
          elment-loading-spinner="el-icon-loading"
          >
          <el-row :gutter="24">
            <el-col :span="2">
              <el-link
                style="float: left;"
                v-if="this.$route.query.labelType !== 'recommended'"
                :underline="false"
                type="info"
                @click="linkToHomeRecommended">
                <h4 style="color: black;">推荐</h4>
              </el-link>
              <el-link
                style="float: left;"
                v-if="this.$route.query.labelType === 'recommended'"
                :underline="false"
                type="success"
                disabled>
                <h4>推荐</h4>
              </el-link>
            </el-col>
            <el-col :span="2">
              <el-link
                style="float: left;margin-left: 0px;"
                v-if="this.$route.query.labelType !== 'focus'"
                :underline="false"
                type="info"
                @click="linkToHomeFocus">
                <h4 style="color: black;">关注</h4>
              </el-link>
              <el-link
                style="float: left;margin-left: 0px;"
                v-if="this.$route.query.labelType === 'focus'"
                :underline="false"
                type="success"
                disabled>
                <h4>关注</h4>
              </el-link>
            </el-col>
            <!-- 动态标签 -->
            <el-col :span="16">
              <div v-if="isLogin === true">
                <el-row :gutter="24">
                  <!-- 不展开的标签 -->
                  <el-col
                    v-for="(item, index) in labelFocusLists"
                    :key="index"
                    :span="4">
                    <el-link
                      v-if="labelType === 'recommended' || labelType === 'focus' || item.labelName !== labelTypeDynamic"
                      style="float: left;"
                      :underline="false"
                      type="info"
                      @click="linkToLabelDynamic(item)"
                      >
                      <h4 style="color: black;">{{ item.labelName }}</h4>
                    </el-link>
                    <el-link
                      v-if="labelType === item.labelName"
                      style="float: left;"
                      :underline="false"
                      type="success"
                      disabled>
                      <h4>{{ item.labelName }}</h4>
                    </el-link>
                  </el-col>
                </el-row>
              </div>
            </el-col>
            <el-col :span="2" >
              <el-link
                style="float: right;"
                v-if="isLogin === true && labelLength > 6 && isMore === 'false'"
                :underline="false"
                type="info"
                @click="showMoreLabel">
                <h4 style="color: black;">展开更多</h4>
              </el-link>
            </el-col>
            <el-col :span="2">
              <el-link
                style="float: right;"
                v-if="isMore === 'true'"
                :underline="false"
                type="info"
                @click="closeLessLabel">
                <h4 style="color: black;">收起标签</h4>
              </el-link>
            </el-col>
            <!-- 跳转标签管理 -->
            <el-col :span="2" :offset="labelAdminOffset">
              <el-link style="float: right;" :underline="false" type="info" @click="linkToLabelFocus">
                <h4 style="color: black;">标签管理</h4>
              </el-link>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
    <hr style="margin-top: 0px;margin-bottom: 0px;border: 1px solid #ECF0F1;">
    <!-- 内容 -->
    <div class="main">
      <!-- 文章 -->
      <div class="main-article">
        <!-- 文章搜索区域 -->
        <div
          v-if="sortType !== 'none'"
          class="main-article-sort">
          <el-row :gutter="24">
            <el-col :span="3" v-if="sortType === 'leatest'">
              <el-link style="float: left;margin-top: 17px;" type="success" :underline="false" disabled>
                最新
              </el-link>
            </el-col>
            <el-col :span="3" v-if="sortType !== 'leatest'">
              <el-link style="float: left;margin-top: 17px;" type="info" :underline="false" @click="leatestSort">
                最新
              </el-link>
            </el-col>
            <el-col style="margin-top: 15px;margin-left: -40px;" :span="1">
              <el-divider direction="vertical"></el-divider>
            </el-col>
            <el-col :span="3" v-if="sortType === 'hostest'">
              <el-link style="float: left;margin-top: 17px;" type="success" :underline="false" disabled>
                最热门
              </el-link>
            </el-col>
            <el-col :span="3" v-if="sortType !== 'hostest'">
              <el-link style="float: left;margin-top: 17px;" type="info" :underline="false" @click="hostestSort">
                最热门
              </el-link>
            </el-col>
          </el-row>
        </div>
        <hr v-if="sortType !== 'none'" style="margin-top: 0px;margin-bottom: 0px;border: 1px solid #ECF0F1;">
        <!-- 文章内容区域 -->
        <div
          v-loading="articleLoading"
          element-loading-text="拼命加载中......"
          elment-loading-spinner="el-icon-loading"
          class="main-article-content"
          >
          <div v-if="homeArticleLists.length === 0">
            <div>
              <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
            </div>
            <br>
            <div>
              <h4>这里什么也没有!</h4>
            </div>
          </div>
          <div
            v-for="(item, index) in homeArticleLists"
            :key="index"
            >
            <el-divider v-if="index !== 0"></el-divider>
            <el-row :gutter="24">
              <!-- 左边区域 -->
              <el-col style="margin-left: 20px;" :span="14">
                <el-row :gutter="24">
                  <el-col :span="20">
                    <el-link style="float: left;" type="info" :underline="false" @click="linkToBlog(item)">
                      <h5> {{ item.sysUserNickname }} · </h5>
                    </el-link>
                    <el-link style="float: left;" type="info" :underline="false" @click="linkToArticleDetails(item)">
                      <h5>
                        {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日 ·
                      </h5>
                    </el-link>
                    <el-link style="float: left;" type="info" :underline="false" @click="linkToLabelDetails(item)">
                      <h5>
                        {{ item.labelName }}
                      </h5>
                    </el-link>
                  </el-col>
                </el-row>
                <!-- 文章标题 -->
                <el-row :gutter="24">
                  <el-col :span="20">
                    <el-link style="float: left;" :underline="false" @click="linkToArticleDetails(item)">
                      <h3 style="margin-top: -10px;color: black;">{{ item.articleTitle }}</h3>
                    </el-link>
                  </el-col>
                </el-row>
                <!-- 评论，点赞，浏览数量统计 -->
                <el-row :gutter="24">
                  <div>
                    <el-col :span="20">
                      <!-- 浏览数量 -->
                      <el-badge style="float: left;margin-right: 5px;margin-top: -10px;">
                        <el-button type="info" size="mini" plain @click="linkToArticleDetails(item)">
                          <svg-icon icon-class="liulang00"></svg-icon>
                          {{ item.articleBrowseSum }}
                        </el-button>
                      </el-badge>
                      <!-- 点赞数量 -->
                      <el-badge style="float: left;margin-right: 5px;margin-top: -10px;">
                        <el-button v-if="item.isPraise === false" type="info" size="mini" plain @click="submitArticlePraise(item)">
                          <svg-icon icon-class="praise00"></svg-icon>
                          {{ item.articlePraiseCount }}
                        </el-button>
                      </el-badge>
                      <el-badge style="float: left;margin-right: 5px;margin-top: -10px;">
                        <el-button v-if="item.isPraise === true" type="success" size="mini" @click="removeArticlePraise(item)">
                          <svg-icon icon-class="praise00"></svg-icon>
                          {{ item.articlePraiseCount }}
                        </el-button>
                      </el-badge>
                      <!-- 评论数量 -->
                      <el-badge style="float: left;margin-top: -10px;">
                        <el-button type="info" size="mini" plain @click="linkToArticleCommentsDetails(item)">
                          <svg-icon icon-class="pinglun00"></svg-icon>
                          {{ item.articleCommentsCount }}
                        </el-button>
                      </el-badge>
                    </el-col>
                  </div>
                </el-row>
              </el-col>
              <!-- 右边区域 -->
              <el-col v-if="item.articleImgUrl" :span="4" :offset="5">
                <el-image
                  style="height: 80px;width: 80px;margin-top: 10px;"
                  :src="item.articleImgUrl"
                  @click="linkToArticleDetails(item)"
                  >
                </el-image>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
      <!-- 联系 -->
      <div class="main-contact">
        <div>
          <img style="height: 200px; width: 200px;" src="../../../src/img/wechat.jpeg" />
        </div>
        <div>
          <h3>有问题或者更好的建议,请通过微信联系我!</h3>
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
  import { getToken } from '@/utils/auth'
  import { getUserLabelFocus, getHomeArticle } from '@/api/home'
  import { insertArticleBrowse, addArticlePraise, deleteArticlePraise } from '@/api/homePageArticle'
  import vueToTop from 'vue-totop'
  export default {
    name: 'Home',
    components: { vueToTop },
    data() {
      return {
        // 是否登录
        isLogin: false,
        // 是否展开更多
        isMore: 'false',
        sysUserId: '',
        labelType: 'recommended',
        sortType: 'leatest',
        labelTypeDynamic: '',
        labelAdminOffset: 18,
        articleLoading: false,
        labelLoading: false,
        labelLength: '',
        // 标签
        labelFocusLists: [
          {
            sysLabelFocusId: '',
            labelName: ''
          }
        ],
        // 文章列表数据
        homeArticleLists: [
          {
            articleTitle: '',
            created: '',
            articleId: '',
            labelName: '',
            articleImgUrl: '',
            labelId: '',
            articleType: '',
            articleContent: '',
            articleBrowseSum: '',
            articlePraiseCount: '',
            isPraise: '',
            sysUserId: '',
            sysUserNickname: '',
            articleCommentsCount: '',
            articleImgKey: '',
            updated: ''
          }
        ]
      }
    },
    created() {
      // 强制重定向赋予值
      if (this.$route.query.labelType === undefined) {
        this.$router.push({
          name: 'Home',
          query: {
            labelType: 'recommended',
            sortType: 'leatest',
            isMore: this.isMore
          }
        })
      }
      this.labelType = this.$route.query.labelType
      this.sortType = this.$route.query.sortType
      this.isMore = this.$route.query.isMore
      setTimeout(() => {
        this.checkLogin()
      }, 500)
      if (this.labelType !== 'recommended' && this.labelType !== 'focus') {
        this.labelTypeDynamic = this.$route.query.labelType
      }
    },
    methods: {
      // 判断是否登录
      checkLogin() {
        const hashToken = getToken()
        if (hashToken) {
          this.isLogin = true
          // 获取当前登录用户id
          this.sysUserId = localStorage.getItem('sysUserId')
          this.labelLoading = true
          // 获取用户已关注标签
          getUserLabelFocus(this.$route.query.isMore).then(res => {
            if (res.code === 200) {
              this.labelFocusLists = res.data.labelFocusLists
              this.labelLength = res.data.labelLength
              this.labelLoading = false
              if (this.labelLength > 6) {
                this.labelAdminOffset = 0
              } else {
                this.labelAdminOffset = 2
              }
            } else {
              this.labelLoading = false
              this.$message.error(res.message)
            }
          })
          this.refreshPage()
        } else {
          this.refreshPage()
          this.isLogin = false
        }
      },
      // 页面刷新请求参数-不需要登录的
      refreshPage() {
        // 获取首页文章列表数据
        if (this.sysUserId === '') {
          this.sysUserId = -1
        }
        this.articleLoading = true
        getHomeArticle(this.sysUserId, this.labelType, this.sortType).then(res => {
          if (res.code === 200) {
            this.homeArticleLists = res.data.homeArticleLists
            this.articleLoading = false
          } else {
            this.articleLoading = false
            this.$message.error(res.message)
          }
        })
      },
      // 跳转首页-推荐
      linkToHomeRecommended() {
        this.$router.push({
          name: 'Home',
          query: {
            labelType: 'recommended',
            sortType: 'leatest',
            isMore: this.isMore
          }
        })
        window.location.reload()
      },
      // 跳转首页-关注
      linkToHomeFocus() {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          this.$router.push({
            name: 'Home',
            query: {
              labelType: 'focus',
              sortType: 'none',
              isMore: this.isMore
            }
          })
          window.location.reload()
        }
      },
      // 跳转首页-已关注标签(动态)
      linkToLabelDynamic(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          this.$router.push({
            name: 'Home',
            query: {
              labelType: item.labelName,
              sortType: 'leatest',
              isMore: this.isMore
            }
          })
          window.location.reload()
        }
      },
      // 展开更多标签
      showMoreLabel() {
        this.$router.push({
          name: 'Home',
          query: {
            labelType: this.labelType,
            sortType: this.sortType,
            isMore: 'true'
          }
        })
        window.location.reload()
      },
      // 收起标签
      closeLessLabel() {
        this.$router.push({
          name: 'Home',
          query: {
            labelType: 'recommended',
            sortType: this.sortType,
            isMore: 'false'
          }
        })
        window.location.reload()
      },
      // 跳转标签管理
      linkToLabelFocus() {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          this.$router.push({ name: 'LabelFocus' })
        }
      },
      // 没有登录系统先跳转登录,然后再返回该页面
      linkToLogin() {
        this.$message.warning('请先登录系统，再执行此操作!')
        setTimeout(() => {
          // 跳转登录，并通过redirect = yes / redirect = no 来判断是否返回原页面
          this.$router.push({
            path: '/login',
            query: {
              redirect: 'yes'
            }
          })
        }, 1500)
      },
      // 按最新进行搜索
      leatestSort() {
        this.$router.push({
          name: 'Home',
          query: {
            labelType: this.labelType,
            sortType: 'leatest',
            isMore: this.isMore
          }
        })
        window.location.reload()
      },
      // 按最热门进行搜索
      hostestSort() {
        this.$router.push({
          name: 'Home',
          query: {
            labelType: this.labelType,
            sortType: 'hostest',
            isMore: this.isMore
          }
        })
        window.location.reload()
      },
      // 跳转文章详情页面
      linkToArticleDetails(item) {
        // 防止单击el-image导致页面卡死
        document.body.style = null
        this.addArticleBrowse(item)
      },
      // 跳转文章评论详情页面
      linkToArticleCommentsDetails(item) {
        insertArticleBrowse(item.articleId).then(res => {
          if (res.code === 200) {
            const routeUrl = this.$router.resolve({
              name: 'ArticleDetails',
              query: { articleId: item.articleId, jump: 'comment' }
            })
            window.open(routeUrl.href, '_blank')
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 查看文章详情时，使文章被阅读数加一
      addArticleBrowse(item) {
        insertArticleBrowse(item.articleId).then(res => {
          if (res.code === 200) {
            const routeUrl = this.$router.resolve({
              name: 'ArticleDetails',
              query: { articleId: item.articleId }
            })
            window.open(routeUrl.href, '_blank')
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 跳转标签详情页面
      linkToLabelDetails(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          const routeUrl = this.$router.resolve({
            name: 'LabelDetails',
            query: { labelId: item.labelId, labelName: item.labelName }
          })
          window.open(routeUrl.href, '_blank')
        }
      },
      // 点赞文章
      submitArticlePraise(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          addArticlePraise(item.articleId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 取消文章的点赞
      removeArticlePraise(item) {
        const confirm = '此操作将取消文章 《' + item.articleTitle + '》 的点赞,是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteArticlePraise(item.articleId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 跳转主页
      linkToBlog(item) {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: item.sysUserId
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>

  .home {
    background-color: #ECF0F1;
    height: auto;
    width: auto;

    .label {
      background-color: #fff;
      min-height: 50px;

      .label-content {
        background-color: #fff;
        min-height: 50px;
        width: 980px;
      }
    }

    .main {
      background-color: #fff;
      margin-top: 20px;
      width: auto;
      height: auto;
      max-width: 980px;

      .main-article {
        background-color: #fff;
        width: 700px;
        min-height: 700px;
        float: left;
        padding: 20px;
        margin-bottom: 20px;
      }

      .main-article-sort {
        background-color: #fff;
        height: 50px;
        width: 100%;
      }

      .main-article-content {
        margin-top: 10px;
        background-color: #fff;
        width: 100%;
        min-height: 500px;
      }

      .main-contact {
        position: fixed;
        background-color: #fff;
        margin-left: 740px;
        height: 300px;
        width: 240px;
        max-width: 240px;
        padding: 20px;
      }
    }

  }
</style>
