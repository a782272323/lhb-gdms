<template>
    <div align="center">
      <!-- 标签导航栏区域 -->
      <div class="labelAll-navbar">
        <hr class="labelAll-navbar-hr">
        <div class="labelAll-navbar-content">
          <div class="labelAll-navbar-menu">
            <el-menu
              :default-active="activeIndex"
              mode="horizontal"
              active-text-color="#2ECC71"
            >
              <el-menu-item index="1" @click="linkToLabelFocus">
                已关注标签
              </el-menu-item>
              <el-menu-item index="2" @click="linkToLabelAll">
                全部标签
              </el-menu-item>
            </el-menu>
          </div>
        </div>
      </div>
      <!-- 标签内容区域 -->
      <div class="labelAll-box">
        <!-- 搜索标签区域 -->
        <div class="labelAll-box-sort">
          <el-row :gutter="24">
            <el-col :span="2">
              <el-link
                style="margin-top: 22px"
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
                style="margin-top: 22px;margin-left: -20px;"
                :type="this.linkHottestType"
                :underline="false"
                @click="hotSort"
                >
                最热门
              </el-link>
            </el-col>
            <el-col :span="6">
              <el-input
                v-model.trim="inputLabelName"
                style="margin-top: 16px;"
                placeholder="搜索标签"
                type="text"
                size="mini"
                clearable
                >
              </el-input>
            </el-col>
            <el-col :span="2">
              <el-button
                type="success"
                plain
                size="mini"
                style="margin-top: 16px;"
                @click="queryList"
                @keyup.enter.native="queryList"
                >搜索</el-button>
            </el-col>
          </el-row>
        </div>
        <!-- 标签内容区域 -->
        <div
          class="labelAll-box-content"
          v-loading="loading"
          element-loading-text="拼命加载中......"
          >
          <el-row :gutter="24">
            <el-col
              :span="6"
              v-for="(item,index) in labelLists"
              :key="index"
              >
              <el-card
                class="labelAll-box-col"
                shadow="hover"
                >
                <div align="center">
                  <!-- 标签头像 -->
                  <div>
                    <el-image
                      style="height: 60px;width: 60px;"
                      :src="item.labelIconUrl"
                      @click="linkToLabelDetails(item)"
                    ></el-image>
                  </div>
                  <!-- 标签名称 -->
                  <div>
                    <el-link
                      style="margin-bottom: -25px;"
                      type="info"
                      :underline="false"
                      @click="linkToLabelDetails(item)"
                      >
                      <h4>{{ item.labelName }}</h4>
                    </el-link>
                  </div>
                  <!-- 关注数和文章数 -->
                  <h5 style="margin-bottom: 10px;">
                    {{ item.labelFocusCount }} 关注 · {{ item.labelArticleCount }} 文章
                  </h5>
                  <div>
                    <el-button v-if="item.isFocus == true" type="success" size="small" @click="removeFocus(item)">已关注</el-button>
                    <el-button v-if="item.isFocus == false" type="success" size="small" plain @click="submitFocus(item)">未关注</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
</template>

<script>
  import { getAllLabelLists, deleteLabelFocusOne, insertLabelFocus } from '@/api/label'
    export default {
      name: 'LabelAll',
      data() {
        return {
          loading: false,
          // 导航栏默认选项
          activeIndex: '2',
          // 文字链接类型
          linkLatestType: 'success',
          linkHottestType: 'info',
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
          ],
          inputLabelName: ''
        }
      },
      created() {
        this.chooseSortType()
      },
      methods: {
        refreshPage() {
        },
        // 跳转已关注标签页面
        linkToLabelFocus() {
          this.$router.push({ path: '/labelFocus' })
        },
        // 跳转全部标签页面
        linkToLabelAll() {
          this.$router.push({ path: '/labelAll' })
        },
        // 默认排序（最新）
        defaultSort() {
          this.$router.push({
            name: 'LabelAll',
            query: { sortType: 'latest' }
          })
          this.linkLatestType = 'success'
          this.linkHottestType = 'info'
          this.loading = true
          // 请求数据
          getAllLabelLists(this.$route.query.sortType, this.inputLabelName).then(res => {
            if (res.code === 200) {
              this.labelLists = res.data.getLists
              this.loading = false
            } else {
              this.$message.error(res.message)
              this.loading = false
            }
          })
        },
        // 最热门排序
        hotSort() {
          this.$router.push({
            name: 'LabelAll',
            query: { sortType: 'hottest' }
          })
          this.linkLatestType = 'info'
          this.linkHottestType = 'success'
          this.loading = true
          // 请求数据
          getAllLabelLists(this.$route.query.sortType, this.inputLabelName).then(res => {
            if (res.code === 200) {
              this.labelLists = res.data.getLists
              this.loading = false
            } else {
              this.$message.error(res.message)
              this.loading = false
            }
          })
        },
        // 判断排序类型
        chooseSortType() {
          if (this.$route.query.sortType === undefined) {
            this.defaultSort()
          } else if (this.$route.query.sortType === 'latest') {
            this.defaultSort()
          } else if (this.$route.query.sortType === 'hottest') {
            this.hotSort()
          }
        },
        queryList() {
          this.loading = true
          getAllLabelLists(this.$route.query.sortType, this.inputLabelName).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              this.labelLists = res.data.getLists
              this.loading = false
            } else {
              this.$message.error(res.message)
              this.loading = false
            }
          })
        },
        // 取消关注
        removeFocus(item) {
          this.$confirm('此操作将取消关注,是否继续', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            deleteLabelFocusOne(item.labelId).then(res => {
              if (res.code === 200) {
                this.$message.success(res.message)
                this.chooseSortType()
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
          insertLabelFocus(item.labelId).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              this.chooseSortType()
            } else {
              this.$message.error(res.message)
            }
          })
        },
        // 跳转标签详情页面
        linkToLabelDetails(item) {
          const routeUrl = this.$router.resolve({
            name: 'LabelDetails',
            query: {
              labelId: item.labelId,
              labelName: item.labelName
            }
          })
          window.open(routeUrl.href, '_blank')
        }
      }
    }
</script>

<style lang="scss" scoped>
  .labelAll-navbar {
    width: 100%;
    min-height: 60px;
    background-color: #fff;
    margin-top: 0px;

    .labelAll-navbar-hr {
      margin-top: 0px;
      margin-bottom: 0px;
      border: 1px solid #ECF0F1;
    }

    .labelAll-navbar-content {
      width: 980px;
      min-height: 60px;
    }

    .labelAll-navbar-menu {
      height: 60px;
      width: 300px;
      float: left;
    }
  }

  .labelAll-box {
    margin-top: 20px;
    padding: 20px;
    width: 980px;
    min-height: 750px;
    background-color: #fff;

    .labelAll-box-sort {
      width: 100%;
      height: 60px;
    }

    .labelAll-box-content {
      margin-top: 15px;
      width: 100%;
      /*background-color: red;*/
    }

    .labelAll-box-col {
      float: left;
      width: 220px;
      height: 220px;
      background-color: #fff;
      margin-right: 15px;
      margin-top: 15px;
    }
  }

  /deep/.el-input__inner {
    background-color: transparent !important;
    background-image: none;
    border: 1px solid #2ECC71;
  }
</style>
