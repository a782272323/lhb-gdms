import request from '@/utils/request'

// 获取所有标签详细信息包括查询
export function getAllLabelLists(sortType, keyWord) {
  return request({
    url: '/consumer-blog/blog/label/all/lists',
    method: 'get',
    params: { sortType: sortType, keyWord: keyWord }
  })
}

// 查询当前登录用户关注的标签
export function getLabelFocusLists() {
  return request({
    url: '/consumer-blog/blog/label/focus/lists',
    method: 'get'
  })
}

// 取消标签关注
export function deleteLabelFocusOne(labelId) {
 return request({
   url: '/consumer-blog/blog/label/focus/' + labelId,
   method: 'delete'
 })
}

// 添加标签关注
export function insertLabelFocus(labelId) {
  return request({
    url: '/consumer-blog/blog/label/focus/' + labelId,
    method: 'post'
  })
}

// 查看标签下的文章详情
export function getLabelDetails(labelId, sortType) {
  return request({
    url: '/consumer-blog/blog/label/details/' + labelId,
    method: 'get',
    params: { sortType: sortType }
  })
}

// 根据标签id查询标签详情
export function getLabelList(labelId) {
  return request({
    url: '/consumer-blog/blog/label/list/' + labelId,
    method: 'get'
  })
}
