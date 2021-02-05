<template>
    <div align="center">
      <div class="fabu-title">
        <svg-icon style="height: 100px;width: 100px;" icon-class="fabu01"></svg-icon>
        <br>
        <div style="margin-top: 20px;">
          发布成功! 如果您能分享文章，Bin博客会变得更美好!
        </div>
      </div>
      <div class="fabu-content">
        <div>
          <el-link style="margin-top: 50px;" :underline="false" type="success" @click="linkToArticleDetails">
            <h2>{{ articleList[0].articleTitle }}</h2>
          </el-link>
        </div>
        <div style="margin-top: 50px;">
          分享到:
          <el-button
            style="margin-left: 10px;color: black;"
            type="success"
            plain
            round
            @click="copyLinkToShareArticle"
            >
            <svg-icon icon-class="link00"></svg-icon>
            复制链接
          </el-button>
        </div>
      </div>
      <div class="fabu-foot">
        <el-link :underline="false" type="success" @click="linkToHome">
          <h3>回到首页</h3>
        </el-link>
      </div>
    </div>
</template>

<script>
  import { getArticleList } from '@/api/writeArticle'
  import { insertArticleBrowse } from '@/api/homePageArticle'
    export default {
      name: 'ArticleFaBu',
      data() {
        return {
          articleList: [
            {
              created: '',
              updated: '',
              articleId: '',
              articleTitle: '',
              articleContent: '',
              articleType: '',
              articleBrowseSum: '',
              articleImgUrl: '',
              articleImgKey: '',
              sysUserId: '',
              labelId: ''
            }
          ],
          // 外链分享
          shareLinkUrl: ''
        }
      },
      created() {
        this.refreshPage()
      },
      methods: {
        refreshPage() {
          getArticleList(this.$route.query.articleId).then(res => {
            if (res.code === 200) {
              this.articleList = res.data.articleList
            } else {
              this.$message.error(res.message)
            }
          })
        },
        // 回到首页
        linkToHome() {
          this.$router.push({ name: 'Home' })
        },
        // 跳转文章详情页面
        linkToArticleDetails() {
          this.addArticleBrowse()
        },
        // 查看文章详情时，使文章被阅读数加一
        addArticleBrowse() {
          insertArticleBrowse(this.$route.query.articleId).then(res => {
            if (res.code === 200) {
              const routeUrl = this.$router.resolve({
                name: 'ArticleDetails',
                query: { articleId: this.$route.query.articleId }
              })
              window.open(routeUrl.href, '_blank')
            } else {
              this.$message.error(res.message)
            }
          })
        },
        // 复制链接分享文章
        copyLinkToShareArticle() {
          this.shareLinkUrl = 'http://localhost:8527/#/articleDetails?articleId=' + this.$route.query.articleId
          console.log('s = ' + this.shareLinkUrl)
          this.$copyText(this.shareLinkUrl).then(res => {
            this.onCopy()
          }).catch(() => {
            this.onError()
          })
        },
        onCopy() {
          this.$message.success('文章链接已经复制到剪贴板!')
        },
        onError() {
          this.$message.error('抱歉,复制失败,请稍后重试!')
        }
      }
    }
</script>

<style scoped>
  .fabu-title {
    margin-top: 50px;
  }

  .fabu-content {
    background-color: #fff;
    height: 300px;
    width: 700px;
    margin-top: 30px;
  }

  .fabu-foot {
    margin-top: 20px;
  }
</style>
