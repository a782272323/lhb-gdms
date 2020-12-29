import request from '@/utils/request'

// 新建收藏集
export function insertCollection(sysCollectionName) {
  return request({
    url: '/consumer-blog/blog/collection',
    method: 'post',
    params: { sysCollectionName: sysCollectionName }
  })
}

// 编辑收藏集名称
export function updateCollectionName(sysCollectionId, sysCollectionName) {
  return request({
    url: '/consumer-blog/blog/collection/' + sysCollectionId,
    method: 'put',
    params: { sysCollectionName: sysCollectionName }
  })
}

// 获取收藏集合列表信息
export function getCollectionLists() {
  return request({
    url: '/consumer-blog/blog/collection/lists',
    method: 'get'
  })
}
