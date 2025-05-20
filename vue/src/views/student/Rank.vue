<template>
  <div>
    <el-card class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px;">
        <span style="margin-right: 10px;">排序字段：</span>
        <el-radio-group v-model="data.orderBy" @change="load">
          <el-radio-button label="rating">评分</el-radio-button>
          <el-radio-button label="order_count">订单数</el-radio-button>
        </el-radio-group>
      </div>
      <div>
        <span style="margin-right: 10px;">排序方式：</span>
        <el-radio-group v-model="data.orderDir" @change="load">
          <el-radio-button label="desc">降序</el-radio-button>
          <el-radio-button label="asc">升序</el-radio-button>
        </el-radio-group>
      </div>
    </el-card>

    <el-card>
      <el-table :data="data.tableData" stripe>
        <el-table-column type="index" label="排名" width="60" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="rating" label="评分" />
        <el-table-column prop="orderCount" label="订单数" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

const data = reactive({
  orderBy: 'rating',       // 默认按评分
  orderDir: 'desc',        // 默认降序
  tableData: []
})

const load = () => {
  request.get('/rank/list', {
    params: {
      orderBy: data.orderBy,
      orderDir: data.orderDir
    }
  }).then(res => {
    if (res.code==='200') {
      data.tableData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

load() // 初始化加载
</script>
