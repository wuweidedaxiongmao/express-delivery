<template>
  <div>
    <div style="display: flex; grid-gap: 10px; margin-bottom: 10px;">
      <el-card style="flex: 1; text-align: center; padding: 20px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);">
        <div style="font-size: 36px; color: #409EFF; margin-bottom: 10px;">
          <el-icon><ShoppingTrolley /></el-icon> </div>
        <div style="font-size: 16px; color: #606266;">代取订单总数</div>
        <div style="font-size: 28px; font-weight: bold; color: #303133; margin-top: 5px;">{{data.orders}} <span style="font-size: 16px;">(件)</span></div>
      </el-card>

      <el-card style="flex: 1; text-align: center; padding: 20px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);">
        <div style="font-size: 36px; color: #67C23A; margin-bottom: 10px;">
          <el-icon><Money /></el-icon> </div>
        <div style="font-size: 16px; color: #606266;">新增代取员认证申请数</div>
        <div style="font-size: 28px; font-weight: bold; color: #303133; margin-top: 5px;">{{data.identifications}} <span style="font-size: 16px;">(人)</span></div>
      </el-card>

      <el-card style="flex: 1; text-align: center; padding: 20px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);">
        <div style="font-size: 36px; color: #E6A23C; margin-bottom: 10px;">
          <el-icon><Wallet /></el-icon> </div>
        <div style="font-size: 16px; color: #606266;">新增代取员等级提升申请数</div>
        <div style="font-size: 28px; font-weight: bold; color: #303133; margin-top: 5px;">{{ data.upgradeApplys }} <span style="font-size: 16px;">(个)</span></div>
      </el-card>

      <el-card style="flex: 1; text-align: center; padding: 20px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);">
        <div style="font-size: 36px; color: #F56C6C; margin-bottom: 10px;">
          <el-icon><User /></el-icon> </div>
        <div style="font-size: 16px; color: #606266;">新增代取员解封申请数</div>
        <div style="font-size: 28px; font-weight: bold; color: #303133; margin-top: 5px;">{{data.releaseApplys}} <span style="font-size: 16px;">(个)</span></div>
      </el-card>

      <el-card style="flex: 1; text-align: center; padding: 20px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);">
        <div style="font-size: 36px; color: #909399; margin-bottom: 10px;">
          <el-icon><Avatar /></el-icon> </div>
        <div style="font-size: 16px; color: #606266;">新增反馈数</div>
        <div style="font-size: 28px; font-weight: bold; color: #303133; margin-top: 5px;">{{ data.feedbacks }} <span style="font-size: 16px;">(条)</span></div>
      </el-card>
    </div>

    <div style="display: flex; grid-gap: 10px; margin-bottom: 10px;">
      <el-card id="pie" style="flex: 1;height: 400px;"></el-card>
      <el-card id="bar" style="flex: 1;height: 400px;"></el-card>
    </div>

    <el-card style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); padding: 0px;">
      <div class="quick-access-container">
        <div class="quick-access-title">
          <el-icon style="margin-right: 8px;"><Star /></el-icon> <span>快捷操作</span>
        </div>
        <div class="quick-access-buttons">
          <el-button type="primary" size="large" @click="router.push('/admin/identification')">
            <el-icon><UserFilled /></el-icon> 快递员认证申请
          </el-button>
<!--          <el-button type="success" size="large" @click="router.push('/admin/withdrawalApply')">-->
<!--            <el-icon><Money /></el-icon> 快递员提现申请-->
<!--          </el-button>-->
          <el-button type="warning" size="large" @click="router.push('/admin/releaseApply')">
            <el-icon><Unlock /></el-icon> 快递员解封申请
          </el-button>
          <el-button type="danger" size="large" @click="router.push('/admin/upgradeApply')">
            <el-icon><SetUp /></el-icon> 快递员等级提升申请
          </el-button>
          <el-button type="info" size="large" @click="router.push('/admin/feedback')">
            <el-icon><ChatDotRound /></el-icon> 反馈中心
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import {onMounted, reactive} from "vue";
import request from "../../utils/request";
import router from '../../router'; // 引入 router 实例，以便在模板中使用 router.push()

import {
  ShoppingTrolley, // 购物车图标
  Money,           // 金钱图标
  Wallet,          // 钱包图标
  User,            // 用户图标
  Avatar,          // 用户头像图标
  Star,            // 快捷操作标题图标
  UserFilled,      // 认证申请图标
  Unlock,          // 解封申请图标
  SetUp,           // 等级提升图标
  ChatDotRound     // 反馈中心图标
} from '@element-plus/icons-vue';

const data=reactive({
  orders:0,
  identifications:0,
  upgradeApplys:0,
  releaseApplys:0,
  feedbacks:0,
})

const loadData=()=>{
  request.get('/homeData').then(res=>{
    if(res.code==='200'){
      data.orders=res.data.orders
      data.identifications=res.data.identifications
      data.upgradeApplys=res.data.upgradeApplys
      data.releaseApplys=res.data.releaseApplys
      data.feedbacks=res.data.feedbacks
    }else{
      console.log('统计数据失败')
    }
  })
}

const pieOption = {
  title: {
    text: '订单状态统计图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};
const barOption = {
  title: {
    text: '学生与代取员数量柱状图',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'bar',
      itemStyle: {
        normal: { // normal 表示正常状态下的样式
          color: function(params) {
            // params.dataIndex 是当前数据项的索引
            // params.name 是当前数据项的名称，例如 '学生' 或 '代取员'
            if (params.name === '学生') {
              return '#5470C6'; // 蓝色系
            } else if (params.name === '代取员') {
              return '#91CC75'; // 绿色系
            }
            return '#ccc'; // 默认颜色，以防万一
          }
        }
      }
    }
  ]
};


// onMounted 表示页面的所有dom元素都初始化完成了
onMounted(()=>{
  //加载data
  loadData()
  // 基于准备好的dom，初始化echarts实例
  const pieChart = echarts.init(document.getElementById('pie'));
  const barChar=echarts.init(document.getElementById('bar'))
  request.get('/echarts/ordersStatus').then(res=>{
    if(res.code==='200'){
      pieOption.series[0].data=res.data
      pieChart.setOption(pieOption);
    }
  })
  request.get('/echarts/studentRole').then(res=>{
    if(res.code==='200'){
      barOption.xAxis.data=res.data.name
      barOption.series[0].data=res.data.count
      barChar.setOption(barOption)
    }
  })


})
</script>

<style scoped>
/* 确保快速入口按钮容器有合适的间距和布局 */
.quick-access-container {
  display: flex; /* 使用 flex 布局 */
  align-items: center; /* 垂直居中对齐 */
  flex-wrap: wrap; /* 允许按钮换行 */
  gap: 15px; /* 标题和按钮之间的间距，以及按钮之间的间距 */
  padding: 10px; /* 内部填充 */
}

.quick-access-title {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 18px;
  /* 确保标题不会被按钮挤压 */
  flex-shrink: 0;
  /* 标题右侧与第一个按钮的间距 */
  margin-right: 40px;
}

.quick-access-buttons {
  display: flex;
  flex-wrap: wrap; /* 允许按钮换行 */
  gap: 15px; /* 按钮之间的间距 */
  flex: 1; /* 按钮组占据剩余空间 */
}

/* 按钮内部图标和文字的间距 */
.quick-access-buttons .el-button .el-icon {
  margin-right: 5px;
}
</style>