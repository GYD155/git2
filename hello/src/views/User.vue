<template>
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="请输入名称" v-model="name"></el-input>
<!--    <el-input style="width: 200px" placeholder="请输入邮箱" v-model="email"></el-input>-->
<!--    <el-input style="width: 200px" placeholder="请输入电话" v-model="phone"></el-input>-->
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
    <el-table-column prop="id" label="ID" width="120">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="120">
    </el-table-column>
    <el-table-column prop="role" label="角色">
    </el-table-column>
    <el-table-column prop="email" label="邮箱">
    </el-table-column>
    <el-table-column prop="phone" label="电话">
    </el-table-column>

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

  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px">
      <el-form-item label="角色">
        <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
          <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户名" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" >
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话" >
        <el-input v-model="form.phone" autocomplete="off"></el-input>
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
      email:"",
      phone:"",
      pageSize:10,
      selection:[],
      roles:[],
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

      request.get("/role/test77").then(res => {
        this.roles = res.data
      })

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