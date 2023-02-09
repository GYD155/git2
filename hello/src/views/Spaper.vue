<template>
<div>
  <div>
    <el-card>
<!--      <div>-->
<!--        <el-table :data="exam" >-->
<!--        <el-table-column prop="userRole" label="所属计划" width="100">-->
<!--          <template >-->
<!--            <span>{{exam.find(v=>v.id===this.examId).userRole}}</span>-->
<!--          </template>-->
<!--        </el-table-column>-->

<!--      </el-table>-->
<!--      </div>-->
      <div v-for="(item,index) in questions" :key="item.id" style="margin: 20px 0">

        <div style="margin: 10px 0"><span>{{index+1}}.</span>{{item.name}}
          <span v-if="item.type===1">（选择题）</span>
          <span v-if="item.type===2">（问答题）</span>
        </div>
        <div v-if="item.type===1">
          <span style="margin: 20px"><el-radio v-model="item['studentAnswer']" label="A">A.{{item.a}}</el-radio></span>
          <span style="margin: 20px"><el-radio v-model="item['studentAnswer']" label="B">B.{{item.b}}</el-radio></span>
          <span style="margin: 20px"><el-radio v-model="item['studentAnswer']" label="C">C.{{item.c}}</el-radio></span>
        </div>
        <div v-if="item.type===2" >
          <el-input type="textarea" v-model="item['studentAnswer']" ></el-input>
        </div>
<!--        <div>-->
<!--          答案：{{item.answer}}-->
<!--        </div>-->

      </div>
    </el-card>

    <div>
      <el-button size="medium" type="primary" @click="submitPaper">提交</el-button>
    </div>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Spaper",
  data(){
    return{
      questions:[],
      exam:[],
      paperId: this.$route.query.paperId,
      examId: this.$route.query.examId,
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
    }
  },
  created()
  {
    //根据试卷id查询所有试题
    request.get("/paper/view/"+this.paperId).then(res => {
      // console.log(res.data)
      this.questions = res.data
    })
        request.get("/exam").then(res => {
          // console.log(res.data)
          this.exam = res.data
          // console.log(this.exam)
        })
  },
  methods:{
    submitPaper()
    {
      // console.log(this.questions)
      request.post("/studentPaper",{examId:this.examId,userName:this.user.name,paper:JSON.stringify(this.questions)}).then(res => {
        // console.log(res.data)
        // console.log(this.user)
        this.exam = res.data
        if(res.code==='200')
        {
          this.$message.success("提交成功")
          this.$router.push('/sexam')
        }
        else{
          this.$message.error(res.msg)
        }


        // this.dialogFormVisible2=true
      })
    }
  }
}
</script>

<style scoped>

</style>