<template>
  <div style="padding: 10px">

    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-upload
        action="http://localhost:9090/purchase/import"
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
      <el-input v-model="search" placeholder="请输入产品名" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="purchaseId"
          label="采购单编号"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="productName"
          label="产品名">
      </el-table-column>
      <el-table-column
          prop="companyName"
          label="供应商">
      </el-table-column>
      <el-table-column
          prop="adminUsername"
          label="经手人">
      </el-table-column>
      <el-table-column
          prop="purchaseDate"
          label="采购时间">
      </el-table-column>
      <el-table-column label="操作" width="400">
        <template #default="scope">
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
<!--          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.purchaseId)">-->
<!--            <template #reference>-->
<!--              <el-button size="mini" type="danger">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
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
<!--        <el-form-item label="采购单号">-->
<!--          <el-input v-model="form.purchaseId" style="width: 80%"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="产品名">
          <el-input v-model="form.productName" style="width: 80%" placeholder="必填"></el-input>
        </el-form-item>
        <el-form-item label="供应商">
          <el-input v-model="form.companyName" style="width: 80%" placeholder="必填"></el-input>
        </el-form-item>
        <el-form-item label="经手人">
          <el-input v-model="form.adminUsername" style="width: 80%" placeholder="经手人"></el-input>
        </el-form-item>
        <el-form-item label="采购时间">
          <div class="block">
            <el-date-picker
                v-model="form.vueDate"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
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
  name: 'Outbox',
  components: {},
  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      vueDate: '',
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
      request.put("/purchase/changeRole", row).then(res => {
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
      request.get("/purchase", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
        console.log(this.form)
      })

    },
    handleUploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功")
        this.load()
      }
    },
    exportUser() {
      location.href = "http://" + window.server.filesUploadUrl + ":9090/purchase/export";
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.purchaseId) {  // 更新
        request.put("/purchase", this.form).then(res => {
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
        request.post("/purchase", this.form).then(res => {
          console.log(this.form)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
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
      }

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id) {
      request.delete("/purchase/" + id).then(res => {
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
