<template>
    <div>
      文章详情
    </div>
</template>

<script>
  export default {
    name: 'ArticleDetails',
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

<style lang="scss" scoped>

</style>
