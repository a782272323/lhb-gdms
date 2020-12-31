<template>
    <div>发布成功</div>
</template>

<script>
    export default {
      name: 'ArticleFaBu',
      data() {
        return {
        }
      },
      created() {
        console.log('文章id = ' + this.$route.query.articleId)
      },
      methods: {
      }
    }
</script>

<style scoped>

</style>
