<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.name" placeholder="请输入姓名搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-select v-model="data.status" style="margin-right: 5px; width: 200px; margin-left: 10px" placeholder="请选择状态查询">
          <el-option value="Pending" label="待审核"></el-option>
          <el-option value="Approved" label="通过"></el-option>
          <el-option value="Rejected" label="拒绝"></el-option>
        </el-select>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()" style="margin-left: 20px">重置</el-button>
      </el-card>
      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
          <el-table-column prop="id" label="ID"/>
          <el-table-column prop="username" label="账号" />
          <el-table-column prop="name" label="姓名"/>
          <el-table-column label="问题">
            <template #default="scope">
              <el-button type="primary" @click="viewProblem(scope.row)">查看问题</el-button>
            </template>
          </el-table-column>
          <el-table-column label="理由">
            <template #default="scope">
              <el-button type="primary" @click="viewReason(scope.row)">查看理由</el-button>
            </template>
          </el-table-column>
          <el-table-column label="状态">
            <template v-slot="scope">
              <el-tag type="primary" v-if="scope.row.status==='Pending'" size="large">待审核</el-tag>
              <el-tag type="success" v-if="scope.row.status==='Approved'" size="large">通过</el-tag>
              <el-tag type="warning" v-if="scope.row.status==='Rejected'" size="large">拒绝</el-tag>
            </template>
          </el-table-column>

          <el-table-column prop="createdAtStr" label="创建时间"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="large" :icon="Edit" @click="handleUpdate(scope.row)" v-if="scope.row.status==='Pending'">处理</el-button>
              <el-button link type="primary" size="large" :icon="Delete" @click="del(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>

        </el-table>
        <div style="margin-top: 10px;">
          <el-pagination
              @size-change="load()"
              @current-change="load()"
              v-model:current-page="data.pageNumber"
              v-model:page-size="data.pageSize"
              :page-sizes="[1, 3, 5, 7]"
              :background="background"
              layout="total, sizes, prev, pager, next, jumper"
              :total="data.total"
          />
        </div>
      </el-card>
    </div>

    <el-dialog v-model="data.formVisible" title="解封信息" width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="问题" prop="problem">
          <div style="padding: 10px" v-html="data.form.problem"></div>
        </el-form-item>
        <el-form-item label="理由" prop="reason">
          <div style="padding: 10px" v-html="data.form.reason"></div>
        </el-form-item>
        <el-form-item label="是否解封" prop="status">
          <el-select v-model="data.form.status" style="width: 100%" size="large">
            <el-option value="Pending" label="待审核"></el-option>
            <el-option value="Approved" label="通过"></el-option>
            <el-option value="Rejected" label="拒绝"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="update()">保存
          </el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="问题" v-model="data.problemVisible" width="50%" :close-on-click-modal="false" destory-on-close>
      <div class="editor-content-view" style="padding: 20px" v-html="data.problem"></div>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="data.problemVisible = false">关闭</el-button>
    </span>
      </template>
    </el-dialog>
    <el-dialog title="理由" v-model="data.reasonVisible" width="50%" :close-on-click-modal="false" destory-on-close>
      <div class="editor-content-view" style="padding: 20px" v-html="data.reason"></div>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="data.reasonVisible = false">关闭</el-button>
    </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Search,Edit,Delete} from "@element-plus/icons-vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
const data=reactive({
  name:null,
  status:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  problemVisible:false,
  reasonVisible:false,
  problem:null,
  reason:null,
  form:{},
  ids:[],
  rules:{
    status:[
      {required:true, message:"请选择操作", trigger: "blur"}
    ],
  },
  student:{}, //记录当approved时修改对应的代取员信息
})

const formRef=ref()

const load=()=>{
  request.get('/releaseApply/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      name:data.name,
      status:data.status
    }
  }).then(res=>{
    data.tableData=res.data.list
    data.total=res.data.total
    console.log(res.data)
  })
}
load()

const reset=()=>{
  data.name=null
  data.status=null
  load()
}

const handleAdd=()=>{
  data.formVisible=true
  data.form={}
}

const update=()=>{
  //当接收解封申请时，需要修改student表
  request.put('/releaseApply/update',data.form).then(res=>{
    if(res.code=='200'){
      if(data.form.status==='Approved'){
        data.student.id=data.form.courierId
        data.student.problem='已经解决'
        data.student.ifBlack=0  //取消拉黑
        request.put('student/update',data.student).then(res=>{
          if(res.code==='200'){
            data.formVisible=false
            ElMessage.success("审核通过")
            load()
          }else{
            ElMessage.error(res.msg)
          }
        })
      }else{
        data.formVisible=false
        ElMessage.success("操作成功")
        load()
      }
    } else{
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdate=(row)=>{
  // data.form=row  会出现浅拷贝
  data.form=JSON.parse(JSON.stringify(row)) //深拷贝
  console.log(data.form)
  data.formVisible=true
}

const del=(id)=>{
  ElMessageBox.confirm("删除数据后无法恢复，确认删除吗","确认删除",{type:"warning"}).then(()=>{
    request.delete('/releaseApply/deleteById',{
      params:{
        id:id
      }
    }).then(res=>{
      if(res.code=='200'){
        ElMessage.success("操作成功")
        load() //删除后重新加载数据
      } else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()

}

const viewProblem=(row)=>{
  data.problem=row.problem
  data.problemVisible=true
}
const viewReason=(row)=>{
  data.reason=row.reason
  data.reasonVisible=true
}

</script>