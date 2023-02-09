<template>
  <div class="wrapper">
    <div style="margin: 200px auto;background-color: #fff;width: 350px;height:300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px">
        <b>登录</b>
      </div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.name"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.pwd"></el-input>
      <div style="margin: 10px 0;text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
      </div>
    </div>

  </div>

</template>

<script>
import request from "@/utils/request";
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data()
  {
    return{
      user:{}
    }
  },
  methods:{
    login()
    {
      request.post("/login",this.user).then(res=>{
        if(res.code==='200')
        {
          localStorage.setItem("user",JSON.stringify(res.data))//把用户信息储存到浏览器
          localStorage.setItem("menus",JSON.stringify(res.data.menus))//把用户信息储存到浏览器

          setRoutes()


          this.$message.success("登陆成功")
          this.$router.push("/")
          // if(res.data.role==='')
          // {
          //
          // }else
          // {
          //
          // }
        }
        else
        {
          this.$message.error(res.msg)
        }
      })

    }
  }
}
</script>

<style>
  .wrapper{
    height: 100vh;
    /*#FC466B,#3F5EFB*/
    background-image: linear-gradient(to bottom right,#FC466B, #3f65fb);
    overflow: hidden;
  }

</style>