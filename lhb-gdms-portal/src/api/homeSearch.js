import request from '@/utils/request'

// 新增搜索框历史记录
export function insertInputHistory(searchContent) {
  return request({
    url: '/consumer-blog/blog/home-search/search',
    method: 'post',
    params: { searchContent: searchContent }
  })
}

// 获取搜索框历史记录
export function getInputHistory() {
  return request({
    url: '/consumer-blog/blog/home-search/search',
    method: 'get'
  })
}

// 修改搜索框历史记录顺序
export function updateInputHistory(searchContent) {
  return request({
    url: '/consumer-blog/blog/home-search/search',
    method: 'put',
    params: { searchContent: searchContent }
  })
}

// 删除搜索框历史记录
export function deleteInputHistory(sysInputHistoryId) {
  return request({
    url: '/consumer-blog/blog/home-search/search/' + sysInputHistoryId,
    method: 'delete'
  })
}

// 清空搜索框历史记录
export function clearInputHistory() {
  return request({
    url: '/consumer-blog/blog/home-search/search/clear',
    method: 'delete'
  })
}

// 搜索文章
export function searchArticleLists(sysUserId, search, articleType) {
  return request({
    url: '/consumer-blog/web/blog/home-search/search/article/' + sysUserId,
    method: 'get',
    params: { search: search, articleType: articleType }
  })
}

// 搜索用户
export function searchUserLists(sysUserId, search) {
  return request({
    url: '/consumer-blog/web/blog/home-search/search/user/' + sysUserId,
    method: 'get',
    params: { search: search }
  })
}

// 搜索标签
export function searchLabelLists(sysUserId, search) {
  return request({
    url: '/consumer-blog/web/blog/home-search/search/label/' + sysUserId,
    method: 'get',
    params: { search: search }
  })
}
