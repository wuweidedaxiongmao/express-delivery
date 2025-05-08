<template>
  <div class="information-container">
    <!-- 搜索区域 -->
    <div class="search-container" style=" display: flex;justify-content: center;">
<!--      <el-input-->
<!--          v-model="data.title"-->
<!--          placeholder="请输入资讯标题"-->
<!--          clearable-->
<!--          style="width: 300px"-->
<!--          @keyup.enter="load"-->
<!--      />-->
<!--      <el-button type="primary" :icon="Search" @click="load">搜索</el-button>-->
<!--      <el-button @click="reset">重置</el-button>-->
      <el-input style="margin-right: 5px; width: 240px" v-model="data.title" placeholder="请输入资讯标题查询" prefix-icon="Search"></el-input>
      <el-button type="primary" plain @click="load()">查询</el-button>
      <el-button style="margin-left: 20px" type="primary" plain @click="reset()">重置</el-button>
    </div>

    <!-- 资讯列表 -->
    <div class="information-list">
      <el-card
          v-for="item in data.tableData"
          :key="item.id"
          class="information-item"
          shadow="hover"
      >
        <div class="item-header">
          <h1 class="item-title" style="margin: 0 auto;">{{ item.title }}</h1>
          <span class="item-date">{{ item.createdAtStr}}</span>
        </div>

        <div class="item-content">
          <el-image
              v-if="item.img"
              :src="item.img"
              class="item-image"
              fit="cover"
          />
          <div class="item-text" v-html="item.content"></div>
        </div>

        <div class="item-footer">
          <el-button type="primary" text @click="viewDetail(item.id)">查看详情</el-button>
        </div>
      </el-card>
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
          v-model:current-page="data.pageNumber"
          v-model:page-size="data.pageSize"
          :page-sizes="[1, 3, 5, 7]"
          :total="data.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="load"
          @current-change="load"
      />
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { Search } from "@element-plus/icons-vue";
import request from "@/utils/request";
import { useRouter } from "vue-router";

const router = useRouter();
const data = reactive({
  user: JSON.parse(localStorage.getItem("user")),
  tableData: [],
  pageNumber: 1,
  pageSize: 5,
  total: 0,
  title: null,
});

// 加载资讯列表
const load = () => {
  request
      .get("/information/selectPage", {
        params: {
          pageNum: data.pageNumber,
          pageSize: data.pageSize,
          title: data.title,
        },
      })
      .then((res) => {
        data.tableData = res.data.list;
        data.total = res.data.total;
      });
};

// 重置搜索条件
const reset = () => {
  data.title = null;
  load();
};

// 查看详情
const viewDetail = (id) => {
  router.push(`/student/information/detail/${id}`);
};

// 初始化加载数据
onMounted(() => {
  load();
});
</script>

<style scoped>
.information-container {
  padding: 20px;
}

.search-container {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.information-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.information-item {
  cursor: pointer;
  transition: all 0.3s;
}

.information-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.item-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.item-title {
  font-size: 20px;
  color: #333;
}

.item-date {
  font-size: 12px;
  color: #999;
}

.item-content {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.item-image {
  width: 100%;
  height: 250px;
  border-radius: 4px;
}

.item-text {
  color: #666;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item-footer {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>