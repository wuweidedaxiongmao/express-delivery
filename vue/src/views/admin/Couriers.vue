<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.name" placeholder="请输入姓名搜索" prefix-icon="Search">
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
          <el-table-column label="头像">
            <template #default="scope">
              <el-image v-if="scope.row.avatar"
                        :src="scope.row.avatar"
                        style="width: 40px;height: 40px;border-radius: 50%"
                        :preview-src-list="[scope.row.avatar]"
                        preview-teleported="true"
                        close-on-press-escape/>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="姓名" show-overflow-tooltip="true"/>
          <el-table-column prop="username" label="账号" show-overflow-tooltip="true"/>
          <el-table-column prop="phone" label="手机号" show-overflow-tooltip="true"/>
          <el-table-column prop="email" label="邮箱" show-overflow-tooltip="true"/>
          <el-table-column prop="money" label="金额(元)" show-overflow-tooltip="true"/>
          <el-table-column prop="addMoney" label="工资(个)" show-overflow-tooltip="true"/>
          <el-table-column prop="level" label="代取员等级" show-overflow-tooltip="true"/>
          <el-table-column prop="rating" label="平均评分" show-overflow-tooltip="true"/>
          <el-table-column prop="orderCount" label="接单数量" show-overflow-tooltip="true"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="small" :icon="Edit" @click="handleUpdate(scope.row)">
                编辑
              </el-button><br>
              <el-button link type="primary" size="small" :icon="Delete" @click="del(scope.row.id)">删除</el-button><br>
              <el-button link type="primary" size="small" :icon="Delete" @click="black(scope.row)" v-if="scope.row.ifBlack!==1">拉黑<br></el-button>
              <el-tag type="primary" v-if="scope.row.ifBlack===1">已拉黑</el-tag>
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

    <el-dialog v-model="data.formVisible" title="学生信息" width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="data.form.password" autocomplete="off" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="头像" >
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9091/files/upload"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="data.form.phone" autocomplete="off" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="data.form.email" autocomplete="off" placeholder="请输入邮箱"/>
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

<!--    拉黑页面-->
    <el-dialog v-model="data.blackVisible" title="拉黑" width="50%" destroy-on-close>
      <el-form ref="blackRef" :rules="data.blackRules" :model="data.blackForm" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.blackForm.name" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="问题描述">
          <div style="border: 1px solid #ccc; width: 100%">
            <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :mode="mode"/>
            <Editor
                style="height: 500px; overflow-y: hidden;"
                v-model="data.blackForm.problem"
                :mode="mode"
                :defaultConfig="editorConfig"
                @onCreated="handleCreated"/>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.blackVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmBlack()">确认拉黑
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {onBeforeUnmount, reactive, ref, shallowRef} from "vue";
import {Search,Edit,Delete} from "@element-plus/icons-vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import '@wangeditor/editor/dist/css/style.css'
import {Editor,Toolbar} from '@wangeditor/editor-for-vue'

const data=reactive({
  name:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  form:{},
  ids:[],
  rules:{
    name:[
      {required:true, message:"请输入姓名", trigger: "blur"}
    ],
    username:[
      {required:true, message:"请输入账号", trigger: "blur"}
    ],
    password:[
      {required:true, message:"请输入密码", trigger: "blur"}
    ],
  },
  blackRules:{

  },
  blackForm:{},
  blackVisible:false,
})
/* wangEditor5 初始化代码 */
const baseUrl = 'http://localhost:9091'
const editorRef = shallowRef() // 编辑器实例，必须用 shallowRef
const mode = 'default'
const editorConfig = { MENU_CONF: {} }

// 图片上传配置
editorConfig.MENU_CONF['uploadImage'] = {
  server: baseUrl + '/files/wang/upload', // 服务端图片上传接口
  fieldName: 'file' // 服务端图片上传接口参数
}

// 组件销毁时，也及时销毁编辑器，否则可能会造成内存泄露
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

// 记录实例，重要！
const handleCreated = (editor) => {
  editorRef.value = editor
}

/* wangEditor5 初始化结束 */

const formRef=ref()
const blackRef=ref()

const load=()=>{
  request.get('/student/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      name:data.name
    }
  }).then(res=>{
    data.tableData=res.data.list.filter(item=>item.role==='COUR')
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
  data.form.role='STU'
  request.post('/student/add',data.form).then(res=>{
    if(res.code=='200'){
      data.formVisible=false
      ElMessage.success("操作成功")
      load() //新增后重新加载数据
    } else{
      ElMessage.error(res.msg)
    }
  })
}
const update=()=>{
  request.put('/student/update',data.form).then(res=>{
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
    request.delete('/student/deleteById',{
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
    request.delete('/student/deleteBatch',{data:data.ids}).then(res=>{
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

const black=(row)=>{
  data.blackForm.id=row.id
  data.blackForm.name=row.name
  data.blackVisible=true;
}
const confirmBlack=()=>{
  data.blackForm.ifBlack=1 //设置拉黑标志
  request.put('/student/update',data.blackForm).then(res=>{
    if(res.code=='200'){
      data.blackVisible=false
      ElMessage.success("拉黑成功")
      load() //拉黑后重新加载数据
    } else{
      ElMessage.error(res.msg)
    }
  })
}

</script>