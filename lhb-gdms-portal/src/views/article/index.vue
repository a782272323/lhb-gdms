<template>
  <div style="background-color: #fff" align="center">
    <!-- 导航栏区域 -->
    <div class="navbar" align="center">
      <el-row :gutter="24">
        <!-- logo -->
        <el-col :span="4">
          <div class="logo">
            <img class="logo-img" src="../../img/Blog02.png" @click="linkToHome"/>
            <p style="float: right" @click="linkToHome">Bin 博客平台</p>
          </div>
        </el-col>
        <el-col :span="4">
          <el-button style="margin-top: 17px;float: left;" type="success" plain round @click="openCoverImgDialog">添加封面图片</el-button>
        </el-col>
        <el-col :span="4">
          <el-button style="margin-top: 17px;float: left;" type="success" plain round @click="saveDraft">保存到草稿箱</el-button>
        </el-col>
        <el-col :span="4">
          <el-button style="margin-top: 17px;float: left;" type="success" plain round @click="changeCompiler">
            <span v-if="this.compilerType === 'tinyMCE'">切换为Markdown编译器</span>
            <span v-if="this.compilerType === 'Markdown'">切换为富文本编译器</span>
          </el-button>
        </el-col>
        <el-col :span="4">
          <div style="float: right;">
            <el-popover
              width="400"
              placement="bottom-start"
              title="发布文章"
              trigger="click"
            >
              <el-divider></el-divider>
              <el-button style="margin-top: 17px" type="success" plain round slot="reference" @click="clickFaBu">
                发布
              </el-button>
              <!-- 使用当前标签 -->
              <div v-if="this.isLabel === true">
                <el-row :gutter="24">
                  <el-col :span="6">
                    <h4 style="color: black;">
                      当前标签 :
                    </h4>
                  </el-col>
                  <el-col :span="3">
                    <el-image style="margin-top: 10px;width: 40px;height: 40px;border-radius: 10px" :src="EditLabelIconUrl"></el-image>
                  </el-col>
                  <el-col :span="9">
                    <h4 style="color: black;">{{ EditLabelName }}</h4>
                  </el-col>
                  <el-col :span="4">
                    <el-button style="margin-top: 20px;" type="success" size="mini" plain round @click="chooseNewLabel">
                      重新选择
                    </el-button>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
              </div>
              <!-- 搜索标签 -->
              <div v-if="this.isLabel === false">
                <el-input
                  v-model="labelKeyWord"
                  ref="keyWord"
                  placeholder="搜索标签......"
                  type="text"
                  clearable
                  style="width: 120px;margin-right: 10px"
                  @keyup.enter.native="queryLabel"
                />
                <el-button type="success" @click="queryLabel">搜索</el-button>
                <el-button v-if="EditLabelId >= 0" type="success" @click="chooseOldLabel">查看原本标签</el-button>
                <el-table
                  :data="labelList"
                  style="margin-top: 10px"
                  max-height="250px"
                  fit
                  border
                  stripe
                  highlight-current-row
                  @current-change="clickCurrentChange"
                >
                  <el-table-column fixed label="标签图标" prop="labelIconUrl" align="center" width="120">
                    <template slot-scope="scope">
                      <div class="block">
                        <el-image style="width: 30px;height: 30px;border-radius: 10px" :src="scope.row.labelIconUrl"></el-image>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column fixed label="标签名称" prop="labelName" align="center">
                    <template slot-scope="scope">
                      <span> {{ scope.row.labelName }}</span>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div style="margin-top: 10px" align="center">
                <el-button type="success" @click="submitFaBu">确定并发布</el-button>
              </div>
            </el-popover>
          </div>
        </el-col>
        <!-- 个人中心区域 -->
        <el-col :span="4">
          <div>
            <el-dropdown class="right-menu-item hover-effect" slot="dropdown">
              <div class="avatar-wrapper">
                <img :src="this.imageUrl+'?imageView2/1/w/80/h/80'" class="user-avatar">
                <span style="float: right;margin-left: 10px;margin-top: 10px;color: #2ECC71">个人中心
                </span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <router-link to="/">
                  <el-dropdown-item><i class="el-icon-s-home"></i>首页</el-dropdown-item>
                </router-link>
                <router-link to="/blog">
                  <el-dropdown-item><svg-icon icon-class="Blog03"></svg-icon> 我的主页</el-dropdown-item>
                </router-link>
                <router-link to="/message">
                  <el-dropdown-item><i class="el-icon-bell"></i>消息</el-dropdown-item>
                </router-link>
                <router-link to="/article">
                  <el-dropdown-item divided><i class="el-icon-edit"></i>写文章</el-dropdown-item>
                </router-link>
                <router-link to="/caoGao">
                  <el-dropdown-item><svg-icon icon-class="caoGao01"></svg-icon> 草稿箱</el-dropdown-item>
                </router-link>
                <router-link to="/focus">
                  <el-dropdown-item><svg-icon icon-class="focus00"></svg-icon> 关注</el-dropdown-item>
                </router-link>
                <router-link to="/collection">
                  <el-dropdown-item><svg-icon icon-class="collection00"></svg-icon> 收藏集</el-dropdown-item>
                </router-link>
                <router-link to="/labelFocus">
                  <el-dropdown-item><svg-icon icon-class="label00"></svg-icon> 标签管理</el-dropdown-item>
                </router-link>
                <router-link to="/profile/index">
                  <el-dropdown-item divided><svg-icon icon-class="system00"></svg-icon> 设置</el-dropdown-item>
                </router-link>
                <el-dropdown-item @click.native="logout">
                  <span style="display:block;"><svg-icon icon-class="logout00"></svg-icon> 注销</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-col>
      </el-row>
    </div>
