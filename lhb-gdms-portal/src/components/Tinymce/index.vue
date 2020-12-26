<template>
  <div :class="{fullscreen:fullscreen}" class="tinymce-container" :style="{width:containerWidth}">
    <textarea :id="tinymceId" class="tinymce-textarea" />
    <div class="editor-custom-btn-container">
<!--      <editorImage color="#1890ff" class="editor-upload-btn" @successCBK="imageSuccessCBK" />-->
    </div>
    <div>
    </div>
  </div>
</template>

<script>
/**
 * docs:
 * https://panjiachen.github.io/vue-element-admin-site/feature/component/rich-editor.html#tinymce
 */
import editorImage from './components/EditorImage'
import plugins from './plugins'
import toolbar from './toolbar'
import load from './dynamicLoadScript'
import { getUserDetails } from '@/api/system'
import { articleContentImg } from '@/api/writeArticle'

// why use this cdn, detail see https://github.com/PanJiaChen/tinymce-all-in-one
const tinymceCDN = 'https://cdn.jsdelivr.net/npm/tinymce-all-in-one@4.9.3/tinymce.min.js'

export default {
  name: 'Tinymce',
  components: { editorImage },
  props: {
    id: {
      type: String,
      default: function() {
        return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
      }
    },
    value: {
      type: String,
      default: ''
    },
    toolbar: {
      type: Array,
      required: false,
      default() {
        return []
        // return ['removeformat undo redo |  bullist numlist | outdent indent | forecolor | fullscreen code', 'bold italic blockquote | h2 p  media link | alignleft aligncenter alignright | fontsizeselect | fontselect']
      }
    },
    menubar: {
      type: String,
      default: 'file edit insert view format table tools help'
      // default: '文件编辑插入查看格式表'
    },
    height: {
      type: [Number, String],
      required: false,
      default: 360
    },
    width: {
      type: [Number, String],
      required: false,
      default: 'auto'
    }
  },
  created() {
    this.getUserInfo()
  },
  data() {
    return {
      hasChange: false,
      hasInit: false,
      tinymceId: this.id,
      fullscreen: false,
      languageTypeList: {
        'en': 'en',
        'zh': 'zh_CN',
        'es': 'es_MX',
        'ja': 'ja'
      },
      // 上传图片弹窗
      dialogImg: false,
      // 用户信息详情
      userList: {
        sysUserId: '',
        sysUserNickName: ''
      }
    }
  },
  computed: {
    containerWidth() {
      const width = this.width
      if (/^[\d]+(\.[\d]+)?$/.test(width)) { // matches `100`, `'100'`
        return `${width}px`
      }
      return width
    }
  },
  watch: {
    value(val) {
      if (!this.hasChange && this.hasInit) {
        this.$nextTick(() =>
          window.tinymce.get(this.tinymceId).setContent(val || ''))
      }
    }
  },
  mounted() {
    this.init()
  },
  activated() {
    if (window.tinymce) {
      this.initTinymce()
    }
  },
  deactivated() {
    this.destroyTinymce()
  },
  destroyed() {
    this.destroyTinymce()
  },
  methods: {
    // 获取用户信息详情
    getUserInfo() {
      getUserDetails().then(res => {
        this.userList = res.data.getList
      })
    },
    // 打开上传图片弹窗
    openImgDialog() {
      console.log('用户id = ' + this.userList.sysUserId)
      this.dialogImg = true
    },
    // 上传图片前格式校验
    handleBeforeUpload(file) {
      this.flag = 0
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isLt5M = file.size / 1024 / 1024 < 5
      this.size = (file.size / 1024 / 1024).toFixed(2) + 'M'
      if (isJPG) {
        this.flag = 1
        this.type = 'jpg'
      } else if (isPNG) {
        this.flag = 1
        this.type = 'png'
      } else {
        this.$message.error('文件格式只能为jpg或png类型文件式')
      }
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过5M')
        this.flag = 0
      }
      return false
    },
    onChanges(file, fileList) {
      var _this = this
      var event = event || window.event
      var reader = new FileReader()
      file = event.target.files[0]
      // 转 base64
      reader.onload = function(e) {
        if (_this.flag === 1) {
          _this.uploadImgParams.url = e.target.result
          const image = new Image()
          image.onload = function() {
            const width = image.width
            const height = image.height
            console.log('参数 宽高: ' + width + 'x' + height)
          }
          image.scr = e.target.result
        }
      }
      this.uploadImgParams.img = file
      reader.readAsDataURL(file)
    },
    // 下面是官方的代码
    init() {
      // dynamic load tinymce from cdn
      load(tinymceCDN, (err) => {
        if (err) {
          this.$message.error(err.message)
          return
        }
        this.initTinymce()
      })
    },
    initTinymce() {
      const _this = this
      window.tinymce.init({
        // selector: `#${this.tinymceId}`,
        selector: `#${this.tinymceId}`,
        // 设置富文本编译器语言
        language: this.languageTypeList['zh'],
        // language: this.languageTypeList['en'],
        height: this.height,
        body_class: 'panel-body ',
        object_resizing: false,
        toolbar: this.toolbar.length > 0 ? this.toolbar : toolbar,
        menubar: this.menubar,
        plugins: plugins,
        end_container_on_empty_block: true,
        powerpaste_word_import: 'clean',
        code_dialog_height: 450,
        code_dialog_width: 1000,
        advlist_bullet_styles: 'square',
        advlist_number_styles: 'default',
        imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
        default_link_target: '_blank',
        link_title: false,
        nonbreaking_force_tab: true, // 插入不间断空格需要不间断空间插件 inserting nonbreaking space &nbsp; need Nonbreaking Space Plugin
        init_instance_callback: editor => {
          if (_this.value) {
            editor.setContent(_this.value)
            console.log('富文本内容 = ' + editor.setContent(_this.value))
          }
          _this.hasInit = true
          editor.on('NodeChange Change KeyUp SetContent', () => {
            this.hasChange = true
            this.$emit('input', editor.getContent())
          })
        },
        setup(editor) {
          editor.on('FullscreenStateChanged', (e) => {
            _this.fullscreen = e.state
          })
        },
        // 自定义上传图片
        images_upload_handler: function(blobInfo, succFun, failFun) {
          var formData = new FormData()
          var imgUrl = ''
          var file = blobInfo.blob()
          formData.append('file', file)
          articleContentImg(formData).then(res => {
            if (res.code === 200) {
              imgUrl = res.data.url
              // this.uploadImgParams.url = imgUrl
              console.log('url = ' + imgUrl)
              succFun(imgUrl)
            } else {
              console.log(res.message)
            }
          }).catch(err => {
            failFun(err + ',图片格式只能为jpg者png')
          })
        }
        // 整合七牛上传
        // images_dataimg_filter(img) {
        //   setTimeout(() => {
        //     const $image = $(img);
        //     $image.removeAttr('width');
        //     $image.removeAttr('height');
        //     if ($image[0].height && $image[0].width) {
        //       $image.attr('data-wscntype', 'image');
        //       $image.attr('data-wscnh', $image[0].height);
        //       $image.attr('data-wscnw', $image[0].width);
        //       $image.addClass('wscnph');
        //     }
        //   }, 0);
        //   return img
        // },
        // images_upload_handler(blobInfo, success, failure, progress) {
        //   progress(0);
        //   const token = _this.$store.getters.token;
        //   getToken(token).then(response => {
        //     const url = response.data.qiniu_url;
        //     const formData = new FormData();
        //     formData.append('token', response.data.qiniu_token);
        //     formData.append('key', response.data.qiniu_key);
        //     formData.append('file', blobInfo.blob(), url);
        //     upload(formData).then(() => {
        //       success(url);
        //       progress(100);
        //     })
        //   }).catch(err => {
        //     failure('出现未知问题，刷新页面，或者联系程序员')
        //     console.log(err);
        //   });
        // },
      })
    },
    destroyTinymce() {
      const tinymce = window.tinymce.get(this.tinymceId)
      if (this.fullscreen) {
        tinymce.execCommand('mceFullScreen')
      }

      if (tinymce) {
        tinymce.destroy()
      }
    },
    setContent(value) {
      window.tinymce.get(this.tinymceId).setContent(value)
    },
    getContent() {
      console.log('富文本内容 = ' + window.tinymce.get(this.tinymceId).getContent())
      window.tinymce.get(this.tinymceId).getContent()
    },
    imageSuccessCBK(arr) {
      const _this = this
      arr.forEach(v => {
        window.tinymce.get(_this.tinymceId).insertContent(`<img class="wscnph" src="${v.url}" >`)
      })
    }
  }
}
</script>

<style scoped>
.tinymce-container {
  position: relative;
  line-height: normal;
}
.tinymce-container>>>.mce-fullscreen {
  z-index: 10000;
}
.tinymce-textarea {
  visibility: hidden;
  z-index: -1;
}
.editor-custom-btn-container {
  position: absolute;
  right: 4px;
  top: 4px;
  /*z-index: 2005;*/
}
.fullscreen .editor-custom-btn-container {
  z-index: 10000;
  position: fixed;
}
.editor-upload-btn {
  display: inline-block;
}
</style>
