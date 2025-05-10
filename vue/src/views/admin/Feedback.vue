<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.id" placeholder="请输入id搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-select v-model="data.status" style="margin-right: 5px; width: 200px; margin-left: 10px" placeholder="请选择状态查询">
          <el-option value="待处理" label="待处理"></el-option>
          <el-option value="已解决" label="已解决"></el-option>
        </el-select>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()" style="margin-left: 20px">重置</el-button>
      </el-card>
      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
<!--          <el-table-column prop="id" label="ID"/>-->
          <el-table-column label="反馈人角色">
            <template v-slot="scope">
              <el-tag v-if="scope.row.userType==='STU'">学生</el-tag>
              <el-tag v-if="scope.row.userType==='COUR'">代取员</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="userId" label="反馈人id" show-overflow-tooltip="true"/>
          <el-table-column prop="studentName" label="反馈人姓名" />
          <el-table-column prop="studentUsername" label="反馈人账号" show-overflow-tooltip="true"/>
          <el-table-column prop="content" label="反馈内容" show-overflow-tooltip="true"/>
          <el-table-column label="状态">
            <template v-slot="scope">
              <el-tag type="warning" v-if="scope.row.status==='待处理'" size="large">待处理</el-tag>
              <el-tag type="success" v-if="scope.row.status==='已解决'" size="large">已解决</el-tag>
            </template>
          </el-table-column>

          <el-table-column prop="createdAtStr" label="创建时间"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="large" :icon="Edit" @click="handleUpdate(scope.row)" v-if="scope.row.status==='待处理'">处理</el-button>
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

    <el-dialog v-model="data.formVisible" title="反馈信息" width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="反馈内容" prop="content">
          <el-input v-model="data.form.content" autocomplete="off" disabled type="textarea" autosize/>
        </el-form-item>
        <el-form-item label="处理状态">
          <el-select v-model="data.form.status" style="width: 100%" size="large">
            <el-option value="待处理" label="待处理"></el-option>
            <el-option value="已解决" label="已解决"></el-option>
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
  id:null,
  status:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  form:{},
  ids:[],
  rules:{
    typeName:[
      {required:true, message:"请输入类型", trigger: "blur"}
    ],
    price:[
      {required:true, message:"请输入价格", trigger: "blur"}
    ],
  }
})

const formRef=ref()

const load=()=>{
  request.get('/feedback/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      id:data.id,
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
  data.typeName=null
  data.status=null
  load()
}

const handleAdd=()=>{
  data.formVisible=true
  data.form={}
}

const save=()=>{  // 2个操作 add & edit
  formRef.value.validate((valid)=>{
    if(valid){
      if(data.form.id==null){
        add()
      }else{
        update()
      }
    }
  })
}

const update=()=>{
  request.put('/feedback/update',data.form).then(res=>{
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
    request.delete('/expressType/deleteById',{
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
  // request.delete('/admin/deleteBatch',{
  //   params:{
  //     ids:data.ids
  //   }
  // })
  ElMessageBox.confirm("批量删除删除数据后无法恢复，确认删除吗",{type:"warning"}).then(()=>{
    request.delete('/expressType/deleteBatch',{data:data.ids}).then(res=>{
      if(res.code=='200'){
        ElMessage.success("操作成功")
        load() //新增后重新加载数据
      } else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()

}
const handleAvatarSuccess=(res)=>{
  //console.log(res)
  data.form.avatar=res.data
}

</script>