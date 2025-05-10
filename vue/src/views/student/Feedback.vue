<template>
  <div class="feedback-container">
    <div class="plum-blossom-bg"></div>
    <el-card class="feedback-card">
      <div class="plum-branch"></div>
      <h1 class="feedback-title">提交您的反馈信息</h1>

      <el-form :model="data.form" :rules="rules" ref="feedbackFormRef" label-width="80px">
        <el-form-item label="反馈内容" prop="content">
          <el-input
              type="textarea"
              v-model="data.form.content"
              :rows="8" placeholder="请输入您的反馈内容或建议"
              class="plum-input"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              @click="submitForm"
              class="plum-button"
              :loading="submitting"
          >
            <i class="icon-submit"></i> 提交
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue';
import { ElMessage } from 'element-plus';
import request from "../../utils/request";
import router from "../../router";

const data =reactive({
  user:JSON.parse(localStorage.getItem('user')),
  form:{},
})


const submitting = ref(false);

const rules = {
  content: [ // 只保留 content 的验证规则
    { required: true, message: '请输入您的反馈内容或建议', trigger: 'blur' }
  ]
};

const feedbackFormRef = ref(null);

const submitForm = () => {
  feedbackFormRef.value.validate((valid) => {
    if (valid) {
      submitting.value = true;
      data.form.userId=data.user.id
      data.form.userType=data.user.role
      data.form.status='待处理'
      request.post('/feedback/add',data.form).then(res=>{
        if(res.code=='200'){
          ElMessage.success("提交成功，感谢您的反馈")
          router.push('/student/home')
        } else{
          ElMessage.error(res.msg)
        }
      }).finally(() => { // 无论成功失败都将提交状态设为false
        submitting.value = false;
      })
    } else {
      // 表单验证失败，保持 submitting 状态为 false
      return false;
    }
  });
};
</script>

<style scoped>
.feedback-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e0f2f7; /* Light blue background */
  position: relative;
  overflow: hidden; /* Prevent scrollbar */
}

.plum-blossom-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><path d="M50 10 Q60 30 70 10 Q80 30 90 10 Q85 40 100 50 Q85 60 90 90 Q80 70 70 90 Q60 70 50 90 Q40 70 30 90 Q20 70 10 90 Q15 60 0 50 Q15 40 10 10 Q20 30 30 10 Q40 30 50 10 Z" fill="%2390caf9" opacity="0.1"/></svg>'); /* Light blue plum blossom */
  background-size: 200px;
  opacity: 0.5;
  z-index: 0;
}

.feedback-card {
  width: 750px; /* 增加卡片宽度 */
  padding: 40px; /* 增加卡片内边距 */

  position: relative;
  z-index: 1;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(144, 202, 249, 0.5); /* Light blue shadow */
  border: 1px solid rgba(144, 202, 249, 0.7); /* Light blue border */
  background-color: rgba(255, 255, 255, 0.9);
  transform: translateY(-70px); /* 将卡片向上移动 70 像素 */
}

.plum-branch {
  position: absolute;
  top: -50px;
  right: -30px;
  width: 150px;
  height: 150px;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><path d="M10 80 Q30 60 50 70 Q70 60 90 80" stroke="%231976d2" stroke-width="2" fill="none"/><circle cx="30" cy="40" r="5" fill="%231976d2"/><circle cx="60" cy="30" r="5" fill="%231976d2"/><circle cx="70" cy="50" r="5" fill="%231976d2"/></svg>'); /* Dark blue branch */
  background-repeat: no-repeat;
  transform: rotate(15deg);
}

.feedback-title {
  text-align: center;
  color: #1976d2; /* Dark blue title */
  margin-bottom: 30px;
  font-weight: bold;
  position: relative;
}

.feedback-title::after {
  content: "";
  display: block;
  width: 50px;
  height: 3px;
  background: linear-gradient(to right, #90caf9, #1976d2); /* Blue gradient */
  margin: 10px auto 0;
}

.plum-input ::v-deep .el-textarea__inner {
  border-radius: 8px;
  border: 1px solid #90caf9; /* Light blue input border */
  transition: all 0.3s;
}

.plum-input ::v-deep .el-textarea__inner:focus {
  border-color: #1976d2; /* Dark blue focus border */
  box-shadow: 0 0 0 2px rgba(25, 118, 210, 0.2); /* Light dark blue focus shadow */
}
.plum-button {
  background-color: #1976d2; /* Dark blue button */
  border-color: #1976d2; /* Dark blue button border */
  border-radius: 20px;
  padding: 10px 30px;
  transition: all 0.3s;
}

.plum-button:hover {
  background-color: #1565c0; /* Darker blue hover */
  border-color: #1565c0; /* Darker blue hover border */
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(25, 118, 210, 0.3); /* Dark blue hover shadow */
}

.icon-submit {
  display: inline-block;
  width: 16px;
  height: 16px;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M21 12L3 19V5L21 12Z" fill="white"/></svg>');
  background-repeat: no-repeat;
  margin-right: 5px;
  vertical-align: middle;
}
</style>

<style>
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow: hidden; /* 确保 html 和 body 不会产生滚动条 */
}
</style>