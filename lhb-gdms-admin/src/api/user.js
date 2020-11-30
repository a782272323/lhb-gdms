import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/provider-user/user/login/admin',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/provider-user/user/info/admin',
    method: 'get',
    params: { access_token: token }
  })
}

export function logout(token) {
  return request({
    url: '/provider-user/user/logout/admin',
    method: 'post',
    params: { access_token: token }
  })
}