<!--    <el-divider style="margin-top: -10px"></el-divider>-->
    <!-- 上传图片弹窗 -->
    <div
      v-loading="coverLoading"
      element-loading-text="拼命加载中......"
      elment-loading-spinner="el-icon-loading"
      align="center">
      <el-dialog
        :visible.sync="dialogCoverImg"
        title="上传文章封面图片"
        width="30%"
        center
      >
        <!-- 判断是否已经上传了封面图片 -->
        <div v-if="this.uploadImgParams.url != ''" align="center">
          <img style="width: 100%;height: 100%" v-if="this.uploadImgParams.url" :src="this.uploadImgParams.url" />
          <el-button style="margin-top: 20px" type="success" @click="deleteCoverImg">删除图片</el-button>
        </div>
        <div v-if="uploadImgParams.url === ''" align="center">
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
          <img class="showImg" v-if="uploadImgParams.url" :src="uploadImgParams.url" />
          <el-button style="margin-top: 20px" type="success" @click="removeCoverImg">取 消</el-button>
          <el-button type="success" @click="submitCoverImg">确 认</el-button>
        </div>
      </el-dialog>
    </div>
    <hr style="margin-top: 0px;margin-bottom: 0px;border: 1px solid #ECF0F1;">
    <!-- 标题输入框 -->
    <div align="center">
      <div style="margin-top: 30px;width: 960px;">
        <el-input
          v-model="articleParams.title"
          ref="articleParams.title"
          placeholder="请输入文章标题"
          type="text"
          clearable
        />
      </div>
    </div>
    <!-- 编译器区域 -->
    <!-- 富文本编译器 -->
    <div v-if="this.compilerType === 'tinyMCE'" align="center">
      <div class="compiler-tinyMCE">
        <tinymce v-model="articleParams.content" :height="400"/>
        <el-button style="margin-top: 10px" type="success" round @click="openTinymceDialog">预览文章</el-button>
      </div>
    </div>
    <!-- 预览富文本编译器文章弹窗 -->
    <div align="center">
      <el-dialog
        :visible.sync="dialogTinymce"
        width="650px"
        center
        >
        <div align="center">
          <span>预览文章</span>
          <el-divider></el-divider>
          <h1>{{ articleParams.title }}</h1>
          <img style="width: 300px;height: 100%" v-if="this.uploadImgParams.url" :src="this.uploadImgParams.url" />
          <el-divider v-if="this.uploadImgParams.url"></el-divider>
        </div>
        <div v-html="articleParams.content" />
      </el-dialog>
    </div>
    <!-- markdown 编译器 -->
    <div v-if="this.compilerType === 'Markdown'" align="center">
      <div class="compiler-Markdown">
        <MarkdownEditor ref="markdownEditor" v-model="articleParams.content" :language="language" align="left"/>
