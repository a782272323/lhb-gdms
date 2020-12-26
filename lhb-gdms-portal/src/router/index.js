import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */

/**
 * Note: sub-menu only appear when route children.length >= 1
 * 注意:子菜单只在路径子时出现。长度> = 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 *                                如果设置为真，项目将不会显示在侧边栏中(默认为假)
 * alwaysShow: true               if set true, will always show the root menu
 *                                如果设置为真，将始终显示根菜单
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 *                                如果不设置alwaysShow，当项目有多个子路由时，它将成为嵌套模式，否则不显示根菜单
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 *                                如果设置了noRedirect将不会在面包屑中重定向
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 *                                该名称由<keep-alive>使用(必须设置!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
                                 控制页面角色(可以设置多个角色)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
                                 在侧边栏和面包屑中显示的名称(推荐设置)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
                                 如果设置为真，页面将不会被缓存(默认为false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 * 没有权限要求的基本页面可以访问所有角色
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    name: 'Login',
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        component: () => import('@/views/home/index'),
        name: 'Home',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/blog',
    children: [
      {
        path: 'blog',
        component: () => import('@/views/blog/index'),
        name: 'Blog',
        meta: { title: '我的主页', affix: true }
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/message',
    children: [
      {
        path: 'message',
        component: () => import('@/views/message/index'),
        name: 'Message',
        meta: { title: '消息', affix: true }
      }
    ]
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      // 设置
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '设置', noCache: true }
      },
      // 关注
      {
        path: '/focus',
        component: () => import('@/views/profile/focus'),
        name: 'Focus',
        hidden: true,
        meta: { title: '关注', noCache: true }
      },
      // 收藏集
      {
        path: '/collection',
        component: () => import('@/views/profile/collection'),
        name: 'Collection',
        hidden: true,
        meta: { title: '收藏集', noCache: true }
      }
    ]
  },
  // 注册
  {
    path: '/registered',
    component: () => import('@/views/login/registered'),
    name: 'Registered',
    hidden: true,
    meta: { title: '注册', noCache: true }
  },
  // 写文章
  {
    path: '/article',
    component: () => import('@/views/article/index'),
    name: 'Article',
    hidden: true,
    meta: { title: '写文章', noCache: true }
  },
  // 文章发布成功页面
  {
    path: '/',
    component: Layout,
    redirect: '/articleFaBu',
    children: [
      {
        path: '/articleFaBu',
        component: () => import('@/views/article/articleFaBu'),
        name: 'ArticleFaBu',
        hidden: true,
        meta: { title: '发布成功', noCache: true }
      }
    ]
  },
  // 草稿箱
  {
    path: '/',
    component: Layout,
    redirect: '/caoGao',
    children: [
      {
        path: 'caoGao',
        component: () => import('@/views/article/caoGao'),
        name: 'CaoGao',
        hidden: true,
        meta: { title: '草稿箱', noCache: true }
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/labelFocus',
    children: [
      // 已关注标签
      {
        path: '/labelFocus',
        component: () => import('@/views/label/labelFocus'),
        name: 'LabelFocus',
        hidden: true,
        meta: { title: '标签管理', noCache: true }
      },
      // 所有标签
      {
        path: '/labelAll',
        component: () => import('@/views/label/labelAll'),
        name: 'LabelAll',
        hidden: true,
        meta: { title: '标签管理', noCache: true }
      }
    ]
  },
  // 忘记密码
  {
    path: '/forgetPassword',
    component: () => import('@/views/login/forgetPassword'),
    name: 'ForgetPassword',
    hidden: true,
    meta: { title: '忘记密码', noCache: true }
  },
  // 重置密码
  {
    path: '/resetPassword',
    component: () => import('@/views/login/resetPassword'),
    name: 'ResetPassword',
    hidden: true,
    meta: { title: '重置密码', noCache: true }
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 * 需要根据用户角色动态加载的路由
 */
export const asyncRoutes = [

  /** when your routing map is too long, you can split it into small modules **/

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
