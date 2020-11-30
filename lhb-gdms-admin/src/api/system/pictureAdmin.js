import request from '@/utils/request'

// 获取列表全部信息
export function getLists() {
  return request({
    url: '/consumer-admin/admin/system/icon/lists',
    method: 'get'
  })
}

// 添加头像
export function insert(data) {
  return request({
    url: '/consumer-admin/admin/system/icon',
    method: 'post',
    data: data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 设置默认头像
export function updateDefaultIcon(data) {
  return request({
    url: '/consumer-admin/admin/system/icon/default/' + data,
    method: 'put'
  })
}

// 删除
export function deleteIcon(data) {
  return request({
    url: '/consumer-admin/admin/system/icon/' + data,
    method: 'delete'
  })
}
