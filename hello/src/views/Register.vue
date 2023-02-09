<template>
  <div class="wrapper">
    <div style="margin: 200px auto;background-color: #fff;width: 350px;height:300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px">
        <b>注册</b>
      </div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.name"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.pwd"></el-input>
      <div style="margin: 10px 0;text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login">确定</el-button>
        <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
      </div>
    </div>

  </div>

</template>

<script>
import request from "@/utils/request";

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
      request.post("/register",this.user).then(res=>{
        if(res.code==='200')
        {
          this.$message.success("注册成功")
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