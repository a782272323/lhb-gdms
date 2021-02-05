import request from '@/utils/request'

// 用户列表数据详情
export function getUserLists(offSet, limit, keyWord, orderBy) {
  return request({
    url: '/consumer-admin/admin/average/user/lists',
    method: 'get',
    params: { offSet: offSet, limit: limit, keyWord: keyWord, orderBy: orderBy }
  })
}

// 搜索用户
export function searchUserLists(offSet, limit, keyWord, orderBy,
                                sysUserNickname, sysUserUsername, sysUserPhone, sysUserEmail) {
  return request({
    url: '/consumer-admin/admin/average/user/lists/search',
    method: 'get',
    params: { offSet: offSet, limit: limit, keyWord: keyWord, orderBy: orderBy,
    sysUserNickname: sysUserNickname, sysUserUsername: sysUserUsername, sysUserPhone: sysUserPhone, sysUserEmail: sysUserEmail }
  })
}

// 根据用户id查看单个用户信息
export function getUserList(sysUserId) {
  return request({
    url: '/consumer-admin/admin/average/user/list/' + sysUserId,
    method: 'get'
  })
}
