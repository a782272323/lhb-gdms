<template>
    <div align="center">
      <!-- 标签标题区域 -->
      <div v-loading="loading" element-loading-text="拼命加载中......" class="labelDetails-title">
        <hr class="labelDetails-hr">
        <h1 style="margin-top: 30px;">
          {{ labelName }}
        </h1>
        <h4 style="margin-top: -15px;">
          {{ labelFocusCount }} 关注 · {{ labelArticleCount }} 文章
        </h4>
      </div>
      <!-- 标签排序区域 -->
      <div v-loading="loading" element-loading-text="拼命加载中......" class="labelDetails-sort">
        <el-row :gutter="24">
          <el-col :span="2">
            <el-image
              class="labelDetails-sort-img"
              :src="labelIconUrl"></el-image>
          </el-col>
          <el-col :span="2">
            <el-button
              style="margin-top: 19px;"
              v-if="this.isFocus === false"
              type="success"
              size="small"
              plain
              @click="submitFocus()"
              >未关注</el-button>
            <el-button
              style="margin-top: 19px;margin-left: -10px;"
              v-if="this.isFocus === true"
              type="success"
              size="small"
              @click="removeFocus()"
              >已关注</el-button>
          </el-col>
          <el-col :span="2" :offset="15">
            <el-link
              style="margin-top: 27px;"
              :type="this.linkLatestType"
              :underline="false"
              @click="defaultSort"
              >
              最新
            </el-link>
          </el-col>
          <el-col style="margin-top: 20px;margin-left: -20px;"  :span="1">
            <el-divider direction="vertical"></el-divider>
          </el-col>
          <el-col :span="2">
            <el-link
              style="margin-top: 27px;margin-left: -20px;"
              :type="this.linkHottestType"
              :underline="false"
              @click="hotSort"
              >
              最热门
            </el-link>
          </el-col>
        </el-row>
      </div>
      <!-- 标签下的文章详情区域 -->
      <div
        v-loading="contentLoading"
        element-loading-text="拼命加载中......"
        class="articleDetails-sort-box"
        >
        <div
          v-for="(item, index) in articleList"
          :key="index"
          class="articleDetails-sort-content"
          >
          <el-divider v-if="index !== 0"></el-divider>
          <el-row :gutter="24">
            <!-- 左边区域 -->
            <el-col style="margin-left: 20px;margin-top: -20px" :span="14">
              <el-row :gutter="24">
                <el-col :span="20">
                  <el-link style="float: left" type="info" :underline="false" @click="linkToBlog(item)">
                    <h5>{{ item.userNickName }} · </h5>
                  </el-link>
                  <el-link style="float: left" type="info" :underline="false" @click="linkToArticleDetails(item)">
                    <h5>
                      {{ item.articleDetails.created | parseTime('{y}') }}年{{ item.articleDetails.created | parseTime('{m}') }}月{{ item.articleDetails.created | parseTime('{d}') }}日 ·
                    </h5>
                  </el-link>
                  <el-link style="float: left" type="info" :underline="false">
                    <h5>
                      {{ item.articleDetails.labelName }}
                    </h5>
                  </el-link>
                </el-col>
              </el-row>
              <!-- 文章标题 -->
              <el-row :gutter="24">
                <el-col :span="20">
                  <el-link style="float: left;" :underline="false" @click="linkToArticleDetails(item)">
                    <h2 style="margin-top: -10px;color: black">{{ item.articleDetails.articleTitle }}</h2>
                  </el-link>
                </el-col>
              </el-row>
              <!-- 评论，点赞，浏览数量统计 -->
              <el-row :gutter="24">
                <div>
                  <el-col :span="14">
                    <!-- 浏览数量 -->
                    <el-badge style="float: left;margin-right: 5px;margin-top: -10px;">
                      <el-button type="info" size="mini" plain @click="linkToArticleDetails(item)">
                        <svg-icon icon-class="liulang00"></svg-icon>
                        {{ item.articleDetails.articleBrowseSum }}
                      </el-button>
                    </el-badge>
                    <!-- 点赞数量 -->
                    <el-badge style="float: left;margin-right: 5px;margin-top: -10px;">
                      <el-button v-if="item.isPraise === false" type="info" size="mini" plain @click="submitArticlePraise(item)">
                        <svg-icon icon-class="praise00"></svg-icon>
                        {{ item.articlePraiseCount }}
                      </el-button>
                      <el-button v-if="item.isPraise === true" type="success" size="mini" @click="removeArticlePraise(item)">
                        <svg-icon icon-class="praise00"></svg-icon>
                        {{ item.articlePraiseCount }}
                      </el-button>
                    </el-badge>
                    <!-- 评论数量 -->
                    <el-badge style="float: left;margin-right: 5px;margin-top: -10px;">
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
            <el-col :span="4" :offset="5">
              <el-image
                v-if="item.articleDetails.articleImgUrl"
                style="height: 80px;width: 80px"
                :src="item.articleDetails.articleImgUrl"
                @click="linkToArticleDetails(item)"
                >
              </el-image>
            </el-col>
          </el-row>
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
  import { getLabelDetails, deleteLabelFocusOne, getLabelList, insertLabelFocus } from '@/api/label'
  import { insertArticleBrowse, addArticlePraise, deleteArticlePraise } from '@/api/homePageArticle'
  import vueToTop from 'vue-totop'
  export default {
    name: 'LabelDetails',
    components: { vueToTop },
    data() {
      return {
        loading: false,
        contentLoading: false,
        linkLatestType: 'success',
        linkHottestType: 'info',
        labelName: '',
        labelIconUrl: '',
        labelIconKey: '',
        labelArticleCount: '',
        labelFocusCount: '',
        isFocus: '',
        labelId: '',
        articleList: [
          {
            articleId: '',
            sysArticleCollection: '',
            articlePraiseCount: 0,
            articleCommentsCount: 0,
            // 判断是否当前用户点赞
            isPraise: '',
            userNickName: '',
            articleDetails: [
              {
                articleId: '',
                sysUserId: '',
                labelId: '',
                labelName: '',
                articleImgUrl: '',
                articleImgKey: '',
                articleType: '',
                articleContent: '',
                articleTitle: '',
                created: '',
                updated: '',
                articleBrowseSum: ''
              }
            ]
          }
        ]
      }
    },
    created() {
      this.labelName = this.$route.query.labelName
      this.labelId = this.$route.query.labelId
      this.refreshPage()
      this.chooseSortType()
    },
    mounted() {
      // 设置页面标题
      document.title = this.$route.query.labelName + ' - 标签'
    },
    methods: {
      refreshPage() {
        this.loading = true
        // 请求数据
        getLabelList(this.$route.query.labelId).then(res => {
          if (res.code === 200) {
            this.isFocus = res.data.isFocus
            this.labelFocusCount = res.data.labelFocusCount
            this.labelArticleCount = res.data.labelArticleCount
            this.labelIconUrl = res.data.labelList[0].labelIconUrl
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        })
      },
      // 默认排序(最新)
      defaultSort() {
        this.$router.push({
          name: 'LabelDetails',
          query: {
            labelId: this.labelId,
            labelName: this.labelName,
            sortType: 'leatest'
          }
        })
        this.loading = true
        this.linkLatestType = 'success'
        this.linkHottestType = 'info'
        // 请求数据
        getLabelDetails(this.$route.query.labelId, this.$route.query.sortType).then(res => {
          if (res.code === 200) {
            this.loading = false
            this.articleList = res.data.articleLists
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        })
      },
      // 最热门排序
      hotSort() {
        this.$router.push({
          name: 'LabelDetails',
          query: {
            labelId: this.labelId,
            labelName: this.labelName,
            sortType: 'hottest'
          }
        })
        this.linkLatestType = 'info'
        this.linkHottestType = 'success'
        this.loading = true
        // 请求数据
        getLabelDetails(this.$route.query.labelId, this.$route.query.sortType).then(res => {
          if (res.code === 200) {
            this.articleList = res.data.articleLists
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        })
      },
      // 判断排序类型
      chooseSortType() {
        if (this.$route.query.sortType === undefined) {
          this.defaultSort()
        } else if (this.$route.query.sortType === 'leatest') {
          this.defaultSort()
        } else if (this.$route.query.sortType === 'hottest') {
          this.hotSort()
        }
      },
      // 取消关注
      removeFocus() {
        this.$confirm('此操作将取消关注,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteLabelFocusOne(this.labelId).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              this.refreshPage()
              this.chooseSortType()
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取该操作')
        })
      },
      // 添加关注
      submitFocus() {
        insertLabelFocus(this.labelId).then(res => {
          if (res.code === 200) {
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 跳转文章详情页面
      linkToArticleDetails(item) {
        // 反正el-image导致页面卡死
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
        insertArticleBrowse(item.articleDetails.articleId).then(res => {
          if (res.code === 200) {
            const routeUrl = this.$router.resolve({
              name: 'ArticleDetails',
              query: { articleId: item.articleDetails.articleId }
            })
            window.open(routeUrl.href, '_blank')
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 点赞文章
      submitArticlePraise(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          addArticlePraise(item.articleId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.chooseSortType()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 取消文章的点赞
      removeArticlePraise(item) {
        this.$confirm('此操作将取消点赞,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log(item.articleId)
          deleteArticlePraise(item.articleId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.chooseSortType()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
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

  .labelDetails-title {
    width: 100%;
    height: 150px;
    margin-top: 0px;
    background-color: #fff;

    .labelDetails-hr {
      margin-top: 0px;
      margin-bottom: 0px;
      border: 1px solid #ECF0F1;
    }
  }

  .labelDetails-sort {
    width: 980px;
    height: 70px;
    background-color: #fff;
    margin-top: 10px;

    .labelDetails-sort-img {
      margin-top: 15px;
      height: 40px;
      width: 40px;
    }
  }

  .articleDetails-sort-box {
    width: 980px;
    min-height: 700px;
    margin-top: 15px;
    background-color: #fff;
    margin-bottom: 20px;
    padding: 20px;

    .articleDetails-sort-content {
      max-height: 140px;
      background-color: #fff;
      margin-top: 20px;
    }
  }
</style>
