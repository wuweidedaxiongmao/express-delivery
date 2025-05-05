import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
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
          {path:'student',meta:{title:'学生信息'},component:()=>import('../views/admin/Student.vue')},
        {path:'courierCommission',name:'courierCommission',meta:{title:'代取员等级信息'},component:()=>import('../views/admin/CourierCommission.vue')},
        {path:'person',meta: {title: '管理员信息'},component:()=>import('../views/admin/Person.vue')},
        {path: 'identification',meta: {title: '代取员认证申请'},component:()=>import('../views/admin/Identification.vue')},
        {path: 'orders',meta: {title: '代取订单信息'},component:()=>import('../views/admin/Orders.vue')}


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
        {path: 'orders',meta: {title: '订单信息'},component:()=>import('../views/student/Orders.vue')},
        {path: 'address',meta: {title: '地址管理'},component:()=>import('../views/student/Address.vue')},
        {path: 'acceptOrder',meta: {title: '我要接单'},component:()=>import('../views/student/AcceptOrder.vue')},
      ]
    }
  ],
})

//beforeEach表示跳转之前的一些操作
router.beforeEach((to,from,next)=>{
  document.title=to.meta.title
  next() //必须要有这个参数才能实现跳转
})

export default router
