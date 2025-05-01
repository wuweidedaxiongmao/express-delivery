<template>
  <div class="login-container">
    <div class="login-box">
      <div style="padding: 50px 30px;background-color: white; margin-bottom: 60px; margin-top:20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.2)">
        <el-form ref="formRef" :rules="data.rules" :model="data.form" style="width: 300px">
          <div style="margin-bottom: 30px;font-size: 24px;color: blue;font-weight: bold;text-align: center;"> 欢 迎 注 册 </div>
          <el-form-item prop="username">
            <el-input v-model="data.form.username" placeholder="请输入账号" size="large" prefix-icon="user" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password v-model="data.form.password" placeholder="请输入密码" size="large" prefix-icon="lock"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input show-password v-model="data.form.confirmPassword" placeholder="请确认密码" size="large" prefix-icon="lock"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="data.form.role" style="width: 100%" size="large">
              <el-option value="STU" label="学生"></el-option>
            </el-select>
          </el-form-item>
          <div style="margin-bottom: 20px;display: flex;justify-content: center">
            <el-button size="large" style="width: 80%" type="primary" @click="register">注册</el-button>
          </div>
          <div style="text-align: center">已有账号，请<a href="/login" style="color: blue; text-decoration: none">登入</a></div>
        </el-form>
      </div>

    </div>
  </div>
</template>

<script setup>

import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "../utils/request";
import router from "../router";

const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请再次确认密码'))
  } else if (value !== data.form.password) {
    callback(new Error("两次密码不一致!"))
  } else {
    callback()
  }
}
const data=reactive({
  form:{ role:'STU' },
  rules:{
    username:[
      {required:true,message:"请输入账号",trigger:"blur"}
    ],
    password:[
      {required:true,message:"请输入密码",trigger:"blur"}
    ],
    confirmPassword:[
      {validator:validatePass, trigger: 'blur'}
    ]
  }
})

// 表单校验
const formRef=ref()
const register=()=>{
  formRef.value.validate((valid)=>{
    if(valid){
        request.post('/register',data.form).then(res=>{
          if(res.code==='200'){//注册成功
            //存储data
            //localStorage.setItem('user',JSON.stringify(res.data))
            ElMessage.success('注册成功')
            //console.log(res)
          }else{
            ElMessage.error(res.msg)
          }
        })
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  overflow: hidden;
  background-image: url("../assets/login2.png");
  background-size: cover;
  background-position: 0 -85px;
}
.login-box {
  position: absolute;
  width: 35%;
  height: 100%;
  right:0;
  display: flex;
  align-items: center;
}

</style>