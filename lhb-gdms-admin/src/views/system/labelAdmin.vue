<template>
    <div class="app-container">
      <div class="filter-container">
        <!-- 按钮搜索区域 -->
        <div>
          <el-row :gutter="20">
            <el-col :span="24" :offset="0">
              <el-form :inline="true">
                <el-form-item>
                  <el-input
                    v-model.trim="sysLabelFormParams.labelName"
                    placeholder="输入标签名称进行搜索......"
                    name="labelName"
                    style="width: 200px;border: 1px solid #409EFF"
                    >
                  </el-input>
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
                    icon="el-icon-plus"
                    type="primary"
                    @click="Add"
                  >
                    添加标签
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
            :default-sort="{ prop: 'label_name', order: 'ascending'}"
            :data="getList"
            class="table"
            border
            fit
            stripe
            highlight-current-row
            @sort-change="sortChange"
            >
            <el-table-column fixed label="标签图标" prop="labelIconUrl" align="center" width="120">
              <template slot-scope="scope">
                <div class="block">
                  <el-image class="imgToAvatar" :src="scope.row.labelIconUrl" @click="checkPicture(scope.row.labelIconUrl)"></el-image>
                </div>
              </template>
            </el-table-column>
            <el-table-column fixed label="标签名称" sortable="custom" prop="labelName" align="center">
              <template slot-scope="scope">
                <span> {{ scope.row.labelName }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="创建时间" sortable="custom" prop="created" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.created }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="更新时间" sortable="custom" prop="updated" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.updated }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="primary" plain @click="labelIconEdit(scope.row)">编辑标签图标</el-button>
                <el-button type="primary" plain @click="labelNameEdit(scope.row)">编辑标签名称</el-button>
                <el-button type="danger" plain @click="deleteOne(scope.row)">删除</el-button>
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
                  style="margin-top: 30px"
                  @size-change="handleChangeSize"
                  @current-change="handlerChangeCurrent"
                  />
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <!-- 添加标签 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogAdd"
          title="添加标签"
          width="30%"
          center
          >
          <div align="center">
            <el-upload
              drag
              multiple
              action="1"
              list-type="picture"
              :on-remove="handleRemove"
              :auto-upload="false"
              :before-upload="handleBeforeUpload"
              :on-change="onChanges"
              accept="image/*"
              >
                <i class="el-icon-upload" style="color: #409EFF"></i>
                <div class="el-upload__text">将文件拖到此处,或<em>单击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png格式文件,且大小不能超过5M</div>
            </el-upload>
            <div>
              <img class="showImg" v-if="uploadImgParams.url" :src="uploadImgParams.url" />
            </div>
          </div>
          <el-form
            ref="sysLabelFormParams"
            label-position="left"
            label-width="80px"
            :model="sysLabelFormParams"
            >
            <el-form-item style="margin-top: 20px;width: 410px;" label="标签名称" prop="labelName">
              <el-input class="border01" v-model.trim="sysLabelFormParams.labelName"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button style="margin-right: 160px;" @click="removeAdd(sysLabelFormParams)">取 消</el-button>
              <el-button type="primary" @click="submitAdd">添 加</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
      <!-- 编辑标签图标 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogLabelIconEdit"
          title="编辑标签图标"
          width="30%"
          center
          >
          <div align="center">
            <el-upload
              drag
              multiple
              action="1"
              list-type="picture"
              :on-remove="handleRemove"
              :auto-upload="false"
              :before-upload="handleBeforeUpload"
              :on-change="onChanges"
              accept="image/*"
            >
              <i class="el-icon-upload" style="color: #409EFF"></i>
              <div class="el-upload__text">将文件拖到此处,或<em>单击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png格式文件,且大小不能超过5M</div>
            </el-upload>
            <div>
              <img class="showImg" v-if="uploadImgParams.url" :src="uploadImgParams.url" />
            </div>
            <el-button style="margin-top: 20px" @click="removeLabelIconEdit">取 消</el-button>
            <el-button type="primary" @click="submitLabelIconEdit">确 认</el-button>
          </div>
        </el-dialog>
      </div>
      <!-- 编辑标签名称 -->
      <div align="center">
        <el-dialog
          :visible.sync="dialogLabelNameEdit"
          title="编辑标签名称"
          width="30%"
          center
          >
            <el-form
              ref="sysLabelFormParams"
              label-position="left"
              :model="sysLabelFormParams"
              label-width="80px"
              >
              <el-form-item style="margin-top: 20px" label="标签名称" prop="labelname">
                <el-input v-model.trim="labelNameToName"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button style="margin-right: 160px" @click="removeLabelNameEdit">取 消</el-button>
                <el-button type="primary" @click="submitLabelNameEdit">确 认</el-button>
              </el-form-item>
            </el-form>
        </el-dialog>
      </div>
      <!-- 查看大图标组件 -->
      <pictureCheck ref="pictureCheck"/>
    </div>
