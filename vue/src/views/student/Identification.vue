<template>
  <div class="card-container">
    <el-card style="width: 50%; margin: 20px auto;">
      <div style="display: flex; flex-direction: column; align-items: center;">
        <h2>代取员认证申请</h2>
        <div v-if="data.identification?.status!==null" style="margin-bottom: 20px;">
          <el-tag v-if="data.identification?.status==='Pending'" type="primary" plain size="large">正在审核中</el-tag>
          <el-tag v-if="data.identification?.status==='Approved'" type="success" plain size="large">审核通过</el-tag>
          <el-tag v-if="data.identification?.status==='Rejected'" type="warning" plain size="large">审核拒绝，请重新提交</el-tag>
        </div>
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
          <el-form-item label="姓名" prop="name">
            <el-input disabled v-model="data.form.name" autocomplete="off" style="width: 60%"/>
          </el-form-item>

          <el-form-item label="学生证照片">
            <div style="display: flex; justify-content: center;">
              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:9091/files/uploadStuCard"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :disabled="data.ifDisable"
              >
                <el-image v-if="data.form.image" :src="data.form.image" class="avatar"/>
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </div>
          </el-form-item>

          <el-form-item>
            <div style="text-align: center; width: 100%;padding-right: 160px">
              <el-button type="primary" size="large" @click="addIdentification" :disabled="data.ifDisable">
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
import {reactive, ref} from "vue";
import request from "../../utils/request";
import {ElMessage} from "element-plus";
import router from "../../router";

const formRef=ref()
const data =reactive({
  user:JSON.parse(localStorage.getItem('user')),
  form:{},
  identification:null,
  ifDisable:true,
})

//需要根据是否存在认证表以及认证表的状态来判断 表单是否可编辑
const load=()=>{
  const url='/identification/selectByStuId/'+data.user.id
  request.get(url).then(res=>{
    // console.log(res)
    data.identification=res.data
    console.log(data.identification)
    if(data.identification!==null){
      data.form=data.identification
      console.log(data.form)
      if(data.form.status==='Rejected'){
        data.ifDisable=false
      }
    }else{
      data.form.studentId=data.user.id
      data.form.name=data.user.name
      data.form.adminId=1
      data.ifDisable=false
    }

  })
}
load()
// if(data.user.role==='emp'){
//   request.get('/employee/selectById/'+data.user.id).then(res=>{
//     data.form=res.data
//   })
//   //data.form=data.user
// } else{
//   data.form=data.user
// }



// const updateUser=()=>{
//   request.put('/student/update',data.form).then(res=>{
//     if(res.code==='200'){
//       ElMessage.success('更新成功')
//       //更新缓存
//       localStorage.setItem('user',JSON.stringify(data.form))
//     }else{
//       ElMessage.error(res.msg)
//     }
//   })
// }
const addIdentification=()=>{
  if(data.identification===null){
    //用户没有上传过认证申请
    request.post('/identification/add',data.form).then(res=>{
      if(res.code==='200'){
        ElMessage.success('提交认证成功')
        router.push('/student/home')
      }else{
        ElMessage.error(res.msg)
      }
    })
  }else{
    //用户上传过但是被拒绝了
    //其实无所谓，只要用户没有上传过就行，不用管状态都是update
    data.form.status='Pending'
    request.put('/identification/update',data.form).then(res=>{
      if(res.code==='200'){
        ElMessage.success("提交成功")
        router.push('/student/home')
      } else{
        ElMessage.error(res.msg)
      }
    })
  }

}

const handleAvatarSuccess=(res)=>{
  console.log(res)
  data.form.image=res.data
  console.log(data.form)
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
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 158px;
  text-align: center;
}

.card-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  height: 700px;
}
</style>
