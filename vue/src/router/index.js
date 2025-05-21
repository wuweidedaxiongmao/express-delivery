import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/ai',component:()=>import('../views/AiChatPanel.vue')
    },
    {path: '/aiAssistant',meta:{title: 'ai助手'},component:()=>import('../views/AiAssistantEntry.vue')},
    {
      path:'/login',
      name:'Login',
      meta:{title:'欢迎登入'},
      component:()=>import('../views/Login.vue')
    },
    {
      path:'/register',
      name:'register',
      meta:{title:'注册'},
      component:()=>import('../views/Register.vue')
    },
    {
      path:'/admin',
      name:'admin',
      meta:{title:'智能快递代取管理系统'},
      component:()=>import('../views/admin/Admin.vue'),
      children:[
          {path:'expressType',name:'expressType',meta:{title:'快递类型'},component:()=>import('../views/admin/ExpressType.vue')},
          {path:'students',meta:{title:'学生信息'},component:()=>import('../views/admin/Students.vue')},
        {path:'courierCommission',name:'courierCommission',meta:{title:'代取员等级信息'},component:()=>import('../views/admin/CourierCommission.vue')},
        {path:'person',meta: {title: '管理员信息'},component:()=>import('../views/admin/Person.vue')},
        {path: 'identification',meta: {title: '代取员认证申请'},component:()=>import('../views/admin/Identification.vue')},
        {path: 'orders',meta: {title: '代取订单信息'},component:()=>import('../views/admin/Orders.vue')},
        {path: 'couriers',meta: {title: '代取员信息'},component:()=>import('../views/admin/Couriers.vue')},
        {path: 'admins',meta: {title: '管理员信息'},component:()=>import('../views/admin/Admins.vue')},
        {path: 'information',meta: {title: '校园资讯'},component:()=>import('../views/admin/Information.vue')},
        {path: 'feedback',meta: {title: '反馈信息'},component:()=>import('../views/admin/Feedback.vue')},
        {path: 'announcement',meta: {title: '公告信息'},component:()=>import('../views/admin/Announcement.vue')},
        {path: 'upgradeApply',meta: {title: '代取员等级提升申请'},component:()=>import('../views/admin/UpgradeApply.vue')},
        {path: 'releaseApply',meta: {title: '代取员解封申请'},component:()=>import('../views/admin/ReleaseApply.vue')}
      ]
    },
    {
      path:'/student',
      name:'student',
      meta:{title: '快递代取系统'},
      component:()=>import('../views/student/Student.vue'),
      children:[
        {path: 'home',name:'home',meta: {title: '首页'},component:()=>import('../views/student/Home.vue')},
        {path: 'person',name:'person',meta: {title: '个人信息'},component:()=>import('../views/student/Person.vue')},
        {path: 'identification',meta: {title: '加入代取员'},component:()=>import('../views/student/Identification.vue')},
        {path: 'order',meta: {title: '下单'},component:()=>import('../views/student/Order.vue')},
        {path: 'orders',meta: {title: '待收订单'},component:()=>import('../views/student/Orders.vue')},
        {path: 'address',meta: {title: '地址管理'},component:()=>import('../views/student/Address.vue')},
        {path: 'acceptOrder',meta: {title: '我要接单'},component:()=>import('../views/student/AcceptOrder.vue')},
        {path: 'myOrders',meta: {title: '我的订单'},component:()=>import('../views/student/MyOrders.vue')},
        {path: 'deliveryOrders',meta: {title: '派送中订单'},component:()=>import('../views/student/DeliveryOrders.vue')},
        {path: 'finishOrders',meta: {title: '已完成订单'},component:()=>import('../views/student/FinishOrders.vue')},
        {path: 'information',meta: {title: '校园资讯'},component:()=>import('../views/student/Information.vue')},
        {path: 'feedback',meta: {title: '反馈'},component:()=>import('../views/student/Feedback.vue')},
        {path: 'upgradeApply',meta: {title: '提示等级'},component:()=>import('../views/student/UpgradeApply.vue')},
        {path: 'courierCommission',meta: {title: '等级信息'},component:()=>import('../views/student/CourierCommission.vue')},
        {path: 'releaseApply',meta: {title: '代取员解封'},component:()=>import('../views/student/ReleaseApply.vue')},
        {path: 'rank',meta: {title: '排行榜'},component:()=>import('../views/student/Rank.vue')},
      ]
    },
    {path: '/student/information/detail/:id',meta: {title: '详细资讯'},component:()=>import('../views/student/Detail.vue')},
    {
      path: '/:pathMatch(.*)*', // 匹配所有未定义的路由
      name: 'NotFound',
      meta: { title: '页面不存在' },
      component:()=>import('../views/NotFound.vue'),
    },
  ],
})

//beforeEach表示跳转之前的一些操作
router.beforeEach((to,from,next)=>{
  document.title=to.meta.title
  next() //必须要有这个参数才能实现跳转
})

export default router
