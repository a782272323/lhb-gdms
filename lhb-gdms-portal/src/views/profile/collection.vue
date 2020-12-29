<template>
    <div class="collection" align="center">
      <div v-loading="loading" element-loading-text="拼命加载中......" class="collection-content">
        <el-link :underline="false" type="success">
          <h1>收藏集({{ this.collectionCount }})</h1>
        </el-link>
        <!-- 收藏集盒子区域 -->
        <div class="collection-box">

        </div>
      </div>
    </div>
</template>

<script>
  import { insertCollection, updateCollectionName, getCollectionLists} from '../../api/collection'
  export default {
    name: 'Collection',
    data() {
      return {
        loading: false,
        collectionList: [],
        collectionCount: 0,
        collectionParams: {
          sysCollectionId: '',
          sysCollectionName: '',
          sysUserId: '',
          created: '',
          updated: ''
        }
      }
    },
    created() {
      this.refreshPage()
    },
    methods: {
      refreshPage() {
        getCollectionLists().then(res => {
          this.loading = true
          if (res.code === 200) {
            this.collectionList = res.data.getLists
            this.collectionCount = res.data.count
            this.loading = false
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .collection{
    width: 100%;
    padding-top: 25px;

    .collection-content {
      background-color: #fff;
      width: 700px;
      min-height: 750px;
      margin-bottom: 30px;
      padding: 30px;
    }

    .collection-box {
      max-height: 100px;
    }
  }
</style>
