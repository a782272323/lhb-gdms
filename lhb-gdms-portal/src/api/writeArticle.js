import request from '@/utils/request'

// 用户写文章内容时上传的图片
export function articleContentImg(data) {
  return request({
    url: '/consumer-blog/blog/write/article/content/img',
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 添加文章封面图片
export function articleCoverImg(data) {
  return request({
    url: '/consumer-blog/blog/write/article/cover/img',
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 删除文章封面图片
export function deleteArticleCoverImg(params) {
  return request({
    url: '/consumer-blog/blog/write/article/cover/img',
    method: 'delete',
    params: { key: params }
  })
}

// 保存到草稿箱
export function addDraft(data) {
  return request({
    url: '/consumer-blog/blog/write/draft',
    method: 'post',
    data
  })
}

// 获取标签信息列表
export function getLabelInfo() {
  return request({
    url: '/consumer-blog/blog/write/article/label',
    method: 'get'
  })
}

// 搜索标签信息列表
export function queryLabelInfo(keyWord) {
  return request({
    url: '/consumer-blog/blog/write/article/label/query',
    method: 'get',
    params: { keyWord: keyWord }
  })
}

// 发布文章
export function faBuArticle(data, labelId) {
  return request({
    url: '/consumer-blog/blog/write/article',
    method: 'post',
    params: { labelId: labelId },
    data
  })
}

// 获取草稿箱列表
export function getDraftLists() {
  return request({
    url: '/consumer-blog/blog/write/draft/lists',
    method: 'get'
  })
}

// 删除草稿箱
export function deleteDraft(sysDraftId) {
  return request({
    url: '/consumer-blog/blog/write/draft/' + sysDraftId,
    method: 'delete'
  })
}

// 编辑草稿箱
export function updateDraft(sysDraftId, data) {
  return request({
    url: '/consumer-blog/blog/write/draft/' + sysDraftId,
    method: 'put',
    data
  })
}

// 根据文章id获取文章详情
export function getArticleList(articleId) {
  return request({
    url: '/consumer-blog/blog/write/article/list/' + articleId,
    method: 'get'
  })
}

// 根据标签id获取标签信息
export function getLabelDetails(labelId) {
  return request({
    url: '/consumer-blog/blog/write/article/label/' + labelId,
    method: 'get'
  })
}
