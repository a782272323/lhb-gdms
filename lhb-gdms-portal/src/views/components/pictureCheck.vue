<template>
    <el-dialog
      :visible.sync="dialogVisible"
      :width="width"
      top="70px"
      center
      >
      <h1 style="" align="center">图片预览</h1>
      <br>
      <img :src="url" @load="onLoad"/>
      <span slot="footer">
        <el-button type="primary" @click="cancel">关闭</el-button>
      </span>
    </el-dialog>
</template>

<script>
  export default {
      name: 'PictureCheck',
      data() {
        return {
          dialogVisible: false,
          width: '',
          url: ''
        }
      },
    methods: {
      check(url) {
        this.dialogVisible = true
        this.url = url
      },
      // 图片自适应
      onLoad(e) {
        const img = e.target
        let width = 0
        if (img.fileSize > 0 || (img.width > 1 && img.height > 1)) {
          width = img.width + 50
        }
        this.width = width + 'px'
      },
      cancel() {
        this.dialogVisible = false
        this.url = ''
      }
    }
    }
</script>

<style scoped>
  img {
    max-width: 1200px;
  }
</style>
