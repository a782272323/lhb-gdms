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

// 发送短信验证码
export function sendPhoneCode(phone) {
  return request({
    url: '/consumer-blog/blog/person/center/system/send/phone-code',
    method: 'post',
    params: { phone: phone }
  })
}

// 校验手机验证码
export function checkPhoneCode(phone, code) {
  return request({
    url: '/consumer-blog/blog/person/center/system/check/phone-code',
    method: 'get',
    params: { phone: phone, code: code }
  })
}

// 发送邮箱验证码
export function sendEmailCode(to) {
  return request({
    url: '/consumer-blog/blog/person/center/system/send/email-code',
    method: 'post',
    params: { to: to }
  })
}

// 校验邮箱验证码
export function checkEmailCode(to, code) {
  return request({
    url: '/consumer-blog/blog/person/center/system/check/email-code',
    method: 'get',
    params: { to: to, code: code }
  })
}

// 重置密码
export function resetPassword(password) {
  return request({
    url: '/consumer-blog/blog/person/center/system/reset/password',
    method: 'post',
    params: { password: password }
  })
}

// 忘记密码-重置密码
export function resetPasswordWithoutLogin(keyword, password, code, type) {
  return request({
    url: '/consumer-blog/web/blog/person/center/system/reset/password',
    method: 'post',
    params: { keyword: keyword, password: password, code: code, type: type }
  })
}
