<template>

  <div class="rank-wrapper">
    <!-- 左：评分榜 -->
    <el-card class="rank-card">
      <div class="rank-title">
        🏅 评分榜单
      </div>
      <div v-for="(item, index) in ratingData.slice(0,10)" :key="item.username" class="rank-item">
        <div class="rank-left">
          <div :class="['rank-name', rankColor(index)]">{{ item.username }}</div>
          <div :class="['rank-score', rankColor(index)]">评分: {{ item.rating }}(分)</div>
        </div>
        <div :class="['rank-right', rankColor(index)]">Top {{ index + 1 }}</div>
      </div>
    </el-card>

    <!-- 右：订单榜 -->
    <el-card class="rank-card">
      <div class="rank-title">
        💪 接单数榜单
      </div>
      <div v-for="(item, index) in orderCountData.slice(0,10)" :key="item.username" class="rank-item">
        <div class="rank-left">
          <div :class="['rank-name', rankColor(index)]">{{ item.username }}</div>
          <div :class="['rank-score', rankColor(index)]">接取单数: {{ item.orderCount }}(件)</div>
        </div>
        <div :class="['rank-right', rankColor(index)]">Top {{ index + 1 }}</div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

const ratingData = ref([])
const orderCountData = ref([])

const loadRating = () => {
  request.get('/rank/list', {
    params: {
      orderBy: 'rating',
      orderDir: 'desc'
    }
  }).then(res => {
    if (res.code === '200') {
      ratingData.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const loadOrderCount = () => {
  request.get('/rank/list', {
    params: {
      orderBy: 'order_count',
      orderDir: 'desc'
    }
  }).then(res => {
    if (res.code === '200') {
      orderCountData.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 排名样式
const rankColor = (index) => {
  if (index === 0) return 'first'
  if (index === 1) return 'second'
  if (index === 2) return 'third'
  return ''
}

loadRating()
loadOrderCount()
</script>

<style scoped>
.rank-wrapper {
  display: flex;
  gap: 24px;
  justify-content: center;
  padding: 20px;
}

.rank-card {
  flex: 1;
  padding: 20px;
}

.rank-title {
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}

.rank-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
  gap: 12px;
}

.rank-left {
  display: flex;
  flex-direction: column;
}

.rank-name {
  font-size: 16px;
  font-weight: bold;
}

.rank-score {
  font-size: 14px;
  margin-top: 2px;
}

.rank-right {
  font-size: 16px;
  font-weight: bold;
}

/* 前三名颜色 */
.first {
  color: #e74c3c; /* 红 */
}
.second {
  color: #f39c12; /* 橙 */
}
.third {
  color: #8e44ad; /* 紫 */
}
</style>
