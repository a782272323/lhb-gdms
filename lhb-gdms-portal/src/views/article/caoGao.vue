<template>
  <div class="caoGao" align="center">
    <div v-loading="loading" element-loading-text="拼命加载中......" class="caoGao-content">
      <el-link :underline="false" type="success" @click="linkToCaoGao">
        <h1>草稿箱({{ this.draftCount }})</h1>
      </el-link>
      <!-- 草稿箱盒子区域 -->
      <div v-for="item in draftList" class="caoGao-box">
        <el-divider></el-divider>
        <el-row :gutter="20">
          <el-col :span="20">
            <el-link class="caoGao-box-title" :underline="false" @click="editOne(item)">
              <h3 style="color: black">{{ item.draftTitle }}</h3>
            </el-link>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <span style="float: left;margin-left: 22px">{{ item.updated }}</span>
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
  </div>
</template>

<script>
  import { getDraftLists, deleteDraft } from '@/api/writeArticle'
    export default {
      name: 'CaoGao',
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
          this.$router.push({
            name: 'Article',
            params: {
              sysDraftId: item.sysDraftId,
              draftTitle: item.draftTitle,
              draftContent: item.draftContent,
              draftType: item.draftType,
              draftImgUrl: item.draftImgUrl,
              draftImgKey: item.draftImgKey
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
    min-height: 10000px;
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
