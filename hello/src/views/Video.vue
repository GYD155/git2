<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="视频名称"></el-table-column>
      <el-table-column label="播放">
        <template slot-scope="scope">
          <el-button type="primary" @click="detail(scope.row.id)">播放</el-button>
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
          :total="total">
      </el-pagination>
    </div>



  </div>
</template>


<script>




import request from "@/utils/request";

export default {
  name: "File",
  data() {
    return {
      videos: [],
      // serverIp: serverIp,
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      // url:''
    }
  },
  created(){

    this.load();

  },
  methods: {

     load() {
      // const {data:res} = await this.$http.post("alluser",this.queryInfo);//访问后台

       request.get("/file/page1",{
         params:{
           pageNum: this.pageNum,
           pageSize: this.pageSize,
           name: this.name,

         }
       }).then(res=>{
         // console.log(res.data)
         this.tableData = res.data.records
         this.total = res.data.total
         this.videos=res.data
       })


    },

    detail(id)
    {

      this.$router.push({path: "/VideoDetail",query: {id: id}})
    },

    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },

    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleFileUploadSuccess(res) {
      console.log(res)
      this.$message.success("上传成功")
      this.load()
    },
    download(url) {
      window.open(url)
    },
    preview(url) {
      window.open('https://file.keking.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
    },
    // changeEnable(row) {
    //   request.post("/video/update", row).then(res => {
    //     if (res.code === '200') {
    //       this.$message.success("操作成功")
    //     }
    //   })
    // },

  }



}
</script>
<style scoped>

</style>