<template>

  <div>

    <el-card style="width: 600px;height: 40vh">

      <el-form label-width="80px" >

        <el-form-item label="用户名" >
          <el-input v-model="form.name" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" >
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" >
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div style="text-align: center">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>

    </el-card>

  </div>
</template>
<script>
// import request from "@/utils/request";
import request from "@/utils/request";

export default {
  name:'Info',
  data()
  {
    return{
      form:{},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
   this.load()
  },
  methods:{
    load()
    {
      const name=this.user.name
      request.get("/test11/" +name).then(res=> {
        this.form = res.data
      })
    },
    save()
    {
      request.post("/test7",this.form).then(res=>{
        if(res)
        {
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
          this.$emit('refreshUser')//子触发父
        }
        else
        {
          this.$message.error("保存失败")
        }
      })
    },
  }
}
</script>