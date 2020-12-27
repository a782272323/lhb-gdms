<template>
  <div class="app-bg">
    <router-view />
  </div>
<!--  <div id="app">-->
<!--    <router-view />-->
<!--  </div>-->
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="scss" scoped>
  .app-bg {
    background-color: #ECF0F1;
    margin: 0px;
  }
</style>
