import request from '@/utils/request'

// 你的关注数据列表
export function getFocusPersonLists() {
  return request({
    url: '/consumer-blog/blog/focus/person/lists',
    method: 'get'
  })
}

// 获取当前登录用户详情
export function getFocusUserLists() {
  return request({
    url: '/consumer-blog/blog/focus/user/lists',
    method: 'get'
  })
}

// 当前登录用户关注的标签数据列表
export function getFocusLabelLists() {
  return request({
    url: '/consumer-blog/blog/focus/label/lists',
    method: 'get'
  })
}

// 粉丝数据列表
export function getFocusFansLists() {
  return request({
    url: '/consumer-blog/blog/focus/fans/lists',
    method: 'get'
  })
}

// 取消对用户的关注
export function deleteFocusPerson(sysUserFocusId) {
  return request({
    url: '/consumer-blog/blog/focus/person/' + sysUserFocusId,
    method: 'delete'
  })
}

// 关注用户
export function insertFocusPerson(formSysUserId) {
  return request({
    url: '/consumer-blog/blog/focus/person/' + formSysUserId,
    method: 'post'
  })
}
