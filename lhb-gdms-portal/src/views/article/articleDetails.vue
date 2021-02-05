<template>
    <div style="margin-top: 20px" align="center">
      <div
        v-loading="loading"
        element-loading-text="拼命加载中......"
        class="articleDetails-main"
        >
        <!-- 左侧点赞收藏评论 -->
        <div class="articleDetails-left">
          <!-- 点赞 -->
          <div v-if="isPraise === false" style="border-radius: 50%;height: 40px;width: 40px;background-color: #fff;">
            <el-badge :value="articlePraiseCount" type="info">
              <el-button size="mini" circle @click="submitArticlePraise">
                <svg-icon style="width: 25px;height: 25px;margin-top: 0px;" icon-class="praise00-#909399"></svg-icon>
              </el-button>
            </el-badge>
          </div>
          <div v-if="isPraise === true" style="border-radius: 50%;height: 40px;width: 40px;background-color: #fff;">
            <el-badge :value="articlePraiseCount" type="success">
              <el-button size="mini" circle @click="removeArticlePraise">
                <svg-icon style="width: 25px;height: 25px;margin-top: 0px;" icon-class="praise00-#67C23A"></svg-icon>
              </el-button>
            </el-badge>
          </div>
          <!-- 评论 -->
          <div
            style="border-radius: 50%;height: 40px;width: 40px;margin-top: 15px;background-color: #fff;"
            @click="jumpToPingLun">
            <el-badge :value="articleCommentsCount" type="info">
              <el-button size="mini" circle>
                <svg-icon style="width: 25px;height: 25px;margin-top: 0px;" icon-class="pinglun00-#909399"></svg-icon>
              </el-button>
            </el-badge>
          </div>
          <!-- 收藏 -->
          <div style="border-radius: 50%;height: 40px;width: 40px;margin-top: 15px;background-color: #fff;" @click="clickCollection">
            <el-button v-if="isLogin === false" size="mini" circle>
              <svg-icon style="width: 25px;height: 25px;margin-top: 0px;" icon-class="collection01-#909399"></svg-icon>
            </el-button>
            <el-popover
              v-if="isLogin === true"
              placement="right"
              width="400"
              height="200px"
              trigger="click"
              ref="popover"
              center
              >
              <div align="center">
                <h3 style="margin-top: 0px;margin-bottom: -10px;">收藏文章</h3>
                <el-divider></el-divider>
                <el-table
                  stripe
                  border
                  height="250px"
                  width="420px"
                  :data="collectionLists"
                  >
                  <el-table-column label="标题" prop="sysCollectionName" align="center">
                    <template slot-scope="scope">
                      <span>{{ scope.row.sysCollectionName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="数量" prop="collectionArticleCount" align="center">
                    <template slot-scope="scope">
                      <span>{{ scope.row.collectionArticleCount }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                      <el-button v-if="scope.row.isArticleCollection === false" size="mini" type="success" plain @click="addArticleCollection(scope.row)">
                        收藏
                      </el-button>
                      <el-button v-if="scope.row.isArticleCollection === true" size="mini" circle @click="removeArticleCollection(scope.row)">
                        <svg-icon style="height: 25px;width: 25px;" icon-class="success00"></svg-icon>
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <el-link v-if="isAddCollection === false" style="margin-top: 15px;" :underline="false" @click="handleClickAddCollection">
                  新建收藏集
                </el-link>
                <el-row style="margin-top: 10px;" v-if="isAddCollection === true" :gutter="24">
                  <el-col :span="15">
                    <el-input
                      v-model="collectionName"
                      clearable
                      >
                    </el-input>
                  </el-col>
                  <el-col :span="4">
                    <el-button type="info" plain @click="cancelAddCollection">取消</el-button>
                  </el-col>
                  <el-col :span="4">
                    <el-button type="success" plain @click="submitAddCollection">添加</el-button>
                  </el-col>
                </el-row>
              </div>
              <el-button slot="reference" size="mini" circle>
                <svg-icon v-if="isCollection === false" style="width: 25px;height: 25px;margin-top: 0px;" icon-class="collection01-#909399"></svg-icon>
                <svg-icon v-if="isCollection === true" style="width: 25px;height: 25px;margin-top: 0px;" icon-class="collection01"></svg-icon>
              </el-button>
            </el-popover>
          </div>
          <!-- 举报反馈 -->
<!--          <div style="border-radius: 50%;height: 40px;width: 40px;margin-top: 15px;background-color: #fff;">-->
<!--            <el-badge type="info">-->
<!--              <el-button size="mini" circle>-->
<!--                <svg-icon style="width: 25px;height: 25px;margin-top: 0px;" icon-class="jubao00"></svg-icon>-->
<!--              </el-button>-->
<!--            </el-badge>-->
<!--          </div>-->
          <div>
            <h5>分享</h5>
          </div>
          <div style="border-radius: 50%;height: 40px;width: 40px;margin-top: 15px;background-color: #fff;">
            <el-tooltip content="复制链接" placement="bottom" effect="light">
              <el-badge type="info">
                <el-button
                  size="mini"
                  circle
                  @click="copyLinkToShareArticle"
                  >
                  <svg-icon style="width: 25px;height: 25px;margin-top: 0px;" icon-class="link01"></svg-icon>
                </el-button>
              </el-badge>
            </el-tooltip>
          </div>
        </div>
        <!-- 文章内容 -->
        <div class="articleDetails-box">
          <!-- 文章区域 -->
          <div>
            <!-- 文章作者简介区域 -->
            <div
              v-loading="userTitleLoading"
              element-loading-text="拼命加载中......"
              class="articleDetails-box-author-title">
              <el-row :gutter="24">
                <el-col :span="2">
                  <el-image
                    class="articleDetails-box-author-title-img"
                    :src="userImgUrl"
                    @click="linkToBlog(userDetails.sysUserId)"
                    ></el-image>
                </el-col>
                <el-col :span="14">
                  <el-row :gutter="24">
                    <el-col :span="14">
                      <el-link style="float: left;" :underline="false" type="info" @click="linkToBlog(userDetails.sysUserId)">
                        <h4 style="color: black;">{{ userDetails.sysUserNickname }}</h4>
                      </el-link>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col style="margin-top: -40px;" :span="12">
                      <h6 style="float: left;color: #909399">
                        {{ userDetails.created | parseTime('{y}') }}年{{ userDetails.created | parseTime('{m}') }}月{{ userDetails.created | parseTime('{d}') }}日 {{ userDetails.created | parseTime('{h}:{m}:{s}')}}
                      </h6>
                    </el-col>
                    <el-col style="margin-top: -40px;margin-left: -30px"  :span="6">
                      <h6 style="float: left;color: #909399">
                        阅读 {{ articleDetails[0].articleBrowseSum }}
                      </h6>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="8">
                  <div v-if="this.sysUserId !== this.userDetails.sysUserId">
                    <el-button v-if="!isLogin" style="margin-top: 16px;float: right;" type="success" size="mini" plain>
                      未关注
                    </el-button>
                    <el-button v-if="isLogin" style="margin-top: 16px;float: right;" type="success" size="mini">
                      已关注
                    </el-button>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!-- 文章内容区域 -->
            <div class="articleDetails-box-content">
              <el-image
                v-if="articleDetails[0].articleImgUrl"
                class="articleDetails-box-content-img"
                :src="articleDetails[0].articleImgUrl"
                :fit="fit"
              >
              </el-image>
              <h1>{{ articleDetails[0].articleTitle }}</h1>
              <!-- 文章内容 -->
              <div
                style="width: 660px;"
                v-if="articleDetails[0].articleType === 'Markdown'"
                align="left"
                >
                <p class="content" v-html="html" />
              </div>
              <div
                v-if="articleDetails[0].articleType === 'tinyMCE'"
                >
                <p class="content" v-html="html" />
              </div>
            </div>
          </div>
          <!-- 评论区域 -->
          <el-divider></el-divider>
          <div id="pinglun" class="articleDetails-pinglun">
            <!-- 评论 -->
            <div class="articleDetails-pinglun-header">
              <el-row :gutter="24">
                <el-col :span="3">
                  <el-image style="float: left;" class="articleDetails-pinglun-header-img" :src="userImgUrl"></el-image>
                </el-col>
                <el-col :span="20">
                  <el-input
                    style="margin-top: 10px;"
                    type="textarea"
                    maxlength="200"
                    v-model.trim="articleCommentsContent"
                    placeholder="请肆意评论吧!"
                    show-word-limit
                    @focus="openCommentsButton"
                    @blur="closeCommentsButton"
                    clearable
                    >
                  </el-input>
                  <div v-if="this.isCommentsButton === true">
                    <el-button v-if="articleCommentsContent !== ''" style="float: right;margin-top: 10px;margin-bottom: 10px;margin-left: 5px;" type="success" plain @click="submitCommentsButton">
                      评论
                    </el-button>
                    <el-button v-if="articleCommentsContent === ''" style="float: right;margin-top: 10px;margin-bottom: 10px;margin-left: 5px;" type="success" plain @click="submitCommentsButton" disabled>
                      评论
                    </el-button>
                    <el-button style="float: right;margin-top: 10px;margin-bottom: 10px;" type="success" plain @click="removeCommentsButton">取消</el-button>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!-- 评论详情 -->
            <div
              v-loading="articleCommentsLoading"
              element-loading-text="拼命加载中......"
              class="articleDetails-pinglun-content"
              v-for="(item, index) in commentsLists"
              :key="index"
              >
              <el-divider></el-divider>
              <el-row :gutter="24">
                <el-col :span="2" :offset="2">
                  <el-image class="articleDetails-pinglun-content-img" :src="item.sysUserIcon" @click="linkToBlog(item.sysUserId)"></el-image>
                </el-col>
                <el-col :span="20">
                  <el-row :gutter="24">
                    <el-col :span="20">
                      <el-link style="float: left;margin-left: 5px;" :underline="false" type="info" @click="linkToBlog(item.sysUserId)">
                        <h4 v-if="item.isArticleAuthor === false" style="color: black;">{{ item.sysUserNickname }}</h4>
                        <h4 v-if="item.isArticleAuthor === true" style="color: black;">{{ item.sysUserNickname }} (作者) </h4>
                      </el-link>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="20">
                      <p style="float: left;margin-top: -10px;margin-left: 5px;">{{ item.articleCommentsContent }}</p>
                    </el-col>
                  </el-row>
                  <el-row :gutter="24">
                    <el-col :span="12">
                      <h6 style="float: left;color: #909399;margin-top: -5px;margin-left: 5px;">
                        {{ item.created | parseTime('{y}') }}年{{ item.created | parseTime('{m}') }}月{{ item.created | parseTime('{d}') }}日 {{ item.created | parseTime('{h}:{m}:{s}')}}
                      </h6>
                    </el-col>
                    <el-col :span="10">
                      <!-- 评论 -->
                      <el-link
                        style="float: right;margin-top: -7px;margin-left: 10px;"
                        :underline="false"
                        type="info"
                        @click="openCommentsReply1(item, index)"
                        >
                        <svg-icon icon-class="pinglun00-#909399"></svg-icon> 回复
                      </el-link>
                      <!-- 点赞 -->
                      <el-link
                        v-if="item.isArticleCommentsPraise === false"
                        style="float: right;margin-top: -7px;"
                        :underline="false"
                        type="info"
                        @click="addCommentsPraise(item)"
                        >
                        <svg-icon icon-class="praise00-#909399"></svg-icon> {{ item.articleCommentsPraiseCount }}
                      </el-link>
                      <el-link
                        v-if="item.isArticleCommentsPraise === true"
                        style="float: right;margin-top: -7px;"
                        :underline="false"
                        type="info"
                        @click="removeCommentsPraise(item)"
                        >
                        <svg-icon icon-class="praise00-#67C23A"></svg-icon> {{ item.articleCommentsPraiseCount }}
                      </el-link>
                    </el-col>
                  </el-row>
                  <!-- 评论回复输入框区域 -->
                  <div
                    v-if="isCommentsReplyButton1 === true && isCommentsReply1 === index"
                    style="background-color: #ECF0F1;width: 100%;height: 115px;padding: 10px;margin-top: -10px;"
                    >
                    <el-input
                      type="textarea"
                      v-model.trim="commentsReplyContent1"
                      maxlength="200"
                      placeholder="请肆意评论吧!"
                      show-word-limit
                      clearable
                      @blur="closeCommentsReply1(item, index)"
                    >
                    </el-input>
                    <div>
                      <el-button
                        v-if="commentsReplyContent1 !== ''"
                        style="float: right;margin-top: 10px;"
                        size="small"
                        type="success"
                        plain
                        @click="submitCommentsReplyButton1(item, index)">
                        评论
                      </el-button>
                      <el-button
                        v-if="commentsReplyContent1 === ''"
                        style="float: right;margin-top: 10px;"
                        size="small"
                        type="success"
                        plain
                        disabled>
                        评论
                      </el-button>
                      <el-button style="float: right;margin-top: 10px;margin-right: 10px;" size="small" type="success" plain @click="removeCommentsReply1">
                        取消
                      </el-button>
                    </div>
                  </div>
                  <!-- 回复详情区域 -->
                  <div
                    class="articleDetails-reply-content"
                    v-for="(row, i) in item.commentsReplyLists"
                    :key="i"
                    v-if="item.commentsReplyLists.length >= 0"
                  >
                    <el-row :gutter="24">
                      <el-col :span="2">
                        <el-image class="articleDetails-reply-content-img" :src="row.fromSysUserIcon" @click="linkToBlog(row.fromSysUserId)"></el-image>
                      </el-col>
                      <el-col :span="20">
                        <!-- 该回复发表人 -->
                        <el-row :gutter="24">
                          <el-col :span="20">
                            <el-link type="info" :underline="false" style="float: left;margin-left: 5px;margin-top: -10px;" @click="linkToBlog(row.fromSysUserId)">
                              <h4 v-if="row.fromSysUserId !== articleDetails[0].sysUserId" style="color: black;">{{ row.fromSysUserNickname }}</h4>
                              <h4 v-if="row.fromSysUserId === articleDetails[0].sysUserId" style="color: black;">{{ row.fromSysUserNickname }} (作者) </h4>
                            </el-link>
                          </el-col>
                        </el-row>
                        <!-- 回复内容 -->
                        <el-row :gutter="24">
                          <el-col :span="20">
                            <div v-if="row.toSysUserNickname === null" >
                              <el-row :gutter="24">
                                <el-col :span="24">
                                  <p style="float: left;margin-top: -10px;margin-left: 5px;">{{ row.commentsReplyContent }}</p>
                                </el-col>
                              </el-row>
                            </div>
                            <div v-if="row.toSysUserNickname !== null">
                              <el-row :gutter="24">
                                <el-col :span="6">
                                  <h5 style="float: left;margin-left: 5px;color: black;margin-top: -10px;"> 回复 </h5>
                                </el-col>
                                <el-col :span="16">
                                  <el-link style="float: left;margin-top: -28px;margin-left: -15px;" :underline="false" type="success" @click="linkToBlog(row.toSysUserId)">
                                    <h5 v-if="row.toSysUserId !== articleDetails[0].sysUserId"> {{ row.toSysUserNickname }} : </h5>
                                    <h5 v-if="row.toSysUserId === articleDetails[0].sysUserId"> {{ row.toSysUserNickname }} (作者) : </h5>
                                  </el-link>
                                </el-col>
                              </el-row>
                              <el-row :gutter="24">
                                <el-col :span="24">
                                  <p style="float: left;margin-top: -10px;margin-left: 5px;">{{ row.commentsReplyContent }}</p>
                                </el-col>
                              </el-row>
                            </div>
                          </el-col>
                        </el-row>
                        <!-- 年月日 点赞 评论 -->
                        <el-row :gutter="24">
                          <el-col :span="12">
                            <h6 style="float: left;color: #909399;margin-top: -5px;margin-left: 5px;">
                              {{ row.created | parseTime('{y}') }}年{{ row.created | parseTime('{m}') }}月{{ row.created | parseTime('{d}') }}日 {{ row.created | parseTime('{h}:{m}:{s}')}}
                            </h6>
                          </el-col>
                          <el-col :span="10">
                            <!-- 评论 -->
                            <el-link
                              style="float: right;margin-top: -7px;margin-left: 10px;"
                              :underline="false"
                              type="info"
                              @click="openCommentsReply2(row, i, index)"
                            >
                              <svg-icon icon-class="pinglun00-#909399"></svg-icon> 回复
                            </el-link>
                            <!-- 点赞 -->
                            <el-link
                              v-if="row.isPraise === false"
                              style="float: right;margin-top: -7px;"
                              :underline="false"
                              type="info"
                              @click="addReplyPraise(row)"
                            >
                              <svg-icon icon-class="praise00-#909399"></svg-icon> {{ row.replyPraiseCount }}
                            </el-link>
                            <el-link
                              v-if="row.isPraise === true"
                              style="float: right;margin-top: -7px;"
                              :underline="false"
                              type="info"
                              @click="removeReplyPraise(row)"
                            >
                              <svg-icon icon-class="praise00-#67C23A"></svg-icon> {{ row.replyPraiseCount }}
                            </el-link>
                          </el-col>
                        </el-row>
                        <!-- 回复评论的回复输入框区域 -->
                        <div
                          v-if="isCommentsReplyButton2 === true && isCommentsReply2 === i && isCommentsReplyIndex2 === index"
                          style="background-color: #fff;width: 100%;height: 115px;padding: 10px;margin-top: -10px;"
                          >
                          <el-input
                            type="textarea"
                            v-model.trim="commentsReplyContent2"
                            maxlength="200"
                            show-word-limit
                            clearable
                            @blur="closeCommentsReply2(row, i, index)"
                            >
                          </el-input>
                          <div>
                            <!-- 评论按钮 -->
                            <el-button
                              v-if="commentsReplyContent2 === ''"
                              style="float: right;margin-top: 10px;margin-left: 10px;"
                              size="small"
                              type="success"
                              plain
                              disabled
                              >
                              评论
                            </el-button>
                            <el-button
                              v-if="commentsReplyContent2 !== ''"
                              style="float: right;margin-top: 10px;margin-left: 10px;"
                              size="small"
                              type="success"
                              plain
                              @click="submitCommentsReply2(row, i)"
                            >
                              评论
                            </el-button>
                            <!-- 取消按钮 -->
                            <el-button style="float: right;margin-top: 10px;" size="small" type="success" plain @click="removeCommentsReply2(row, i)">
                              取消
                            </el-button>
                          </div>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </el-col>
              </el-row>
              <!-- 回复详情 -->
            </div>
          </div>
        </div>
        <!-- 其他区域 -->
        <div class="articleDetails-right">
          <!-- 关于作者区域 -->
          <div
            v-loading="authorLoading"
            element-loading-text="拼命加载中......"
            class="articleDetails-right-author"
            >
            <span>关于作者</span>
            <hr style="margin-top: 10px;margin-bottom: 10px;border: 1px solid #ECF0F1;">
            <div>
              <el-row :gutter="24">
                <el-col :span="6">
                  <el-image class="articleDetails-right-img" :src="userImgUrl" @click="linkToBlog(userDetails.sysUserId)"></el-image>
                </el-col>
                <el-col :span="14">
                  <el-link :underline="false" type="info" @click="linkToBlog(userDetails.sysUserId)">
                    <h6 style="color: black;">{{ userDetails.sysUserNickname }}</h6>
                  </el-link>
                </el-col>
              </el-row>
              <el-row :gutter="24">
                <el-col :span="4">
                  <svg-icon class="articleDetails-right-svg" icon-class="dianzan01-#1afa29"></svg-icon>
                </el-col>
                <el-col :span="20">
                  <h6 style="float: left;margin-left: 10px;">总获得点赞 {{ userPriseAll }}</h6>
                </el-col>
              </el-row>
              <el-row :gutter="24">
                <el-col :span="4">
                  <svg-icon style="height: 32px;width: 32px;margin-left: 10px;margin-top: -5px;" icon-class="yuedu00"></svg-icon>
                </el-col>
                <el-col :span="20">
                  <h6 style="margin-top: 2px;float: left;margin-left: 10px;">文章被阅读 {{ userBrowseAll }}</h6>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 作者有关文章区域 -->
          <div class="articleDetails-right-article">
            <el-row :gutter="24">
              <el-col :span="12">
                <span>相关文章</span>
              </el-col>
              <el-col :span="12">
              </el-col>
            </el-row>
            <hr style="margin-top: 10px;margin-bottom: 10px;border: 1px solid #ECF0F1;">
            <div
              v-for="(item, index) in userArticleDetails"
              :key="index"
              style="margin-top: 20px;"
              >
              <el-divider v-if="index !== 0"></el-divider>
              <el-link
                style="float: left;margin-left: 10px;margin-top: -10px;color: black"
                :underline="false"
                type="info"
                @click="linkToArticleDetails(item)"
                >
                {{ item.articleTitle }}
              </el-link>
              <el-row :gutter="24">
                <div>
                  <el-col :span="24">
                    <!-- 浏览数量 -->
                    <div style="float: left;margin-left: 10px;margin-top: 10px;color: #909399;">
                      <svg-icon icon-class="liulang00"></svg-icon>
                      {{ item.articleBrowseSum }}
                    </div>
                    <!-- 点赞数量 -->
                    <div
                      style="float: left;margin-top: 10px;margin-left: 10px;color: #909399"
                      v-if="item.isPraise === false"
                      >
                      <svg-icon icon-class="praise00"></svg-icon>
                      {{ item.articlePraiseCount }}
                    </div>
                    <div
                      style="float: left;margin-top: 10px;margin-left: 10px;color: #1afa29"
                      v-if="item.isPraise === true"
                      >
                      <svg-icon icon-class="dianzan01-#1afa29"></svg-icon>
                      {{ item.articlePraiseCount }}
                    </div>
                    <!-- 评论数量 -->
                    <div style="float: left;margin-top: 10px;margin-left: 10px;color: #909399">
                      <svg-icon icon-class="pinglun00"></svg-icon>
                      {{ item.articleCommentsCount }}
                    </div>
                  </el-col>
                </div>
              </el-row>
            </div>
            <div style="margin-bottom: 20px;height: 20px">
            </div>
            <!-- 自动生成文章目录区域 -->
          </div>
        </div>
      </div>
      <!-- 回到顶部 -->
      <el-tooltip content="返回顶部" placement="top">
        <vue-to-top
          type="12"
          right="100"
          bottom="50"
          size="60"
          color="#67C23A"
          duration="300"
        >
        </vue-to-top>
      </el-tooltip>
    </div>
</template>

<script>
  import { getToken } from '@/utils/auth'
  import { getArticleDetails, getArticleUserDetails, isUserPraise, addArticlePraise, deleteArticlePraise, insertArticleBrowse,
    getCommentsLists, insertArticlesComments, insertCommentsPraise, deleteCommentsPraise, insertReplyPraise, deleteReplyPraise,
    insertCommentsOrReply } from '@/api/homePageArticle'
  import { getCollectionsToArticle, insertArticleToCollection, deleteArticleToCollection, insertCollection } from '@/api/collection'
  import marked from 'marked'
  import vueToTop from 'vue-totop'
  export default {
    name: 'ArticleDetails',
    components: { vueToTop },
    data() {
      return {
        loading: false,
        userTitleLoading: false,
        authorLoading: false,
        articleCommentsLoading: false,
        // 是否登录
        isLogin: false,
        userImgUrl: '',
        // 判断该文章的发布者是否是本人
        isMySelf: false,
        sysUserId: '',
        collectionName: '',
        collectionLists: [
          {
            sysCollectionId: '',
            created: '',
            updated: '',
            sysUserId: '',
            isArticleCollection: '',
            collectionArticleCount: ''
          }
        ],
        userDetails: {
          created: '',
          updated: '',
          sysUserId: '',
          sysUserNickname: '',
          sysUserPhone: '',
          sysUserEmail: '',
          sysUserAreaCode: '',
          sysUserSex: '',
          sysUserStatus: '',
          sysUserUsername: '',
          sysUserPassword: '',
          sysUserType: '',
          sysUserIcon: '',
          sysUserKey: '',
          sysUserIntroduction: ''
        },
        articleDetails: [
          {
            created: '',
            updated: '',
            articleId: '',
            articleTitle: '',
            articleContent: '',
            articleType: '',
            articleBrowseSum: '',
            articleImgUrl: '',
            articleImgKey: '',
            sysUserId: '',
            labelId: ''
          }
        ],
        userArticleDetails: [
          {
            articleTitle: '',
            labelId: '',
            articleId: '',
            articleType: '',
            updated: '',
            created: '',
            articleContent: '',
            articleBrowseSum: '',
            sysUserId: '',
            articleImgUrl: '',
            articleImgKey: ''
          }
        ],
        // 评论表详情
        commentsLists: [
          {
            created: '',
            updated: '',
            sysArticleCommentsId: '',
            articleId: '',
            sysUserId: '',
            sysUserNickname: '',
            isArticleAuthor: '',
            articleCommentsContent: '',
            sysUserIcon: '',
            articleCommentsPraiseCount: '',
            isArticleCommentsPraise: false,
            // 回复详情
            commentsReplyLists: [
              {
                created: '',
                updated: '',
                fromSysUserId: '',
                toSysUserId: '',
                commentsReplyContent: '',
                commentsReplyType: '',
                sysCommentsReplyId: '',
                sysArticleCommentsId: '',
                replyPraiseCount: '',
                fromSysUserNickname: '',
                toSysUserNickname: '',
                isPraise: '',
                fromSysUserIcon: ''
              }
            ]
          }
        ],
        // 文章点赞数
        articlePraiseCount: 0,
        // 文章评论数
        articleCommentsCount: 0,
        isPraise: false,
        isCollection: false,
        userPriseAll: 0,
        userBrowseAll: 0,
        // 是否新建收藏集
        isAddCollection: false,
        fit: 'contain',
        html: '',
        // 评论内容
        articleCommentsContent: '',
        // 是否显示评论按钮
        isCommentsButton: false,
        // 是否显示回复评论
        isCommentsReplyButton1: false,
        isCommentsReply1: -1,
        commentsReplyContent1: '',
        // 是否显示评论回复的评论
        isCommentsReplyButton2: false,
        isCommentsReply2: -1,
        isCommentsReplyIndex2: -1,
        commentsReplyContent2: '',
        // 是否显示回复详情
        isShowCommentReplyList: false,
        // 回复表参数
        commentsReplyParams: {
          // 回复类型 comments or reply
          commentsReplyType: '',
          sysArticleCommentsId: '',
          commentsReplyContent: '',
          fromSysUserId: '',
          toSysUserId: '',
          created: '',
          updated: ''
        },
        // 自动生成目录相关
        tabPosition: 'right',
        activeName: 'tab0',
        // 滚动距离
        scroll: '',
        navList: []
      }
    },
    watch: {
    },
    created() {
      this.checkLogin()
      // 判断是否跳转评论
      if (this.$route.query.jump === 'comment') {
        setTimeout(() => {
          this.jumpToPingLun()
        }, 1000)
      }
    },
    methods: {
      // 判断是否登录
      checkLogin() {
        const hashToken = getToken()
        if (hashToken) {
          this.isLogin = true
          // 获取当前登录用户id
          this.sysUserId = localStorage.getItem('sysUserId')
          // 判断当前文章是否是当前用户点赞过了
          isUserPraise(this.$route.query.articleId).then(res => {
            if (res.code === 200) {
              this.isPraise = res.data.isPraise
            } else {
              this.isPraise = false
              this.$message.error(res.message)
            }
          })
          // 判断当前文章是否已经被当前用户收藏了
          getCollectionsToArticle(this.$route.query.articleId).then(res => {
            if (res.code === 200) {
              this.isCollection = res.data.isCollection
              this.collectionLists = res.data.collectionLists
            } else {
              this.isCollection = false
              this.$message.error(res.message)
            }
          })
          // 获取刷新页面数据
          this.refreshPage()
        } else {
          this.refreshPage()
          this.isLogin = false
        }
      },
      refreshPage() {
        // 获取文章详情
        this.loading = true
        if (this.sysUserId === '') {
          this.sysUserId = -1
        }
        getArticleDetails(this.$route.query.articleId).then(res => {
          if (res.code === 200) {
            this.articleDetails = res.data.articleDetails
            document.title = this.articleDetails[0].articleTitle
            // this.html = res.data.articleDetails[0].articleContent
            // this.html = this.$refs.markdownEditor.getHtml()
            this.articlePraiseCount = res.data.articlePraiseCount
            this.articleCommentsCount = res.data.articleCommentsCount
            if (res.data.articleDetails[0].articleType === 'Markdown') {
              // 对图片做处理,防止图片过大
              const content = marked(res.data.articleDetails[0].articleContent)
              const regex = new RegExp('<img', 'gi')
              res.data.articleDetails[0].articleContent = content.replace(regex, `<img style="max-width: 100%;height: auto;"`)
              const regex1 = new RegExp('<p', 'gi')
              const content1 = res.data.articleDetails[0].articleContent
              res.data.articleDetails[0].articleContent = content1.replace(regex1, `<p style="max-width: 100%;"`)
              this.html = marked(res.data.articleDetails[0].articleContent)
            }
            if (res.data.articleDetails[0].articleType === 'tinyMCE') {
              // 对图片做处理,防止图片过大
              const content = res.data.articleDetails[0].articleContent
              const regex = new RegExp('<img', 'gi')
              res.data.articleDetails[0].articleContent = content.replace(regex, `<img style="max-width: 100%;height: auto;"`)
              this.html = res.data.articleDetails[0].articleContent
            }
            this.loading = false
          } else {
            this.$message.error(res.message)
            this.loading = false
          }
        })
        // 获取用户详情
        this.userTitleLoading = true
        getArticleUserDetails(this.$route.query.articleId, this.sysUserId).then(res => {
          if (res.code === 200) {
            this.userDetails = res.data.userDetails
            this.userImgUrl = res.data.userDetails.sysUserIcon
            this.userPriseAll = res.data.userPriseAll
            this.userBrowseAll = res.data.userBrowseAll
            this.userArticleDetails = res.data.userArticleDetails
            this.userTitleLoading = false
          } else {
            this.userTitleLoading = false
            this.$message.error(res.message)
          }
        })
        // 获取评论详情
        this.articleCommentsLoading = true
        getCommentsLists(this.$route.query.articleId, this.sysUserId).then(res => {
          if (res.code === 200) {
            this.commentsLists = res.data.commentsLists
            this.articleCommentsLoading = false
          } else {
            this.articleCommentsLoading = false
            this.$message.error(res.message)
          }
        })
      },
      // 跳转文章详情
      linkToArticleDetails(item) {
        this.addArticleBrowse(item)
      },
      // 查看文章详情时，使文章被阅读数加一
      addArticleBrowse(item) {
        insertArticleBrowse(item.articleId).then(res => {
          if (res.code === 200) {
            const routeUrl = this.$router.resolve({
              name: 'ArticleDetails',
              query: { articleId: item.articleId }
            })
            window.open(routeUrl.href, '_blank')
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 点击评论跳转到评论区域
      jumpToPingLun() {
        // 获取评论区域div
        var pingLunDiv = document.querySelector('#pinglun')
        // 打印出对应页面与窗口的距离
        // console.log(pingLunDiv.offsetTop)
        // 使用平滑属性，滑动到上方获取的距离
        // widow 根据浏览器滚动条，如果要在某个盒子里面产生滑动，记得修改
        this.$nextTick(() => {
          window.scrollTo({
            'top': pingLunDiv.offsetTop,
            'behavior': 'smooth'
          })
        })
      },
      // 没有登录系统先跳转登录,然后再返回该页面
      linkToLogin() {
        this.$message.warning('请先登录系统，再执行此操作!')
        setTimeout(() => {
          // 跳转登录，并通过redirect = yes / redirect = no 来判断是否返回原页面
          this.$router.push({
            path: '/login',
            query: {
              redirect: 'yes'
            }
          })
        }, 1500)
      },
      // 点赞文章
      submitArticlePraise() {
        // 先判断是否登录
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          addArticlePraise(this.$route.query.articleId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 取消点赞
      removeArticlePraise() {
        this.$confirm('此操作将取消点赞,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteArticlePraise(this.$route.query.articleId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 收藏集点击时事件
      clickCollection() {
        // 先判断是否登录
        if (this.isLogin === false) {
          this.linkToLogin()
        }
      },
      // 触发添加收藏集
      handleClickAddCollection() {
        this.isAddCollection = true
      },
      // 添加文章收藏
      addArticleCollection(item) {
        insertArticleToCollection(this.$route.query.articleId, item.sysCollectionId).then(res => {
          if (res.code === 200) {
            // 关闭 popover 弹窗
            this.$refs[`popover`].doClose()
            this.checkLogin()
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 取消文章收藏
      removeArticleCollection(item) {
        this.$confirm('此操作将取消该文章的收藏,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteArticleToCollection(this.$route.query.articleId, item.sysCollectionId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 新建收藏集取消按钮
      cancelAddCollection() {
        this.isAddCollection = false
        this.$message.info('取消成功!')
      },
      // 新建收藏集确认按钮
      submitAddCollection() {
        if (this.collectionName === null || this.collectionName === '') {
          this.$message.error('收藏集名称不能为空!')
        } else {
          insertCollection(this.collectionName).then(res => {
            if (res.code === 200) {
              this.isAddCollection = false
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 评论框聚焦时打开对应按钮
      openCommentsButton() {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          this.isCommentsButton = true
        }
      },
      // 评论框失焦时关闭对应按钮
      closeCommentsButton() {
        if (this.articleCommentsContent !== '') {
          this.isCommentsButton = true
        } else {
          this.isCommentsButton = false
        }
      },
      // 评论框取消按钮
      removeCommentsButton() {
        this.isCommentsButton = false
      },
      // 评论框确认按钮
      submitCommentsButton() {
        insertArticlesComments(this.$route.query.articleId, this.articleCommentsContent).then(res => {
          if (res.code === 200) {
            this.checkLogin()
            this.isCommentsButton = false
            this.articleCommentsContent = ''
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 回复评论框聚焦时
      openCommentsReply1(item, index) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          this.isCommentsReplyButton1 = true
          this.isCommentsReply1 = index
        }
      },
      // 回复评论框失焦时
      closeCommentsReply1(item, index) {
        if (this.commentsReplyContent1 !== '') {
          this.isCommentsReplyButton1 = true
          this.isCommentsReply1 = index
        } else {
          this.isCommentsReplyButton1 = false
          this.isCommentsReply1 = -1
        }
      },
      // 取消回复评论框按钮
      removeCommentsReply1() {
        this.isCommentsReplyButton1 = false
        this.isCommentsReply1 = -1
        this.commentsReplyContent1 = ''
      },
      // 回复评论的评论按钮
      submitCommentsReplyButton1(item, index) {
        this.commentsReplyParams.sysArticleCommentsId = item.sysArticleCommentsId
        this.commentsReplyParams.commentsReplyContent = this.commentsReplyContent1
        this.commentsReplyParams.commentsReplyType = 'comment'
        this.commentsReplyParams.fromSysUserId = this.sysUserId
        this.commentsReplyParams.toSysUserId = null
        insertCommentsOrReply(this.commentsReplyParams).then(res => {
          if (res.code === 200) {
            this.checkLogin()
            this.isCommentsReplyButton1 = false
            this.isCommentsReply1 = -1
            this.commentsReplyContent1 = ''
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 评论回复的评论聚焦时
      openCommentsReply2(row, i, index) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          this.isCommentsReplyButton2 = true
          this.isCommentsReply2 = i
          this.isCommentsReplyIndex2 = index
          console.log(this.isCommentsReply2 + '/' + index)
        }
      },
      // 评论回复的评论失焦时
      closeCommentsReply2(row, i, index) {
        if (this.commentsReplyContent2 !== '') {
          this.isCommentsReplyButton2 = true
          this.isCommentsReply2 = i
          this.isCommentsReplyIndex2 = index
        } else {
          this.isCommentsReplyButton2 = false
          this.isCommentsReply2 = -1
          this.isCommentsReplyIndex2 = -1
        }
      },
      // 取消评论回复的评论按钮
      removeCommentsReply2(row, i) {
        this.isCommentsReplyButton2 = false
        this.isCommentsReply2 = -1
        this.isCommentsReplyIndex2 = -1
        this.commentsReplyContent2 = ''
      },
      // 评论回复的评论的评论按钮
      submitCommentsReply2(row, i) {
        this.commentsReplyParams.sysArticleCommentsId = row.sysArticleCommentsId
        this.commentsReplyParams.commentsReplyType = 'reply'
        this.commentsReplyParams.fromSysUserId = this.sysUserId
        this.commentsReplyParams.commentsReplyContent = this.commentsReplyContent2
        this.commentsReplyParams.toSysUserId = row.fromSysUserId
        insertCommentsOrReply(this.commentsReplyParams).then(res => {
          if (res.code === 200) {
            this.checkLogin()
            this.isCommentsReplyButton2 = false
            this.isCommentsReply2 = -1
            this.commentsReplyContent2 = ''
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 点赞评论
      addCommentsPraise(item) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          insertCommentsPraise(item.sysArticleCommentsId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 点赞回复
      addReplyPraise(row) {
        if (this.isLogin === false) {
          this.linkToLogin()
        } else {
          insertReplyPraise(row.sysCommentsReplyId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 取消评论点赞
      removeCommentsPraise(item) {
        this.$confirm('此操作将取消该评论的点赞,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteCommentsPraise(item.sysArticleCommentsId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 取消回复的点赞
      removeReplyPraise(row) {
        this.$confirm('此操作将取消该回复的点赞,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteReplyPraise(row.sysCommentsReplyId).then(res => {
            if (res.code === 200) {
              this.checkLogin()
              this.$message.success(res.message)
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message.info('已取消该操作')
        })
      },
      // 跳转用户主页
      linkToBlog(id) {
        this.$router.push({
          name: 'Blog',
          query: {
            sysUserId: id
          }
        })
      },
      // 复制链接分享文章
      copyLinkToShareArticle() {
        this.shareLinkUrl = 'http://localhost:8527/#/articleDetails?articleId=' + this.$route.query.articleId
        console.log('s = ' + this.shareLinkUrl)
        this.$copyText(this.shareLinkUrl).then(res => {
          this.onCopy()
        }).catch(() => {
          this.onError()
        })
      },
      onCopy() {
        this.$message.success('文章链接已经复制到剪贴板!')
      },
      onError() {
        this.$message.error('抱歉,复制失败,请稍后重试!')
      }
    }
  }
</script>

<style lang="scss" scoped>

  .articleDetails-main {
    background-color: #ECF0F1;
    width: 1020px;

    .articleDetails-left {
      position: fixed;
      width: 40px;
      height: 400px;
      /*background-color: black;*/
      background-color: #ECF0F1;
      float: left;
      margin-top: 180px;
      margin-left: -40px;
    }

    .articleDetails-box {
      width: 700px;
      min-height: 800px;
      background-color: #fff;
      float: left;
      margin-left: 20px;
      padding: 20px;

      .articleDetails-box-author-title {
        width: 100%;
        background-color: #fff;
        max-height: 60px;
      }

      .articleDetails-box-author-title-img {
        margin-top: 10px;
        height: 40px;
        width: 40px;
        border-radius: 50%;
      }

      .articleDetails-box-content {
        width: 100%;
        background-color: #fff;
        min-height: 400px;
        margin-top: 20px;
      }

      .articleDetails-box-content-img {
        width: 100%;
      }
    }

    .articleDetails-pinglun {
      /*background-color: #13ce66;*/
      background-color: #fff;
      width: 100%;
      min-height: 300px;
      padding: 20px;

      .articleDetails-pinglun-header {
        background-color: #ECF0F1;
        width: 100%;
        min-height: 100px;
      }

      .articleDetails-pinglun-header-img {
        margin-top: 10px;
        margin-left: 10px;
        height: 50px;
        width: 50px;
        border-radius: 50%;
      }

      .articleDetails-pinglun-content {
        background-color: #fff;
        width: 100%;
        min-height: 100px;
      }

      .articleDetails-pinglun-content-img {
        margin-top: 10px;
        margin-left: 10px;
        height: 40px;
        width: 40px;
        border-radius: 50%;
      }

      .articleDetails-reply-content {
        background-color: #ECF0F1;
        width: 100%;
        min-height: 100px;
        margin-top: 10px;
        padding: 10px;
      }

      .articleDetails-reply-content-img {
        height: 40px;
        width: 40px;
        border-radius: 50%;
      }
    }

    .articleDetails-right {
      width: 240px;
      min-height: 800px;
      background-color: #ECF0F1;
      float: left;
      margin-left: 20px;
      padding: 20px;

      .articleDetails-right-author {
        background-color: #fff;
        width: 100%;
      }

      .articleDetails-right-img {
        margin-top: 10px;
        margin-left: 10px;
        height: 50px;
        width: 50px;
        border-radius: 50%;
      }

      .articleDetails-right-svg {
        width: 30px;
        height: 30px;
        margin-left: 10px;
        margin-top: 15px;
      }

      .articleDetails-right-article {
        background-color: #fff;
        min-height: 300px;
        width: 100%;
        margin-top: 20px;
      }
    }
  }
</style>
