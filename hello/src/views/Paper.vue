<template>
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="请输入名称" v-model="name"></el-input>
    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
  </div >

  <div style="margin: 10px 0">
    <el-button class="ml-5" type="primary" @click="handleAdd">新增</el-button>

    <el-button class="ml-5" type="primary" @click="handleBatchDelete">批量删除</el-button>


    <el-upload action="http://localhost:9090/import"
               :show-file-list="false"
               accept="xlsx"
               :on-success="handleImport"
               style="display: inline-block">
    <el-button class="ml-5" type="primary" >导入</el-button>
    </el-upload>

    <el-button class="ml-5" type="primary" @click="exp">导出</el-button>
  </div>

  <el-table :data="tableData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="100">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="100">
    </el-table-column>
    <el-table-column prop="courseName" label="课程" width="100">
      <template v-slot="scope">
        <span>{{course.find(v=>v.id===scope.row.courseId).name}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="score" label="分数">
    </el-table-column>
    <el-table-column prop="duration" label="时长(分)">
    </el-table-column>

    <el-table-column label="考卷" >
      <template v-slot="scope">
        <el-button type="primary" @click="viewPaper(scope.row.id)">查看考卷</el-button>
      </template>
    </el-table-column>


    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope">
        <el-button class="ml-5" type="primary" @click="takePaper(scope.row.id,scope.row.courseId)">组卷</el-button>
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
          答案：{{item.answer}}
        </div>

      </div>
    </el-card>
  </el-dialog>

  <el-dialog title="组卷" :visible.sync="dialogFormVisible1" width="30%">
    <el-form label-width="80px">
      <el-form-item label="选择题数量" >
        <el-input v-model="form1.type1" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="问答题数量" >
        <el-input v-model="form1.type2" autocomplete="off"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible1 = false">取 消</el-button>
      <el-button type="primary" @click="generatePaper">确 定</el-button>
    </div>
  </el-dialog>


  <el-dialog title="信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px">

      <el-form-item label="名称" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="课程" >
        <el-select clearable v-model="form.courseId" placeholder="请选择" style="width: 100%">
          <el-option v-for="item in course"
                     :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分数" >
        <el-input v-model="form.score" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="时长(分)" >
        <el-input v-model="form.duration" autocomplete="off"></el-input>
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
  name: "User",
  data(){
    return{
      tableData: [],
      dataNum:0,
      pageNum:1,
      name:"",
      duration:"",
      score:"",
      email:"",
      phone:"",
      pageSize:10,
      selection:[],
      roles:[],
      questions:[],
      course:[],
      dialogFormVisible:false,
      dialogFormVisible1:false,
      dialogFormVisible2:false,
      form:{},
      form1:{},
      msg:"你好",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWith:200,
      logoTextShow:true
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
    takePaper(paperId,courseId)
    {
      this.form1={type1:1,type2:1,paperId:paperId,courseId:courseId}
      this.dialogFormVisible1=true
    },
    load()
    {
      request.get("/paper/test8",{
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
      request.get("/course").then(res => {
        this.course = res.data
      })

      request.get("/role/test77").then(res => {
        this.roles = res.data
      })

    },
    save()
    {
      request.post("/paper/test7",this.form).then(res=>{
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
    generatePaper()
    {
      request.post("/paper/takePaper",this.form1).then(res=>{
        if(res)
        {
          // console.log(res)
          this.$message.success("组卷成功")
          this.dialogFormVisible1=false
          // this.load()
        }
        else
        {
          this.$message.error("组卷失败")
        }
      })
    },
    handleBatchDelete()
    {
      let ids=this.selection.map(v => v.id)
      request.post("/paper/test10",ids).then(res=>{
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
      request.delete("/paper/test9/"+id).then(res=>{
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