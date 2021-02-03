import request from '@/utils/request'

// 评论相关详情列表
export function getMessageCommentsLists() {
  return request({
    url: '/consumer-blog/blog/message/comment/lists',
    method: 'get'
  })
}

// 点赞相关详情列表
export function getMessagePraiseLists() {
  return request({
    url: '/consumer-blog/blog/message/praise/lists',
    method: 'get'
  })
}
