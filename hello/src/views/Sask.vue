<template>
  <div>
<!--    <div class="head-container">-->
<!--      &lt;!&ndash; 搜索 &ndash;&gt;-->
<!--      <el-input-->
<!--          v-model="stuId"-->
<!--          placeholder="输入用户ID"-->
<!--          style="width: 200px"-->
<!--          class="filter-item"/>-->
<!--      <el-input-->
<!--          v-model="askId"-->
<!--          placeholder="输入问题ID"-->
<!--          style="width: 200px"-->
<!--          class="filter-item1"/>-->
<!--      <el-date-picker-->
<!--          v-model="askDate"-->
<!--          type="datetime"-->
<!--          placeholder="选择日期时间"-->
<!--          class="filter-item2">-->
<!--      </el-date-picker>-->
<!--      <el-button-->
<!--          class="filter-item"-->
<!--          type="primary"-->
<!--          icon="el-icon-search"-->
<!--      >搜索</el-button>-->
<!--    </div>-->


    <div>
      <el-card class="el-card-d" shadow="always">
        <div class="infinite-list-wrapper" style="overflow:auto;">
          <el-timeline infinite-scroll-disabled="disabled">
            <div v-if="allmessages.length>0">
              <el-timeline-item v-for="(item,index) in allmessages"  :key="index" :timestamp='item.createTime' placement="top">
                <el-card class="el-card-m" style="height:120px">
                  <h4>{{item.memberName}}：</h4>
                  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{item.content}}</p>
                </el-card>
              </el-timeline-item>
            </div>
            <div v-else>
              <el-timeline-item placement="top">
                <el-card class="el-card-m" style="height:120px">
                  <h4>@机构管理员：</h4>
                  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 请输入您的问题</p>
                </el-card>
              </el-timeline-item>
            </div>
          </el-timeline>
        </div>

      </el-card>
      <div class="el-card-messages">
        <el-form lable-width="400px">
          <el-form-item label="请输入你的问题">
            <el-input
                type="textarea"
                :rows="5"
                placeholder="请输入内容"
                v-model="form.qus">
            </el-input>
          </el-form-item>

          <el-form-item label="提问人" >
            <el-input v-model="form.userName=this.user.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
<!--        <el-input  type="textarea" :rows="5" placeholder="输入留言" maxlength="200" v-model="qus"></el-input>-->
        <el-button type="primary" round class="submit-message" @click="save">提交</el-button>
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
      email:"",
      phone:"",
      pageSize:10,
      selection:[],
      roles:[],
      dialogFormVisible1:false,
      form:{},
      msg:"你好",
      qus:"",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWith:200,
      logoTextShow:true,
      allmessages:"",
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
    }
  },
  // created() {
  //   this.load()
  // },
  methods:{
    // load()
    // {
    //   request.get("/ask/test8",{
    //     params:{
    //       pageNum:this.pageNum,
    //       pageSize:this.pageSize,
    //       name:this.name,
    //       qus:this.qus,
    //       email:this.email,
    //       phone:this.phone,
    //
    //     }
    //   }).then(res=>{
    //     console.log(res)
    //     this.tableData=res.records
    //     this.dataNum=res.total
    //   })
    //
    //   request.get("/role/test77").then(res => {
    //     this.roles = res.data
    //   })
    //
    // },
    save()
    {
      request.post("/ask/test7",this.form).then(res=>{
        if(res)
        {
          this.$message.success("保存成功")
          this.dialogFormVisible1=false
          // this.load()
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
      request.post("/ask/test10",ids).then(res=>{
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
      this.dialogFormVisible1=true
    },
    handleDelete(id)
    {
      request.delete("/ask/test9/"+id).then(res=>{
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