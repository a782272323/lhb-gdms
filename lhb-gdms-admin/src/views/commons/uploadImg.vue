<template>
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
    <i class="el-icon-upload" style="color:#409EFF"></i>
    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    <div class="el-upload__tip" slot="tip">只能上传jpg/png格式文件，且不超过5M</div>
  </el-upload>
</template>

<script>
    export default {
      name: 'UploadImg',
      data() {
        return {
          url: '',
          img: '',
          dialogCheck: false,
          fileSize: 5
        }
      },
      created() {
      },
      methods: {
        handleRemove(file) {
        },
        // 上传前校验格式
        handleBeforeUpload(file) {
          console.log('校验格式')
          this.flag = 0
          const isJPG = file.type === 'image/jpeg'
          const isPNG = file.type === 'image/png'
          const isLtXxxM = file.size / 1024 / 1024 < this.fileSize
          this.size = (file.size / 1024 / 1024).toFixed(2) + 'M'
          console.log('图片大小' + this.size)
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
          if (!isLtXxxM) {
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
              console.log('vue本地图片路径 = ' + e.target.result)
              // 图片路径赋予src
              _this.uploadImgParams.url = e.target.result
              const image = new Image()
              image.onload = function() {
                const width = image.width
                const height = image.height
                console.log('参数 宽高：' + width + 'x' + height)
              }
              image.scr = e.target.result
            }
          }
          this.uploadImgParams.img = file
          reader.readAsDataURL(file)
        }
      }
    }
</script>

<style scoped>

</style>
