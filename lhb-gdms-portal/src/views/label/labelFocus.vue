<template>
    <div align="center">
      <!-- 标签导航栏区域 -->
      <div class="labelFocus-navbar">
        <hr class="labelFocus-navbar-hr">
        <div class="labelFocus-navbar-content">
          <div class="labelFocus-navbar-menu">
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
      <div
        class="labelFocus-box"
        v-loading="loading"
        element-loading-text="拼命加载中......"
        >
        <el-row :gutter="24">
          <el-col
            :span="6"
            v-for="(item, index) in labelLists"
            :key="index"
            >
            <el-card class="labelFocus-box-col" shadow="hover">
              <div align="center">
                <!-- 标签头像 -->
                <div>
                  <el-image
                    style="height: 60px;width: 60px"
                    :src="item.labelIconUrl"
                    @click="linkToLabelDetails(item)"
                    >
                  </el-image>
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
                <h5 style="margin-bottom: 10px">
                  {{ item.labelFocusCount }} 关注 · {{ item.labelArticleCount }} 文章
                </h5>
              </div>
              <div>
                <el-button v-if="item.isFocus == true" type="success" size="small" @click="removeFocus(item)">已关注</el-button>
                <el-button v-if="item.isFocus == false" type="success" size="small" plain @click="submitFocus(item)">未关注</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
</template>

<script>
  import { getLabelFocusLists, deleteLabelFocusOne, insertLabelFocus } from '@/api/label'
  export default {
    name: 'LabelFocus',
    data() {
      return {
        loading: false,
        // 导航栏默认选项
        activeIndex: '1',
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
      this.refreshPage()
    },
    methods: {
      refreshPage() {
        this.loading = true
        getLabelFocusLists().then(res => {
          if (res.code === 200) {
            this.loading = false
            this.labelLists = res.data.getLists
          } else {
            this.$message.error(res.message)
            this.loading = false
          }
        })
      },
      // 跳转已关注标签页面
      linkToLabelFocus() {
        this.$router.push({ path: '/labelFocus' })
      },
      // 跳转全部标签页面
      linkToLabelAll() {
        this.$router.push({ path: '/labelAll' })
      },
      // 取消关注
      removeFocus(item) {
        deleteLabelFocusOne(item.labelId).then(res => {
          if (res.code === 200) {
            this.$message.success(res.message)
            this.refreshPage()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 添加关注
      submitFocus(item) {
        insertLabelFocus(item.labelId).then(res => {
          if (res.code === 200) {
            this.$message.success(res.message)
            this.refreshPage()
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
 .labelFocus-navbar {
   width: 100%;
   min-height: 60px;
   background-color: #fff;
   margin-top: 0px;

   .labelFocus-navbar-hr {
     margin-top: 0px;
     margin-bottom: 0px;
     border: 1px solid #ECF0F1;
   }

   .labelFocus-navbar-content {
     width: 980px;
     min-height: 60px;
   }

   .labelFocus-navbar-menu {
     height: 60px;
     width: 300px;
     float: left;
   }
 }

 .labelFocus-box {
   margin-top: 20px;
   padding: 20px;
   width: 980px;
   min-height: 750px;
   background-color: #fff;

   .labelFocus-box-col {
     float: left;
     width: 220px;
     height: 220px;
     background-color: #fff;
     margin-right: 15px;
     margin-top: 15px;
   }
 }
</style>
