<template>
  <div style="margin-bottom: 20px" align="center">
    <div class="message">
      <!-- 选择模块 -->
      <div class="message-choose">
        <!-- 评论 -->
        <div>
          <el-button
            v-if="this.$route.query.messageType === 'comment'"
            class="message-choose-button"
            type="success"
            >
            <svg-icon icon-class="pinglun01"></svg-icon>
            评论
          </el-button>
          <el-button
            v-if="this.$route.query.messageType !== 'comment'"
            class="message-choose-button"
            type="success"
            plain
            @click="chooseComment"
          >
            <svg-icon icon-class="pinglun01"></svg-icon>
            评论
          </el-button>
        </div>
        <!-- 点赞 -->
        <div>
          <el-button
            v-if="this.$route.query.messageType === 'praise'"
            class="message-choose-button"
            type="success"
          >
            <svg-icon icon-class="praise01"></svg-icon>
            点赞
          </el-button>
          <el-button
            v-if="this.$route.query.messageType !== 'praise'"
            class="message-choose-button"
            type="success"
            plain
            @click="choosePraise"
          >
            <svg-icon icon-class="praise01"></svg-icon>
            点赞
          </el-button>
        </div>
        <!-- 系统通知 -->
        <div>
        </div>
      </div>
      <!-- 内容模块 -->
      <div class="message-content">
        <!-- 评论内容 -->
        <div
          class="message-content-comment"
          v-if="this.$route.query.messageType === 'comment'"
          >
          <div v-if="commentsLists.length === 0">
            <div>
              <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
            </div>
            <br>
            <div>
              <h4>这里什么也没有!</h4>
            </div>
          </div>
          <div
            v-for="(item, index) in commentsLists"
            :key="index"
            >
            <el-divider v-if="index !== 0"></el-divider>
            <el-row :gutter="24">
              <!-- 头像 -->
              <el-col :span="4">
                <el-image class="message-content-img" :src="item.sysUserIcon" @click="linkToBlog(item)"></el-image>
              </el-col>
              <!-- 具体评论详情 -->
              <el-col :span="18">
                <el-row :gutter="24">
                  <el-col :span="23">
                    <!-- 用户昵称 -->
                    <el-link
                      style="float: left;color: black;margin-top: -10px;"
                      :underline="false"
                      type="info"
                      @click="linkToBlog(item)"
                      >
                      <h4>{{ item.sysUserNickname }} </h4>
                    </el-link>
                    <h5
                      v-if="item.type === 'reply'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      回复了你在文章
                    </h5>
                    <h5
                      v-if="item.type === 'comment'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      评论了你的文章
                    </h5>
                    <!-- 文章标题 -->
                    <el-link
                      style="float: left;margin-top: -10px;margin-left: 5px;"
                      :underline="false"
                      type="success"
                      @click="linkToArticleCommentsDetails(item)"
                    >
                      <h4>{{ item.articleTitle }} </h4>
                    </el-link>
                    <h5
                      v-if="item.type === 'reply'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      下的评论
                    </h5>
                  </el-col>
                </el-row>
                <!-- 评论内容 -->
                <el-row :gutter="24">
                  <el-col :span="20">
                    <p
                      class="content"
                      style="float: left;margin-top: -10px;font-size: 17px;color: #909399;"
                      >
                      {{ item.content }}
                    </p>
                  </el-col>
                </el-row>
                <!-- 时间 -->
                <el-row :gutter="24">
                  <el-col :span="20">
                    <h4 style="float: left; color: #909399;margin-top: -5px;">
                      {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日 {{ item.created | parseTime('{h}:{m}:{s}')}}
                    </h4>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </div>
        </div>
        <!-- 点赞内容 -->
        <div
          class="message-content-praise"
          v-if="this.$route.query.messageType === 'praise'"
          >
          <div v-if="praiseLists.length === 0">
            <div>
              <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
            </div>
            <br>
            <div>
              <h4>这里什么也没有!</h4>
            </div>
          </div>
          <div
            v-for="(item, index) in praiseLists"
            :key="index"
            >
            <el-divider v-if="index !== 0"></el-divider>
            <el-row :gutter="24">
              <!-- 头像 -->
              <el-col :span="4">
                <el-image class="message-content-img" :src="item.sysUserIcon" @click="linkToBlog(item)"></el-image>
              </el-col>
              <!-- 具体点赞详情 -->
              <el-col :span="19">
                <el-row :gutter="24">
                  <el-col :span="23">
                    <!-- 用户昵称 -->
                    <el-link
                      style="float: left;color: black;margin-top: -10px;"
                      :underline="false"
                      type="info"
                      @click="linkToBlog(item)"
                    >
                      <h4>{{ item.sysUserNickname }} </h4>
                    </el-link>
                    <h5
                      v-if="item.type === 'reply'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      点赞了你在文章
                    </h5>
                    <h5
                      v-if="item.type === 'comment'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      点赞了你在文章
                    </h5>
                    <h5
                      v-if="item.type === 'article'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      点赞了你的文章
                    </h5>
                    <!-- 文章标题 -->
                    <el-link
                      style="float: left;margin-top: -10px;margin-left: 5px;"
                      :underline="false"
                      type="success"
                      @click="linkToArticleCommentsDetails(item)"
                    >
                      <h4>{{ item.articleTitle }} </h4>
                    </el-link>
                    <h5
                      v-if="item.type === 'reply'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      下的回复
                    </h5>
                    <h5
                      v-if="item.type === 'comment'"
                      style="margin-top: 10px; float: left;color: #909399;margin-left: 5px;">
                      下的评论
                    </h5>
                  </el-col>
                </el-row>
                <!-- 时间 -->
                <el-row :gutter="24">
                  <el-col :span="20">
                    <h4 style="float: left; color: #909399;margin-top: -5px;">
                      {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日 {{ item.created | parseTime('{h}:{m}:{s}')}}
                    </h4>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </div>
        </div>
        <!-- 系统通知 -->
        <div>
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
  import { getMessageCommentsLists, getMessagePraiseLists } from '@/api/message'
  import { insertArticleBrowse } from '@/api/homePageArticle'
  export default {
    name: 'Message',
    components: { vueToTop },
    data() {
      return {
        loading: false,
        // 评论列表
        commentsLists: [
          {
            articleTitle: '',
            articleId: '',
            sysUserIcon: '',
            sysUserId: '',
            sysUserNickname: '',
            created: '',
            content: '',
            type: ''
          }
        ],
        // 点赞列表
        praiseLists: [
          {
            articleTitle: '',
            sysUserIcon: '',
            created: '',
            articleId: '',
            sysUserId: '',
            sysUserNickname: '',
            type: ''
          }
        ]
      }
    },
    created() {
      this.setDefaultMessageType()
      this.refreshPage()
    },
    methods: {
      // 判断消息类型是否是undefined,是则赋予comment
      setDefaultMessageType() {
        if (this.$route.query.messageType === undefined) {
          this.$router.push({
            name: 'Message',
            query: {
              messageType: 'comment'
            }
          })
          window.location.reload()
        }
      },
      // 刷新数据
      refreshPage() {
        if (this.$route.query.messageType === 'comment') {
          getMessageCommentsLists().then(res => {
            if (res.code === 200) {
              this.commentsLists = res.data.commentsLists
            } else {
              this.$message.error(res.message)
            }
          })
        }
        if (this.$route.query.messageType === 'praise') {
          getMessagePraiseLists().then(res => {
            if (res.code === 200) {
              this.praiseLists = res.data.praiseLists
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 选择点赞
      choosePraise() {
        this.$router.push({
          name: 'Message',
          query: {
            messageType: 'praise'
          }
        })
        window.location.reload()
      },
      // 选择评论
      chooseComment() {
        this.$router.push({
          name: 'Message',
          query: {
            messageType: 'comment'
          }
        })
        window.location.reload()
      },
      // 跳转用户主页
      linkToBlog(item) {
        // 防止单击el-image导致页面卡死
        document.body.style = null
        const routeUrl = this.$router.resolve({
          name: 'Blog',
          query: {
            sysUserId: item.sysUserId,
            blogType: 'article'
          }
        })
        window.open(routeUrl.href, '_blank')
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
      }
    }
  }
</script>

<style lang="scss" scoped>
  .message{
    /*background-color: #fff;*/
    background-color: #ECF0F1;
    width: 960px;
    min-height: 700px;
    margin-top: 20px;

    .message-choose {
      position: fixed;
      background-color: #fff;
      min-height: 100px;
      width: 220px;

      .message-choose-button {
        width: 100%;
        height: 55px;
        color: black;
        font-size: 20px;
      }
    }

    .message-content {
      background-color: #fff;
      width: 700px;
      min-height: 700px;
      padding: 10px;
      margin-left: 260px;

      .message-content-comment {
        background-color: #fff;
        width: 100%;
        min-height: 400px;
        padding: 20px;
      }

      .message-content-img {
        width: 70px;
        height: 70px;
        border-radius: 50%;
      }

      .message-content-praise {
        background-color: #fff;
        width: 100%;
        min-height: 400px;
        padding: 20px;
      }
    }
  }
</style>
