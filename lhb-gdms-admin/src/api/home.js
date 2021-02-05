import request from '@/utils/request'

// 门户网站总用户数和总文章数量
export function getAllUserAndArticleLists() {
  return request({
    url: '/consumer-admin/admin/home/user/article/all',
    method: 'get'
  })
}

// 按时间段查询新增文章数
export function getArticleCountByTime(timeType) {
  return request({
    url: '/consumer-admin/admin/home/article/time',
    method: 'get',
    params: { timeType: timeType }
  })
}

// 按时间段查询新增用户数
export function getUserCountByTime(timeType) {
  return request({
    url: '/consumer-admin/admin/home/user/time',
    method: 'get',
    params: { timeType: timeType }
  })
}

// 用户增长曲线和文章增长曲线数据
export function getArticleAndUserCurveLists(veLineYear) {
  return request({
    url: '/consumer-admin/admin/home/article/and/user/curve/lists',
    method: 'get',
    params: { veLineYear: veLineYear }
  })
}
