import request from '@/utils/request'

// 获取主页用户相关数据列表详情
export function getHomePageUserLists(sysUserId) {
  return request({
    url: '/consumer-blog/web/blog/home-page-user/' + sysUserId,
    method: 'get'
  })
}

// 当前登录用户是否关注了某一位用户
export function checkFocusUser(formSysUserId) {
  return request({
    url: '/consumer-blog/blog/home-page-user/focus/user/' + formSysUserId,
    method: 'get'
  })
}

// 用户主页详情
export function getHomeUserDetails(sysUserId, userId, blogType) {
  return request({
    url: '/consumer-blog/web/blog/home-page-user/details/lists/' + sysUserId + '/' + userId,
    method: 'get',
    params: { blogType: blogType }
  })
}

// 用户主页取消对用户的关注
export function deleteHomeFocusPerson(formSysUserId) {
  return request({
    url: '/consumer-blog/blog/home-page-user/focus/person/' + formSysUserId,
    method: 'delete'
  })
}
