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
                  <el-tooltip content="输入昵称搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysUserParams.sysUserNickname"
                      placeholder="输入昵称搜索..."
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
                  <el-tooltip content="输入用户名搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysUserParams.sysUserUsername"
                      placeholder="输入用户名搜索..."
                      ref="sysUserUsername"
                      name="sysUserUsername"
                      style="border: 1px solid #409EFF;"
                      @keyup.enter.native="Search"
                      clearable
                    >
                    </el-input>
                  </el-tooltip>
                </el-form-item>
                <el-form-item>
                  <el-tooltip content="输入手机号码搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysUserParams.sysUserPhone"
                      placeholder="输入手机号码搜索..."
                      ref="sysUserPhone"
                      name="sysUserPhone"
                      style="border: 1px solid #409EFF;"
                      @keyup.enter.native="Search"
                      clearable
                    >
                    </el-input>
                  </el-tooltip>
                </el-form-item>
                <el-form-item>
                  <el-tooltip content="输入邮箱搜索..." placement="bottom" effect="light">
                    <el-input
                      v-model.trim="sysUserParams.sysUserEmail"
                      placeholder="输入邮箱搜索..."
                      ref="sysUserEmail"
                      name="sysUserEmail"
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
            :default-sort="{ prop: 'sys_user_id', order: 'ascending' }"
            :data="userLists"
            class="table"
            border
            fit
            stripe
            highlight-current-row
            @sort-change="sortChange"
          >
            <el-table-column fixed="left" label="用户头像" prop="sysUserIcon" align="center" width="120">
              <template slot-scope="scope">
                <div class="block">
                  <el-image class="imgToAvatar" :src="scope.row.sysUserIcon"></el-image>
                </div>
              </template>
            </el-table-column>
            <el-table-column fixed="left" label="用户名" sortable="custom" prop="sysUserUsername" align="center" width="150">
              <template slot-scope="scope">
                <span> {{ scope.row.sysUserUsername }}</span>
              </template>
            </el-table-column>
            <el-table-column label="用户昵称" sortable="custom" prop="sysUserNickname" align="center" width="200">
              <template slot-scope="scope">
                <span> {{ scope.row.sysUserNickname }}</span>
              </template>
            </el-table-column>
            <el-table-column label="性别" prop="sysUserSex" align="center">
              <template slot-scope="scope">
                <span> {{ scope.row.sysUserSex }}</span>
              </template>
            </el-table-column>
            <el-table-column label="手机号码" sortable="custom" prop="sysUserPhone" align="center" width="120">
              <template slot-scope="scope">
                <span> {{ scope.row.sysUserPhone }}</span>
              </template>
            </el-table-column>
            <el-table-column label="邮箱" sortable="custom" prop="sysUserEmail" align="center" width="200">
              <template slot-scope="scope">
                <span> {{ scope.row.sysUserEmail }}</span>
              </template>
            </el-table-column>
            <el-table-column label="文章数" sortable="custom" prop="articleCount" align="center" width="120">
              <template slot-scope="scope">
                <span> {{ scope.row.articleCount }}</span>
              </template>
            </el-table-column>
            <el-table-column label="收获点赞" sortable="custom" prop="articlePraise" align="center" width="120">
              <template slot-scope="scope">
                <span> {{ scope.row.articlePraise }}</span>
              </template>
            </el-table-column>
            <el-table-column label="总浏览量" sortable="custom" prop="articleBrowseSum" align="center" width="120">
              <template slot-scope="scope">
                <span> {{ scope.row.articleBrowseSum }}</span>
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
            <el-table-column fixed="right" label="操作" align="center" width="120">
              <template slot-scope="scope">
                <el-button type="primary" plain size="small" @click="openDialogCheck(scope.row)">查看</el-button>
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
    <!-- 查看用户信息详情弹窗 -->
    <div align="center">
      <el-dialog
        :visible.sync="dialogCheck"
        title="查看单个用户信息"
        width="50%"
        :close-on-click-modal="false"
        center
        >
        <el-form
          ref="userList"
          label-position="left"
          :model="userList"
          >
          <el-row :gutter="24">
            <el-col :span="8">
              <el-form-item label="用户头像 : ">
                <el-image style="width: 60px;height: 60px;border-radius: 10px;margin-top: -15px;" :src="userList[0].sysUserIcon"></el-image>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="用户名 : ">
                <span>{{ userList[0].sysUserUsername }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="用户昵称 : ">
                <span>{{ userList[0].sysUserNickname }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="4">
              <el-form-item label="性别 : ">
                <span>{{ userList[0].sysUserSex }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="文章数 : ">
                <span>{{ userList[0].articleCount }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="收获点赞 : ">
                <span>{{ userList[0].articlePraise }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="总浏览量 : ">
                <span>{{ userList[0].articleBrowseSum }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="手机号码 : ">
                <span>{{ userList[0].sysUserPhone }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱号码 : ">
                <span>{{ userList[0].sysUserEmail }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="22">
              <el-form-item label="用户简介 : ">
                <span>{{ userList[0].sysUserIntroduction }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="创建时间 : ">
                <span>{{ userList[0].created }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="更新时间 : ">
                <span>{{ userList[0].updated }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <div align="center">
            <el-form-item>
              <el-button style="width: 50%;" type="primary" plain @click="closeDialogCheck">
                关闭
              </el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import { getUserLists, searchUserLists, getUserList } from '@/api/averageUser'
  export default {
    name: 'OrdinaryUser',
    data() {
      return {
        loading: false,
        getLists: [],
        // 用户列表数据
        userLists: [
          {
            sysUserEmail: '',
            created: '',
            sysUserIntroduction: '',
            sysUserPhone: '',
            sysUserIcon: '',
            articlePraise: 0,
            articleBrowseSum: 0,
            sysUserId: '',
            sysUserNickname: '',
            updated: '',
            sysUserSex: '',
            sysUserUsername: '',
            articleCount: 0
          }
        ],
        userList: [
          {
            sysUserEmail: '',
            created: '',
            sysUserIntroduction: '',
            sysUserPhone: '',
            sysUserIcon: '',
            articlePraise: 0,
            articleBrowseSum: 0,
            sysUserId: '',
            sysUserNickname: '',
            updated: '',
            sysUserSex: '',
            sysUserUsername: '',
            articleCount: 0
          }
        ],
        // 分页和排序参数
        tableParams: {
          offSet: 1,
          limit: 10,
          limits: [5, 10, 20, 50],
          keyWord: 'A.sys_user_id',
          orderBy: 'ASC',
          count: 0
        },
        sysUserParams: {
          sysUserId: '',
          sysUserNickname: '',
          sysUserPhone: '',
          sysUserEmail: '',
          sysUserSex: '',
          sysUserUsername: '',
          sysUserIcon: '',
          sysUserKey: '',
          sysUserIntroduction: ''
        },
        // 查看用户信息详情弹窗
        dialogCheck: false
      }
    },
    created() {
      this.refreshPage()
    },
    methods: {
      // 获取刷新数据
      refreshPage() {
        this.loading = true
        getUserLists(this.tableParams.offSet, this.tableParams.limit, this.tableParams.keyWord, this.tableParams.orderBy).then(res => {
          if (res.code === 200) {
            this.userLists = res.data.userLists
            this.getLists = res.data.userLists
            this.tableParams.count = res.data.count
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
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
      // 模糊查询
      Search() {
        this.loading = true
        searchUserLists(this.tableParams.offSet, this.tableParams.limit, this.tableParams.keyWord, this.tableParams.orderBy,
          this.sysUserParams.sysUserNickname, this.sysUserParams.sysUserUsername, this.sysUserParams.sysUserPhone, this.sysUserParams.sysUserEmail).then(res => {
          if (res.code === 200) {
            this.tableParams.count = res.data.count
            this.userLists = res.data.userLists
            this.loading = false
          } else {
            this.loading = false
            this.$message.error(res.message)
          }
        }).catch(() => {
          this.loading = false
        })
      },
      // 刷新页面
      reload() {
        window.location.reload()
      },
      // 查看用户信息详情
      getOneList(row) {
        getUserList(row.sysUserId).then(res => {
          if (res.code === 200) {
            this.userList = res.data.userList
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 打开查看用户信息详情弹窗
      openDialogCheck(row) {
        this.dialogCheck = true
        this.getOneList(row)
      },
      // 关闭查看用户信息详情弹窗
      closeDialogCheck() {
        this.dialogCheck = false
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
