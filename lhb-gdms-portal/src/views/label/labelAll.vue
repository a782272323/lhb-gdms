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
      <!-- 搜索标签区域 -->
      <div>
      </div>
      <!-- 标签内容区域 -->
      <div>
      </div>
    </div>
</template>

<script>
    export default {
      name: 'LabelAll',
      data() {
        return {
          // 导航栏默认选项
          activeIndex: '2'
        }
      },
      created() {
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
</style>
