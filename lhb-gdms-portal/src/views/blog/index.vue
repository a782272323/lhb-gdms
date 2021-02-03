<template>
  <div style="margin-bottom: 20px" align="center">
    <div class="blog">
      <!-- 用户详情区域 -->
      <div class="blog-user">
        <el-row :gutter="24">
          <!-- 用户头像 -->
          <el-col :span="4">
            <el-image class="blog-user-img" :src="homeUserLists[0].sysUserIcon"></el-image>
          </el-col>
          <!-- 用户昵称 -->
          <el-col :span="15">
            <el-row :gutter="24">
              <el-col :span="20">
                <h2 style="float: left;margin-top: 0px;">{{ homeUserLists[0].sysUserNickname }}</h2>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col style="font-size: 18px;"  :span="4">
                <h5 style="margin-bottom: 0px;margin-top: 0px;">{{ homeUserLists[0].userArticleCountAll }}</h5>
                <h5 style="margin-top: 5px;">文章</h5>
              </el-col>
              <el-col :span="1">
                <div class="blog-user-divToHr"></div>
              </el-col>
              <el-col style="font-size: 18px;" :span="5">
                <h5 style="margin-bottom: 0px;margin-top: 0px;">{{ homeUserLists[0].userArticlePraiseCountAll }}</h5>
                <h5 style="margin-top: 5px;">收获点赞</h5>
              </el-col>
              <el-col :span="1">
                <div class="blog-user-divToHr"></div>
              </el-col>
              <el-col style="font-size: 18px;" :span="6">
                <h5 style="margin-bottom: 0px;margin-top: 0px;">{{ homeUserLists[0].userArticleBrowseCountAll }}</h5>
                <h5 style="margin-top: 5px;">文章被浏览</h5>
              </el-col>
              <el-col :span="1">
                <div class="blog-user-divToHr"></div>
              </el-col>
              <el-col style="font-size: 18px;" :span="4">
                <h5 style="margin-bottom: 0px;margin-top: 0px;">{{ homeUserLists[0].userCommentsCountAll }}</h5>
                <h5 style="margin-top: 5px;">评论</h5>
              </el-col>
            </el-row>
          </el-col>
          <!-- 按钮 -->
          <el-col :span="4">
            <el-button
              v-if="isUser === true"
              class="blog-user-button"
              type="success"
              @click="linkToProfile"
              >
              编辑个人资料
            </el-button>
            <el-button
              v-if="isLogin === true && isFocus == 'true'"
              class="blog-user-button"
              type="success"
              @click="removeHomeUserPersonFocus"
              >
              已关注
            </el-button>
            <el-button
              v-if="isLogin === true && isFocus == 'false' && isUser !== true"
              class="blog-user-button"
              type="success"
              plain
              @click="submitHomeUserPersonFocus(homeUserLists[0].sysUserId)"
            >
              未关注
            </el-button>
            <el-button
              v-if="isLogin === false"
              class="blog-user-button"
              type="success"
              plain
              @click="linkToLogin"
            >
              未关注
            </el-button>
          </el-col>
        </el-row>
      </div>
      <!-- 关注的人和粉丝区域 -->
      <div class="blog-focus">
        <el-row :gutter="24">
          <el-col :span="11">
            <h4 v-if="isUser === false" style="margin-bottom: 0px;">Ta的关注</h4>
            <h4 v-if="isUser === true" style="margin-bottom: 0px;">你的关注</h4>
            <h4 style="margin-top: 10px;">
              {{ homeUserLists[0].userFocusPersonCountAll }}
            </h4>
          </el-col>
          <el-col :span="2">
            <div class="blog-focus-divToHr"></div>
          </el-col>
          <el-col :span="10">
            <h4 style="margin-bottom: 0px;">粉丝</h4>
            <h4 style="margin-top: 10px;">
              {{ homeUserLists[0].userFocusFansCountAll }}
            </h4>
          </el-col>
        </el-row>
      </div>
      <!-- 收藏集，关注的标签，加入于区域 -->
      <div class="blog-other">
        <!-- 收藏集数量 -->
        <el-row style="height: 50px;" :gutter="24">
          <el-col :span="12">
            <h4 style="float: left;margin-left: 10px;">收藏集</h4>
          </el-col>
          <el-col :span="12">
            <h4 style="float: right;margin-right: 10px;">
              {{ homeUserLists[0].userCollectionCountAll }}
            </h4>
          </el-col>
        </el-row>
        <!-- 关注标签的数量 -->
        <hr style="margin-top: 10px;margin-bottom: -10px;border: 1px solid #ECF0F1;">
        <el-row style="height: 50px;" :gutter="24">
          <el-col :span="12">
            <h4 style="float: left;margin-left: 10px;">关注标签</h4>
          </el-col>
          <el-col :span="12">
            <h4 style="float: right;margin-right: 10px;">
              {{ homeUserLists[0].userFocusLabelCountAll }}
            </h4>
          </el-col>
        </el-row>
        <!-- 加入于 -->
        <hr style="margin-top: 20px;margin-bottom: -20px;border: 1px solid #ECF0F1;">
        <el-row style="height: 50px;" :gutter="24">
          <el-col :span="12">
            <h4 style="float: left;margin-left: 10px;">加入于</h4>
          </el-col>
          <el-col :span="12">
            <h4 style="float: right;margin-right: 10px;">
              {{ homeUserLists[0].created | parseTime('{y}') }}-{{ homeUserLists[0].created | parseTime('{m}') }}-{{ homeUserLists[0].created | parseTime('{d}') }}
            </h4>
          </el-col>
        </el-row>
      </div>
      <!-- 主页内容区域 -->
      <div class="focus-main">
        <!-- 导航栏区域 -->
        <div class="focus-main-nav">
          <div style="float: left;">
            <el-menu
              style="width: 400px;"
              mode="horizontal"
              :default-active="this.$route.query.blogType"
              active-text-color="#2ECC71"
              >
              <el-menu-item index="article" @click="linkToArticleAttribute">
                文章
              </el-menu-item>
              <el-menu-item v-if="isUser === true" index="dynamic" @click="linkToDynamicAttribute">
                动态
              </el-menu-item>
              <el-menu-item v-if="isUser === true" index="comment" @click="linkToCommentAttribute">
                评论
              </el-menu-item>
              <el-menu-item v-if="isUser === true" index="praise" @click="linkToPraiseAttribute">
                点赞
              </el-menu-item>
            </el-menu>
          </div>
        </div>
        <hr style="margin-top: 0px;margin-bottom: 0px;border: 1px solid #ECF0F1;">
        <!-- 内容区域 -->
        <div class="focus-main-content">
          <!-- 动态 -->
          <div v-if="this.$route.query.blogType === 'dynamic'" class="focus-main-content-dynamic">
            <div v-if="dynamicLists.length === 0">
              <div>
                <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
              </div>
              <br>
              <div>
                <h4>这里什么也没有!</h4>
              </div>
            </div>
            <div
              v-for="(item, index) in dynamicLists"
              :key="index"
              >
              <el-divider v-if="index !== 0"></el-divider>
              <el-row :gutter="24">
                <el-col :span="4">
                  <el-image class="focus-main-img" :src="homeUserLists[0].sysUserIcon"></el-image>
                </el-col>
                <el-col :span="18">
                  <div
                    v-if="item.type === 'user'"
                    style="margin-top: -5px; margin-left: -15px;"
                    >
                    <el-link style="float: left;color: black;" type="info" :underline="false">
                      <h2>{{ homeUserLists[0].sysUserNickname }}</h2>
                    </el-link>
                    <h3 style="float: left;margin-left: 10px;color: #909399;">关注了用户</h3>
                    <el-link style="float: left;margin-left: 10px;" type="success" :underline="false" @click="linkToBlog(item)">
                      <h2>{{ item.name }}</h2>
                    </el-link>
                  </div>
                  <div
                    v-if="item.type === 'label'"
                    style="margin-top: -5px; margin-left: -15px;"
                  >
                    <el-link style="float: left;color: black;" type="info" :underline="false">
                      <h2>{{ homeUserLists[0].sysUserNickname }}</h2>
                    </el-link>
                    <h3 style="float: left;margin-left: 10px;color: #909399;">关注了标签</h3>
                    <el-link style="float: left;margin-left: 10px" type="success" :underline="false" @click="linkToLabelDetails(item)">
                      <h2>{{ item.name }}</h2>
                    </el-link>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 文章 -->
          <div v-if="this.$route.query.blogType === 'article'" class="focus-main-content-article">
            <div v-if="articleLists.length === 0">
              <div>
                <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
              </div>
              <br>
              <div>
                <h4>这里什么也没有!</h4>
              </div>
            </div>
            <div
              v-for="(item, index) in articleLists"
              :key="index"
              @mouseenter="enter(index)"
              @mouseleave="leave"
              >
              <el-divider v-if="index !== 0"></el-divider>
              <el-row :gutter="24">
                <!-- 左边区域 -->
                <el-col style="margin-left: 20px;" :span="19">
                  <el-row :gutter="24">
                    <el-col :span="20">
                      <el-link style="float: left;" type="info" :underline="false">
                        <h5> {{ homeUserLists[0].sysUserNickname }} · </h5>
                      </el-link>
                      <el-link style="float: left;" type="info" :underline="false">
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
                        <!-- 编辑文章 -->
                        <el-badge v-if="isArticleEditor === index" style="float: left;margin-top: -10px;margin-left: 5px;">
                          <el-button type="info" size="mini" plain icon="el-icon-edit" @click="editArticle(item)">
                          </el-button>
                        </el-badge>
                        <!-- 删除文章 -->
                        <el-badge v-if="isArticleDelete === index" style="float: left;margin-top: -10px;margin-left: 5px;">
                          <el-button type="danger" size="mini" plain icon="el-icon-delete" @click="removeArticle(item)">
                          </el-button>
                        </el-badge>
                      </el-col>
                    </div>
                  </el-row>
                </el-col>
                <!-- 右边区域 -->
                <el-col v-if="item.articleImgUrl" :span="4">
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
          <!-- 评论 -->
          <div v-if="this.$route.query.blogType === 'comment'" class="focus-main-content-comment">
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
                <el-col :span="4">
                  <el-image style="height: 80px;width: 80px;border-radius: 50%;margin-top: 15px;" :src="homeUserLists[0].sysUserIcon"></el-image>
                </el-col>
                <el-col :span="20">
                  <el-row :gutter="24">
                    <div
                      v-if="item.type === 'comment'">
                      <el-col :span="20">
                        <el-link style="float: left;color: black;margin-left: -15px;" type="info" :underline="false">
                          <h4>{{ homeUserLists[0].sysUserNickname }}</h4>
                        </el-link>
                        <h5 style="float: left;margin-left: 10px;color: #909399;margin-top: 18px;">
                          发表了在文章
                        </h5>
                        <el-link style="float: left;margin-left: 10px;" type="success" :underline="false" @click="linkToArticleCommentsDetails(item)">
                          <h4>{{ item.title }}</h4>
                        </el-link>
                        <h5 style="float: left;margin-left: 10px;color: #909399;margin-top: 18px;">
                          下的评论
                        </h5>
                      </el-col>
                    </div>
                    <div v-if="item.type === 'reply'">
                      <el-col :span="20">
                        <el-link style="float: left;color: black;margin-left: -15px;" type="info" :underline="false">
                          <h4>{{ homeUserLists[0].sysUserNickname }}</h4>
                        </el-link>
                        <h5 style="float: left;margin-left: 10px;color: #909399;margin-top: 18px;">
                          回复了在文章
                        </h5>
                        <el-link style="float: left;margin-left: 10px;" type="success" :underline="false" @click="linkToArticleCommentsDetails(item)">
                          <h4>{{ item.title }}</h4>
                        </el-link>
                        <h5 style="float: left;margin-left: 10px;color: #909399;margin-top: 18px;">
                          下的评论
                        </h5>
                      </el-col>
                    </div>
                  </el-row>
                  <!-- 评论内容详情 -->
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <el-link style="float: left;" type="info" :underline="false">
                        <p style="margin-top: -10px;margin-left: 5px;">{{ item.commentsContent }}</p>
                      </el-link>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <h4 style="float: left; color: #909399;margin-top: -5px;margin-left: -15px;">
                        {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日 {{ item.created | parseTime('{h}:{m}:{s}')}}
                      </h4>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 点赞 -->
          <div v-if="this.$route.query.blogType === 'praise'" class="focus-main-content-praise">
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
                <el-col :span="4">
                  <el-image class="focus-main-img" :src="homeUserLists[0].sysUserIcon"></el-image>
                </el-col>
                <el-col :span="20">
                  <el-row :gutter="24">
                    <el-col :span="22">
                      <div
                        v-if="item.type === 'article'"
                        style="margin-top: 0px; margin-left: -25px;"
                        >
                        <el-link
                          style="float: left;color: black;"
                          :underline="false"
                          type="info">
                          <h3>{{ homeUserLists[0].sysUserNickname }}</h3>
                        </el-link>
                        <h4 style="float: left;margin-left: 10px;color: #909399;margin-top: 15px;">
                           点赞了文章
                        </h4>
                        <el-link
                          style="float: left;margin-left: 10px;"
                          :underline="false"
                          type="success"
                          @click="linkToArticleDetails(item)"
                          >
                          <h3>{{ item.title }}</h3>
                        </el-link>
                      </div>
                      <div
                        v-if="item.type === 'comment' || item.type === 'reply'"
                        style="margin-top: 0px; margin-left: -25px;"
                        >
                        <el-link
                          style="float: left;color: black;"
                          :underline="false"
                          type="info">
                          <h3>{{ homeUserLists[0].sysUserNickname }}</h3>
                        </el-link>
                        <h4 style="float: left;margin-left: 10px;color: #909399;margin-top: 15px;">
                          点赞了文章的评论
                        </h4>
                        <el-link
                          style="float: left;margin-left: 10px;"
                          :underline="false"
                          type="success"
                          @click="linkToArticleCommentsDetails(item)"
                          >
                          <h3>{{ item.title }}</h3>
                        </el-link>
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
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
  import { getToken } from '@/utils/auth'
  import vueToTop from 'vue-totop'
  import { getHomePageUserLists, checkFocusUser, getHomeUserDetails, deleteHomeFocusPerson } from '@/api/homePageUser'
  import { insertFocusPerson } from '@/api/focus'
  import { insertArticleBrowse, addArticlePraise, deleteArticlePraise, deleteArticle } from '@/api/homePageArticle'
  export default {
    name: 'Blog',
    components: { vueToTop },
    data() {
      return {
        isLogin: false,
        // 页面数据加载
        loading: false,
        // 判断当前用户主页是否是当前登录的用户主页
        isUser: false,
        // 当前登录用户是否关注了某一位用户
        isFocus: 'false',
        // 主页类型 article comment praise dynamic
        blogType: '',
        // 用户基本数据列表,
        userId: '',
        // 显示文章是否编辑
        isArticleEditor: -1,
        // 显示文章是否删除
        isArticleDelete: -1,
        homeUserLists: [
          {
            sysUserEmail: '',
            userFocusPersonCountAll: '',
            created: '',
            sysUserIntroduction: '',
            userArticlePraiseCountAll: '',
            userFocusLabelCountAll: '',
            userArticleCountAll: '',
            userArticleBrowseCountAll: '',
            sysUserPhone: '',
            sysUserIcon: '',
            sysUserStatus: '',
            userCollectionCountAll: '',
            sysUserId: '',
            sysUserNickname: '',
            sysUserType: '',
            userCommentsCountAll: '',
            userFocusFansCountAll: '',
            sysUserAreaCode: '',
            updated: '',
            sysUserSex: '',
            sysUserUsername: ''
          }
        ],
        // 文章列表
        articleLists: [
          {
            articleTitle: '',
            created: '',
            articleId: '',
            articleImgUrl: '',
            labelId: '',
            articleType: '',
            articleContent: '',
            articleBrowseSum: '',
            articlePraiseCount: '',
            isPraise: '',
            sysUserId: '',
            articleCommentsCount: '',
            articleImgKey: '',
            labelName: '',
            updated: ''
          }
        ],
        // 动态列表
        dynamicLists: [
          {
            created: '',
            id: '',
            name: '',
            type: ''
          }
        ],
        // 评论列表
        commentsLists: [
          {
            created: '',
            commentsContent: '',
            id: '',
            title: '',
            type: ''
          }
        ],
        // 点赞列表
        praiseLists: [
          {
            created: '',
            articleId: '',
            praiseId: '',
            sysUserId: '',
            title: '',
            type: ''
          }
        ]
      }
    },
    created() {
      this.checkLogin()
      console.log(this.$route.query.sysUserId)
      if (this.$route.query.sysUserId === undefined) {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: localStorage.getItem('sysUserId'),
            blogType: 'article'
          }
        })
        window.location.reload()
      }
      if (this.$route.query.blogType === undefined) {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: this.$route.query.sysUserId,
            blogType: 'article'
          }
        })
        window.location.reload()
      }
    },
    methods: {
      // 鼠标移入事件
      enter(index) {
        if (this.isUser === true) {
          this.isArticleEditor = index
          this.isArticleDelete = index
        }
      },
      // 鼠标移出事件
      leave() {
        this.isArticleEditor = -1
        this.isArticleDelete = -1
      },
      // 判断是否登录
      checkLogin() {
        const hashToken = getToken()
        if (hashToken) {
          this.isLogin = true
          // 获取当前登录用户id
          if (this.$route.query.sysUserId === undefined) {
            this.$router.push({
              name: 'Blog',
              query: {
                sysUserId: localStorage.getItem('sysUserId')
              }
            })
          }
          // 获取页面不需要登录数据
          this.refreshPage()
        } else {
          this.refreshPage()
          this.isLogin = false
        }
      },
      // 获取页面不需要登录数据
      refreshPage() {
        // 获取主页用户相关数据列表详情
        getHomePageUserLists(this.$route.query.sysUserId).then(res => {
          if (res.code === 200) {
            this.homeUserLists = res.data.homeUserLists
            // 当前用户主页为登录的用户
            if (parseInt(localStorage.getItem('sysUserId')) === this.homeUserLists[0].sysUserId) {
              this.isUser = true
            }
            // 判断是否登录来设置标题
            if (this.isLogin === false) {
              document.title = this.homeUserLists[0].sysUserNickname + ' 的个人主页'
            }
            if (this.isLogin === true && this.isUser === false) {
              document.title = this.homeUserLists[0].sysUserNickname + ' 的个人主页'
              // 校验当前登录用户是否关注了某一位用户
              this.checkFocus(this.$route.query.sysUserId)
            }
            if (this.$route.query.blogType === 'dynamic' && this.isUser === false) {
              this.$router.push({
                name: 'Blog',
                query: {
                  sysUserId: this.$route.query.sysUserId,
                  blogType: 'article'
                }
              })
              window.location.reload()
            }
            if (this.$route.query.blogType === 'comment' && this.isUser === false) {
              this.$router.push({
                name: 'Blog',
                query: {
                  sysUserId: this.$route.query.sysUserId,
                  blogType: 'article'
                }
              })
              window.location.reload()
            }
            if (this.$route.query.blogType === 'praise' && this.isUser === false) {
              this.$router.push({
                name: 'Blog',
                query: {
                  sysUserId: this.$route.query.sysUserId,
                  blogType: 'article'
                }
              })
              window.location.reload()
            }
          } else {
            this.$message.error(res.message)
          }
        })
        // 用户主页数据详情
        this.userId = localStorage.getItem('sysUserId')
        if (localStorage.getItem('sysUserId') === null) {
          this.userId = -1
        }
        getHomeUserDetails(this.$route.query.sysUserId, this.userId, this.$route.query.blogType).then(res => {
          if (this.$route.query.blogType === 'article') {
            this.articleLists = res.data.articleLists
          }
          if (this.$route.query.blogType === 'dynamic') {
            this.dynamicLists = res.data.dynamicLists
          }
          if (this.$route.query.blogType === 'comment') {
            this.commentsLists = res.data.commentsLists
          }
          if (this.$route.query.blogType === 'praise') {
            this.praiseLists = res.data.praiseLists
          }
        })
      },
      // 校验当前登录用户是否关注了某一位用户
      checkFocus(formSysUserId) {
        checkFocusUser(formSysUserId).then(res => {
          if (res.code === 200) {
            this.isFocus = res.isFocus
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 跳转设置
      linkToProfile() {
        this.$router.push({
          name: 'Profile'
        })
      },
      // 跳转文章属性
      linkToArticleAttribute() {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: this.$route.query.sysUserId,
            blogType: 'article'
          }
        })
        window.location.reload()
      },
      // 跳转评论属性
      linkToCommentAttribute() {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: this.$route.query.sysUserId,
            blogType: 'comment'
          }
        })
        window.location.reload()
      },
      // 跳转点赞属性
      linkToPraiseAttribute() {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: this.$route.query.sysUserId,
            blogType: 'praise'
          }
        })
        window.location.reload()
      },
      // 跳转动态属性
      linkToDynamicAttribute() {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: this.$route.query.sysUserId,
            blogType: 'dynamic'
          }
        })
        window.location.reload()
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
      // 用户主页关注用户
      submitHomeUserPersonFocus(formSysUserId) {
        insertFocusPerson(formSysUserId).then(res => {
          if (res.code === 200) {
            this.checkLogin()
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 用户主页取消用户关注
      removeHomeUserPersonFocus() {
        const confirm = '此操作将取消对 ' + this.homeUserLists[0].sysUserNickname + ' 的关注,是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteHomeFocusPerson(this.$route.query.sysUserId).then(res => {
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
      // 跳转标签详情
      linkToLabelDetails(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          if (this.$route.query.blogType === 'article') {
            const routeUrl = this.$router.resolve({
              name: 'LabelDetails',
              query: {
                labelId: item.labelId,
                labelName: item.labelName
              }
            })
            window.open(routeUrl.href, '_blank')
          }
          if (this.$route.query.blogType === 'dynamic') {
            const routeUrl = this.$router.resolve({
              name: 'LabelDetails',
              query: {
                labelId: item.id,
                labelName: item.name
              }
            })
            window.open(routeUrl.href, '_blank')
          }
        }
      },
      // 跳转用户主页
      linkToBlog(item) {
        const routeUrl = this.$router.resolve({
          name: 'Blog',
          query: {
            sysUserId: item.id,
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
        if (this.$route.query.blogType === 'article' || this.$route.query.blogType === 'praise') {
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
        }
        if (this.$route.query.blogType === 'comment') {
          insertArticleBrowse(item.id).then(res => {
            if (res.code === 200) {
              const routeUrl = this.$router.resolve({
                name: 'ArticleDetails',
                query: { articleId: item.id, jump: 'comment' }
              })
              window.open(routeUrl.href, '_blank')
            } else {
              this.$message.error(res.message)
            }
          })
        }
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
      // 编辑文章
      editArticle(item) {
        // 清楚缓存
        localStorage.removeItem('articleTitle')
        localStorage.removeItem('articleContent')
        localStorage.removeItem('articleType')
        localStorage.removeItem('articleImgUrl')
        localStorage.removeItem('articleImgKey')
        localStorage.removeItem('labelId')
        // 将变量存入缓存
        localStorage.setItem('articleTitle', item.articleTitle)
        localStorage.setItem('articleContent', item.articleContent)
        localStorage.setItem('articleType', item.articleType)
        localStorage.setItem('articleImgUrl', item.articleImgUrl)
        localStorage.setItem('articleImgKey', item.articleImgKey)
        localStorage.setItem('labelId', item.labelId)
        this.$router.push({
          name: 'Article',
          query: {
            articleId: item.articleId
          }
        })
      },
      // 删除文章
      removeArticle(item) {
        const confirm = '此操作将删除你发布的文章 《' + item.articleTitle + '》 且不可恢复，是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteArticle(item.articleId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('取消收藏成功')
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .blog {
    background-color: #ECF0F1;
    width: 960px;
    min-height: 700px;

    .blog-user {
      background-color: #fff;
      min-height: 150px;
      width: 700px;
      margin-top: 20px;
      margin-left: -260px;
      padding: 20px;

      .blog-user-img {
        height: 90px;
        width: 90px;
        border-radius: 50%;
      }

      .blog-user-divToHr {
        width: 1px;
        border: 1px solid black;
        height: 40px;
      }

      .blog-user-button {
        margin-top: 30px;
        /*width: 126px;*/
      }
    }

    .focus-main {
      background-color: #fff;
      width: 700px;
      min-height: 530px;
      margin-left: -260px;
      padding: 20px;
      margin-top: 20px;

      .focus-main-nav {
        width: 100%;
        height: 60px;
      }

      .focus-main-content {
        background-color: #fff;
        width: 100%;
        min-height: 400px;
        padding: 20px;
      }

      .focus-main-content-article {
        background-color: #fff;
        width: 100%;
        min-height: 100px;
      }

      .focus-main-content-dynamic {
        background-color: #fff;
        width: 100%;
        min-height: 80px;
      }

      .focus-main-content-comment {
        background-color: #fff;
        width: 100%;
        min-height: 100px;
      }

      .focus-main-content-praise {
        background-color: #fff;
        width: 100%;
        min-height: 80px;
      }

      .focus-main-img {
        width: 50px;
        height: 50px;
        border-radius: 50%;
      }
    }

    .blog-focus {
      position: fixed;
      background-color: #fff;
      width: 240px;
      height: 80px;
      margin-top: -150px;
      margin-left: 720px;

      .blog-focus-divToHr {
        width: 1px;
        border: 1px solid black;
        height: 60px;
        margin-bottom: 10px;
        margin-top: 10px;
      }
    }

    .blog-other {
      position: fixed;
      background-color: #fff;
      width: 240px;
      min-height: 150px;
      margin-left: 720px;
      margin-top: -50px;
    }
  }
</style>
