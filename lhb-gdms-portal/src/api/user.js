import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/provider-user/user/login/portal',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/provider-user/user/info/portal',
    method: 'get',
    params: { access_token: token }
  })
}

export function logout(token) {
  return request({
    url: '/provider-user/user/logout/portal',
    method: 'post',
    params: { access_token: token }
  })
}

// 获取阿里云短信验证码
export function getPhoneCode(phone) {
  return request({
    url: '/provider-cloud/web/cloud/aliyun/sms/checkCode',
    method: 'post',
    params: { phone: phone }
  })
}

// 获取qq邮箱验证码
export function getEmailCode(to) {
  return request({
    url: '/provider-cloud/web/cloud/qq/mail/code',
    method: 'post',
    params: { to: to }
  })
}

// 用户注册
export function registered(data) {
  return request({
    url: '/provider-user/user/registered/portal',
    method: 'post',
    data
  })
}
