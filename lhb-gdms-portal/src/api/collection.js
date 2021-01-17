import request from '@/utils/request'

// 新建收藏集
export function insertCollection(sysCollectionName) {
  return request({
    url: '/consumer-blog/blog/collection',
    method: 'post',
    params: { sysCollectionName: sysCollectionName }
  })
}

// 编辑收藏集名称
export function updateCollectionName(sysCollectionId, sysCollectionName) {
  return request({
    url: '/consumer-blog/blog/collection/' + sysCollectionId,
    method: 'put',
    params: { sysCollectionName: sysCollectionName }
  })
}

// 获取收藏集合列表信息
export function getCollectionLists() {
  return request({
    url: '/consumer-blog/blog/collection/lists',
    method: 'get'
  })
}

// 删除收藏集
export function deleteOne(sysCollectionId) {
  return request({
    url: '/consumer-blog/blog/collection/' + sysCollectionId,
    method: 'delete'
  })
}

// 查询收藏集下文章详情
export function getCollectionListById(sysCollectionId) {
  return request({
    url: '/consumer-blog/blog/collection/list/' + sysCollectionId,
    method: 'get'
  })
}

// 查询当前用户的收藏集并且用于文章详情判断当前文章是否被收藏
export function getCollectionsToArticle(articleId) {
  return request({
    url: '/consumer-blog/blog/collection/lists/' + articleId,
    method: 'get'
  })
}

// 添加文章到收藏集
export function insertArticleToCollection(articleId, sysCollectionId) {
  return request({
    url: '/consumer-blog/blog/collection/article/' + articleId + '/' + sysCollectionId,
    method: 'post'
  })
}

// 取消文章的收藏
export function deleteArticleToCollection(articleId, sysCollectionId) {
  return request({
    url: '/consumer-blog/blog/collection/article/' + articleId + '/' + sysCollectionId,
    method: 'delete'
  })
}
