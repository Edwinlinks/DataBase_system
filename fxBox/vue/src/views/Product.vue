<template>
  <div style="padding: 10px">

    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-upload
        action="http://localhost:9090/product/import"
        :on-success="handleUploadSuccess"
        :show-file-list=false
        :limit="1"
        accept='.xlsx'
        style="display: inline-block; margin: 0 10px"
      >
        <el-button type="primary">导入</el-button>
      </el-upload>
      <el-button type="primary" @click="exportUser">导出</el-button>
    </div>

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入物品名称" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="productId"
          label="ID"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="productName"
          label="名称">
      </el-table-column>
      <el-table-column
          prop="intro"
          label="简介">
      </el-table-column>
<!--      <el-table-column-->
<!--          prop="pnum"-->
<!--          label="库存">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--          prop="sname"-->
<!--          label="供应商">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--          prop="address"-->
<!--          label="地址">-->
<!--      </el-table-column>-->
<!--      <el-table-column label="角色列表" width="300">-->
<!--        <template #default="scope">-->
<!--          <el-select v-model="scope.row.roles" multiple placeholder="请选择" style="width: 80%">-->
<!--            <el-option v-for="item in roles" :key="item.id" :label="item.comment" :value="item.id"></el-option>-->
<!--          </el-select>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="操作" width="400">
        <template #default="scope">
<!--          <el-button size="mini" type="primary" @click="handleChange(scope.row)">保存角色信息</el-button>-->
<!--          <el-button size="mini" type="success" plain @click="showBooks(scope.row.bookList)">查看图书列表</el-button>-->
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.productId)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>



    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="名称">
          <el-input v-model="form.productName" style="width: 80%" placeholder="必填"></el-input>
        </el-form-item>
        <el-form-item label="简介">
          <el-input type="textarea" v-model="form.intro" style="width: 80%" placeholder="选填"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>

      </template>
    </el-dialog>

  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'Home',
  components: {},
  data() {
    return {
      loading: true,
      form: {},
      dialogVisible: false,
      bookVis: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      roles: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleChange(row) {
      request.put("/product/changeRole", row).then(res => {
        if (res.code === '0') {
          this.$message.success("更新成功")
          if (res.data) {
            this.$router.push("/login")
          }
        }
      })
    },
    load() {
      this.loading = true
      request.get("/product", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleUploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功")
        this.load()
      }
    },
    exportUser() {
      location.href = "http://" + window.server.filesUploadUrl + ":9090/product/export";
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.productId) {  // 更新
        request.put("/product", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.dialogVisible = false  // 关闭弹窗
          this.load() // 刷新表格的数据
        })
      } else {  // 新增
        request.post("/product", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
            this.currentPage=res.data.pages;
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

          this.dialogVisible = false  // 关闭弹窗
          this.load() // 刷新表格的数据
        })
      }

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id) {
      request.delete("/product/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>
