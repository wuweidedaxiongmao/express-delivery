<template>
  <div class="page-container">
    <!-- 顶部导航 -->
    <el-header class="header">
      <!-- 左侧 logo -->
      <div class="header-left">
        <img src="@/assets/Panda.png" alt="Logo" class="logo" />
      </div>

      <!-- 中间菜单导航 -->
      <div class="header-center">
        <el-menu
            :default-active="router.currentRoute.value.path"
            router
            mode="horizontal"
            class="nav-menu"
            style="background-color: #81aee2;font-weight: bold; height: 80px;margin-left: 10px"
        >
          <el-menu-item index="/student/home">首页</el-menu-item>
          <el-menu-item index="/student/orders">代收订单</el-menu-item>
          <el-menu-item v-if="ifCourier" index="/student/delivery">派送中订单</el-menu-item>
          <el-menu-item v-if="ifCourier" index="/student/acceptOrder">我要接单</el-menu-item>
          <el-menu-item index="/student/rank">拼行榜</el-menu-item>
          <el-menu-item index="/student/news">校园资讯</el-menu-item>
          <el-menu-item index="/student/logistics">后勤快讯</el-menu-item>
          <el-menu-item index="/student/person">个人中心</el-menu-item>
        </el-menu>
      </div>

      <!-- 右侧用户区域 -->
      <div class="header-right">
        <el-dropdown v-if="data.user.id" trigger="click">
          <div class="user-info">
            <el-avatar :src="data.user.avatar" :size="30" />
            <span>{{ data.user.name }}</span>
            <el-icon><CaretBottom /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/student/person')">个人中心</el-dropdown-item>
              <el-dropdown-item @click="router.push('/student/address')">地址管理</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <div v-else>
          <el-button type="primary" @click="router.push('/login')"
          >登录</el-button
          >
          <el-button @click="router.push('/register')">注册</el-button>
        </div>
      </div>
    </el-header>

    <!-- 横幅广告 -->

    <!-- 底部操作区域 -->
    <div>
      <RouterView @updateUser="updateUser"/>
    </div>
  </div>
</template>

<script setup>
import router from "../../router";
import { ref, reactive } from 'vue';
import { ElHeader, ElRow, ElCol, ElMenu, ElMenuItem, ElButton, ElIcon, ElDropdown, ElDropdownMenu, ElDropdownItem, ElAvatar } from 'element-plus';
import { Search, Bicycle, Plus, Wallet, CaretBottom } from '@element-plus/icons-vue';

// 路由实例

// 响应式数据
const data = reactive({
  // user: {
  //   id: null, // 模拟未登录状态
  //   name: '大圣搬',
  //   avatar: 'https://example.com/avatar.png'
  // }
  user:JSON.parse(localStorage.getItem('user')),
});

const ifCourier=data.user.role==='COUR';

// 退出登录方法
const logout = () => {
  // localStorage.removeItem('user')
  // router.push('/login');
  localStorage.removeItem('user')
  location.href = '/login'
};

const updateUser=()=>{
  data.user=JSON.parse(localStorage.getItem('user'))
}
if(data.user===null||data.user.role===null){
  location.href="/login"
}
if(data.user.role!=='STU'&&data.user.role!='COUR'){
  location.href='/login'
}
</script>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background-color: #81aee2;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: 80px;
}

.header-left {
  flex: 0 0 auto;
}

.logo {
  height: 40px;
}

.header-center {
  flex: 1;
  /*display: flex;*/
  /*justify-content: center;*/
}

.nav-menu {
  border-bottom: none;
}

.header-right {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-info span {
  margin: 0 8px;
}

.banner {
  position: relative;
  height: 700px;
  background: url('src/assets/imgs/login.jpg') no-repeat center center;
  background-size: 100% auto;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding-left: 50px;
}

.banner-content h1 {
  font-size: 48px;
  font-weight: bold;
  color: #ff4d4f;
  margin-bottom: 10px;
}

.banner-content p {
  font-size: 24px;
  color: #fff;
}

.action-bar {
  position: fixed;
  bottom: 0;
  width: 100%;
  background-color: #ff4d4f;
  padding: 10px 0;
  text-align: center;
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #fff;
  max-width: 8%;
}

.action-item p {
  margin-top: 5px;
  font-size: 15px;
  color: black;
}
</style>