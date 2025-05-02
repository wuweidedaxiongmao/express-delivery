<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.typeName" placeholder="请输入代取员搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()">重置</el-button>
      </el-card>

      <el-card class="card">
        <el-button type="primary" plain @click="handleAdd()">新增</el-button>
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
          <el-table-column prop="courierLevel" label="代取员等级" show-overflow-tooltip="true"/>
          <el-table-column prop="commissionRate" label="体现比例" show-overflow-tooltip="true"/>
          <el-table-column prop="info" label="具体说明"/>

          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="small" :icon="Edit" @click="handleUpdate(scope.row)">
                编辑
              </el-button><br>
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

    <el-dialog v-model="data.formVisible" title="代取员等级表" width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="代取员等级" prop="courierLevel">
          <el-input-number v-model="data.form.courierLevel" autocomplete="off" placeholder="请输入代取员等级"/>
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
        <el-form-item label="体现比例" prop="commissionRate">
          <el-input-number :precision="2" :step="0.1" :max="1" :min="0" v-model="data.form.commissionRate" autocomplete="off" placeholder="请输入体现比例"/>
        </el-form-item>
        <el-form-item label="具体说明" prop="info">
          <el-input v-model="data.form.info" autocomplete="off" placeholder="请输入对代取员等级的具体说明"/>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save()">保存
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
  courierLevel:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  form:{},
  ids:[],
  rules:{
    courierLevel:[
      {required:true, message:"请输入代取员等级", trigger: "blur"}
    ],
    commissionRate:[
      {required:true, message:"请输入体现比例", trigger: "blur"}
    ],
  }
})

const formRef=ref()

const load=()=>{
  request.get('/courierCommission/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      courierLevel:data.courierLevel
    }
  }).then(res=>{
    data.tableData=res.data.list
    data.total=res.data.total
    //console.log(res.data)
  })
}
load()

const reset=()=>{
  data.courierLevel=null
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

const add=()=>{
  request.post('/courierCommission/add',data.form).then(res=>{
    if(res.code=='200'){
      data.formVisible=false
      ElMessage.success("新增操作成功")
      load() //新增后重新加载数据
    } else{
      ElMessage.error(res.msg)
    }
  })
}
const update=()=>{
  request.put('/courierCommission/update',data.form).then(res=>{
    if(res.code=='200'){
      data.formVisible=false
      ElMessage.success("更新操作成功")
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
    request.delete('/courierCommission/deleteById',{
      params:{
        id:id
      }
    }).then(res=>{
      if(res.code=='200'){
        ElMessage.success("删除成功")
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
  //console.log(data.ids)
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
    request.delete('/courierCommission/deleteBatch',{data:data.ids}).then(res=>{
      if(res.code=='200'){
        ElMessage.success("批量删除成功")
        load() //新增后重新加载数据
      } else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()

}
// const handleAvatarSuccess=(res)=>{
//   //console.log(res)
//   data.form.avatar=res.data
// }

</script>