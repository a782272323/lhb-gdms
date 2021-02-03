import request from '@/utils/request'

// 文章被点击查看时被阅读数加
export function insertArticleBrowse(articleId) {
  return request({
    url: '/consumer-blog/web/blog/home-page-article/article/browse/' + articleId,
    method: 'post'
  })
}

// 文章以及文章有关详情
export function getArticleDetails(articleId) {
 return request({
   url: '/consumer-blog/web/blog/home-page-article/article/details/' + articleId,
   method: 'get'
 })
}

// 获取文章相关用户详情标题
export function getArticleUserDetails(articleId, sysUserId) {
  return request({
    url: '/consumer-blog/web/blog/home-page-article/user/details/' + articleId + '/' + sysUserId,
    method: 'get'
  })
}

// 判断当前文章是否是当前用户点赞过了
export function isUserPraise(articleId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/user/isPraise/' + articleId,
    method: 'get'
  })
}

// 点赞文章
export function addArticlePraise(articleId) {
 return request({
   url: '/consumer-blog/blog/home-page-article/article/praise/' + articleId,
   method: 'post'
 })
}

// 取消点赞
export function deleteArticlePraise(articleId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/article/praise/' + articleId,
    method: 'delete'
  })
}

// 根据文章id获取文章评论表全部列表信息
export function getCommentsLists(articleId, sysUserId) {
  return request({
    url: '/consumer-blog/web/blog/home-page-article/comments/lists/' + articleId + '/' + sysUserId,
    method: 'get'
  })
}

// 发表评论
export function insertArticlesComments(articleId, articleCommentsContent) {
  return request({
    url: '/consumer-blog/blog/home-page-article/comments/' + articleId,
    method: 'post',
    params: { articleCommentsContent: articleCommentsContent }
  })
}

// 点赞评论
export function insertCommentsPraise(sysArticleCommentsId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/comments/praise/' + sysArticleCommentsId,
    method: 'post'
  })
}

// 取消评论点赞
export function deleteCommentsPraise(sysArticleCommentsId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/comments/praise/' + sysArticleCommentsId,
    method: 'delete'
  })
}

// 点赞回复
export function insertReplyPraise(sysCommentsReplyId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/reply/praise/' + sysCommentsReplyId,
    method: 'post'
  })
}

// 取消回复的点赞
export function deleteReplyPraise(sysCommentsReplyId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/reply/praise/' + sysCommentsReplyId,
    method: 'delete'
  })
}

// 回复评论
export function insertCommentsOrReply(data) {
  return request({
    url: '/consumer-blog/blog/home-page-article/comments/reply',
    method: 'post',
    data
  })
}

// 删除单个文章
export function deleteArticle(articleId) {
  return request({
    url: '/consumer-blog/blog/home-page-article/article/' + articleId,
    method: 'delete'
  })
}

// 编辑文章
export function editArticle(articleId, data) {
  return request({
    url: '/consumer-blog/blog/home-page-article/article/' + articleId,
    method: 'put',
    data
  })
}
