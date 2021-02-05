import request from '@/utils/request'

// 获取文章列表
export function getArticleLists(tableParams) {
  return request({
    url: '/consumer-admin/admin/blog/overview/article/lists',
    method: 'get',
    params: { offSet: tableParams.offSet, limit: tableParams.limit, keyWord: tableParams.keyWord, orderBy: tableParams.orderBy }
  })
}

// 搜索文章
export function searchArticleLists(tableParams, searchParams) {
  return request({
    url: '/consumer-admin/admin/blog/overview/article/lists/search',
    method: 'get',
    params: { offSet: tableParams.offSet, limit: tableParams.limit, keyWord: tableParams.keyWord, orderBy: tableParams.orderBy,
      sysUserNickname: searchParams.sysUserNickname, articleTitle: searchParams.articleTitle, labelName: searchParams.labelName }
  })
}

// 根据文章id查看单个文章信息
export function getArticleList(articleId) {
  return request({
    url: '/consumer-admin/admin/blog/overview/article/list/' + articleId,
    method: 'get'
  })
}
