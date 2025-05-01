<template>
    <!-- 横幅广告 -->
<!--    <div class="banner">-->
<!--      <div class="banner-content">-->
<!--        <h1>随时快车 校园顺风车</h1>-->
<!--        <p>最快4分钟</p>-->
<!--      </div>-->
<!--      <div class="banner-image">-->
<!--        &lt;!&ndash; 背景图片，可以通过 CSS 引入 &ndash;&gt;-->
<!--      </div>-->
<!--    </div>-->
<!--  轮播图-->
  <div>
    <el-carousel height="650px" motion-blur>
      <el-carousel-item v-for="item in [img1,img2]" :key="item">
        <img :src="item" alt="" style="width: 100%;height: 800px"/>
      </el-carousel-item>
    </el-carousel>
  </div>
    <!-- 底部操作区域 -->
    <div class="action-bar">
      <el-row :gutter="20" justify="center">
        <el-col :span="6" class="action-item">
          <el-button circle type="info" size="large" @click="router.push('/front/order-query')">
            <el-icon><Search /></el-icon>
          </el-button>
          <p>订单查询</p>
        </el-col>
        <el-col :span="6" class="action-item">
          <el-button circle size="large" type="success" @click="router.push('/front/support-order')" >
            <el-icon><Bicycle /></el-icon>
          </el-button>
          <p>支持下单</p>
        </el-col>
        <el-col :span="6" class="action-item">
          <el-button circle size="large" @click="router.push('/front/join')" type="primary">
            <el-icon><Plus /></el-icon>
          </el-button>
          <p>加入代收员</p>
        </el-col>
        <el-col :span="6" class="action-item">
          <el-button circle size="large" @click="router.push('/front/withdraw')" type="warning">
            <el-icon><Wallet /></el-icon>
          </el-button>
          <p>提现页面</p>
        </el-col>
      </el-row>
    </div>
</template>

<script setup>
import router from "../../router";
import { ref, reactive } from 'vue';
import { ElHeader, ElRow, ElCol, ElMenu, ElMenuItem, ElButton, ElIcon, ElDropdown, ElDropdownMenu, ElDropdownItem, ElAvatar } from 'element-plus';
import { Search, Bicycle, Plus, Wallet, CaretBottom } from '@element-plus/icons-vue';
import img1 from '../../assets/imgs/login.jpg'
import img2 from '../../assets/imgs/img2.png'
// 路由实例

// 响应式数据
const data = reactive({
  // user: {
  //   id: null, // 模拟未登录状态
  //   name: '大圣搬',
  //   avatar: 'https://example.com/avatar.png'
  // }
  user:JSON.parse(localStorage.getItem('user'))
});

// 退出登录方法
const logout = () => {
  localStorage.removeItem('user')
  router.push('/login');
};
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
}

.header-left {
  flex: 0 0 auto;
}

.logo {
  height: 40px;
}

.header-center {
  flex: 1;
  display: flex;
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
  padding-top: 40px;
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