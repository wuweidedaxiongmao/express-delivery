<template>
  <div class="card-container">
    <el-card style="width: 50%; margin: 20px auto;">
      <div style="display: flex; flex-direction: column; align-items: center;">
        <h2>订单信息填写</h2>
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
          <el-form-item label="学生ID" prop="studentId">
            <el-input-number v-model="data.form.studentId" autocomplete="off" style="width: 60%"/>
          </el-form-item>
          <el-form-item label="快递类型ID" prop="typeId">
            <el-select v-model="data.form.typeId" @change="changeType()">
              <el-option :label="item.typeName+' : '+item.price+'元'" v-for="item in data.typeList" :key="item.id" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="取快递地址" prop="pickupAddress">
            <el-input v-model="data.form.pickupAddress" autocomplete="off" style="width: 60%"/>
          </el-form-item>
          <el-form-item label="快递送达地址" prop="addressId">
            <el-input-number v-model="data.form.addressId" autocomplete="off" style="width: 60%"/>
          </el-form-item>
          <el-form-item label="快递位置照片">
            <div style="display: flex; justify-content: center;">
              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:9091/files/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
              >
                <el-image v-if="data.form.image" :src="data.form.image" class="avatar"/>
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </div>
          </el-form-item>
          <el-form-item label="订单描述" prop="description">
            <el-input v-model="data.form.description" autocomplete="off" style="width: 60%"/>
          </el-form-item>

          <el-form-item>
            <h3>需要支付 {{data.price}} 元</h3>
          </el-form-item>
          <el-form-item>
            <div style="text-align: center; width: 100%;padding-right: 160px">
              <el-button type="primary" size="large" @click="addOrder" :disabled="data.ifDisable">
                下单
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
  form:{
    studentId: JSON.parse(localStorage.getItem('user')).id,
  },
  typeList:[],
  price:0,
})

request.get('/expressType/selectAll').then(res=>{
  data.typeList=res.data
  console.log(data.typeList)
})
const changeType=()=>{
  data.price=data.typeList.find(v=>v.id===data.form.typeId).price
}

const addOrder=()=>{
  request.post('/orders/add',data.form).then(res=>{
    if(res.code==='200'){
      ElMessage.success('发布成功')
    }else{
      ElMessage.error(res.msg)
    }
  })

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
