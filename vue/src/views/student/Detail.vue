<template>
  <div class="detail-container">
    <!-- 返回按钮 -->
    <el-button type="text" :icon="ArrowLeft" @click="router.back()" class="back-btn">
      返回资讯列表
    </el-button>

    <!-- 加载状态 -->
    <el-skeleton :rows="5" animated v-if="loading" />

    <!-- 错误提示 -->
    <el-empty description="资讯加载失败" v-if="error" />

    <!-- 资讯内容 -->
    <template v-if="detail.id && !loading">
      <!-- 资讯标题 -->
      <h1 class="detail-title">{{ detail.title }}</h1>

      <!-- 资讯元信息 -->
      <div class="detail-meta">
        <span>发布时间：{{ formatDate(detail.createdAtStr) }}</span>
        <span v-if="detail.updatedAt">最后更新：{{ formatDate(detail.updatedAtStr) }}</span>
      </div>

      <!-- 资讯封面图片 -->
      <el-image
          v-if="detail.img"
          :src="detail.img"
          class="detail-image"
          :preview-src-list="[detail.img]"
          fit="cover"
      />

      <!-- 资讯内容 -->
      <div class="detail-content" v-html="detail.content"></div>

    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ArrowLeft } from '@element-plus/icons-vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const detail = ref({})
const relatedNews = ref([])
const loading = ref(true)
const error = ref(false)

// 日期格式化
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 获取资讯详情
const fetchDetail = async () => {
  console.log('hello')
  try {
    loading.value = true
    error.value = false

    // 调用你提供的后端接口
    const res = await request.get(`/information/selectById/${route.params.id}`)

    if (res.code === '200') {  // 假设Result.success返回的code是200
      detail.value = res.data
    } else {
      throw new Error(res.message || '获取资讯详情失败')
    }
  } catch (err) {
    console.error('获取资讯详情失败:', err)
    error.value = true
    ElMessage.error(err.message || '获取资讯详情失败')
  } finally {
    loading.value = false
  }
}

fetchDetail()
</script>

<style scoped>
.detail-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.back-btn {
  margin-bottom: 20px;
}

.detail-title {
  font-size: 28px;
  margin: 20px 0;
  text-align: center;
  color: #333;
  line-height: 1.4;
}

.detail-meta {
  display: flex;
  justify-content: center;
  gap: 30px;
  color: #666;
  margin-bottom: 30px;
  font-size: 14px;
}

.detail-image {
  width: 100%;
  max-height: 500px;
  margin-bottom: 30px;
  border-radius: 4px;
}

.detail-content {
  line-height: 1.8;
  font-size: 16px;
  color: #333;
}

/*.detail-content :deep(p) {*/
/*  margin-bottom: 1.5em;*/
/*}*/

/*.detail-content :deep(img) {*/
/*  max-width: 100%;*/
/*  height: auto;*/
/*  display: block;*/
/*  margin: 20px auto;*/
/*  border-radius: 4px;*/
/*}*/

.related-news {
  margin-top: 50px;
  border-top: 1px solid #eee;
  padding-top: 30px;
}

.related-news h3 {
  font-size: 20px;
  margin-bottom: 20px;
  color: #333;
}

.related-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 15px;
}

.related-item {
  cursor: pointer;
  transition: all 0.3s;
}

.related-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.related-title {
  font-size: 16px;
  margin-bottom: 8px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.related-date {
  font-size: 12px;
  color: #999;
}
</style>