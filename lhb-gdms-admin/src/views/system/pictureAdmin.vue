<template>
    <div class="app-container">
      <div class="filter-container">
        <!-- 按钮区域 -->
        <div>
          <el-row :gutter="20">
            <el-col :span="24"  :offset="0">
              <el-button
                icon="el-icon-plus"
                type="primary"
                @click="Add"
                >添加头像</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 表格区域 -->
        <div>
          <el-table
            v-loading="loading"
            element-loading-text="拼命加载中......"
            :data="getList"
            style="margin-top: 20px;width: 100%;border: 1px solid #409EFF;"
            border
            fit
            stripe
            highlight-current-row
            >
            <el-table-column fixed label="头像预览" prop="userIconUrl" align="center" width="120">
              <template slot-scope="scope">
                <div class="demo-basic--circle">
                  <div class="block">
<!--                    <el-avatar shape="square" :size="50" :src="scope.row.userIconUrl">-->
<!--                    </el-avatar>-->
                    <el-image class="imgToAvatar" :src="scope.row.userIconUrl" @click="checkPicture(scope.row.userIconUrl)"></el-image>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column fixed label="头像昵称" prop="userIconName" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.userIconName }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="创建时间" prop="created" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.created }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="更新时间" prop="updated" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.updated }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.isDefault != null" type="" effect="dark">默认头像</el-tag>
                <el-button v-if="scope.row.isDefault == null" @click="setDefaultIcon(scope.row)" type="primary" plain >设为默认</el-button>
                <el-button type="danger" @click="deleteById(scope.row)" plain>删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 添加头像 -->
        <div align="center">
          <el-dialog
            :visible.sync="dialogAdd"
            title="添加头像"
            width="30%"
            center>
            <div align="center">
              <el-upload
                drag
                multiple
                action="1"
                list-type="picture"
                :on-remove="handleRemove"
                :auto-upload="false"
                :before-upload="handleBeforeUpload"
                :on-change="onChange"
                accept="image/*"
              >
                <i class="el-icon-upload" style="color:#409EFF"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过5M</div>
              </el-upload>
              <div>
                <img class="showImg" v-if="uploadImgParams.url" :src="uploadImgParams.url" />
              </div>
            </div>
            <el-form
              ref="sysUserIconParams"
              :label-position="labelPosition"
              label-width="80px"
              :model="sysUserIconParams"
              >
              <el-form-item style="margin-top: 20px" label="头像昵称" prop="userIconName">
                <el-input v-model.trim="sysUserIconParams.userIconName"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button style="margin-right: 160px" @click="removeAdd('sysUserIconParams')">取 消</el-button>
                <el-button type="primary" @click="submitAdd()">添 加</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!-- /添加头像 -->
        </div>
      </div>
      <!-- 模版中使用组件 -->
      <pictureCheck ref="pictureCheck" />
    </div>
</template>

