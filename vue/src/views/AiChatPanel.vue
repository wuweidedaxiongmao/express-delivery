<template>
  <el-config-provider>
    <el-card class="ai-assistant-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>AI 助手 (Ollama)</span>
        </div>
      </template>

      <el-radio-group v-model="selectedEndpoint" class="endpoint-selector">
        <el-radio-button label="v1">API v1 (完整响应)</el-radio-button>
        <el-radio-button label="v2">API v2 (流式响应)</el-radio-button>
      </el-radio-group>

      <div class="chat-window-wrapper">
        <el-scrollbar ref="scrollbarRef" always>
          <div ref="chatInnerRef" class="chat-inner-content">
            <div
                v-for="message in chatHistory"
                :key="message.id"
                :class="['message-row', message.sender]"
            >
              <el-avatar :style="{ backgroundColor: message.sender === 'user' ? '#409EFF' : '#67C23A' }">
                {{ message.sender === 'user' ? '你' : 'AI' }}
              </el-avatar>
              <div class="message-content-wrapper">
                <!-- 显示 <think> 内容（可折叠） -->
                <div v-if="message.thinkContent" class="think-container">
                  <button class="toggle-think" @click="toggleThink(message.id)">
                    {{ expandStates.get(message.id) ? '[折叠]' : '[展开]' }}
                  </button>
                  <div class="think-content" :class="{ 'expanded': expandStates.get(message.id) }">
                    <span v-html="formatMessage(message.thinkContent)"></span>
                  </div>
                </div>
                <!-- 显示实际回复内容 -->
                <div class="text-content" v-html="formatMessage(message.content)"></div>
              </div>
            </div>
            <div v-if="isLoading && streamingPlaceholder" class="message-row ai">
              <el-avatar :style="{ backgroundColor: '#67C23A' }">AI</el-avatar>
              <div class="message-content-wrapper">
                <div class="text-content"><em>{{ streamingPlaceholder }}</em></div>
              </div>
            </div>
          </div>
        </el-scrollbar>
      </div>

      <div class="input-area">
        <el-input
            v-model="userInput"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 5 }"
            placeholder="输入你的消息然后按 Enter 发送 (Shift+Enter 换行)"
            @keydown.enter.exact.prevent="sendMessage"
            :disabled="isLoading"
            resize="none"
        ></el-input>
        <el-button
            type="primary"
            @click="sendMessage"
            :loading="isLoading"
            :disabled="!userInput.trim()"
            class="send-button"
        >
          {{ isLoading ? '发送中...' : '发送' }}
        </el-button>
      </div>
    </el-card>
  </el-config-provider>
</template>

<script setup>
import { ref, nextTick, watch } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

const userInput = ref('');
const chatHistory = ref([]);
const isLoading = ref(false);
const selectedEndpoint = ref('v2');
const scrollbarRef = ref(null);
const chatInnerRef = ref(null);

let messageIdCounter = 0;
let eventSource = null;
const streamingPlaceholder = ref('');
const BASE_URL = 'http://localhost:9091';

// 存储每个消息的展开状态
const expandStates = ref(new Map());

const scrollToBottom = async () => {
  await nextTick();
  if (scrollbarRef.value && chatInnerRef.value) {
    const scrollContainer = scrollbarRef.value.$el.querySelector('.el-scrollbar__wrap');
    if (scrollContainer) {
      scrollContainer.scrollTop = scrollContainer.scrollHeight;
    }
  }
};

watch(chatHistory, scrollToBottom, { deep: true });
watch(() => chatHistory.value.length, scrollToBottom);

const escapeHtml = (unsafe) => {
  return unsafe
      .replace(/&/g, '&')
      .replace(/</g, '<')
      .replace(/>/g, '>');
};

const formatMessage = (content) => {
  if (typeof content !== 'string') return '';
  return escapeHtml(content)
      .replace(/ /g, ' ')
      .replace(/\n/g, '<br>');
};

// 分隔 <think> 内容和实际回复内容的函数
const splitThinkContent = (response) => {
  const thinkMatch = response.match(/<think>([\s\S]*?)<\/think>/);
  let thinkContent = '';
  let mainContent = response;

  if (thinkMatch) {
    thinkContent = thinkMatch[1]; // <think> 标签内的内容
    mainContent = response.replace(/<think>[\s\S]*?<\/think>/, '').trim(); // 移除 <think> 标签及其内容
  }

  return { thinkContent, mainContent };
};

// 切换展开/折叠状态
const toggleThink = (messageId) => {
  expandStates.value.set(messageId, !expandStates.value.get(messageId));
};

