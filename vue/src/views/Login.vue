<template>
  <div class="login-container">
    <div class="login-box">
      <div style="padding: 50px 30px;background-color: white; margin-bottom: 60px; margin-top:20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.2)">
        <el-form ref="formRef" :rules="data.rules" :model="data.form" style="width: 300px">
          <div style="margin-bottom: 30px;font-size: 24px;color: blue;font-weight: bold;text-align: center;">欢迎登入智能快递代取系统</div>
          <el-form-item prop="username">
            <el-input v-model="data.form.username" placeholder="请输入账号" size="large" prefix-icon="user" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password v-model="data.form.password" placeholder="请输入密码" size="large" prefix-icon="lock"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="data.form.role" style="width: 100%" size="large">
              <el-option value="STU" label="学生"></el-option>
              <el-option value="COUR" label="学生+代取员"></el-option>
              <el-option value="ADMIN" label="管理员"></el-option>
            </el-select>
          </el-form-item>
          <div style="margin-bottom: 20px;display: flex;justify-content: center">
            <el-button size="large" style="width: 80%" type="primary" @click="login">登入</el-button>
          </div>
          <div style="text-align: center">还没有账号？请<a href="/register" style="color: blue; text-decoration: none">注册</a></div>
        </el-form>
      </div>

    </div>
  </div>
  <AiAssistantEntry/>
</template>

<script setup>

import {onMounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "../utils/request";
import router from "../router";
import AiAssistantEntry from "./AiAssistantEntry.vue";
const data=reactive({
  form:{ role:'STU' },
  rules:{
    username:[
      {required:true,message:"请输入账号",trigger:"blur"}
    ],
    password:[
      {required:true,message:"请输入密码",trigger:"blur"}
    ],
  }
})

// 表单校验
const formRef=ref()
const login=()=>{
  formRef.value.validate((valid)=>{
    if(valid){
        request.post('/login',data.form).then(res=>{
          if(res.code==='200'){//登入成功
            //存储data
            localStorage.setItem('user',JSON.stringify(res.data))
            ElMessage.success('登入成功')
            //console.log(res)
            if(res.data.role==='ADMIN'){
              //location.href='/admin/home'
              router.push('/admin')
            }else if(res.data.role==='STU'||res.data.role==='COUR'){
              //location.href='student/home'
              router.push('/student/home')
            }else {
              ElMessage.error("role为空，请通知开发人员检查！！！")
            }
          }else{
            ElMessage.error(res.msg)
          }
        })
    }
  })
}
// 动态加载聊天脚本
onMounted(() => {
  // 创建 script 标签
  const script = document.createElement("script");
  script.src = "https://app.qiaoqiaoyun.com/chat/chat.js";
  script.id = import.meta.env.VITE_QIAOQIAOYUN_SCRIPT_ID;
  script.async = true;

  // 当脚本加载完成后，初始化聊天
  script.onload = () => {
    if (window.createAiChat) {
      window.createAiChat({
        appId: import.meta.env.VITE_QIAOQIAOYUN_APP_ID,
        iconPosition: "bottom-right",
      });
    } else {
      console.error("createAiChat function not found");
    }
  };

  // 将脚本添加到 document
  document.body.appendChild(script);

  // 清理脚本（可选，避免内存泄漏）
  return () => {
    document.body.removeChild(script);
  };
});
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