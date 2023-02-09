import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Manage.vue'

Vue.use(VueRouter)

const routes = [

  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export const setRoutes=()=>{
  const storeMenus=localStorage.getItem("menus");
  if(storeMenus)
  {
    const manageRoute=
        {path:'/',name:'Manage',component:()=>import('../views/Manage.vue'),redirect:'/home',children:[
        {path: 'info', name: 'Info', component: () => import( '../views/Info.vue'),},
        {path: 'password', name: 'Password', component: () => import( '../views/Password.vue'),},
            {path: 'handlePaper', name: 'HandlePaper', component: () => import( '../views/HandlePaper.vue'),},
            {path: 'spaper', name: 'Spaper', component: () => import( '../views/Spaper.vue'),},
            {path: 'home', name: 'Home', component: () => import( '../views/Home.vue'),},
            {path: 'handlePaper1', name: 'HandlePaper1', component: () => import( '../views/HandlePaper1.vue'),},
            {path: 'videoDetail', name: 'VideoDetail', component: () => import( '../views/VideoDetail.vue'),},
          ]}
    const menus=JSON.parse(storeMenus)
    menus.forEach(item=>{
      if(item.path)
      {
        let itemMenu={path:item.path.replace("/",""),name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
        manageRoute.children.push(itemMenu)
      } else if(item.children.length)
      {
        item.children.forEach(item=>{
          if(item.path)
          {
            let itemMenu={path:item.path.replace("/",""),name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
            manageRoute.children.push(itemMenu)
          }
      })
      }
    })
    const currentRouteNames=router.getRoutes().map(v=>v.name)
    if(!currentRouteNames.includes('Manage'))
    {
      router.addRoute(manageRoute)
    }

  }

}
setRoutes()
// router.addRoute({
//   path: '/',
//   name: 'Manage',
//   component: () => import( '../views/Manage.vue'),
//   redirect:"/home",
//   children:[
//     {path: 'user', name: 'User', component: () => import( '../views/User.vue'),},
//     {path: 'home', name: 'Home', component: () => import( '../views/Home.vue'),},
//     {path: 'info', name: 'Info', component: () => import( '../views/Info.vue'),},
//     {path: 'password', name: 'Password', component: () => import( '../views/Password.vue'),},
//     {path: 'file', name: 'File', component: () => import( '../views/File.vue'),},
//     {path: 'role', name: 'Role', component: () => import( '../views/Role.vue'),},
//     {path: 'menu', name: 'Menu', component: () => import( '../views/Menu.vue'),},
//   ]
// },)

export default router
