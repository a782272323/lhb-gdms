<template>
  <div class="home" align="center">
    <!-- 标签栏 -->
    <div class="label">
<!--    <div class="home-label">-->
      <div class="home-label-content">
        首页-标签栏
      </div>
    </div>

    <!-- 内容 -->
    <div class="main">
<!--    <div class="home-main">-->
      <!-- 文章 -->
      <div class="article">
<!--      <div class="home-main-article">-->
        文章区域
      </div>

      <!-- 联系 -->
      <div class="contact">
<!--      <div class="home-main-contact">-->
        二维码
      </div>
    </div>

  </div>
</template>

<script>
    export default {
        name: 'Home'
    }
</script>

<style lang="scss" scoped>

  .home {
    background-color: #FFFF00;
    height: auto;
    width: auto;

    .label {
      background-color: #13ce66;
      padding-top: 15px;
      height: 50px;

      .content {
        background-color: #4dd9d5;

        height: 20px;
        max-width: 980px;
      }
    }

    .main {
      background-color: #5a5e66;
      margin-top: 20px;
      /*margin-left: 80px;*/
      height: 1000px;
      width: auto;
      max-width: 980px;
      max-height: 1000px;

      .article {
        background-color: #409EFF;
        height: auto;
        width: 700px;
        max-width: 700px;
        float: left;
      }

      .contact {
        background-color: #C03639;
        margin-left: 20px;
        height: 300px;
        width: 240px;
        max-width: 240px;
        float: left;
      }
    }

  }
/*.home {*/
/*  background-color: #FFFF00;*/
/*  height: auto;*/
/*  width: auto;*/
/*}*/
/*.home-label {*/
/*  background-color: #13ce66;*/
/*  padding-top: 15px;*/
/*  height: 50px;*/
/*}*/
/*.home-label-content {*/
/*  background-color: #4dd9d5;*/

/*  height: 20px;*/
/*  max-width: 960px;*/
/*}*/
/*.home-main {*/
/*  background-color: #5a5e66;*/
/*  margin-top: 20px;*/
/*  !*margin-left: 80px;*!*/
/*  height: 1000px;*/
/*  width: auto;*/
/*  max-width: 960px;*/
/*  max-height: 1000px;*/
/*}*/
/*.home-main-article {*/
/*  background-color: #409EFF;*/
/*  height: auto;*/
/*  width: 700px;*/
/*  max-width: 700px;*/
/*  float: left;*/
/*}*/
/*.home-main-contact {*/
/*  background-color: #C03639;*/
/*  margin-left: 20px;*/
/*  height: 300px;*/
/*  width: 240px;*/
/*  max-width: 240px;*/
/*  float: left;*/
/*}*/
</style>
