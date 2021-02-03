import request from '@/utils/request'

// 获取当前登录用户已关注标签
export function getUserLabelFocus(isMore) {
  return request({
    url: '/consumer-blog/blog/home/user/label/focus',
    method: 'get',
    params: { isMore: isMore }
  })
}

// 首页文章列表信息
export function getHomeArticle(sysUserId, labelType, sortType) {
  return request({
    url: '/consumer-blog/web/blog/home/article/' + sysUserId,
    method: 'get',
    params: { labelType: labelType, sortType: sortType }
  })
}
