<template>
  <div class="card-container">
    <el-card style="width: 50%; margin: 20px auto;">
      <div style="display: flex; flex-direction: column; align-items: center;">
        <h2>代取员解封申请</h2>
        <el-tag v-if="data.ifDisable" type="primary" plain size="large" style="margin-bottom: 20px">正在审核中</el-tag>
      </div>

      <!-- 修改后的表单部分 -->
      <div style="display: flex; justify-content: center;">
        <el-form
            ref="formRef"
            :rules="data.rules"
            :model="data.form"
            label-width="100px"
            style="width: 70%;padding-left: 50px"
        >
          <el-form-item label="代取员id" prop="id">
            <el-input disabled v-model="data.form.courierId" autocomplete="off" style="width: 60%"/>
          </el-form-item>
          <el-form-item label="账号" prop="username">
            <el-input disabled v-model="data.form.username" autocomplete="off" style="width: 60%"/>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input disabled v-model="data.form.name" autocomplete="off" style="width: 60%"/>
          </el-form-item>
          <el-form-item label="问题" prop="problem">
            <div class="editor-content-view" style="padding: 10px" v-html="data.form.problem"></div>
          </el-form-item>
          <el-form-item label="理由">
            <div style="border: 1px solid #ccc; width: 100%">
              <Toolbar
                  style="border-bottom: 1px solid #ccc"
                  :editor="editorRef"
                  :mode="mode"/>
              <Editor
                  style="height: 500px; overflow-y: hidden;"
                  v-model="data.form.reason"
                  :mode="mode"
                  :defaultConfig="editorConfig"
                  @onCreated="handleCreated"/>
            </div>
          </el-form-item>

          <el-form-item>
            <div style="text-align: center; width: 100%;padding-right: 160px">
              <el-button type="primary" size="large" @click="addReleaseApply" :disabled="data.ifDisable">
                提交
              </el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

  </div>
</template>

<script setup>
import {onBeforeUnmount, reactive, ref, shallowRef} from "vue";
import request from "../../utils/request";
import {ElMessage} from "element-plus";
import router from "../../router";
import '@wangeditor/editor/dist/css/style.css'
import {Editor,Toolbar} from '@wangeditor/editor-for-vue'

const formRef=ref()
const data =reactive({
  user:JSON.parse(localStorage.getItem('user')),
  form:{},
  identification:null,
  ifDisable:true,
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

const load=()=>{
  //查询status==='Pending'的数据
  const url='/releaseApply/selectByCourierId/'+data.user.id
  request.get(url).then(res=>{
    if(res.data!==null){
      console.log("已经有了正在申请的申请表")
      data.form=res.data
    }else{
      data.form.courierId=data.user.id
      data.form.username=data.user.username
      data.form.name=data.user.name
      data.form.problem=data.user.problem
      data.ifDisable=false
    }
  })
}
load()

const addReleaseApply=()=>{
    request.post('/releaseApply/add',data.form).then(res=>{
      if(res.code==='200'){
        ElMessage.success('提交解封申请成功')
        router.push('/student/home')
      }else{
        ElMessage.error(res.msg)
      }
    })

}

</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>

.card-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  height: 100%;
}
</style>