const sendMessage = async () => {
  if (!userInput.value.trim()) return;

  const userMessageContent = userInput.value;
  chatHistory.value.push({
    id: messageIdCounter++,
    sender: 'user',
    content: userMessageContent,
  });
  const currentInput = userInput.value;
  userInput.value = '';
  isLoading.value = true;
  streamingPlaceholder.value = selectedEndpoint.value === 'v2' ? 'AI 正在思考...' : '';

  if (eventSource) {
    eventSource.close();
    eventSource = null;
  }

  const currentAiMessageId = messageIdCounter++;

  try {
    const str = '你是一个智能代取快递系统的AI助手，请你用友好的语气回答下面的问题。问题：';
    const encodedMsg = encodeURIComponent(str + currentInput);

    if (selectedEndpoint.value === 'v1') {
      const v1Url = `${BASE_URL}/ollama/chat/v1?msg=${encodedMsg}`;
      try {
        const response = await axios.get(v1Url);
        const aiText = response.data;

        // 分隔 <think> 内容和实际回复
        const { thinkContent, mainContent } = splitThinkContent(aiText);

        chatHistory.value.push({
          id: currentAiMessageId,
          sender: 'ai',
          content: mainContent || '（无回复内容）',
          thinkContent: thinkContent || '',
        });
      } catch (error) {
        console.error('API v1 调用失败:', error);
        ElMessage.error('V1接口调用失败。请查看控制台。');
        chatHistory.value.push({
          id: currentAiMessageId,
          sender: 'ai',
          content: 'V1接口接收回复错误。请查看控制台。',
        });
      } finally {
        isLoading.value = false;
        streamingPlaceholder.value = '';
      }
    } else {
      const aiMessage = {
        id: currentAiMessageId,
        sender: 'ai',
        content: '',
        thinkContent: '',
      };
      chatHistory.value.push(aiMessage);
      streamingPlaceholder.value = '';

      const eventSourceUrl = `${BASE_URL}/ollama/chat/v2?msg=${encodedMsg}`;
      eventSource = new EventSource(eventSourceUrl);

      let accumulatedContent = '';

      eventSource.onopen = () => {
        console.log('SSE 连接已为 /ollama/chat/v2 打开');
      };

      eventSource.onmessage = (event) => {
        const currentAiMsgObj = chatHistory.value.find(m => m.id === currentAiMessageId);
        if (currentAiMsgObj) {
          accumulatedContent += event.data;
          const { thinkContent, mainContent } = splitThinkContent(accumulatedContent);
          currentAiMsgObj.content = mainContent || '（无回复内容）';
          currentAiMsgObj.thinkContent = thinkContent || '';
        }
      };

      eventSource.onerror = (error) => {
        console.log('EventSource 发生错误或连接关闭:', error);
        const currentAiMsgObj = chatHistory.value.find(m => m.id === currentAiMessageId);

        if (currentAiMsgObj && currentAiMsgObj.content.length > 0) {
          console.log('EventSource 流在接收到内容后终止（可能为正常结束）。');
        } else {
          ElMessage.error('流式传输连接失败或在开始时中断。');
          if (currentAiMsgObj) {
            currentAiMsgObj.content = '流式传输连接失败，未能获取回复。';
          } else {
            chatHistory.value.push({
              id: messageIdCounter++,
              sender: 'ai',
              content: '请求处理失败（未能建立流连接）。',
            });
          }
        }

        if (eventSource) eventSource.close();
        eventSource = null;
        isLoading.value = false;
        streamingPlaceholder.value = '';
      };
    }
  } catch (error) {
    console.error('发送消息失败:', error);
    ElMessage.error('发送消息时发生意外错误。');
    chatHistory.value.push({
      id: currentAiMessageId,
      sender: 'ai',
      content: '处理您的请求时出错。',
    });
    isLoading.value = false;
    streamingPlaceholder.value = '';
  }
};
</script>

<style scoped>
.ai-assistant-card {
  max-width: 750px;
  margin: 25px auto;
  display: flex;
  flex-direction: column;
  height: calc(100vh - 50px);
  min-height: 500px;
  box-sizing: border-box;
}

.el-card {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.el-card__body {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 20px;
}

.card-header {
  text-align: center;
  font-size: 1.2em;
  font-weight: bold;
}

.endpoint-selector {
  margin-bottom: 15px;
  display: flex;
  justify-content: center;
}

.chat-window-wrapper {
  flex-grow: 1;
  min-height: 0;
  overflow: hidden;
}

.chat-window-wrapper .el-scrollbar {
  height: 100%;
  overflow: auto;
}

.chat-window-wrapper .el-scrollbar__wrap {
  overflow-y: auto;
  max-height: 100%;
}

.chat-inner-content {
  padding: 10px 15px;
}

.message-row {
  display: flex;
  margin-bottom: 15px;
  align-items: flex-start;
}

.message-row.user {
  flex-direction: row-reverse;
}

.message-row .el-avatar {
  flex-shrink: 0;
}

.message-content-wrapper {
  margin: 0 10px;
  max-width: calc(100% - 70px);
  word-break: break-word;
  overflow-wrap: break-word;
}

.text-content {
  padding: 2px 12px 8px;
  border-radius: 6px;
  background-color: var(--el-color-info-light-9);
  color: var(--el-text-color-primary);
  line-height: 1.6;
  font-size: 14px;
  white-space: normal;
  box-sizing: border-box;
}

.message-row.user .text-content {
  background-color: var(--el-color-primary);
  color: white;
}

.message-row.ai .text-content em {
  color: var(--el-text-color-secondary);
}

.input-area {
  display: flex;
  gap: 10px;
  align-items: flex-end;
}

.input-area .el-textarea {
  flex-grow: 1;
}

.send-button {
  min-height: var(--el-component-size);
}

/* 折叠样式 */
.think-container {
  margin-top: 5px;
}

.toggle-think {
  background: none;
  border: none;
  color: #409EFF;
  cursor: pointer;
  padding: 0;
  font-size: 12px;
}

.toggle-think:hover {
  text-decoration: underline;
}

.think-content {
  display: none;
  padding: 5px 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
  margin-top: 5px;
}

.think-content.expanded {
  display: block;
}
</style>