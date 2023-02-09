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
  </div>

  <el-table :data="tableData" row-key="id" default-expand-all
            @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="100">
    </el-table-column>
    <el-table-column prop="name" label="名称" width="100">
    </el-table-column>
    <el-table-column prop="description" label="描述" width="100">
    </el-table-column>
    <el-table-column prop="path" label="路径" width="100">
    </el-table-column>
    <el-table-column prop="pagePath" label="页面路径" width="100">
    </el-table-column>
<!--    <el-table-column prop="icon" label="图标">-->
<!--    </el-table-column>-->


    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope" >
        <el-button type="success" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单</el-button>
        <el-button type="warning" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>

  </el-table>


  <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px">
      <el-form-item label="名称" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="描述" >
        <el-input v-model="form.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="路径" >
        <el-input v-model="form.path" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="页面路径" >
        <el-input v-model="form.pagePath" autocomplete="off"></el-input>
      </el-form-item>
<!--      <el-form-item label="图标" >-->
<!--        <el-input v-model="form.icon" autocomplete="off"></el-input>-->
<!--      </el-form-item>-->


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
  name: "Menu",
  data(){
    return{
      tableData: [],
      dataNum:0,
      pageNum:1,
      name:"",
      description:"",
      path:"",
      icon:"",
      pageSize:5,
      selection:[],
      dialogFormVisible:false,
      form:{},
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
    load()
    {
      request.get("/menu",{
        params:{
          name:this.name,
          // description:this.description,
          // path:this.path,
          // icon:this.icon,
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data
        // this.dataNum=res.total
      })

    },
    save()
    {
      request.post("/menu/test7",this.form).then(res=>{
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
      request.post("/menu/test10",ids).then(res=>{
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
      request.delete("/menu/test9/"+id).then(res=>{
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
    handleAdd(pid)
    {
      this.dialogFormVisible=true
      this.form={}
      if(pid){
        this.form.pid=pid
      }
    },
    reset()
    {
      this.name=""
      this.description=""
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

  }
}
</script>

<style scoped>

</style>