<!--        <el-button style="margin-top: 10px" type="success" round @click="getHtml">预览文章</el-button>-->
      </div>
    </div>
    <!-- 预览markdown编译器文章 -->
    <div
      align="center">
      <el-dialog
        :visible.sync="dialogMarkdown"
        width="650px"
        center
        >
        <div align="center">
          <span>预览文章</span>
          <el-divider></el-divider>
          <h1>{{ articleParams.title }}</h1>
          <img style="width: 300px; height: 100%" v-if="this.uploadImgParams.url" :src="this.uploadImgParams.url" />
          <el-divider v-if="this.uploadImgParams.url"></el-divider>
        </div>
        <div v-html="html" />
      </el-dialog>
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
  import Tinymce from '@/components/Tinymce'
  import { articleCoverImg, deleteArticleCoverImg, addDraft, getLabelInfo, queryLabelInfo, faBuArticle,
    updateDraft, deleteDraft, getLabelDetails } from '@/api/writeArticle'
  import MarkdownEditor from '@/components/MarkdownEditor'
  import { getUserDetails } from '@/api/system'
  import { editArticle } from '@/api/homePageArticle'
  import vueToTop from 'vue-totop'
  export default {
    name: 'Article',
    components: { Tinymce, MarkdownEditor, vueToTop },
    data() {
      return {
        imageUrl: '',
        // 编译器类型 tinyMCE 富文本编译器 Markdown 编译器
        compilerType: 'tinyMCE',
        dialogTinymce: false,
        dialogMarkdown: false,
        dialogCoverImg: false,
        // 封面图片弹窗
        coverImgUrl: '',
        // 图片上传相关参数
        uploadImgParams: {
          url: '',
          img: '',
          dialogCheck: false,
          key: ''
        },
        // 文章参数
        articleParams: {
          id: '',
          title: '',
          content: '',
          type: '',
          coverImgUrl: '',
          key: '',
          sysUserId: '',
          created: '',
          updated: ''
        },
        // 是否合法离开页面
        isLeaf: false,
        languageType: {
          'zh': 'zh_CN'
        },
        html: '',
        // 标签参数
        labelParams: {
          labelId: '',
          labelName: '',
          labelIconUrl: '',
          labelIconKey: ''
        },
        // 标签列表
        labelList: [],
        labelKeyWord: '',
        labelId: '',
        loading: false,
        coverLoading: false,
        // 判断是否存在标签(文章编辑)
        isLabel: false,
        EditLabelId: '',
        EditLabelName: '',
        EditLabelIconUrl: '',
        // 编辑文章参数
        editArticleParams: {
          articleTitle: '',
          articleContent: '',
          articleType: '',
          articleImgUrl: '',
          articleImgKey: '',
          labelId: ''
        }
      }
    },
    // 离开页面时提示
    beforeRouteLeave(to, from, next) {
      if (this.isLeaf === false) {
        if (this.compilerType === 'Markdown') {
          this.$confirm('您正在离开写文章页面，本页面所有未保存的数据将会丢失', '警告', {
            confirmButtonText: '确定离开',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.removeCache()
            next()
          }).catch(() => {
            this.removeCache()
            window.history.go(1)
          })
        } else {
          this.$confirm('您正在离开写文章页面，本页面所有未保存的数据将会丢失', '警告', {
            confirmButtonText: '确定离开',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.removeCache()
            next()
          }).catch(() => {
            this.removeCache()
            window.history.go(1)
          })
        }
      } else {
        next()
      }
    },
    created() {
      this.refreshPage()
      // 判断是否从草稿箱的编辑项跳转过来
      if (this.$route.query.sysDraftId !== undefined) {
        console.log('从草稿箱过来编辑文章的')
        // 读取缓存
        this.compilerType = localStorage.getItem('draftType')
        this.uploadImgParams.url = localStorage.getItem('draftImgUrl')
        this.uploadImgParams.key = localStorage.getItem('draftImgKey')
        this.articleParams.title = localStorage.getItem('draftTitle')
        this.articleParams.content = localStorage.getItem('draftContent')
      }
      // 判断是否从我的主页跳转过来编辑文章的
      if (this.$route.query.articleId !== undefined) {
        console.log('从我的主页过来编辑文章的')
        // 读取缓存
        this.compilerType = localStorage.getItem('articleType')
        this.uploadImgParams.url = localStorage.getItem('articleImgUrl')
        this.uploadImgParams.key = localStorage.getItem('articleImgKey')
        this.articleParams.title = localStorage.getItem('articleTitle')
        this.articleParams.content = localStorage.getItem('articleContent')
        this.EditLabelId = localStorage.getItem('labelId')
        // 获取标签信息
        getLabelDetails(this.EditLabelId).then(res => {
          if (res.code === 200) {
            this.isLabel = true
            this.EditLabelIconUrl = res.data.labelDetails[0].labelIconUrl
            this.EditLabelName = res.data.labelDetails[0].labelName
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    computed: {
      language() {
        return this.languageType['zh']
      }
    },
    methods: {
      refreshPage() {
        getUserDetails().then(res => {
          this.userList = res.data.getList
          this.imageUrl = res.data.getList.sysUserIcon
        })
      },
      async logout() {
        await this.$store.dispatch('user/logout')
        this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      },
      // 跳转首页
      linkToHome() {
        this.$router.push({ path: '/home' })
      },
      // 切换编译器
      changeCompiler() {
        if (this.compilerType === 'tinyMCE') {
          this.compilerType = 'Markdown'
        } else {
          this.compilerType = 'tinyMCE'
        }
      },
      // 打开上传封面图片弹窗
      openCoverImgDialog() {
        this.dialogCoverImg = true
      },
      // 预览tinymce富文本编译器弹窗
      openTinymceDialog() {
        this.dialogTinymce = true
      },
      // 文章封面图片上传取消按钮
      removeCoverImg() {
        this.dialogCoverImg = false
        this.$message.info('取消成功')
      },
      // 上传图片前格式校验
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
      // 文章封面图片上传确认按钮
      submitCoverImg() {
        this.coverLoading = true
        var formData = new FormData()
        formData.append('file', this.uploadImgParams.img)
        articleCoverImg(formData).then(res => {
          this.dialogCoverImg = false
          this.uploadImgParams.url = res.data.url
          this.uploadImgParams.key = res.data.key
          this.coverLoading = false
          this.$message.success('上传成功!')
        })
      },
      // 删除文章封面图片按钮
      deleteCoverImg() {
        this.$confirm('此操作将删除该封面图片且不可恢复，是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteArticleCoverImg(this.uploadImgParams.key).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              setTimeout(() => {
                location.reload()
              }, 2000)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.dialogCoverImg = false
          this.$message.success('已取消删除')
        })
      },
      // 保存文章到草稿箱
      saveDraft() {
        this.articleParams.coverImgUrl = this.uploadImgParams.url
        this.articleParams.key = this.uploadImgParams.key
        this.articleParams.type = this.compilerType
        if (this.$route.query.sysDraftId !== undefined) {
          updateDraft(this.$route.query.sysDraftId, this.articleParams).then(res => {
            if (res.code === 200) {
              this.isLeaf = true
              this.$message.success(res.message)
              this.$router.push({ path: '/caoGao' })
            } else {
              this.$message.error(res.message)
            }
          })
        } else {
          addDraft(this.articleParams).then(res => {
            if (res.code === 200) {
              this.isLeaf = true
              this.$message.success(res.message)
              this.$router.push({ path: '/caoGao' })
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // markdown 编译器预览
      getHtml() {
        this.dialogTinymce = true
        this.html = this.$refs.markdownEditor.getHtml()
      },
      // 获取标签信息
      clickFaBu() {
        this.loading = true
        getLabelInfo().then(res => {
          if (res.code === 200) {
            this.labelList = res.data.getLists
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        })
      },
      // 搜索标签
      queryLabel() {
        this.loading = true
        if (this.labelKeyWord === null) {
          this.labelKeyWord = ''
        }
        queryLabelInfo(this.labelKeyWord).then(res => {
          if (res.code === 200) {
            this.loading = false
            this.labelList = res.data.getLists
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        })
      },
      // 选择表格的标签
      clickCurrentChange(val) {
        this.labelId = val.labelId
      },
      // 发布文章按钮
      submitFaBu() {
        this.articleParams.coverImgUrl = this.uploadImgParams.url
        this.articleParams.key = this.uploadImgParams.key
        this.articleParams.type = this.compilerType
        if (this.$route.query.articleId === undefined) {
          // 发布文章
          faBuArticle(this.articleParams, this.labelId).then(res => {
            if (res.code === 200) {
              if (this.$route.query.sysDraftId !== undefined) {
                deleteDraft(this.$route.query.sysDraftId)
              }
              this.articleParams.id = res.articleId
              this.isLeaf = true
              this.$message.success(res.message)
              this.$router.push({ path: '/articleFaBu', query: { articleId: this.articleParams.id }})
            } else {
              this.$message.error(res.message)
            }
          })
        }
        // 编辑文章重新发布
        if (this.$route.query.articleId !== undefined) {
          this.editArticleParams.articleContent = this.articleParams.content
          this.editArticleParams.articleImgKey = this.articleParams.key
          this.editArticleParams.articleImgUrl = this.articleParams.coverImgUrl
          this.editArticleParams.articleTitle = this.articleParams.title
          this.editArticleParams.articleType = this.articleParams.type
          // 重新选择标签发布
          if (this.isLabel === false) {
            if (this.labelId === '') {
              this.$message.error('请选择标签')
            }
            this.editArticleParams.labelId = this.labelId
            console.log('重新选择标签发布 ' + JSON.stringify(this.editArticleParams))
            this.submitArticle(this.$route.query.articleId, this.editArticleParams)
          }
          // 使用原本标签发布
          if (this.isLabel === true) {
            this.editArticleParams.labelId = this.EditLabelId
            console.log('使用原本标签发布 ' + JSON.stringify(this.editArticleParams))
            this.submitArticle(this.$route.query.articleId, this.editArticleParams)
          }
        }
      },
      // 编辑文章
      submitArticle(articleId, data) {
        editArticle(articleId, data).then(res => {
          if (res.code === 200) {
            this.isLeaf = true
            this.$message.success(res.message)
            this.$router.push({ path: '/articleFaBu', query: { articleId: articleId }})
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 清除缓存
      removeCache() {
        if (this.$route.query.sysDraftId !== undefined) {
          console.log('清除草稿箱缓存')
          localStorage.removeItem('draftTitle')
          localStorage.removeItem('draftContent')
          localStorage.removeItem('draftType')
          localStorage.removeItem('draftImgUrl')
          localStorage.removeItem('draftImgKey')
        }
        if (this.$route.query.articleId !== undefined) {
          console.log('清除主页缓存')
          localStorage.removeItem('articleTitle')
          localStorage.removeItem('articleContent')
          localStorage.removeItem('articleType')
          localStorage.removeItem('articleImgUrl')
          localStorage.removeItem('articleImgKey')
          localStorage.removeItem('labelId')
        }
      },
      // 重新选择标签
      chooseNewLabel() {
        this.isLabel = false
      },
      // 查看旧标签
      chooseOldLabel() {
        this.isLabel = true
      }
    }
  }
</script>

<style lang="scss" scoped>
  .navbar {
    height: 70px;
    width: 980px;
    background-color: #fff;

    .logo {
      float: left;
      width: 150px;
      height: 50px;
      margin-top: 15px;
      margin-right: 10px;
      font-weight: bold;
      color: #2ECC71;
    }

    .logo-img {
      width: 40px;
      height: 40px;
      margin-right: 10px;
    }

    .right-menu {
      margin-left: 60px;
      float: left;
      height: 70px;
      width: auto;
      line-height: 50px;
      background-color: #fff;

      &:focus {
        outline: none;
      }

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-wrapper {
      float: right;
      margin-top: 15px;
      position: relative;

      .user-avatar {
        cursor: pointer;
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }

      .el-icon-caret-bottom {
        cursor: pointer;
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }

  .compiler-tinyMCE {
    width: 960px;
    height: 400px;
    margin-top: 20px;

    .showImg {
      height: 140px;
      width: 280px;
    }
  }

  .compiler-Markdown {
    background-color: #fff;
    width: 960px;
    /*height: 480px;*/
    margin-top: 20px;
  }

  .el-dropdown-menu__item:not(.is-disabled):hover {
    background-color: #E3F9ED !important;
    color: #333333 !important;
  }
</style>
