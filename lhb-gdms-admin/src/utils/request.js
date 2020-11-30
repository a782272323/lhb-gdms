import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
// 创建一个axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests 当跨域请求时发送cookie
  timeout: 5000 // request timeout 请求超时
})

// request interceptor 请求拦截器
service.interceptors.request.use(
  config => {
    // do something before request is sent 在请求发送之前做些什么

    if (store.getters.token) {
      // let each request carry token 让每个请求携带令牌
      // ['X-Token'] is a custom headers key ['X-Token']是自定义标题键,可以修改
      // please modify it according to the actual situation 请根据实际情况进行修改
      config.headers['Authorization'] = 'Bearer ' + getToken()
    }
    return config
  },
  error => {
    // do something with request error 请求错误的处理
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor 响应拦截器
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * 如果您想要获取http信息，如头信息或状态信息
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * 通过自定义代码确定请求状态
   * Here is just an example
   * 这里只是一个例子
   * You can also judge the status by HTTP Status Code
   * 您还可以通过HTTP状态代码判断状态
   */
  response => {
    const res = response.data

    // if the custom code is not 20000, it is judged as an error.
    // 如果自定义代码不是20000，就会被判定为错误。
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      // 50008:非法令牌;50012:已登录其他客户端;50014:令牌过期;
      if (res.code === 404 || res.code === 405 || res.code === 402) {
        // to re-login
        MessageBox.confirm('您已经登出，您可以取消停留在此页面，或再次登录', '确认注销', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