</template>

<script>
  import { getLists, query, insertLabel, updateLabelIcon, updateLabelName, deleteLabel } from '../../api/system/labelAdmin'
  import pictureCheck from '../commons/pictureCheck'
    export default {
        name: 'LabelAdmin',
      components: { pictureCheck },
      data() {
          return {
            loading: true,
            // 表格分页排序参数
            tableParams: {
              offSet: 1,
              limit: 10,
              limits: [5, 10, 20, 50],
              keyWord: 'label_name',
              orderBy: 'ASC',
              count: 0
            },
            // 表格表单参数
            sysLabelFormParams: {
              labelId: '',
              labelName: '',
              labelIconUrl: '',
              labelIconKey: '',
              created: '',
              updated: ''
            },
            // 获取标签列表信息
            getList: [],
            // 添加标签弹窗
            dialogAdd: false,
            // 编辑标签图标弹窗
            dialogLabelIconEdit: false,
            // 编辑标签名称弹窗
            dialogLabelNameEdit: false,
            // 图片上传相关参数
            uploadImgParams: {
              url: '',
              img: '',
              dialogCheck: false
            },
            // 编辑标签图标时的id
            labelIdToIcon: '',
            // 编辑标签名称时的id
            labelIdToName: '',
            // 编辑标签名称时的名称
            labelNameToName: ''
          }
      },
      // 界面刷新时调用的方法
      created() {
          this.refreshPage()
      },
      // 其他方法函数
      methods: {
        // 查看大图标
        checkPicture(url) {
          this.$refs.pictureCheck.check(url)
        },
        // 全部列表数据
        refreshPage() {
          getLists(this.tableParams).then(res => {
            this.getList = res.data.getLists
            this.tableParams.count = res.data.count
            this.loading = false
          });
        },
        // 改变一页显示的数据条数
        handleChangeSize(val) {
          this.tableParams.limit = val
          this.refreshPage()
        },
        // 切换页数
        handlerChangeCurrent(val) {
          this.tableParams.offSet = val
          this.refreshPage()
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
        // 模糊查询
        Search() {
          this.loading = true
          query(this.tableParams, this.sysLabelFormParams.labelName).then(res => {
            if (res.code === 200) {
              this.tableParams.count = res.data.count
              this.getList = res.data.getLists
              this.loading = false
            } else {
              this.loading = false
              this.$message.error(res.message)
            }
          }).catch(err => {
            this.$message.error(err)
          })
        },
        // 清空表单记录
        removeForm(params) {
          this.$refs[params].resetField()
        },
        // 打开添加标签弹窗
        Add() {
          this.dialogAdd = true
        },
        handleRemove(file) {
        },
        // 上传前校验格式
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
            this.$message.error('上传图片只能说jpg或png格式!')
            this.flag = 0
          }
          if (!isLt5M) {
            this.$message.error('上传图片大小不能超过5MB!')
            this.flag = 0
          }
          return false
        },
        onChanges(file, fileList) {
          var _this = this
          var event = event || window.event
          var reader = new FileReader()
          file = event.target.files[0]
          // 转base 64
          reader.onload = function(e) {
            if (_this.flag === 1) {
              // 图片路径赋予src
              _this.uploadImgParams.url = e.target.result
              const image = new Image()
              image.onload = function() {
                const width = image.width
                const height = image.height
                console.log('参数 宽高：' + width + 'x' + height)
              }
              image.src = e.target.result
            }
          }
          this.uploadImgParams.img = file
          reader.readAsDataURL(file)
        },
        // 添加标签--取消按钮
        removeAdd() {
          this.dialogAdd = false
        },
        // 添加标签--确认按钮
        submitAdd() {
          var formData = new FormData()
          const img = this.uploadImgParams.img
          const labelName = this.sysLabelFormParams.labelName
          formData.append('file', img)
          formData.append('labelName', labelName)
          if (labelName === '' || labelName === null) {
            this.$message.error('标签名称不能为空')
          } else {
            insertLabel(formData).then(res => {
              if (res.code === 200) {
                this.dialogAdd = false
                this.$message.success(res.message)
                setTimeout(() => {
                  location.reload()
                }, 2000)
              } else {
                this.$message.error(res.message)
              }
            })
          }
        },
        // 打开修改标签图标弹窗
        labelIconEdit(row) {
          this.dialogLabelIconEdit = true
          this.labelIdToIcon = row.labelId
        },
        // 编辑标签图标--取消按钮
        removeLabelIconEdit() {
          this.dialogLabelIconEdit = false
        },
        // 编辑标签图标--确认按钮
        submitLabelIconEdit() {
          console.log(' 编辑标签图标labelId = ' + this.labelIdToIcon)
          var formData = new FormData()
          formData.append('file', this.uploadImgParams.img)
          updateLabelIcon(formData, this.labelIdToIcon).then(res => {
            if (res.code === 200) {
              this.dialogLabelIconEdit = false
              this.$message.success(res.message)
              setTimeout(() => {
                location.reload()
              }, 2000)
            } else {
              this.dialogLabelIconEdit = false
              this.$message.error(res.message)
            }
          })
        },
        // 打开编辑标签名称弹窗
        labelNameEdit(row) {
          this.dialogLabelNameEdit = true
          this.labelIdToName = row.labelId
          this.labelNameToName = row.labelName
        },
        // 编辑标签名称--取消按钮
        removeLabelNameEdit() {
          this.dialogLabelNameEdit = false
        },
        // 编辑标签名称--确认按钮
        submitLabelNameEdit(params) {
          console.log('编辑标签名称 id = ' + this.labelIdToName)
          const labelName = this.labelNameToName
          if (labelName === '' || labelName === null) {
            this.$message.error('标签名称不能为空')
          } else {
            updateLabelName(this.labelIdToName, labelName).then(res => {
              if (res.code === 200) {
                this.dialogLabelNameEdit = false
                this.$message.success(res.message)
                this.sysLabelFormParams.labelName = ''
              } else {
                this.dialogLabelNameEdit = false
                this.$message.error(res.message)
              }
            })
          }
        },
        // 单个删除
        deleteOne(row) {
          this.$confirm('此操作将删除该图标且不可恢复，是否继续', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.loading = true
            deleteLabel(row.labelId).then(res => {
              if (res.code === 200) {
                this.loading = false
                this.$message.success(res.message)
                this.refreshPage()
              } else {
                this.loading = false
                this.$message.error(res.message)
              }
            })
          }).catch(() => {
            this.$message.success('已取消删除')
          })
        }
      }
    }
</script>

<style scoped>
  .table {
    margin-top: 20px;
    width: 100%;
    border: 1px solid #409EFF;
  }

  .showImg {
    height: 140px;
    width: 280px;
  }

  .imgToAvatar {
    width: 60px;
    height: 60px;
    border-radius: 10px;
  }

  .border01 {
    border: 1px solid #409EFF;
  }
</style>
