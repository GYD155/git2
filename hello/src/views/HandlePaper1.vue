<template>
<div>
  <div style="margin: 10px 0">


<!--    <el-upload action="http://localhost:9090/import"-->
<!--               :show-file-list="false"-->
<!--               accept="xlsx"-->
<!--               :on-success="handleImport"-->
<!--               style="display: inline-block">-->
<!--      <el-button class="ml-5" type="primary" >导入</el-button>-->
<!--    </el-upload>-->

    <el-button class="ml-5" type="primary" @click="exp">导出</el-button>
  </div>
  <el-table :data="paper" >
<!--    <el-table-column type="selection" width="55"></el-table-column>-->
    <el-table-column prop="id" label="题目ID" >
    </el-table-column>
    <el-table-column prop="name" label="题目名称" >
    </el-table-column>
    <el-table-column prop="type" label="题目类型">
      <template v-slot="scope">
        <span v-if="scope.row.type===1">选择题</span>
        <span v-if="scope.row.type===2">问答题</span>
      </template>
    </el-table-column>
    <el-table-column prop="a" label="a选项">
    </el-table-column>
    <el-table-column prop="b" label="b选项">
    </el-table-column>
    <el-table-column prop="c" label="c选项">
    </el-table-column>
    <el-table-column prop="d" label="d选项">
    </el-table-column>
    <el-table-column prop="score" label="标准分数">
    </el-table-column>
    <el-table-column prop="answer" label="标准答案">
    </el-table-column>
    <el-table-column prop="studentAnswer" label="学生答案">
    </el-table-column>
    <el-table-column prop="studentScore" label="得分">
      <template v-slot="scope">
        <el-input v-model="scope.row.studentScore"></el-input>
      </template>
    </el-table-column>

  </el-table>
<!--  <div>-->
<!--    <el-button type="primary" @click="submitScore">提交</el-button>-->
<!--  </div>-->
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "HandlePaper",
  data()
  {
    return{
      sp:this.$route.query.sp,
      paper:[]
    }
  },
  created()
  {
    console.log(this.sp)
    request.get("/studentPaper/"+this.sp).then(res=>{
      console.log(res)
      this.paper=JSON.parse(res.data.paper)
      if(this.paper&&this.paper.length)
      {
        this.paper.forEach(item=>{
          if(item.answer===item.studentAnswer)
          {
            item.studentScore=item.score
          }
          else {
            item.studentScore=0
          }
        })
      }
    })
  },
  methods:{
    exp()
    {
      window.open("http://localhost:9090/studentPaper/export")
    },
    // handleImport()
    // {
    //   this.$message.success("导入成功")
    //   this.load()
    // },
    submitScore(){
      let sum=0
      this.paper.forEach(item=>{
        if(item.studentScore==null)
        {
          item.studentScore=0
        }
        sum+=parseInt(item.studentScore)
      })
      request.post("/studentPaper/",{id:this.sp,score:sum}).then(res=>{
        this.$message.success("打分成功")
        this.$router.push('/sanswer')
      })
    }
  }
}
</script>

<style scoped>

</style>