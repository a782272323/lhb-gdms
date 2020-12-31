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
      <div>
      </div>
    </div>
</template>

<script>
  export default {
    name: 'LabelFocus',
    data() {
      return {
        // 导航栏默认选项
        activeIndex: '1'
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
</style>
