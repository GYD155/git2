<template>
  <div style="margin: 10px 0">
    <a>主页</a>
<!--    <el-row :gutter="10">-->
<!--      <el-col :span="6" v-for="item in files" :key="item.id" style="margin-bottom: 10px">-->

<!--        <div style="border: 1px solid #ccc; padding-bottom: 10px">-->
<!--          <img :src="item.url" alt="" style="width: 100%">-->
<!--          <div style="color: #666; padding: 10px">{{ item.name }}</div>-->
<!--          <div style="padding: 10px"><el-button type="primary">按钮</el-button></div>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "Home",
  data(){
    return{
      files:[]

    }
  },
  created() {
    request.get("/file/all").then(res=>{
      console.log(res.data)
      this.files=res.data.filter(v => v.type === 'png' || v.type === 'jpg'||v.type === 'webp')
    })
  }
}
</script>

<style scoped>

</style>