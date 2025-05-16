<template>
  <div>
    <!-- 圆形悬浮按钮 -->
    <div class="ai-floating-button" @click="dialogVisible = true">
      🤖
    </div>

    <!-- AI 对话框弹窗 -->
    <el-dialog
        v-model="dialogVisible"
        width="800px"
        :modal="false"
        :append-to-body="true"
        :lock-scroll="false"
        class="ai-dialog"
    >
      <template #header>
        <div class="ai-dialog-header">
          <span>AI 助手</span>
          <el-button text size="small" @click="dialogVisible = false">关闭</el-button>
        </div>
      </template>

      <!-- 这里嵌入你原来的 AI 聊天组件 -->
      <AiChatPanel :prompt="props.prompt"/>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import AiChatPanel from './AiChatPanel.vue'; // 你的聊天面板组件
const dialogVisible = ref(false);
const props=defineProps({
  prompt: {
    type: String,
  },
});
</script>

<style scoped>
html, body {
  overflow: hidden;
  height: 100%;
}

.ai-floating-button {
  position: fixed;
  bottom: 30px;
  left: 30px;
  width: 60px;
  height: 60px;
  background-color: #409EFF;
  color: white;
  border-radius: 50%;
  box-shadow: 0 4px 10px rgba(0,0,0,0.2);
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  font-size: 28px;
  z-index: 9999;
  transition: transform 0.2s ease;
}

.ai-floating-button:hover {
  transform: scale(1.1);
}

/* 调整 el-dialog 的位置到左下角 */
.ai-dialog {
  position: fixed;
  bottom: 30px; /* 距离底部 30px，与悬浮按钮对齐 */
  left: 30px; /* 距离左侧 30px，与悬浮按钮对齐 */
  top: auto; /* 移除默认的 top 居中 */
  transform: none; /* 移除默认的 transform 居中 */
  margin: 0; /* 移除默认的 margin 居中 */
}

/* 调整对话框的尺寸 */
.ai-dialog ::v-deep(.el-dialog) {
  width: 800px; /* 保持宽度 */
  max-height: 80vh; /* 最大高度，避免超出视口 */
  height: auto; /* 高度自适应内容 */
  margin: 0; /* 移除默认的 margin */
}

/* 对话框内容区域 */
.ai-dialog ::v-deep(.el-dialog__body) {
  padding: 0;
  max-height: 70vh; /* 内容区域最大高度，留出头部空间 */
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

/* 对话框头部 */
.ai-dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}
</style>