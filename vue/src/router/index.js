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
      path:'/admin',
      name:'admin',
      meta:{title:'智能快递代取管理系统'},
      component:()=>import('../views/admin/Admin.vue'),
      children:[
          {path:'expressType',name:'expressType',meta:{title:'快递类型'},component:()=>import('../views/admin/ExpressType.vue')},
          {path:'student',name:'student',meta:{title:'学生信息'},component:()=>import('../views/admin/Student.vue')},

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
