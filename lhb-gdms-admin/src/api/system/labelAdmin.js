import request from '@/utils/request'

// 获取列表全部信息
export function getLists() {
  return request({
    url: '/consumer-admin/admin/system/label/lists',
    method: 'get'
  })
}

// 模糊查询
export function query(data, params) {
  return request({
    url: '/consumer-admin/admin/system/label/lists/query',
    method: 'get',
    params: { labelName: params },
    data
  })
}

// 添加标签
export function insertLabel(data) {
  return request({
    url: '/consumer-admin/admin/system/label',
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 编辑标签图标
export function updateLabelIcon(data, params) {
  return request({
    url: '/consumer-admin/admin/system/label/icon/' + params,
    method: 'post',
    data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 编辑标签名称
export function updateLabelName(params1, params2) {
  return request({
    url: '/consumer-admin/admin/system/label/name/' + params1,
    method: 'post',
    params: { labelName: params2 }
  })
}

// 删除标签
export function deleteLabel(params) {
  return request({
    url: '/consumer-admin/admin/system/label/' + params,
    method: 'delete'
  })
}
