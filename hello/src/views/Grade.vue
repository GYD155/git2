<template>
  <div>

    <el-table :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="120">
      </el-table-column>
      <el-table-column prop="examId" label="考试名称" width="120">
        <template v-slot="scope">
          <span>{{exam.find(v=>v.id===scope.row.examId).name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="学生姓名">
      </el-table-column>
<!--      <el-table-column prop="paper" label="答案">-->
<!--      </el-table-column>-->
      <el-table-column prop="score" label="分数">
      </el-table-column>

      <el-table-column prop="operation" label="操作">
<!--        <template slot-scope="scope">-->
<!--          <el-button type="warning" @click="$router.push('/handlePaper?sp='+scope.row.id)">阅卷</el-button>-->
<!--&lt;!&ndash;          <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>&ndash;&gt;-->
<!--        </template>-->
        <template v-slot="scope">
<!--          <el-button type="primary" @click="viewPaper(scope.row.examId)">查看原试卷</el-button>-->
          <el-button type="warning" @click="$router.push('/handlePaper1?sp='+scope.row.id)">查看考卷</el-button>
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

    <el-dialog title="考卷" :visible.sync="dialogFormVisible2" width="30%">
      <el-card>
        <div v-for="(item,index) in questions" :key="item.id" style="margin: 20px 0">
          <div style="margin: 10px 0"><span>{{index+1}}.</span>{{item.name}}
            <span v-if="item.type===1">（选择题）</span>
            <span v-if="item.type===2">（问答题）</span>
          </div>
          <div v-if="item.type===1">
            <span>A.{{item.a}}</span>
            <span>B.{{item.b}}</span>
            <span>C.{{item.c}}</span>
            <span>D.{{item.d}}</span>
          </div>
          <div>
            正确答案：{{item.answer}}

        </div>
        </div>
<!--        <div v-for="(item,index) in tableData" :key="item.id" style="margin: 20px 0">-->
<!--          <div>{{JSON.parse(item.paper)}}</div>-->
<!--        </div>-->
      </el-card>

    </el-dialog>


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
      paper:"",
      email:"",
      phone:"",
      pageSize:10,
      selection:[],
      questions:[],
      roles:[],
      dialogFormVisible:false,
      dialogFormVisible2:false,
      form:{},
      msg:"你好",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWith:200,
      logoTextShow:true,
      exam:[],
      // papers:[],
      // sp:"",
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
    }
  },
  created() {
    this.load()
  },
  methods:{
    viewPaper(paperId)
    {
      request.get("/paper/view/"+paperId).then(res => {
        console.log(res.data)
        this.questions = res.data
        this.dialogFormVisible2=true
      })
    },
    load()
    {
      request.get("/studentPaper/test12/"+this.user.name,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          paper:this.paper,
          email:this.email,
          phone:this.phone,
          // sp:this.id

        }
      }).then(res=>{
        // console.log(this.user.role)
        // this.papers=JSON.parse(res.data.paper)
        // console.log(res)
        this.tableData=res.records
        this.dataNum=res.total
        console.log(this.tableData)
      })
      // request.get("/studentPaper/"+this.sp).then(res=>{
      //   console.log(res.data)
      //   this.papers=JSON.parse(res.data.paper)
      //   console.log(this.papers)
        // if(this.paper&&this.paper.length)
        // {
        //   this.paper.forEach(item=>{
        //     if(item.answer===item.studentAnswer)
        //     {
        //       item.studentScore=item.score
        //     }
        //   })
        // }
      // })

      request.get("/exam").then(res => {
        // console.log(res.data)
        this.exam = res.data
      })

    },
    save()
    {
      request.post("/studentPaper",this.form).then(res=>{
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
      request.post("/studentPaper/test10",ids).then(res=>{
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
      request.delete("/studentPaper/test9/"+id).then(res=>{
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