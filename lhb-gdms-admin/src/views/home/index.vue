<template>
  <div align="center">
    <div class="home">
      <!-- 用户和文章情况 -->
      <div class="home-header">
        <el-row :gutter="24">
          <!-- 用户新增数量 -->
          <el-col :span="6">
            <el-card class="home-header-card" shadow="hover">
              <el-row :gutter="24">
                <el-col :span="6">
                  <svg-icon class="home-icon" icon-class="user02"></svg-icon>
                </el-col>
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <el-select
                        v-model="userTimeType"
                        @change="chooseUserTimeType"
                        >
                        <el-option
                          label="最近一天新增用户数"
                          value="days"
                          >
                        </el-option>
                        <el-option
                          label="最近一周新增用户数"
                          value="weeks"
                        >
                        </el-option>
                        <el-option
                          label="最近一月新增用户数"
                          value="months"
                        >
                        </el-option>
                        <el-option
                          label="最近一年新增用户数"
                          value="years"
                        >
                        </el-option>
                      </el-select>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <h3 style="float: right;margin-top: 10px;"> {{ userCount }}</h3>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <!-- 文章新增数量 -->
          <el-col :span="6">
            <el-card class="home-header-card" shadow="hover">
              <el-row :gutter="24">
                <el-col :span="6">
                  <svg-icon class="home-icon" icon-class="article01"></svg-icon>
                </el-col>
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <el-select
                        v-model="articleTimeType"
                        @change="chooseArticleTimeType"
                      >
                        <el-option
                          label="最近一天新增文章数"
                          value="days"
                        >
                        </el-option>
                        <el-option
                          label="最近一周新增文章数"
                          value="weeks"
                        >
                        </el-option>
                        <el-option
                          label="最近一月新增文章数"
                          value="months"
                        >
                        </el-option>
                        <el-option
                          label="最近一年新增文章数"
                          value="years"
                        >
                        </el-option>
                      </el-select>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <h3 style="float: right;margin-top: 10px;"> {{ articleCount }}</h3>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <!-- 用户总数 -->
          <el-col :span="6">
            <el-card class="home-header-card" shadow="hover">
              <el-row :gutter="24">
                <el-col :span="6">
                  <svg-icon class="home-icon" icon-class="user01"></svg-icon>
                </el-col>
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <h4 style="float: right;margin-top: 0px;">用户总数</h4>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <h3 style="float: right;margin-top: -15px;">{{ allLists[0].count }}</h3>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <!-- 文章总数 -->
          <el-col :span="6">
            <el-card class="home-header-card" shadow="hover">
              <el-row :gutter="24">
                <el-col :span="6">
                  <svg-icon class="home-icon" icon-class="article02"></svg-icon>
                </el-col>
                <el-col :span="18">
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <h4 style="float: right;margin-top: 0px;">文章总数</h4>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="24">
                      <h3 style="float: right;margin-top: -15px;">{{ allLists[1].count }}</h3>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <!-- 用户和文章增长率曲线图 -->
      <div class="home-veLine" align="center">
        <div class="home-veLine-content">
          <h2>用户增长率和文章增长率曲线图数据</h2>
          <ve-line :data="articleAndUserVeLineLists" :settings="articleAndUserVeLineSettings"></ve-line>
          <div>
            <el-row :gutter="24">
              <el-col :span="12">
                <h2 style="float: right;margin-top: 0px;">{{ veLineYear }}年数据详情</h2>
              </el-col>
              <el-col :span="12">
                <el-select
                  v-model="veLineYear"
                  style="float: left;"
                  @change="chooseUserAndArticleVeLineYear"
                  >
                  <el-option label="2021年" value="2021"></el-option>
                  <el-option label="2020年" value="2020"></el-option>
                  <el-option label="2019年" value="2019"></el-option>
                </el-select>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
      <!-- 用户和文章月份数据扇形图 -->
      <!-- 用户和文章一周数据扇形图 -->
    </div>
  </div>
</template>

