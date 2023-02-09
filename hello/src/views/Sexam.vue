<template>
<div style="margin-bottom: 100px">
  <div>
    考试列表
  </div>
  <div style="border: 1px solid #ccc;padding: 10px;border-radius: 10px; margin: 10px 0"  v-for="item in tableData" :key="item.id">
    <div style="margin: 10px 0">考试名称：{{item.name}}</div>
    <div style="margin: 10px 0">考试名称：{{item.name}}</div>
    <div style="margin: 10px 0">
      <span>考试时间：{{item.time}}</span>
<!--      <el-button style="margin-left: 20px" type="primary" @click="sign(item.id)">考试</el-button>-->
      <el-button style="margin-left: 20px" type="primary" @click="doPaper(item.id)">开始考试</el-button>
<!--      <el-button style="margin-left: 20px" type="primary" >开始考试</el-button>-->
    </div>

  </div>

</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data(){
    return{
      tableData: [],
      dataNum:0,
      pageNum:1,
      name:"",
      state:"",
      pageSize:5,
      signData:[],
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      // paperId:null
      // selection:[],
      // email:"",
      // phone:"",
      // time:"",
      // course:[],
      // dialogFormVisible:false,
      // form:{},
      // msg:"你好",
      // collapseBtnClass:'el-icon-s-fold',
      // isCollapse:false,
      // sideWith:200,
      // logoTextShow:true,
    }
  },
  created() {
    this.load()
  },
  methods:{
    doPaper(examId)
    {
      request.get("/examPaper/exam/"+examId).then(res => {
        console.log(res.data)
        if(res.data)
        {
          this.$router.push('/spaper?paperId='+res.data.paperId+'&examId='+res.data.examId)
        }
        else {
          this.$message.error("考试尚未出卷")
        }
        // this.dialogFormVisible1=true
      })
    },
    sign(examId)
    {
      let form={userName:this.user.name, examId:examId}
      request.post("/sign/test7",form).then(res=>{
        if(res)
        {
          this.$message.success("保存成功")
          // this.dialogFormVisible=false
          // this.load()
        }
        else
        {
          this.$message.error("保存失败")
        }
      })
    },
    load()
    {
      request.get("/exam/test8",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res=>{
        // console.log(res.records)
        this.tableData=res.records
        this.dataNum=res.total
      })
      request.get("/sign").then(res => {
        // console.log(res)
        // console.log(res.data)
        this.signData = res.data
      })
      request.get("/course").then(res => {
        this.course = res.data
      })

      request.get("/role/test77").then(res => {
        this.roles = res.data
      })

    },
    save()
    {
      request.post("/sign/test7",this.form).then(res=>{
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
      request.post("/exam/test10",ids).then(res=>{
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
      request.delete("/exam/test9/"+id).then(res=>{
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
    exp()
    {
      window.open("http://localhost:9090/export")
    },
    handleImport()
    {
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>

<style scoped>

</style>