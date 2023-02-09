<template>
<!--  <div style="height: 100%">-->
    <el-container style="min-height: 100vh">
      <el-aside width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc">
          <Header  :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
        </el-header>

        <el-main>
         <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Home',
  data()
  {

    return{

      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWith:200,
      logoTextShow:true,
      user: {}
    }
  },
  created()
  {
  //  请求分页查询数据
  //   this.load()
    // 从后台获取最新的User数据
    this.getUser()
  },
  components: {
    Aside,
    Header,
  },
  methods:{
    collapse()//点击收缩按钮触发
    {
      this.isCollapse=!this.isCollapse
      if(this.isCollapse)
      {
        this.sideWith=64
        this.logoTextShow=false
      }
      else
      {
        this.logoTextShow=true
      }

    },
    load()
    {
      request.get("/test8",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
          email:this.email,
          phone:this.phone,

        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.records
        this.dataNum=res.total
      })
      // fetch("http://localhost:9090/test6?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&name="+this.name)
      //     .then(res=>res.json()).then(res=> {
      //   console.log(res)
      //   this.tableData = res.data
      //   this.dataNum = res.dataNum
      // })
    },
    save()
    {
      request.post("/test7",this.form).then(res=>{
        if(res)
        {
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }
        else
        {
          this.$message.error("保存失败")
        }
      })
    },

    handleSelectionChange(val)
    {
      this.selection=val
    },
    handleBatchDelete()
    {
      let ids=this.selection.map(v => v.id)
      request.post("/test10",ids).then(res=>{
        if(res)
        {
          this.$message.success("删除成功")
          this.dialogFormVisible=false
          this.load()
        }
        else
        {
          this.$message.error("删除失败")
        }
      })

    },
    handleEdit(row)
    {
      this.form=row
      this.dialogFormVisible=true
    },
    handleDelete(id)
    {
      request.delete("/test9/"+id).then(res=>{
        if(res)
        {
          this.$message.success("删除成功")
          this.dialogFormVisible=false
          this.load()
        }
        else
        {
          this.$message.error("删除失败")
        }
      })
    },
    handleAdd()
    {
      this.dialogFormVisible=true
      this.form={}
    },
    reset()
    {
      this.name=""
      this.email=""
      this.phone=""
      this.load()
    },
    handleSizeChange(pageSize)
    {
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum)
    {
      this.pageNum=pageNum
      this.load()
    },
    getUser()
    {
      let name = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).name : ""
      if (name) {
        // 从后台获取User数据
        request.get("/test11/" + name).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    }
  }
}
</script>
