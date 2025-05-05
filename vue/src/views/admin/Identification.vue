<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.typeName" placeholder="请输入搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()">重置</el-button>
      </el-card>

      <el-card class="card">
<!--        <el-button type="primary" plain @click="handleAdd()">新增</el-button>-->
        <el-button type="primary" plain @click="batchDelete">批量删除</el-button>
        <!--        <el-button type="primary" plain>upload</el-button>-->
        <!--        <el-button type="primary" plain>download</el-button>-->
      </el-card>

      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
          <el-table-column type="selection" width="55" />
<!--          <el-table-column prop="id" label="ID"/>-->
<!--          <el-table-column label="头像">-->
<!--            <template #default="scope">-->
<!--              <img v-if="scope.row.avatar" :src="scope.row.avatar" style="width: 40px;height: 40px;border-radius: 50%"/>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column prop="name" label="姓名" show-overflow-tooltip="true"/>
          <el-table-column label="学生证">
            <template #default="scope">
              <el-image v-if="scope.row.image" :src="scope.row.image" style="width: 80px" :preview-src-list="[scope.row.image]" preview-teleported/>
              </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" show-overflow-tooltip="true">
            <template v-slot="scope">
              <el-tag type="primary" v-if="scope.row.status==='Pending'">待审核</el-tag>
              <el-tag type="success" v-if="scope.row.status==='Approved'">已通过</el-tag>
              <el-tag type="danger" v-if="scope.row.status==='Rejected'">已拒绝</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="small" :icon="Edit" @click="handleUpdate(scope.row)" v-if="scope.row.status!=='Approved'">
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

    <el-dialog v-model="data.formVisible" title="认证信息" width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" disabled/>
        </el-form-item>
<!--        <el-form-item label="头像" >-->
<!--          <el-upload-->
<!--              class="avatar-uploader"-->
<!--              action="http://localhost:9090/files/upload"-->
<!--              list-type="picture"-->
<!--              :on-success="handleAvatarSuccess"-->
<!--          >-->
<!--            <el-button type="primary">上传头像</el-button>-->
<!--          </el-upload>-->
<!--        </el-form-item>-->
        <el-form-item prop="status">
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
  name:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  form:{},
  ids:[],
  student:{}
})

const formRef=ref()

const load=()=>{
  request.get('/identification/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      name:data.name
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
  load()
}

const handleAdd=()=>{
  data.formVisible=true
  data.form={}
}

const handleUpdate=(row)=>{
  // data.form=row  会出现浅拷贝
  data.form=JSON.parse(JSON.stringify(row)) //深拷贝
  data.formVisible=true
}

const update=()=>{
  if(data.form.status==='Approved'){
    //id===data.form.student_id
    const url='/student/selectById/'+data.form.studentId
    request.get(url).then(res=>{
      data.student=res.data
      data.student.role='COUR'
      data.student.addMoney=0
      data.student.level=1
      data.student.rating=0
      data.student.ratingCount=0
      data.student.orderCount=0
      request.put('/student/update',data.student)
    })
  }
  console.log(data.form)
  request.put('/identification/update',data.form).then(res=>{
    if(res.code=='200'){
      data.formVisible=false
      ElMessage.success("修改成功")
      load() //新增后重新加载数据
    } else{
      ElMessage.error(res.msg)
    }
  })
}

const del=(id)=>{
  ElMessageBox.confirm("删除数据后无法恢复，确认删除吗","确认删除",{type:"warning"}).then(()=>{
    request.delete('/identification/deleteById',{
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
    request.delete('/identification/deleteBatch',{data:data.ids}).then(res=>{
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
