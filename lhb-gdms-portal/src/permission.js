import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

// todo 我的主页 和 消息暂时进入白名单后续再移除
const whiteList = ['/login',
  '/auth-redirect', '/', '/home', '/home/index',
  '/registered', '/registered/index',
  '/forgetPassword', '/password/forgetPassword', '/resetPassword', '/password/resetPassword'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar 开始进度条
  NProgress.start()

  // set page title 设置页面标题
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in 确定用户是否已经登录
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page 如果已登录，则重定向到主页
      next({ path: '/' })
      NProgress.done()
    } else {
      // determine whether the user has obtained his permission roles through getInfo
      // 确定用户是否通过getInfo获得了他的权限角色
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      if (hasRoles) {
        next()
      } else {
        try {
          // get user info 获取用户信息
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          const { roles } = await store.dispatch('user/getInfo')

          // generate accessible routes map based on roles
          // 根据角色生成可访问路由表
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)

          // dynamically add accessible routes
          // 动态添加可访问路由
          router.addRoutes(accessRoutes)

          // hack method to ensure that addRoutes is complete hack方法，以确保addRoutes是完整的
          // set the replace: true, so the navigation will not leave a history record
          // 设置replace: true，这样导航将不会留下历史记录
          next({ ...to, replace: true })
        } catch (error) {
          // remove token and go to login page to re-login
          // 删除令牌，转到登录页面重新登录
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')

          next(`/login?redirect=${to.path}`)
          // next(`/`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly 在免登录白名单中，直接进入
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      // 其他没有访问权限的页面被重定向到登录页面。
      next(`/login?redirect=${to.path}`)
      // next(`/`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
