<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden;width: 180px"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <b style="color: #fff" v-show="logoTextShow">{{ user.name }}</b>
    </div>

    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
<!--          <i :class="item.icon"></i>-->
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''">
          <template slot="title">
<!--            <i :class="item.icon"></i>-->
            <span slot="title">{{ item.name }}</span>
          </template>
          <div  v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
<!--              <i :class="subItem.icon"></i>-->
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>

<!--      <el-menu-item-group>-->
<!--        <el-menu-item index="/">课程</el-menu-item>-->
<!--        <el-menu-item index="/info">个人信息</el-menu-item>-->
<!--        <el-menu-item index="/password">修改密码</el-menu-item>-->
<!--        <el-menu-item index="/user">test页</el-menu-item>-->
<!--        <el-menu-item index="/file">文件管理</el-menu-item>-->
<!--        <el-menu-item index="/role">角色管理</el-menu-item>-->
<!--        <el-menu-item index="/menu">菜单管理</el-menu-item>-->

<!--      </el-menu-item-group>-->


<!--    </el-submenu>-->

  </el-menu>
</template>

<script>
export default {
  name:"Aside",
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean,

  },
  data()
  {
    return{
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      // opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    }
  },
}
</script>

<style scoped>

</style>