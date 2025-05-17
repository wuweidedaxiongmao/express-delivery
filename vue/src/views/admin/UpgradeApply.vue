<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.courierName" placeholder="请输入姓名搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-select v-model="data.status" style="margin-right: 5px; width: 200px; margin-left: 10px" placeholder="请选择状态查询">
          <el-option value="Pending" label="待处理"></el-option>
          <el-option value="Approved" label="已接收"></el-option>
          <el-option value="Rejected" label="已拒绝"></el-option>
        </el-select>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()" style="margin-left: 20px">重置</el-button>
      </el-card>

      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="申请ID"/>
          <el-table-column prop="courierUsername" label="代取员账号" show-overflow-tooltip="true"/>
          <el-table-column prop="courierName" label="代取员姓名"/>
          <el-table-column prop="rating" label="平均评分"/>
          <el-table-column prop="orderCount" label="接单数量"/>
          <el-table-column prop="currentLevel" label="当前等级"/>
          <el-table-column prop="targetLevel" label="目标等级"/>
          <el-table-column prop="description" label="描述" show-overflow-tooltip/>
          <el-table-column prop="status" label="状态" show-overflow-tooltip="true">
            <template v-slot="scope">
              <el-tag type="primary" v-if="scope.row.status==='Pending'">待审核</el-tag>
              <el-tag type="success" v-if="scope.row.status==='Approved'">已通过</el-tag>
              <el-tag type="danger" v-if="scope.row.status==='Rejected'">已拒绝</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="small" :icon="Edit" @click="handleUpdate(scope.row)" v-if="scope.row.status==='Pending'">
                审核
              </el-button>
              <el-button link type="primary" size="small" :icon="Delete" @click="del(scope.row.id)">删除</el-button>
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

    <el-dialog v-model="data.formVisible" title="代取员提示等级申请" width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="姓名" prop="name" style="width: 80%">
          <el-input v-model="data.form.courierName" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="当前等级" prop="currentLevel" style="width: 80%">
          <el-input v-model="data.form.currentLevel" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="目标等级" prop="targetLevel" style="width: 80%">
          <el-input v-model="data.form.targetLevel" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item prop="status" style="width: 80%">
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
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Search,Edit,Delete} from "@element-plus/icons-vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const data=reactive({
  courierName: null,
  status: 'Pending',
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  form:{},
  courier:{},
  ids:[],
  rules:{
    status:[
      {required:true, message:"请选择状态", trigger: "blur"}
    ],
  }
})

const formRef=ref()

const load=()=>{
  request.get('/upgradeApply/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      courierName:data.courierName,
      status:data.status,
    }
  }).then(res=>{
    data.tableData=res.data.list
    data.total=res.data.total
    console.log(res.data)
  })
}
load()

const reset=()=>{
  data.courierName=null
  data.status=null
  load()
}

const handleAdd=()=>{
  data.formVisible=true
  data.form={}
}

const update=()=>{
  if(data.form.status==='Approved'){
    //成功 update courier
    data.courier.id=data.form.courierId
    data.courier.level=data.form.targetLevel
    request.put('/student/update',data.courier)
  }
  request.put('/upgradeApply/update',data.form).then(res=>{
    if(res.code=='200'){
      data.formVisible=false
      ElMessage.success("操作成功")
      load() //新增后重新加载数据
    } else{
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdate=(row)=>{
  // data.form=row  会出现浅拷贝
  data.form=JSON.parse(JSON.stringify(row)) //深拷贝
  data.formVisible=true
}

const del=(id)=>{
  ElMessageBox.confirm("删除数据后无法恢复，确认删除吗","确认删除",{type:"warning"}).then(()=>{
    request.delete('/upgradeApply/deleteById',{
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

const handleSelectChange=(rows=>{
  console.log(rows)
  data.ids = rows.map(row=>row.id)
  console.log(data.ids)
})

const batchDelete=()=>{
  if(data.ids.length===0){
    ElMessage.warning("请选择要删除的数据")
    return
  }
  ElMessageBox.confirm("批量删除删除数据后无法恢复，确认删除吗",{type:"warning"}).then(()=>{
    request.delete('/upgradeApply/deleteBatch',{data:data.ids}).then(res=>{
      if(res.code=='200'){
        ElMessage.success("操作成功")
        load() //新增后重新加载数据
      } else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()

}

</script>