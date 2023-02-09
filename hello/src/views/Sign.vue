<template>
<div>
<!--  <div style="margin: 10px 0">-->
<!--    <el-input style="width: 200px" placeholder="请输入名称" v-model="name"></el-input>-->
<!--    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>-->
<!--    <el-button class="ml-5" type="primary" @click="reset">重置</el-button>-->
<!--  </div >-->

<!--  <div style="margin: 10px 0">-->
<!--    <el-button class="ml-5" type="primary" @click="handleAdd">新增</el-button>-->
<!--    <el-button class="ml-5" type="primary" @click="handleBatchDelete">批量删除</el-button>-->


<!--    <el-upload action="http://localhost:9090/import"-->
<!--               :show-file-list="false"-->
<!--               accept="xlsx"-->
<!--               :on-success="handleImport"-->
<!--               style="display: inline-block">-->
<!--    <el-button class="ml-5" type="primary" >导入</el-button>-->
<!--    </el-upload>-->

<!--    <el-button class="ml-5" type="primary" @click="exp">导出</el-button>-->
<!--  </div>-->

  <el-table :data="tableData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="120">
    </el-table-column>
    <el-table-column prop="examId" label="考试名称" width="120">
      <template v-slot="scope">
        <span>{{exam.find(v=>v.id===scope.row.examId).name}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="userName" label="学生" width="100">
    </el-table-column>
<!--    <el-table-column prop="state" label="审核状态" width="100">-->
<!--    </el-table-column>-->
<!--    <el-table-column prop="examId" label="审核">-->
<!--      <template v-slot="scope">-->
<!--        <el-button type="primary" @click="changeState(scope.row,'1')">审核通过</el-button>-->
<!--        <el-button type="primary" @click="changeState(scope.row,'0')">审核不通过</el-button>-->
<!--      </template>-->
<!--    </el-table-column>-->


    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope">
        <el-button type="warning" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>

  </el-table>
  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataNum">
    </el-pagination>
  </div>

  <el-dialog title="信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px">

      <el-form-item label="考试Id" >
        <el-input v-model="form.examId" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户名称" >
        <el-input v-model="form.userName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="审核状态" >
        <el-input v-model="form.state" autocomplete="off"></el-input>
      </el-form-item>



    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Sign",
  data(){
    return{
      tableData: [],
      dataNum:0,
      pageNum:1,
      name:"",
      id:"",
      state:"",
      examId:"",
      userName:"",
      email:"",
      phone:"",
      pageSize:5,
      selection:[],
      roles:[],
      dialogFormVisible:false,
      form:{},
      msg:"你好",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWith:200,
      logoTextShow:true,
      exam:[],
      users:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    changeState(row,state)
    {
      this.form=JSON.parse(JSON.stringify(row))
      this.form.state=state
      this.save()

    },
    load()
    {
      request.get("/sign/test8",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          // id:this.id,
          name:this.name
          // examId:this.examId,
          // userName:this.userName,
        }
      }).then(res=>{
        console.log(res.records)
        this.tableData=res.records
        this.dataNum=res.total
      })
      request.get("/exam").then(res => {
        this.exam = res.data
      })
      // request.get("/user").then(res => {
      //   this.users = res.data
      // })


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
      request.post("/sign/test10",ids).then(res=>{
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
      request.delete("/sign/test9/"+id).then(res=>{
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