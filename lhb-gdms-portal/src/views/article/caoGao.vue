<template>
  <div class="caoGao" align="center">
    <div v-loading="loading" element-loading-text="拼命加载中......" class="caoGao-content">
      <el-link :underline="false" type="success" @click="linkToCaoGao">
        <h1>草稿箱({{ this.draftCount }})</h1>
      </el-link>
      <!-- 草稿箱盒子区域 -->
      <div v-for="item in draftList" :key="item.sysDraftId" class="caoGao-box">
        <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="20">
            <el-link class="caoGao-box-title" :underline="false" @click="editOne(item)">
              <h3 style="color: black">{{ item.draftTitle }}</h3>
            </el-link>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <span style="float: left;margin-left: 22px">
              {{ item.updated | parseTime('{y}') }}年{{ item.updated | parseTime('{m}') }}月{{ item.updated | parseTime('{d}') }}日 {{ item.created | parseTime('{h}:{m}:{s}')}}
            </span>
          </el-col>
          <el-col :span="4">
            <el-dropdown trigger="click">
              <span><h1 style="margin-top: -18px;color: black">...</h1></span>
              <el-dropdown-menu>
                <el-dropdown-item>
                  <el-button icon="el-icon-edit" type="success" size="mini" plain @click="editOne(item)">编辑</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button icon="el-icon-delete" style="margin-top: 5px" type="danger" size="mini" plain @click="deleteOne(item)">删除</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
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
  import { getDraftLists, deleteDraft } from '@/api/writeArticle'
  import vueToTop from 'vue-totop'
    export default {
      name: 'CaoGao',
      components: { vueToTop },
      data() {
          return {
            loading: false,
            draftList: [],
            draftCount: '',
            draftParams: {
              sysDraftId: '',
              draftTitle: '',
              draftContent: '',
              draftType: '',
              draftImgUrl: '',
              draftImgKey: '',
              sysUserId: '',
              created: '',
              updated: ''
            }
          }
      },
      created() {
        this.refreshPage()
      },
      methods: {
        refreshPage() {
          this.loading = true
          getDraftLists().then(res => {
            if (res.code === 200) {
              this.loading = false
              this.draftList = res.data.getLists
              this.draftCount = res.data.count
            } else {
              this.loading = false
              this.$message.error(res.message)
            }
          })
        },
        // 跳转草稿箱
        linkToCaoGao() {
          this.$router.push({ path: '/caoGao' })
        },
        // 删除草稿箱
        deleteOne(item) {
          this.$confirm('此操作将删除该条文章且不可恢复,是否继续', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            deleteDraft(item.sysDraftId).then(res => {
              if (res.code === 200) {
                this.$message.success(res.message)
              } else {
                this.$message.error(res.message)
              }
            })
          }).catch(() => {
            this.$message.info('已取消删除')
          })
        },
        // 编辑草稿箱
        editOne(item) {
          // 清除缓存
          localStorage.removeItem('draftTitle')
          localStorage.removeItem('draftContent')
          localStorage.removeItem('draftType')
          localStorage.removeItem('draftImgUrl')
          localStorage.removeItem('draftImgKey')
          // 设置缓存
          localStorage.setItem('draftTitle', item.draftTitle)
          localStorage.setItem('draftContent', item.draftContent)
          localStorage.setItem('draftType', item.draftType)
          localStorage.setItem('draftImgUrl', item.draftImgUrl)
          localStorage.setItem('draftImgKey', item.draftImgKey)
          this.$router.push({
            name: 'Article',
            query: {
              sysDraftId: item.sysDraftId
            }
          })
        }
      }
    }
</script>

<style lang="scss" scoped>
  .caoGao {
    background-color: #ECF0F1;
    width: 100%;
    padding-top: 25px;

    .caoGao-content {
      background-color: #fff;
      width: 700px;
      min-height: 750px;
      margin-bottom: 30px;
      padding: 30px;
    }

    .caoGao-box {
      max-height: 140px;
    }

    .caoGao-box-title {
      max-width: 600px;
      margin-left: 20px;
      float: left;
      margin-top: -10px;
    }

  }
</style>

