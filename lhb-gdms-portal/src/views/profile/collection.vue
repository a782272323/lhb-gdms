<template>
    <div class="collection" align="center">
      <div v-loading="loading" element-loading-text="拼命加载中......" class="collection-content">
        <div>
          <el-link style="margin-left: 0px;" :underline="false" type="success" @click="linkToCollection">
            <h1>收藏集({{ this.collectionCount }})</h1>
          </el-link>
          <el-button style="margin-left: 450px" type="success" size="mini" plain @click="openDialogAdd">新建</el-button>
        </div>

        <!-- 收藏集盒子区域 -->
        <div v-for="(item, index) in collectionList" :key="item.sysCollectionId" class="collection-box">
          <el-divider></el-divider>
          <el-row :gutter="20" v-for="i in item.articles" :key="index">
            <el-col :span="10">
              <el-link class="collection-box-title" :underline="false" @click="linkToCollectionDetails(item, i)">
                <h3 style="color: black">{{ item.sysCollectionName }}</h3>
              </el-link>
            </el-col>
            <el-col style="margin-top: -10px" :span="6">
              <h5 style="color: #2ECC71">{{ i.articleCount }}篇文章</h5>
            </el-col>
            <el-col :span="2" :offset="2">
              <el-button style="margin-top: 5px;" type="success" size="mini" plain @click="openDialogEdit(item)">编辑</el-button>
            </el-col>
            <el-col :span="2" :offset="1">
              <el-button style="margin-top: 5px" type="danger" size="mini" plain @click="deleteOneByOd(item)">删 除</el-button>
            </el-col>
          </el-row>
        </div>
      </div>
      <!-- 新建收藏集弹窗 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogAdd"
          title="新建收藏集"
          width="30%"
          center
          >
          <div align="center">
            <el-form
              ref="collectionParams"
              label-position="left"
              :model="collectionParams"
              label-width="120px"
              >
              <el-form-item style="margin-top: 20px" label="收藏集名称" prop="sysCollectionName">
                <el-input v-model.trim="collectionParams.sysCollectionName" clearable></el-input>
              </el-form-item>
              <el-form-item>
                <el-button style="margin-left: -50px" @click="removeAdd" type="success" plain>取 消</el-button>
                <el-button @click="submitAdd" type="success" plain>确 认</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-dialog>
      </div>
      <!-- 编辑收藏集弹窗 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogEdit"
          title="编辑收藏集"
          width="30%"
          center
          >
          <div align="center">
            <el-form
              ref="collectionParams"
              label-position="left"
              :model="collectionParams"
              label-width="120px"
              >
              <el-form-item style="margin-top: 20px;" label="收藏集名称" prop="sysCollectionName">
                <el-input v-model.trim="collectionParams.sysCollectionName" clearable></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="success" plain @click="removeDialogEdit">取 消</el-button>
                <el-button type="success" plain @click="submitDialogEdit">确 认</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-dialog>
      </div>
    </div>
</template>

<script>
  import { insertCollection, updateCollectionName, getCollectionLists, deleteOne } from '@/api/collection'
  export default {
    name: 'Collection',
    data() {
      return {
        loading: false,
        collectionList: [
          {
            sysCollectionId: '',
            sysCollectionName: '',
            sysUserId: '',
            created: '',
            updated: '',
            article: [
              {
                articleCount: ''
              }
            ]
          }
        ],
        collectionCount: 0,
        collectionParams: {
          sysCollectionId: '',
          sysCollectionName: '',
          sysUserId: '',
          created: '',
          updated: ''
        },
        dialogAdd: false,
        dialogEdit: false
      }
    },
    beforeRouteLeave(to, from, next) {
      next()
    },
    created() {
      this.refreshPage()
    },
    methods: {
      refreshPage() {
        getCollectionLists().then(res => {
          this.loading = true
          if (res.code === 200) {
            this.collectionList = res.data.getLists
            this.collectionCount = res.data.count
            this.loading = false
          } else {
            this.$message.error(res.message)
          }
        })
      },
      linkToCollection() {
        this.$router.push({ path: '/collection' })
      },
      // 跳转收藏集详情页面
      linkToCollectionDetails(item, i) {
        localStorage.removeItem('sysCollectionId')
        localStorage.removeItem('sysCollectionName')
        localStorage.removeItem('articleCount')
        localStorage.setItem('sysCollectionId', item.sysCollectionId)
        localStorage.setItem('sysCollectionName', item.sysCollectionName)
        localStorage.setItem('articleCount', i.articleCount)
        this.$router.push({ path: '/collectionDetails' })
      },
      // 打开新建收藏集弹窗
      openDialogAdd() {
        this.dialogAdd = true
      },
      // 新建收藏集取消确认按钮
      removeAdd() {
        this.$message.info('取消成功')
        this.dialogAdd = false
      },
      // 新建收藏集确认按钮
      submitAdd() {
        if (this.collectionParams.sysCollectionName === '') {
          this.$message.error('收藏集名称不能为空')
        } else {
          insertCollection(this.collectionParams.sysCollectionName).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              this.collectionParams.sysCollectionName = ''
              this.refreshPage()
              this.dialogAdd = false
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 打开编辑弹窗
      openDialogEdit(item) {
        this.collectionParams.sysCollectionName = item.sysCollectionName
        this.collectionParams.sysCollectionId = item.sysCollectionId
        this.dialogEdit = true
      },
      // 取消编辑
      removeDialogEdit() {
        this.dialogEdit = false
        this.$message.info('取消成功')
      },
      // 确认编辑
      submitDialogEdit() {
        updateCollectionName(this.collectionParams.sysCollectionId, this.collectionParams.sysCollectionName).then(res => {
          if (res.code === 200) {
            this.dialogEdit = false
            this.refreshPage()
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 删除收藏集
      deleteOneByOd(item) {
        this.$confirm('此操作将删除该收藏集且移除对于的收藏文章不可恢复，是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteOne(item.sysCollectionId).then(res => {
            if (res.code === 200) {
              this.$message.success(res.message)
              this.refreshPage()
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('取消删除成功')
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .collection{
    width: 100%;
    padding-top: 25px;

    .collection-content {
      background-color: #fff;
      width: 700px;
      min-height: 750px;
      margin-bottom: 30px;
      padding: 30px;
    }

    .collection-box {
      max-height: 100px;
    }

    .collection-box-title {
      max-width: 600px;
      margin-left: 20px;
      float: left;
      margin-top: -10px;
    }
  }

  /*修改输入框*/
  /deep/.el-input__inner {
    background-color: transparent !important;
    background-image: none;
    border: 1px solid #2ECC71;
  }
</style>
