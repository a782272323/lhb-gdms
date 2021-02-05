<template>
  <div align="center">
    <!-- 个人资料 -->
    <div class="content">
      <div class="box" align="center">
        <h1>个人信息</h1>
        <el-divider></el-divider>
        <!-- 头像 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h3>头像</h3>
          </el-col>
          <el-col :span="10">
            <el-image class="imageToAvatar" :src="userInfo[0].sysUserIcon"></el-image>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 修改用户昵称 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h4>用户昵称</h4>
          </el-col>
          <el-col :span="6" :offset="1">
            <div class="item-input-top">
              <span>{{ userInfo[0].sysUserNickname }}</span>
            </div>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <!-- 用户名 -->
        <el-row :gutter="24">
          <el-col :span="4">
            <h4>用户名</h4>
          </el-col>
          <el-col :span="6" :offset="1">
            <div class="item-input-top">
              <span>{{ userInfo[0].sysUserUsername }}</span>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
  import { getPersonInfo } from '@/api/system/system'

  export default {
    name: 'Profile',
    data() {
      return {
        userInfo: [
          {
            sysUserIcon: '',
            sysUserKey: '',
            created: '',
            sysUserId: '',
            sysUserNickname: '',
            updated: '',
            sysUserUsername: ''
          }
        ]
      }
    },
    created() {
      this.refreshPage()
    },
    methods: {
      // 刷新数据
      refreshPage() {
        getPersonInfo().then(res => {
          if (res.code === 200) {
            this.userInfo = res.data.userInfo
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .content {
    background-color: #ECF0F1;
    width: 700px;
    min-height: 300px;
    padding: 30px;
    margin-top: 30px;

    .box {
      background-color: #ECF0F1;
      /*background-color: #2c3e50;*/
      width: 100%;
      min-height: 300px;
      margin-top: -30px;
    }

    .imageToAvatar {
      height: 72px;
      width: 72px;
      margin-left: 40px;
    }

    .item-input-top {
      margin-top: 25px;
      width: 300px;
      margin-left: -20px;
    }

    .imgToAvatar {
      width: 60px;
      height: 60px;
      border-radius: 10px;
    }
  }
</style>