<script>
  import { getAllUserAndArticleLists, getArticleCountByTime, getUserCountByTime, getArticleAndUserCurveLists } from '@/api/home'
  import VeLine from 'v-charts/lib/line'
  export default {
    name: 'Home',
    components: { VeLine },
    data() {
      return {
        allLists: [
          {
            count: '',
            type: ''
          }
        ],
        // 新增文章数
        articleCount: 0,
        // 文章用户数
        userCount: 0,
        userTimeType: 'years',
        articleTimeType: 'years',
        // 用户增长曲线和文章增长曲线数据
        articleAndUserVeLineLists: {
          columns: [
            'month', '文章数量', '用户数量'
          ],
          rows: [
            {
              month: '',
              文章数量: 0,
              用户数量: 0
            }
            // {
            //   'month': '2月份', '文章数量': '12', '用户数量': 0
            // },
            // {
            //   'month': '3月份', '文章数量': '12', '用户数量': 0
            // },
            // {
            //   'month': '4月份', '文章数量': '12', '用户数量': 0
            // },
            // {
            //   'month': '5月份', '文章数量': '12', '用户数量': 0
            // },
            // {
            //   'month': '6月份', '文章数量': '0', '用户数量': 1
            // },
            // {
            //   'month': '7月份', '文章数量': '22', '用户数量': 8
            // },
            // {
            //   'month': '8月份', '文章数量': '5', '用户数量': 0
            // },
            // {
            //   'month': '9月份', '文章数量': '7', '用户数量': 0
            // },
            // {
            //   'month': '10月份', '文章数量': '44', '用户数量': 30
            // },
            // {
            //   'month': '11月份', '文章数量': '90', '用户数量': 0
            // },
            // {
            //   'month': '12月份', '文章数量': '13', '用户数量': 0
            // }
          ]
        },
        articleAndUserVeLineSettings: {
          yAxisName: ['数量']
        },
        // 曲线图年份数据
        veLineYear: '2021'
      }
    },
    created() {
      this.refreshPage()
      this.chooseArticleTimeType()
      this.chooseUserTimeType()
      this.chooseUserAndArticleVeLineYear()
    },
    methods: {
      refreshPage() {
        // 门户网站总用户数和总文章数量
        getAllUserAndArticleLists().then(res => {
          if (res.code === 200) {
            this.allLists = res.data.allLists
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 选择新增文章数量日期类型
      chooseArticleTimeType() {
        getArticleCountByTime(this.articleTimeType).then(res => {
          if (res.code === 200) {
            this.articleCount = res.data.articleCount
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 选择新增用户数量日期类型
      chooseUserTimeType() {
        getUserCountByTime(this.userTimeType).then(res => {
          if (res.code === 200) {
            this.userCount = res.data.userCount
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 选择用户增长率和文章增长率年份
      chooseUserAndArticleVeLineYear() {
        getArticleAndUserCurveLists(this.veLineYear).then(res => {
          if (res.code === 200) {
            this.articleAndUserVeLineLists.rows = res.data.articleAndUserVeLineLists
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .home {
    min-height: 1100px;
    /*background-color: black;*/
    background-color: #ECF0F1;
    padding-bottom: 20px;
    padding-top: 20px;
    padding-left: 20px;
    padding-right: 40px;

    .home-icon {
      width: 60px;
      height: 60px;
      float: left;
    }

    .home-header {
      background-color: #ECF0F1;
      min-height: 140px;
      width: 100%;
      margin-left: 20px;
      margin-top: 10px;
      margin-bottom: 10px;
    }

    .home-header-card {
      width: 300px;
      height: 110px;
      margin-top: 10px;
    }

    .home-veLine {
      margin-top: 20px;
      background-color: #ECF0F1;
      min-height: 250px;
      margin-left: 30px;
      width: 100%;
      padding: 10px;

      .home-veLine-content {
        background-color: #fff;
        min-height: 220px;
        margin-right: 30px;
        padding-top: 5px;
        padding-bottom: 5px;
      }
    }
  }
</style>