<script>
  import { getLists, insert, updateDefaultIcon, deleteIcon } from '../../api/system/pictureAdmin'
  import pictureCheck from '../commons/pictureCheck'
    export default {
      name: 'PictureAdmin',
      components: { pictureCheck },
      data() {
          return {
            // 添加头像信息弹窗
            dialogAdd: false,
            // 是否启用加载
            loading: true,
            // 添加信息左，顶部，右对齐
            labelPosition: 'left',
            // 获取头像列表全部信息
            getList: [],
            // 分页和排序参数
            tableParams: {
              offSet: '',
              limit: '',
              // eslint-disable-next-line no-dupe-keys
              limit: [5, 10, 20, 50],
              keyWord: '',
              orderBy: '',
              count: undefined
            },
            userIconUrl: '',
            // 系统头像表参数
            sysUserIconParams: {
              sysUserIconId: '',
              userIconUrl: '',
              key: '',
              userIconName: '',
              isDefault: '',
              created: '',
              updated: ''
            },
            // 图片上传相关参数
            uploadImgParams: {
              url: '',
              img: '',
              dialogCheck: false
            }
            // 输入框校验规则
            // rules: {
            //
            // }
          }
      },
      // 刷新界面
      created() {
          this.refreshPage()
      },
      methods: {
        // 刷新界面
        refreshPage() {
          getLists().then(result => {
            this.getList = result.data.getList
            this.tableParams.count = result.data.count
            this.userIconUrl = result.data.getList.userIconUrl
            this.loading = false
          })
        },
        // 清空表单记录
        removeForm(formName) {
          this.$refs[formName].resetFields()
        },
        // 打开添加头像弹窗
        Add() {
          this.dialogAdd = true
        },
        handleRemove(file) {

        },
        // 添加头像--取消按钮
        removeAdd(formName) {
          this.dialogAdd = false
          // this.removeForm(formName)
        },
        // 上传前校验格式
        handleBeforeUpload(file) {
          console.log('校验格式')
          this.flag = 0
          const isJPG = file.type === 'image/jpeg'
          const isPNG = file.type === 'image/png'
          const isLt5M = file.size / 1024 / 1024 < 5
          this.size = (file.size / 1024 / 1024).toFixed(2) + 'M'
          console.log('图片大小' + this.size)
          if (isJPG) {
            this.flag = 1
            this.type = 'jpg'
          } else if (isPNG) {
            this.flag = 1
            this.type = 'png'
          } else {
            this.$message.error('上传图片只能说jpg或png格式!')
            this.flag = 0
          }
          if (!isLt5M) {
            this.$message.error('上传图片大小不能超过5MB!')
            this.flag = 0
          }
          return false
        },
        onChange(file, fileList) {
          var _this = this
          var event = event || window.event
          var reader = new FileReader()
          file = event.target.files[0]
          // 转base 64
          reader.onload = function(e) {
            if (_this.flag === 1) {
              console.log('vue本地图片路径 = ' + e.target.result)
              // 图片路径赋予src
              _this.uploadImgParams.url = e.target.result
              const image = new Image()
              image.onload = function() {
                const width = image.width
                const height = image.height
                console.log('参数 宽高：' + width + 'x' + height)
              }
              image.scr = e.target.result
            }
          }
          this.uploadImgParams.img = file
          reader.readAsDataURL(file)
        },
        // 添加头像--确认按钮
        submitAdd() {
          var formData = new FormData()
          formData.append('file', this.uploadImgParams.img)
          formData.append('userIconName', this.sysUserIconParams.userIconName)
          if (this.uploadImgParams.img === '' || this.uploadImgParams.img === null) {
            this.$message.error('请选择要上传的头像图片')
          } else if (this.sysUserIconParams.userIconName === '' || this.sysUserIconParams.userIconName === null) {
            this.$message.error('头像昵称不能为空')
          } else {
            console.log('添加头像')
            console.log('图片 ' + this.uploadImgParams.img.type)
            insert(formData).then(result => {
              if (result.code === 200) {
                // 清空信息
                // this.$refs[formData].resetFields()
                this.dialogAdd = false
                // window.location.reload()
                this.$message.success(result.message)
                this.refreshPage()
              } else {
                this.$message.error(result.message)
              }
            })
          }
        },
        // 设置默认头像
        setDefaultIcon(params) {
          console.log('sysUserIconId = ' + params.sysUserIconId)
          updateDefaultIcon(params.sysUserIconId).then(result => {
            if (result.code === 200) {
              this.$message.success(result.message)
              this.refreshPage()
            } else {
              this.$message.error(result.message)
            }
          })
        },
        // 删除
        deleteById(params) {
          console.log('删除')
          this.$confirm('此操作将删除该头像且不可恢复，是否继续', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            deleteIcon(params.sysUserIconId).then(result => {
              if (result.code === 200) {
                this.$message.success(result.message)
                this.refreshPage()
              } else {
                this.$message.error(result.message)
              }
            })
          }).catch(() => {
            this.$message.success('已取消删除')
          })
        },
        // 查看图片
        checkPicture(url) {
          console.log('查看图片 url = ' + url)
          this.$refs.pictureCheck.check(url)
        }
      }
    }
</script>

<style scoped>
  .showImg {
    height: 140px;
    width: 280px;
  }

  .imgToAvatar {
    width: 60px;
    height: 60px;
    border-radius: 10px;
  }
</style>
