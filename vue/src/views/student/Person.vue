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
        <el-form-item label="余额">
          <div> {{data.form.money}} 元</div>
          <div style="margin-left: 50px"><el-button type="success" @click="money">充值</el-button></div>
        </el-form-item>
        <el-form-item label="工资" v-if="data.form.role==='COUR'">
          <div> {{data.form.addMoney}} 个</div>
          <div style="margin-left: 50px"><el-button type="success">提现</el-button></div>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" size="large" @click="updateUser">更新个人信息</el-button>
      </div>
      <div>
        <el-button @click="deleteAnnouncement">取消公告</el-button>
        <el-button @click="addAnnouncement">显示公告</el-button>
      </div>
    </el-card>

  </div>
  <el-dialog v-model="data.formVisible" title="充值金额" width="500" destroy-on-close>
    <el-form label-width="80px">
      <el-form-item label="充值金额">
        <el-input-number v-model="data.temp" placeholder="请输入先要充值的金额"></el-input-number>
        <el-button type="primary" @click="confirmMoney()" style="margin-left: 20px">确认充值</el-button>
      </el-form-item>
    </el-form>

  </el-dialog>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "../../utils/request";
import {ElMessage} from "element-plus";

const formRef=ref()
const data =reactive({
  formVisible:false,
  user:JSON.parse(localStorage.getItem('user')),
  form:{},
  rules:{
    username:[
      {required:true, message:"请输入账号", trigger: "blur"}
    ],
    name:[
      {required:true, message:"请输入姓名", trigger: "blur"}
    ],
  },
  temp:0,
  formMoney:{
    money:0,
  }
})

const emit=defineEmits(['updateUser','updateAnnouncement'])

const deleteAnnouncement=()=>{
  const value=false
  emit('updateAnnouncement',value)
  console.log("emit delete")
}
const addAnnouncement=()=>{
  const value=true
  emit('updateAnnouncement',value)
  console.log("emit add")
}

data.form=data.user

const updateUser=()=>{
    request.put('/student/update',data.form).then(res=>{
      if(res.code==='200'){
        ElMessage.success('更新成功')
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
const money=()=>{
  data.formVisible=true
}
const confirmMoney=()=>{
  data.formMoney.money=data.temp+data.form.money
  data.formMoney.id=data.form.id

  // const temp=data.form.money
  // data.form.money=temp+data.first
  //updateById ,formMoney列表中必须要有id属性。之前没有更新成功就是因为没有id
  request.put('/student/update',data.formMoney).then(res=>{
    if(res.code==='200'){
      ElMessage.success('更新成功')
      //更新缓存
      data.form.money=data.formMoney.money
      localStorage.setItem('user',JSON.stringify(data.form))
      data.formVisible=false
    }else{
      data.formVisible=false
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
