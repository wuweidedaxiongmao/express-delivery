<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.title" placeholder="请输入标题搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()">重置</el-button>
      </el-card>

      <el-card class="card">
        <el-button type="primary" plain @click="handleAdd()">新增</el-button>
        <el-button type="primary" plain @click="batchDelete">批量删除</el-button>
      </el-card>

      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="ID"/>
          <el-table-column prop="title" label="标题" show-overflow-tooltip="true"/>
          <el-table-column label="封面">
            <template #default="scope">
              <el-image :preview-src-list="[scope.row.img]" preview-teleported v-if="scope.row.img" :src="scope.row.img" style="width: 100px;height: 100px;"/>
            </template>
          </el-table-column>
          <el-table-column label="内容">
            <template #default="scope">
              <el-button type="primary" @click="view(scope.row.content)">查看内容</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="createdAtStr" label="创建时间"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="large" :icon="Edit" @click="handleUpdate(scope.row)">编辑</el-button>
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

    <el-dialog v-model="data.formVisible" title="文章信息" width="50%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="封面" >
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9091/files/upload"
              list-type="picture"
              :on-success="handleAvatarSuccess">
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="内容">
          <div style="border: 1px solid #ccc; width: 100%">
            <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :mode="mode"/>
            <Editor
                style="height: 500px; overflow-y: hidden;"
                v-model="data.form.content"
                :mode="mode"
                :defaultConfig="editorConfig"
                @onCreated="handleCreated"/>
          </div>
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

    <el-dialog title="内容" v-model="data.viewVisible" width="50%" :close-on-click-modal="false" destory-on-close>
      <div class="editor-content-view" style="padding: 20px" v-html="data.content"></div>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="data.viewVisible = false">关闭</el-button>
    </span>
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
  title:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  formVisible:false,
  form:{},
  ids:[],
  content:null,
  viewVisible:false,
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

const load=()=>{
  request.get('/information/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      title:data.title
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
  request.post('/information/add',data.form).then(res=>{
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
  request.put('/information/update',data.form).then(res=>{
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
    request.delete('/information/deleteById',{
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
    request.delete('/information/deleteBatch',{data:data.ids}).then(res=>{
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
  data.form.img=res.data
}
const view=(content)=>{
  data.content=content
  data.viewVisible=true
}

</script>