<template>
    <div align="center">
      <!-- 标题区域 -->
      <div class="colDetails-title">
        <h1>{{ collectionParams.sysCollectionName }}({{ collectionParams.articleCount }}篇)</h1>
      </div>
      <!-- 返回区域 -->
      <div class="colDetails-back">
        <el-link style="margin-left: -785px" :underline="false" type="success" @click="linkToCollection">
          <i class="el-icon-caret-left"></i>
          更多 <img class="colDetails-back-img" :src="userList.sysUserIcon" />
          {{ userList.sysUserNickname }} 的收藏集
        </el-link>
      </div>
      <!-- 文章详情区域 -->
      <div
        v-loading="loading"
        element-loading-text="拼命加载中......"
        elment-loading-spinner="el-icon-loading"
        class="colDetails-box">
        <div v-if="articleList.length === 0">
          <div>
            <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
          </div>
          <br>
          <div>
            <h4>该收藏集空空如也!</h4>
          </div>
        </div>
        <div
          v-for="(item, index) in articleList"
          :key="index"
          class="colDetails-article"
          @mouseenter="enter(index)"
          @mouseleave="leave"
          >
          <el-divider v-if="index !== 0"></el-divider>
          <el-row :gutter="24">
            <!-- 左边区域 -->
            <el-col style="margin-left: 20px;margin-top: -20px"  :span="14">
              <el-row :gutter="24">
                <el-col :span="20">
                  <el-link style="float: left" type="info" :underline="false" @click="linkToBlog(item)">
                    <h5>{{ item.userNickName }} · </h5>
                  </el-link>
                  <el-link style="float: left"  type="info" :underline="false" @click="linkToArticleDetails(item)">
                    <h5>
                      {{ item.articleDetails.created | parseTime('{y}') }}年{{ item.articleDetails.created | parseTime('{m}') }}月{{ item.articleDetails.created | parseTime('{d}') }}日 ·
                    </h5>
                  </el-link>
                  <el-link style="float: left"  type="info" :underline="false" @click="linkToLabelDetails(item)">
                    <h5>{{ item.articleDetails.labelName }}</h5>
                  </el-link>
                </el-col>
              </el-row>
              <!-- 文章标题 -->
              <el-row :gutter="24">
                <el-col :span="10">
                  <el-link style="float: left" :underline="false" @click="linkToArticleDetails(item)">
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
                    <!-- 取消收藏 -->
                    <el-badge v-if="isDeleteCollection === index" style="float: left;margin-top: -10px;">
                      <el-button type="info" size="mini" plain @click="removeArticleToCollection(item)">
                        取消收藏
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
                ></el-image>
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
  import { getUserDetails } from '@/api/system'
  import { getCollectionListById, deleteArticleToCollection } from '@/api/collection'
  import { insertArticleBrowse, deleteArticlePraise, addArticlePraise } from '@/api/homePageArticle'
  import vueToTop from 'vue-totop'
  export default {
    name: 'CollectionDetails',
    components: { vueToTop },
    data() {
      return {
        loading: false,
        // 是否显示取消收藏按钮
        isDeleteCollection: -1,
        collectionParams: {
          sysCollectionId: '',
          sysCollectionName: '',
          articleCount: 0
        },
        userList: [],
        // 文章详情参数
        articleLists: [],
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
      this.refreshPage()
      this.collectionParams.sysCollectionId = localStorage.getItem('sysCollectionId')
      this.collectionParams.sysCollectionName = localStorage.getItem('sysCollectionName')
      this.collectionParams.articleCount = localStorage.getItem('articleCount')
      if (this.collectionParams.articleCount === '' || this.collectionParams.articleCount === null) {
        this.collectionParams.articleCount = 0
      }
      this.getCollectionInfo()
    },
    methods: {
      refreshPage() {
        getUserDetails().then(res => {
          this.userList = res.data.getList
        })
      },
      // 返回更多收藏集
      linkToCollection() {
        this.$router.push({ path: '/collection' })
      },
      // 查看文章详情
      linkToCollectionDetails() {
        console.log('打开新页面')
        const routeUrl = this.$router.resolve({
          name: 'ArticleDetails', query: { articleId: 2 }
        })
        window.open(routeUrl.href, '_blank')
      },
      // 鼠标移入
      enter(index) {
        this.isDeleteCollection = index
      },
      // 鼠标移出
      leave() {
        this.isDeleteCollection = -1
      },
      // 鼠标单击事件
      click() {
        console.log('鼠标单击了')
      },
      // 获取收藏集合列表详情
      getCollectionInfo() {
        this.loading = true
        getCollectionListById(this.collectionParams.sysCollectionId).then(res => {
          if (res.code === 200) {
            this.articleList = res.data.articleLists
            this.loading = false
          } else {
            this.loading = false
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
      // 跳转标签详情页面
      linkToLabelDetails(item) {
        const routeUrl = this.$router.resolve({
          name: 'LabelDetails',
          query: { labelId: item.articleDetails.labelId, labelName: item.articleDetails.labelName }
        })
        window.open(routeUrl.href, '_blank')
      },
      // 点赞文章
      submitArticlePraise(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          addArticlePraise(item.articleId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.getCollectionInfo()
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
              this.getCollectionInfo()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 取消文章的收藏
      removeArticleToCollection(item) {
        this.$confirm('此操作将删除该对应的收藏文章且不可恢复，是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteArticleToCollection(item.articleId, this.$route.query.sysCollectionId).then(res => {
            if (res.code === 200) {
              this.refreshPage()
              this.getCollectionInfo()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('取消收藏成功')
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
  .colDetails-title {
    margin-top: 20px;
    color: #2ECC71;

    .colDetails-title-link {
      margin-left: -220px;
      margin-top: 27px;
    }
  }

  .colDetails-back {
    width: 980px;
    height: 50px;

    .colDetails-back-img {
      width: 15px;
      height: 15px;
      border-radius: 50%;
    }
  }

  .colDetails-box {
    background-color: #fff;
    width: 980px;
    min-height: 750px;
    padding: 20px;
  }

  .colDetails-article {
    max-height: 140px;
    background-color: #fff;
    margin-top: 20px;
  }
</style>
