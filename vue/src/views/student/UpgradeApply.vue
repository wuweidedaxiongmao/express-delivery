<template>
  <div class="card-container">
    <el-card style="width: 50%; margin: 20px auto;">
      <div style="display: flex; flex-direction: column; align-items: center;">
        <h2>代取员等级提升申请表填写</h2>
        <el-tag v-if="data.upgradeApply?.status==='Pending'" type="primary" plain size="large">正在审核中</el-tag>
      </div>

      <div style="display: flex; justify-content: center;margin-top: 15px;">
        <el-form
            ref="formRef"
            :rules="data.rules"
            :model="data.form"
            label-width="100px"
            style="width: 70%;padding-left: 50px"
        >
          <el-form-item label="用户名" prop="username" style="width: 60%">
            <el-input disabled v-model="data.user.username" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="姓名" prop="name" style="width: 60%">
            <el-input disabled v-model="data.user.name" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="当前等级" prop="currentLevel">
            <el-input-number disabled v-model="data.form.currentLevel" autocomplete="off" placeholder="请输入目标等级"/>
          </el-form-item>
          <el-form-item label="目标等级" prop="targetLevel">
            <el-select v-model="data.form.targetLevel" style="width: 40%" placeholder="请输入目标等级" :disabled="data.ifDisable">
              <el-option :label="'等级：'+item" v-for="item in data.courierIds" :key="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" prop="description" style="width: 60%">
            <el-input type="textarea" v-model="data.form.description" autocomplete="off" :disabled="data.ifDisable"/>
          </el-form-item>

          <el-form-item>
            <div style="text-align: center; width: 100%;padding-right: 160px">
              <el-button type="primary" size="large" @click="submit" :disabled="data.ifDisable">
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
  formVisible:false,
  user:JSON.parse(localStorage.getItem('user')),
  form:{},
  rules:{
    targetLevel:[
      {required:true, message:"请输入目标等级", trigger: "blur"}
    ],
  },
  couriers:[],
  courierIds:[],
  ifDisable:false,
  upgradeApply: null,
})


const load=()=>{
  request.get('/courierCommission/selectAll').then(res=>{
    data.couriers=res.data
    data.courierIds = data.couriers.map(courier => courier.id)
    console.log(data.courierIds)
  })

  //查找正在审核中的等级提升申请表，审核完成的不算，并且，如果存在，只会返回一条数据
  //和认证表不同之处在于，认证申请表，一个用户只能有一条数据；而等级提升表，一个用户可以有多条数据

  const url='/upgradeApply/selectByCourierId/'+data.user.id
  request.get(url).then(res=>{
    data.upgradeApply=res.data
    console.log(data.upgradeApply)
    if(data.upgradeApply!==null){  //正在审核
      data.form=data.upgradeApply
      data.ifDisable=true
    }else{ //没有申请表，或者已经审核完毕
      data.form.courierId=data.user.id
      data.form.currentLevel=data.user.level
      data.ifDisable=false
    }

  })
}
load()

const submit=()=>{
  if(data.upgradeApply===null){
    request.post('/upgradeApply/add',data.form).then(res=>{
      if(res.code=='200'){
        data.formVisible=false
        ElMessage.success("操作成功")
        router.push('/student/home')
      } else{
        ElMessage.error(res.msg)
      }
    })
  } else{
    data.form.status='Pending'
    request.put('/upgradeApply/update',data.form).then(res=>{
      if(res.code==='200'){
        ElMessage.success("提交成功")
        router.push('/student/home')
      } else{
        ElMessage.error(res.msg)
      }
    })
  }

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
