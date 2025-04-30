<template>
  <div>
    <!--      头部-->
    <div style="height: 60px; display: flex;align-items: center">
      <div style="width: 300px; display: flex; align-items: center;margin-left: 15px">
        <img style="width: 40px" src="../../assets/Panda.png"/>
        <span style=" font-size: 20px">智能快递代取管理系统</span>
      </div>
      <div style="flex: 1"></div>
      <div style="width: fit-content;display: flex;align-items: center">
        <img :src="data.user.avatar || defaultUrl" style="width: 40px;height: 40px; border-radius: 50%"/>
        <span style="margin-left: 7px;margin-right: 10px">{{data.user.name}}</span>
      </div>

    </div>

    <!--      footer-->
    <div style="display: flex">
      <!--      left-->
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu style="border: 0" router :default-active="router.currentRoute.value.path" :default-openeds="['1']">
          <!--          default-openeds="['1']-->
          <el-menu-item index="/manger/home">
            <el-icon><House /></el-icon>系统首页
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><Document /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/admin/expressType">代取类型信息</el-menu-item>
            <el-menu-item index="/admin">代取订单信息</el-menu-item>
            <el-menu-item index="/admin">快递员认证申请</el-menu-item>
            <el-menu-item index="/admin">快递员提现申请</el-menu-item>
            <el-menu-item index="/admin">快递员解封申</el-menu-item>
            <el-menu-item index="/admin">快递员提升等级申请</el-menu-item>
            <el-menu-item index="/admin">快递资源信息</el-menu-item>
            <el-menu-item index="/admin">反馈信息</el-menu-item>
            <el-menu-item index="/admin">公告信息</el-menu-item>
            <el-menu-item index="/admin">用户冲单信息</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户信息</span>
            </template>
            <el-menu-item index="/admin/student">学生信息</el-menu-item>
            <el-menu-item index="/manger/employee">代取员信息</el-menu-item>
            <el-menu-item index="/manger/employee">管理员信息</el-menu-item>
          </el-sub-menu>

        </el-menu>
      </div>
      <!--      right-->
      <div style="flex: 1; width: 0; background-color: #e6e6ef; padding: 10px">
        <RouterView @updateUser="updateUser"/>
      </div>
    </div>

  </div>
</template>

<script setup>
import router from '../../router';
import {reactive} from "vue";

const data=reactive({
  user:JSON.parse(localStorage.getItem('user'))
})

const logout=()=>{
  localStorage.removeItem('user')
  location.href = '/login'
}

const defaultUrl="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"

const updateUser=()=>{
  data.user=JSON.parse(localStorage.getItem('user'))
}
</script>

<style>
.el-menu .is-active {
  background-color: #e6ecf7;
}
</style>