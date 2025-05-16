<template>
  <div class="announcement-bar">
    <transition name="fade" mode="out-in">
      <div class="item" :key="current.id">
        {{ stripHtml(current.content) }}
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import axios from 'axios'

const announcements = ref([])
const current = ref({ id: 0, title: '', content: '' })
let index = 0
let timer = null

function stripHtml(html) {
  return html.replace(/<[^>]*>?/gm, '')
}

const fetchAnnouncements = async () => {
  try {
    const res = await axios.get('http://localhost:9091/announcement/latest')
    announcements.value = res.data
    if (announcements.value.length > 0) {
      current.value = announcements.value[0]
      startRotation()
    }
  } catch (e) {
    console.error('获取公告失败', e)
  }
}

const startRotation = () => {
  timer = setInterval(() => {
    index = (index + 1) % announcements.value.length
    current.value = announcements.value[index]
  }, 3500)
}

onMounted(fetchAnnouncements)
onBeforeUnmount(() => {
  clearInterval(timer)
})
</script>

<style scoped>
.announcement-bar {
  background-color: #81aee2;
  color: #333;
  padding: 10px 16px;
  min-height: 20px;
  display: flex;
  align-items: center;
  overflow: hidden;
}

.item {
  font-size: 16px;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 100%;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
