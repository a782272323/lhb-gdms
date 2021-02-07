<template>
    <div style="margin-bottom: 20px" align="center">
      <div class="search">
        <!-- 左边区域 -->
        <div class="search-left">
          <!-- 搜索分类栏区域 -->
          <div class="search-left-type">
            <!-- 文章 -->
            <div>
              <el-button
                v-if="this.$route.query.searchType !== 'article'"
                class="search-left-type-button"
                type="success"
                plain
                @click="jumpToArticle"
              >
                <svg-icon icon-class="article00"></svg-icon>
                文章
              </el-button>
              <el-button
                v-if="this.$route.query.searchType === 'article'"
                class="search-left-type-button"
                type="success"
              >
                <svg-icon icon-class="article00"></svg-icon>
                文章
              </el-button>
            </div>
            <!-- 用户 -->
           <div>
             <el-button
               v-if="this.$route.query.searchType !== 'user'"
               class="search-left-type-button"
               type="success"
               plain
               @click="jumpToUser"
             >
               <svg-icon icon-class="user00"></svg-icon>
               用户
             </el-button>
             <el-button
               v-if="this.$route.query.searchType === 'user'"
               class="search-left-type-button"
               type="success"
             >
               <svg-icon icon-class="user00"></svg-icon>
               用户
             </el-button>
           </div>
            <!-- 分类 -->
            <div>
              <el-button
                v-if="this.$route.query.searchType !== 'label'"
                class="search-left-type-button"
                type="success"
                plain
                @click="jumpToLabel"
              >
                <svg-icon icon-class="label00"></svg-icon>
                标签
              </el-button>
              <el-button
                v-if="this.$route.query.searchType === 'label'"
                class="search-left-type-button"
                type="success"
              >
                <svg-icon icon-class="label00"></svg-icon>
                标签
              </el-button>
            </div>
          </div>
          <!-- 搜索历史记录区域 -->
          <div
            v-if="isShowInputHistory === true"
            class="search-left-history">
            <div>
              <hr style="margin-top: 10px;margin-bottom: -10px;border: 1px solid #ECF0F1;">
              <!-- 头部 -->
              <div>
                <el-row :gutter="24">
                  <el-col :span="10">
                    <h5>最近搜索</h5>
                  </el-col>
                  <el-col :span="14">
                    <el-link
                      style="float: right;margin-top: 3px;"
                      :underline="false"
                      type="success"
                      @click="removeAllInputHistory">
                      <h4>清空</h4>
                    </el-link>
                  </el-col>
                </el-row>
              </div>
              <!-- 内容 -->
              <div
                v-for="(item, index) in inputHistoryLists"
                :key="index"
                >
                <el-card style="height: 30px;width: 180px;margin-bottom: 10px;" shadow="hover">
                  <el-row :gutter="24">
                    <el-col :span="2">
                      <svg-icon style="float: left;margin-top: -13px;margin-left: -10px;" icon-class="history00"></svg-icon>
                    </el-col>
                    <el-col :span="16">
                      <el-link
                        style="color: black;float: left;margin-top: -33px;"
                        type="info"
                        :underline="false"
                        @click="linkToHomeSearch(item)"
                        >
                        <h4>{{ item.sysInputHistoryContent }}</h4>
                      </el-link>
                    </el-col>
                    <el-col :span="2">
                      <el-link
                        style="color: black;margin-top: -33px;"
                        type="info"
                        :underline="false"
                        @click="removeInputHistory(item)"
                      >
                        <svg-icon icon-class="delete01"></svg-icon>
                      </el-link>
                    </el-col>
                  </el-row>
                </el-card>
              </div>
            </div>
          </div>
        </div>
        <!-- 右边区域 -->
        <div class="search-right">
          <!-- 内容区域 -->
          <div class="search-right-content">
            <!-- 文章属性 -->
            <div v-if="this.$route.query.searchType === 'article'">
              <!-- 导航栏 -->
              <div class="search-right-content-nav">
                <div style="float: left;">
                  <el-menu
                    style="width: 400px;"
                    mode="horizontal"
                    :default-active="this.$route.query.articleType"
                    active-text-color="#2ECC71"
                    >
                    <el-menu-item index="all" @click="linkToArticleTypeAll">
                      全部
                    </el-menu-item>
                    <el-menu-item index="days" @click="linkToArticleTypeDays">
                      最近一天
                    </el-menu-item>
                    <el-menu-item index="weeks" @click="linkToArticleTypeWeeks">
                      最近一周
                    </el-menu-item>
                    <el-menu-item index="months" @click="linkToArticleTypeMonths">
                      最近三月
                    </el-menu-item>
                  </el-menu>
                </div>
              </div>
              <hr style="margin-top: 0px;margin-bottom: 0px;border: 1px solid #ECF0F1;">
              <!-- 文章内容 -->
              <div>
                <div v-if="articleLists.length === 0" style="margin-top: 20px;">
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
                  >
                  <el-divider v-if="index !== 0"></el-divider>
                  <el-row :gutter="24">
                    <!-- 左边区域 -->
                    <el-col style="margin-left: 20px;" :span="19">
                      <el-row :gutter="24">
                        <el-col :span="20">
                          <el-link style="float: left;" type="info" :underline="false" @click="linkToBlog(item.sysUserId)">
                            <h5 v-html="brightenKeywordBySearch(item.sysUserNickname)"> · </h5>
                          </el-link>
                          <el-link style="float: left;" type="info" :underline="false">
                            <h5>
                              {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日 ·
                            </h5>
                          </el-link>
                          <el-link style="float: left;" type="info" :underline="false">
                            <h5 v-html="brightenKeywordBySearch(item.labelName)">
                            </h5>
                          </el-link>
                        </el-col>
                      </el-row>
                      <!-- 文章标题 -->
                      <el-row :gutter="24">
                        <el-col :span="20">
                          <el-link style="float: left;" :underline="false" @click="linkToArticleDetails(item)">
                            <h3 style="margin-top: -10px;color: black;" class="article_title" v-html="brightenKeywordBySearch(item.articleTitle)"></h3>
                          </el-link>
                        </el-col>
                      </el-row>
                      <!-- 文章内容 -->
                      <el-row :gutter="24">
                        <el-col :span="20">
                          <p
                            style="float: left;margin-top: -10px;font-size: 15px;color: #909399;"
                            class="content"
                            v-html="brightenKeywordContent(item.articleContent)"
                          />
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
            </div>
            <!-- 用户属性 -->
            <div v-if="this.$route.query.searchType === 'user'">
              <div v-if="userLists.length === 0" style="margin-top: 20px;">
                <div>
                  <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
                </div>
                <br>
                <div>
                  <h4>这里什么也没有!</h4>
                </div>
              </div>
              <!-- 用户内容 -->
              <div
                v-for="(item, index) in userLists"
                :key="index"
                style="margin-top: 10px;"
                >
                <el-divider v-if="index !== 0"></el-divider>
                <el-row :gutter="24">
                  <!-- 用户头像 -->
                  <el-col :span="4">
                    <el-image style="height: 60px;width: 60px;border-radius: 50%" :src="item.sysUserIcon" @click="linkToBlog(item.sysUserId)"></el-image>
                  </el-col>
                  <!-- 用户昵称 -->
                  <el-col :span="16">
                    <el-link
                      type="info"
                      style="float: left;color: black;"
                      :underline="false"
                      @click="linkToBlog(item.sysUserId)"
                      >
                      <h3 v-html="brightenKeywordBySearch(item.sysUserNickname)">
                      </h3>
                    </el-link>
                  </el-col>
                  <!-- 按钮区域 -->
                  <el-col :span="4">
                    <el-button
                      style="float: left;margin-top: 10px;"
                      v-if="item.isFocus === false"
                      type="success"
                      plain
                      @click="submitHomeUserPersonFocus(item.sysUserId)"
                      >
                      未关注
                    </el-button>
                    <el-button
                      style="float: left;margin-top: 10px;"
                      v-if="item.isFocus === true"
                      type="success"
                      @click="removeHomeUserPersonFocus(item)"
                    >
                      已关注
                    </el-button>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!-- 标签属性 -->
            <div v-if="this.$route.query.searchType === 'label'">
              <div v-if="labelLists.length === 0" style="margin-top: 20px;">
                <div>
                  <svg-icon style="height: 100px;width: 100px;" icon-class="emptyPage00"></svg-icon>
                </div>
                <br>
                <div>
                  <h4>这里什么也没有!</h4>
                </div>
              </div>
              <!-- 标签内容 -->
              <div
                v-for="(item, index) in labelLists"
                :key="index"
                style="margin-top: 10px;"
                >
                <el-divider v-if="index !== 0"></el-divider>
                <el-row :gutter="24">
                  <!-- 标签图标 -->
                  <el-col :span="4">
                    <el-image style="height: 60px;width: 60px;" :src="item.labelIconUrl" @click="linkToLabelDetails(item)"></el-image>
                  </el-col>
                  <!-- 标签内容 -->
                  <el-col :span="16">
                    <el-row :gutter="24">
                      <el-col :span="4">
                        <el-link
                          :underline="false"
                          type="info"
                          style="color: black;float: left;"
                          @click="linkToLabelDetails(item)"
                          >
                          <h3 v-html="brightenKeywordBySearch(item.labelName)"></h3>
                        </el-link>
                      </el-col>
                      <el-col :span="1">
                        <div class="search-right-divToHr">
                        </div>
                      </el-col>
                      <el-col :span="18">
                        <h3 style="float: left;color: #909399;margin-top: 15px;">
                          {{ item.labelFocusCount }} 关注 · {{ item.labelArticleCount }} 文章
                        </h3>
                      </el-col>
                    </el-row>
                  </el-col>
                  <!-- 按钮 -->
                  <el-col :span="4">
                    <el-button
                      style="float: left;margin-top: 10px;"
                      v-if="item.isFocus === false"
                      type="success"
                      plain
                      @click="submitFocus(item)"
                    >
                      未关注
                    </el-button>
                    <el-button
                      style="float: left;margin-top: 10px;"
                      v-if="item.isFocus === true"
                      type="success"
                      @click="removeFocusLabelButton(item)"
                    >
                      已关注
                    </el-button>
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
  import vueToTop from 'vue-totop'
  import { getToken } from '@/utils/auth'
  import { getInputHistory, clearInputHistory, deleteInputHistory, updateInputHistory, searchArticleLists, searchUserLists,
    searchLabelLists } from '@/api/homeSearch'
  import { insertArticleBrowse, addArticlePraise, deleteArticlePraise } from '@/api/homePageArticle'
  import { insertFocusPerson } from '@/api/focus'
  import { deleteHomeFocusPerson } from '@/api/homePageUser'
  import { deleteLabelFocusOne, insertLabelFocus } from '@/api/label'
  export default {
    name: 'HomeSearch',
    components: { vueToTop },
    data() {
      return {
        loading: false,
        // 是否登录
        isLogin: false,
        // 是否显示输入框历史记录
        isShowInputHistory: false,
        // 输入框历史记录
        inputHistoryLists: [
          {
            created: '',
            updated: '',
            sysInputHistoryId: '',
            sysInputHistoryContent: '',
            sysUserId: ''
          }
        ],
        // 当前登录用户id
        userId: '',
        // 搜索内容
        search: '',
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
            updated: '',
            sysUserNickname: ''
          }
        ],
        // 用户列表
        userLists: [
          {
            sysUserIcon: '',
            isFocus: '',
            created: '',
            sysUserId: '',
            sysUserNickname: '',
            updated: ''
          }
        ],
        // 标签列表
        labelLists: [
          {
            labelId: '',
            isFocus: '',
            created: '',
            labelIconUrl: '',
            labelIconKey: '',
            labelName: '',
            updated: '',
            labelArticleCount: '',
            labelFocusCount: ''
          }
        ]
      }
    },
    created() {
      this.checkLogin()
      this.setDefaultSearchType()
      this.setDefaultArticleType()
    },
    methods: {
      // 判断搜索类型是否undefined，是则赋予article
      setDefaultSearchType() {
        if (this.$route.query.searchType === undefined) {
          this.$router.push({
            name: 'HomeSearch',
            query: {
              searchType: 'article',
              search: this.$route.query.search
            }
          })
          window.location.reload()
        }
      },
      // 判断是否登录
      checkLogin() {
        const hashToken = getToken()
        if (hashToken) {
          this.isLogin = true
          // 登录才刷新的数据
          this.loginRefreshPage()
          // 不需要登录就可以请求的数据
          this.refreshPage()
        } else {
          this.isLogin = false
          // 不需要登录就可以请求的数据
          this.refreshPage()
        }
      },
      // 不需要登录就可以请求的数据
      refreshPage() {
        if (localStorage.getItem('sysUserId') === null) {
          this.userId = -1
        } else {
          this.userId = localStorage.getItem('sysUserId')
        }
        // 搜索文章
        if (this.$route.query.searchType === 'article') {
          searchArticleLists(this.userId, this.$route.query.search, this.$route.query.articleType).then(res => {
            if (res.code === 200) {
              this.articleLists = res.data.articleLists
              this.search = this.$route.query.search
            } else {
              this.$message.error(res.message)
            }
          })
        }
        // 搜索用户
        if (this.$route.query.searchType === 'user') {
          searchUserLists(this.userId, this.$route.query.search).then(res => {
            if (res.code === 200) {
              this.userLists = res.data.userLists
            } else {
              this.$message.error(res.message)
            }
          })
        }
        // 搜索标签
        if (this.$route.query.searchType === 'label') {
          searchLabelLists(this.userId, this.$route.query.search).then(res => {
            if (res.code === 200) {
              this.labelLists = res.data.labelLists
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 设置文章属性的天数条件属性 all/days/weeks/months
      setDefaultArticleType() {
        if (this.$route.query.searchType === 'article' && this.$route.query.articleType === undefined) {
          this.$router.push({
            name: 'HomeSearch',
            query: {
              searchType: 'article',
              search: this.$route.query.search,
              articleType: 'all'
            }
          })
          window.location.reload()
        }
      },
      // 登录才刷新的数据
      loginRefreshPage() {
        // 获取搜索框历史记录
        getInputHistory().then(res => {
          if (res.code === 200) {
            this.inputHistoryLists = res.data.inputHistoryLists
            if (this.inputHistoryLists.length > 0) {
              this.isShowInputHistory = true
            }
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 跳转文章属性
      jumpToArticle() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'article',
            search: this.$route.query.search
          }
        })
        window.location.reload()
      },
      // 跳转用户属性
      jumpToUser() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'user',
            search: this.$route.query.search
          }
        })
        window.location.reload()
      },
      // 跳转标签属性
      jumpToLabel() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'label',
            search: this.$route.query.search
          }
        })
        window.location.reload()
      },
      // 历史记录内容点击跳转搜索页面
      linkToHomeSearch(item) {
        updateInputHistory(item.sysInputHistoryContent).then(res => {
          if (res.code === 200) {
            const routeUrl = this.$router.resolve({
              name: 'HomeSearch',
              query: {
                searchType: this.$route.query.searchType,
                search: item.sysInputHistoryContent
              }
            })
            window.open(routeUrl.href, '_blank')
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 删除搜索框历史记录
      removeInputHistory(item) {
        deleteInputHistory(item.sysInputHistoryId).then(res => {
          if (res.code === 200) {
            this.checkLogin()
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 清空搜索框历史记录
      removeAllInputHistory() {
        this.$confirm('此操作将清空搜索历史记录,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          clearInputHistory().then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
              setTimeout(() => {
                window.location.reload()
              }, 1000)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 跳转文章属性-全部
      linkToArticleTypeAll() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'article',
            search: this.$route.query.search,
            articleType: 'all'
          }
        })
      },
      // 跳转文章属性-最近一天
      linkToArticleTypeDays() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'article',
            search: this.$route.query.search,
            articleType: 'days'
          }
        })
        window.location.reload()
      },
      // 跳转文章属性-最近一周
      linkToArticleTypeWeeks() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'article',
            search: this.$route.query.search,
            articleType: 'weeks'
          }
        })
        window.location.reload()
      },
      // 跳转文章属性-最近三月
      linkToArticleTypeMonths() {
        this.$router.push({
          name: 'HomeSearch',
          query: {
            searchType: 'article',
            search: this.$route.query.search,
            articleType: 'months'
          }
        })
        window.location.reload()
      },
      // 搜索标题标红处理
      brightenKeywordBySearch(val) {
        const keyword = this.$route.query.search
        val = val + ''
        if (val.indexOf(keyword) !== -1 && keyword !== '') {
          return val.replace(keyword, '<font color="red">' + keyword + '</font>')
        } else {
          return val
        }
      },
      // 搜索文本标红处理
      brightenKeywordContent(val) {
        const keyword = this.$route.query.search
        val = val + ''
        if (val.indexOf(keyword) !== -1 && keyword !== '') {
          return val.replace(keyword, '<font color="red">' + keyword + '</font>').substring(0, 200)
        } else {
          return val.substring(0, 400)
        }
      },
      // 跳转用户主页
      linkToBlog(id) {
        const routeUrl = this.$router.resolve({
          name: 'Blog',
          query: {
            sysUserId: id,
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
      removeHomeUserPersonFocus(item) {
        const confirm = '此操作将取消对 ' + item.sysUserNickname + ' 的关注,是否继续'
        this.$confirm(confirm, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteHomeFocusPerson(item.sysUserId).then(res => {
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
      // 跳转标签详情页面
      linkToLabelDetails(item) {
        // 防止单击el-image导致页面卡死
        document.body.style = null
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
      // 添加关注
      submitFocus(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          insertLabelFocus(item.labelId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .search {
    background-color: #ECF0F1;
    width: 960px;
    min-height: 700px;
    margin-top: 20px;

    .search-left {
      position: fixed;
      background-color: #ECF0F1;
      width: 220px;
      min-height: 700px;
      padding: 10px;

      .search-left-type {
        background-color: #fff;
        width: 100%;
        min-height: 100px;

        .search-left-type-button {
          width: 100%;
          height: 55px;
          color: black;
          font-size: 20px;
        }
      }

      .search-left-history {
        background-color: #fff;
        width: 100%;
        min-height: 150px;
        margin-top: 10px;
        padding: 10px;
      }
    }

    .search-right {
      background-color: #fff;
      width: 700px;
      min-height: 700px;
      margin-left: 260px;
      padding: 10px;

      .search-right-content {
        background-color: #fff;
        width: 100%;
        min-height: 700px;

        .search-right-content-nav {
          width: 100%;
          height: 60px;
        }
      }

      .search-right-divToHr {
        width: 1px;
        border: 1px solid black;
        height: 50px;
      }
    }
  }
</style>
