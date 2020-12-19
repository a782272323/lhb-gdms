import request from '@/utils/request'

// 获取用户详情信息
export function getUserDetails() {
  return request({
    url: '/consumer-blog/blog/person/center/system/details',
    method: 'get'
  })
}

// 修改用户基础信息
export function updateSysUserBase(params, data) {
  return request({
    url: '/consumer-blog/blog/person/center/system/base/' + params,
    method: 'put',
    data
  })
}

// 用户自定义上传头像
export function updateUserIcon(params, data) {
  return request({
    url: '/consumer-blog/blog/person/center/system/icon/' + params,
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 用户更改系统头像
export function updateUserSystemIcon(params1, params2) {
  return request({
    url: '/consumer-blog/blog/person/center/system/icon/' + params1 + '/' + params2,
    method: 'put'
  })
}

// 查询系统头像
export function getIconLists() {
  return request({
    url: '/consumer-blog/blog/person/center/system/iconLists',
    method: 'get'
  })
}
