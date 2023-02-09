<template>
  <div style="font-size: 12px;line-height: 60px;display: flex ">
    <div style="flex:1;font-size: 18px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>

<!--      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">-->
<!--        <el-breadcrumb-item :to="{path:'/'}" v-for="item in paths">首页</el-breadcrumb-item>-->
<!--      </el-breadcrumb>-->

    </div>

    <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
      <div style="display: inline-block">
        <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">

        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/info">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/password">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>

      </el-dropdown-menu>
    </el-dropdown>
  </div>

</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:'',
    // user:Object
  },
  data()
  {
    return{
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  methods:{
    logout()
    {
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style scoped>

</style>