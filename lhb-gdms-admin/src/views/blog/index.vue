<template>
  <div class="app-container">
    <div class="filter-container">
      <div>
        <!-- 搜索条件区域 -->
        <div>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form :inline="true">
                <el-form-item>
                  <el-tooltip content="输入作者昵称搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysArticleParams.sysUserNickname"
                      placeholder="输入作者昵称搜索..."
                      ref="sysUserNickname"
                      name="sysUserNickname"
                      style="border: 1px solid #409EFF;"
                      @keyup.enter.native="Search"
                      clearable
                    >
                    </el-input>
                  </el-tooltip>
                </el-form-item>
                <el-form-item>
                  <el-tooltip content="输入文章标题搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysArticleParams.articleTitle"
                      placeholder="输入文章标题搜索..."
                      ref="articleTitle"
                      name="articleTitle"
                      style="border: 1px solid #409EFF;"
                      @keyup.enter.native="Search"
                      clearable
                    >
                    </el-input>
                  </el-tooltip>
                </el-form-item>
                <el-form-item>
                  <el-tooltip content="输入所属标签搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysArticleParams.labelName"
                      placeholder="输入所属标签搜索..."
                      ref="labelName"
                      name="labelName"
                      style="border: 1px solid #409EFF;"
                      @keyup.enter.native="Search"
                      clearable
                    >
                    </el-input>
                  </el-tooltip>
                </el-form-item>
                <el-form-item>
                  <el-button
                    class="filter-item"
                    icon="el-icon-search"
                    type="primary"
                    @click="Search"
                  >
                    搜索
                  </el-button>
                </el-form-item>
                <el-form-item>
                  <el-button
                    class="filter-item"
                    type="primary"
                    @click="reload"
                  >
                    <svg-icon icon-class="refresh00"></svg-icon>
                    刷新
                  </el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </div>
        <!-- 表格区域 -->
        <div>
          <el-table
            v-loading="loading"
            element-loading-text="拼命加载中......"
            :default-sort="{ prop: 'articleId', order: 'ascending' }"
            :data="articleLists"
            class="table"
            border
            fit
            stripe
            highlight-current-row
            @sort-change="sortChange"
            >
            <el-table-column fixed="left" label="文章标题" prop="articleTitle" align="center" width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.articleTitle }}</span>
              </template>
            </el-table-column>
            <el-table-column label="作者昵称" sortable="custom" prop="sysUserNickname" align="center" width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.sysUserNickname }}</span>
              </template>
            </el-table-column>
            <el-table-column label="文章类型" prop="articleType" align="center" width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.articleType }}</span>
              </template>
            </el-table-column>
            <el-table-column label="所属标签" sortable="custom" prop="labelName" align="center" width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.labelName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="被阅读数" sortable="custom" prop="articleBrowseSum" align="center" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.articleBrowseSum }}</span>
              </template>
            </el-table-column>
            <el-table-column label="获赞总数" sortable="custom" prop="articlePraise" align="center" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.articlePraise }}</span>
              </template>
            </el-table-column>
            <el-table-column label="评论总数" prop="articleCommentAll" align="center" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.commentsCount + scope.row.replyCount }}</span>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" sortable="custom" prop="created" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.created }}</span>
              </template>
            </el-table-column>
            <el-table-column label="更新时间" sortable="custom" prop="updated" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.updated }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" align="center" width="200">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" plain @click="openDialogArticleCheck(scope.row)">
                  查看文章
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页区域 -->
        <div>
          <el-row :gutter="20">
            <el-col :span="10" :offset="6">
              <div class="block">
                <el-pagination
                  v-show="tableParams.count > 0"
                  :current-page="tableParams.offSet"
                  :page-size="tableParams.limit"
                  :page-sizes="tableParams.limits"
                  :total="tableParams.count"
                  layout="total, sizes, prev, pager, next, jumper"
                  style="margin-top: 30px;"
                  @size-change="handleChangeSize"
                  @current-change="handleChangeCurrent"
                >
                </el-pagination>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
    <!-- 查看文章详情弹窗 -->
    <div align="center">
      <el-dialog
        :visible.sync="dialogArticleCheck"
        title="查看文章详情"
        width="50%"
        :close-on-click-modal="false"
        center
      >
        <div align="center">
          <el-image
            v-if="articleList[0].articleImgUrl"
            style="width: 90%;"
            :src="articleList[0].articleImgUrl"
            :fit="fit"
          >
          </el-image>
          <h1>{{ articleList[0].articleTitle }}</h1>
          <!-- 文章内容 -->
          <div
            style="width: 90%;"
            v-if="articleList[0].articleType === 'Markdown'"
            align="left"
          >
            <p class="content" v-html="html" />
          </div>
          <div
            v-if="articleList[0].articleType === 'tinyMCE'"
          >
            <p class="content" v-html="html" />
          </div>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import { getArticleLists, searchArticleLists, getArticleList } from '@/api/blogOverview'
  import marked from 'marked'
  export default {
    name: 'Blog',
    data() {
      return {
        loading: false,
        // 文章列表数据
        articleLists: [
          {
            articleTitle: '',
            created: '',
            articleId: '',
            replyCount: '',
            commentsCount: '',
            labelId: '',
            articleType: '',
            articlePraise: '',
            articleContent: '',
            articleBrowseSum: '',
            sysUserId: '',
            sysUserNickname: '',
            labelName: '',
            updated: '',
            articleCommentsAll: ''
          }
        ],
        articleList: [
          {
            articleTitle: '',
            created: '',
            commentsCount: '',
            articleId: '',
            replyCount: '',
            labelId: '',
            articleType: '',
            articlePraise: '',
            articleContent: '',
            articleBrowseSum: '',
            sysUserId: '',
            sysUserNickname: '',
            labelName: '',
            updated: '',
            articleCommentsAll: '',
            articleImgUrl: '',
            articleImgKey: ''
          }
        ],
        // 文章参数
        sysArticleParams: {
          sysUserNickname: '',
          articleTitle: '',
          created: '',
          updated: '',
          articleType: '',
          labelName: '',
          sysUserId: '',
          labelId: ''
        },
        // 分页和排序参数
        tableParams: {
          offSet: 1,
          limit: 10,
          limits: [5, 10, 20, 50],
          keyWord: 'articleId',
          orderBy: 'ASC',
          count: 0
        },
        // 查看文章信息详情弹窗
        dialogArticleCheck: false,
        html: '',
        fit: 'contain'
      }
    },
    created() {
      this.refreshPage()
    },
    methods: {
      // 刷新数据
      refreshPage() {
        this.loading = true
        getArticleLists(this.tableParams).then(res => {
          if (res.code === 200) {
            this.articleLists = res.data.articleLists
            this.tableParams.count = res.data.count
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        }).catch(() => {
          this.loading = false
        })
      },
      // 排序
      sortChange(column) {
        this.tableParams.keyWord = column.prop
        if (column.order === 'ascending') {
          this.tableParams.orderBy = 'ASC'
          this.refreshPage()
        } else if (column.order === 'descending') {
          this.tableParams.orderBy = 'DESC'
          this.refreshPage()
        }
      },
      // 改变一页显示的数据条数
      handleChangeSize(val) {
        this.tableParams.limit = val
        this.refreshPage()
      },
      // 切换页数
      handleChangeCurrent(val) {
        this.tableParams.offSet = val
        this.refreshPage()
      },
      // 刷新页面
      reload() {
        window.location.reload()
      },
      // 打开查看文章信息详情弹窗
      openDialogArticleCheck(row) {
        this.dialogArticleCheck = true
        this.getOneList(row)
      },
      // 查看文章信息详情
      getOneList(row) {
        getArticleList(row.articleId).then(res => {
          if (res.code === 200) {
            this.articleList = res.data.articleList
            if (this.articleList[0].articleType === 'Markdown') {
              // 对图片做处理,防止图片过大
              const content = marked(this.articleList[0].articleContent)
              const regex = new RegExp('<img', 'gi')
              this.articleList[0].articleContent = content.replace(regex, `<img style="max-width: 100%;height: auto;"`)
              const regex1 = new RegExp('<p', 'gi')
              const content1 = this.articleList[0].articleContent
              this.articleList[0].articleContent = content1.replace(regex1, `<p style="max-width: 100%;"`)
              this.html = marked(this.articleList[0].articleContent)
            }
            if (this.articleList[0].articleType === 'tinyMCE') {
              // 对图片做处理,防止图片过大
              const content = this.articleList[0].articleContent
              const regex = new RegExp('<img', 'gi')
              this.articleList[0].articleContent = content.replace(regex, `<img style="max-width: 100%;height: auto;"`)
              this.html = this.articleList[0].articleContent
            }
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 关闭查看文章信息详情弹窗
      closeDialogArticleCheck() {
        this.dialogArticleCheck = false
      },
      // 模糊查询
      Search() {
        this.loading = true
        searchArticleLists(this.tableParams, this.sysArticleParams).then(res => {
          if (res.code === 200) {
            this.tableParams.count = res.data.count
            this.articleLists = res.data.articleLists
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        }).catch(() => {
          this.loading = false
        })
      }
    }
  }
</script>

<style lang="scss" scoped>

  .table {
    margin-top: 20px;
    width: 100%;
    border: 1px solid #409EFF;
  }

  .imgToAvatar {
    width: 60px;
    height: 60px;
    border-radius: 10px;
  }
</style>
