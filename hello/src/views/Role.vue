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

  <el-table :data="tableData" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="120">
    </el-table-column>
    <el-table-column prop="name" label="名称" width="120">
    </el-table-column>
    <el-table-column prop="flag" label="唯一标识" width="120">
    </el-table-column>
    <el-table-column prop="description" label="描述">
    </el-table-column>


    <el-table-column prop="operation" label="操作">
      <template slot-scope="scope">
        <el-button type="info" @click="selectMenu(scope.row.id)">分配菜单 <i class="el-icon-menu"></i></el-button>
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

  <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px">
      <el-form-item label="名称" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="唯一标识" >
        <el-input v-model="form.flag" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="描述" >
        <el-input v-model="form.description" autocomplete="off"></el-input>
      </el-form-item>


    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>


  <el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%">
    <el-form label-width="80px">
      <el-tree
          :props="props"
          :data="menuData"
          ref="tree"
          show-checkbox
          node-key="id"
          :default-expanded-keys="expends"
          :default-checked-keys="checks">
      </el-tree>
<!--    <el-tree-->
<!--        :props="props"-->
<!--        :data="menuData"-->
<!--        show-checkbox-->
<!--        node-key="id"-->
<!--        ref="tree"-->
<!--        :default-expanded-keys="expends"-->
<!--        :default-checked-keys="checks">-->
<!--         <span class="custom-tree-node" slot-scope="{ node, data }">-->
<!--            <span><i :class="data.icon"></i> {{ data.name }}</span>-->
<!--         </span>-->
<!--    </el-tree>-->
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="menuDialogVis = false">取 消</el-button>
      <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
    </div>

  </el-dialog>

</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Role",
  data(){
    return{
      tableData: [],
      dataNum:0,
      pageNum:1,
      name:"",
      description:"",
      pageSize:10,
      selection:[],
      dialogFormVisible:false,
      menuDialogVis: false,
      form:{},
      msg:"你好",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWith:200,
      logoTextShow:true,
      multipleSelection: [],
      menuData: [],
      props: {
        label: 'name',
      },
      expends: [],
      checks: [],
      roleId: 0,
    //   roleFlag: '',
    //   ids: []
    }
  },
  created() {
    this.load()
  },
  methods:{
    load()
    {
      request.get("/role/test8",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
          description:this.description,
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.records
        this.dataNum=res.total
      })

    },
    save()
    {
      request.post("/role/test7",this.form).then(res=>{
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
      request.post("/role/test10",ids).then(res=>{
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
      request.delete("/role/test9/"+id).then(res=>{
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
    selectMenu(roleId)
    {
      this.menuDialogVis = true;
      this.roleId = roleId
      //请求菜单数据
      request.get("/menu",{
        params:{
          name:"",
        }
      }).then(res=>{
        this.menuData=res.data
        // 把后台返回的菜单数据处理成 id数组
        this.expends = this.menuData.map(v => v.id)
      })
      request.get("/role/roleMenu/" + this.roleId).then(res => {
        this.menuDialogVis=true

        this.checks = res.data
        request.get("/menu/ids").then(r=>{
          const ids =r.data;
          ids.forEach(id=>{
            if(!this.checks.includes(id))
            {
              this.$refs.tree.setChecked(id,false)
            }
          })

        })

      })
      // this.roleId = role.id
      // this.roleFlag = role.flag
      //
      // // 请求菜单数据
      // this.request.get("/menu").then(res => {
      //   this.menuData = res.data
      //
      //   // 把后台返回的菜单数据处理成 id数组
      //   this.expends = this.menuData.map(v => v.id)
      // })
      //
      // this.request.get("/role/roleMenu/" + this.roleId).then(res => {
      //   this.checks = res.data
      //   this.ids.forEach(id => {
      //     if (!this.checks.includes(id)) {
      //       // 可能会报错：Uncaught (in promise) TypeError: Cannot read properties of undefined (reading 'setChecked')
      //       this.$nextTick(() => {
      //         this.$refs.tree.setChecked(id, false)
      //       })
      //     }
      //   })
      //   this.menuDialogVis = true
      // })
    },
    saveRoleMenu()
    {
      request.post("/role/roleMenu/"+this.roleId,this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())).then(res=> {
        if (res.code === '200')
        {
          this.$message.success("绑定成功")
          this.menuDialogVis = false
        } else
        {
          this.$message.error(res.msg)
        }
      })
    }

  }
}
</script>

<style scoped>

</style>