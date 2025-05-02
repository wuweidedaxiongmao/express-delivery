<template>
  <div class="card-container">
    <el-card style="width: 50%;">
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9091/files/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input disabled v-model="data.form.username" autocomplete="off" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="data.form.phone" autocomplete="off" placeholder="请输入电话"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="data.form.email" autocomplete="off" placeholder="请输入邮箱"/>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" size="large" @click="updateUser">更新个人信息</el-button>
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
  rules:{
    // username:[
    //   {required:true, message:"请输入账号", trigger: "blur"}
    // ],
    // name:[
    //   {required:true, message:"请输入姓名", trigger: "blur"}
    // ],
  }
})

const emit=defineEmits(['updateUser'])

// if(data.user.role==='emp'){
//   request.get('/employee/selectById/'+data.user.id).then(res=>{
//     data.form=res.data
//   })
//   //data.form=data.user
// } else{
//   data.form=data.user
// }
data.form=data.user

const updateUser=()=>{
    request.put('/admin/update',data.form).then(res=>{
      if(res.code==='200'){
        ElMessage.success('更新成功')
        router.push('/admin')
        //更新缓存
        localStorage.setItem('user',JSON.stringify(data.form))
        emit('updateUser')
      }else{
        ElMessage.error(res.msg)
      }
    })
}

const handleAvatarSuccess=(res)=>{
 //console.log(res)
  data.form.avatar=res.data
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
  width: 178px;
  height: 178px;
  text-align: center;
}

.card-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  height: 700px;
}
</style>
