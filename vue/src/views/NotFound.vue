<template>
  <div class="not-found-container">
    <div class="error-graphic">
      <svg width="180" height="180" viewBox="0 0 200 200" fill="none">
        <path d="M100 0L0 100L100 200L200 100L100 0Z" fill="#FFF0F0"/>
        <path d="M50 50L150 150M50 150L150 50" stroke="#F87171" stroke-width="6" stroke-linecap="round"/>
      </svg>
    </div>
    <div class="error-code">404</div>
    <div class="error-message">哎呀！页面找不到了</div>
    <div class="error-description">
      您访问的 <span class="error-path">{{ currentPath }}</span> 不存在<br>
      可能是地址输入错误或页面已被移除。
    </div>
    <div class="action-buttons">
      <el-button type="primary" @click="goHome" class="home-button animated-button">
        <el-icon><HomeFilled /></el-icon> 返回首页
      </el-button>
      <el-button @click="goBack" class="back-button animated-button">
        <el-icon><ArrowLeft /></el-icon> 返回上一页
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { HomeFilled, ArrowLeft } from '@element-plus/icons-vue'; // 移除 Search icon

const router = useRouter();
const route = useRoute();

const currentPath = computed(() => route.path);

const goHome = () => {
  if(route.path.includes('student')){
    router.push('/student/home');
    return;
  }
  if((route).path.includes('admin')){
    router.push('/admin/home');
    return;
  }
  router.push('/login');
};

const goBack = () => {
  router.go(-1);
};

// 搜索相关的逻辑已移除
</script>

<style scoped>
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

.not-found-container {
  box-sizing: border-box;
  display: flex;
  flex-direction: column; /* 子元素垂直排列 */
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f3e7e9 0%, #e3eeff 100%);
  padding: 20px;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
  text-align: center; /* 内部文本内容居中 */
  animation: fadeIn 0.8s ease-out forwards; /* 容器级别应用淡入动画 */
}

/* .not-found-content 相关的样式被移除或调整 */

.error-graphic {
  margin-bottom: 25px;
  /* animation: fadeIn 0.8s ease-out 0.2s forwards; /* 延迟动画，如果需要单独控制 */
}

.error-graphic svg {
  animation: pulse 2.5s infinite ease-in-out;
  filter: drop-shadow(0 4px 8px rgba(248, 113, 113, 0.3));
}

.error-code {
  font-size: 120px; /* 稍微增大字号以适应更开放的布局 */
  font-weight: 700;
  color: #f56c6c;
  line-height: 1;
  margin-bottom: 15px;
  text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.1); /* 阴影调整 */
  /* animation: fadeIn 0.8s ease-out 0.4s forwards; */
}

.error-message {
  font-size: 32px; /* 稍微增大字号 */
  color: #2c3e50;
  margin-bottom: 25px;
  font-weight: 600;
  /* animation: fadeIn 0.8s ease-out 0.6s forwards; */
}

.error-description {
  font-size: 18px; /* 稍微增大字号 */
  color: #555;
  margin-bottom: 40px; /* 调整底部间距，因为搜索框被移除 */
  line-height: 1.7;
  max-width: 500px; /* 限制描述文本的最大宽度，避免过长 */
  margin-left: auto;  /* 水平居中（如果 max-width 生效） */
  margin-right: auto; /* 水平居中 */
  /* animation: fadeIn 0.8s ease-out 0.8s forwards; */
}

.error-path {
  font-family: 'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, Courier, monospace;
  background: rgba(255, 255, 255, 0.3); /* 调整背景使其在渐变背景上更和谐 */
  color: #c73f3f; /* 深一点的红色 */
  padding: 5px 10px;
  border-radius: 6px;
  font-weight: 500;
  border: 1px solid rgba(0,0,0,0.05);
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 40px; /* 调整底部间距，因为搜索框被移除 */
  /* animation: fadeIn 0.8s ease-out 1s forwards; */
}

.animated-button {
  padding: 14px 32px; /* 按钮稍微增大 */
  font-size: 17px;   /* 按钮文字稍微增大 */
  border-radius: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 阴影调整 */
}

.animated-button:hover {
  transform: translateY(-3px) scale(1.03); /* 悬停效果增强 */
  box-shadow: 0 7px 15px rgba(0, 0, 0, 0.18);
}

.home-button {
  /* el-button type="primary" 的样式を継承 */
}

.back-button {
  /* el-button のデフォルトスタイルを継承 */
}

/* 搜索框相关的样式已移除 */


/* レスポンシブ対応 */
@media (max-width: 768px) {
  .error-code {
    font-size: 100px;
  }

  .error-message {
    font-size: 26px;
  }

  .error-description {
    font-size: 16px;
    max-width: 90%;
  }
  .action-buttons {
    gap: 15px;
  }
  .animated-button {
    padding: 12px 25px;
    font-size: 16px;
  }
}

@media (max-width: 480px) {
  .error-graphic svg {
    width: 120px; /* 移动设备上图形可以更小 */
    height: 120px;
  }
  .error-code {
    font-size: 70px;
  }
  .error-message {
    font-size: 22px;
  }
  .error-description {
    font-size: 15px;
  }
  .action-buttons {
    flex-direction: column; /* 移动端按钮垂直排列 */
    gap: 15px;
    width: 80%; /* 按钮容器宽度 */
    max-width: 300px;
  }
  .animated-button {
    width: 100%; /* 按钮宽度充满其容器 */
    padding: 14px 20px;
  }
}
</style>