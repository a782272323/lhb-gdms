import request from '@/utils/request'

// 个人信息
export function getPersonInfo() {
  return request({
    url: '/consumer-admin/admin/system/person/info',
    method: 'get'
  })
}
