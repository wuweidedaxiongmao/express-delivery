<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.studentName" placeholder="请输入学生姓名查询" prefix-icon="Search"></el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-input style="margin-right: 5px; width: 240px; margin-left: 10px" v-model="data.pickupAddress" placeholder="请输入代取快递地址查询" prefix-icon="Search"></el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button style="margin-left: 20px" type="primary" plain @click="reset()">重置</el-button>
      </el-card>

<!--      <el-card class="card">-->
<!--&lt;!&ndash;        <el-button type="primary" plain @click="handleAdd()">新增</el-button>&ndash;&gt;-->
<!--        <el-button type="primary" plain @click="batchDelete">批量删除</el-button>-->
<!--        &lt;!&ndash;        <el-button type="primary" plain>upload</el-button>&ndash;&gt;-->
<!--        &lt;!&ndash;        <el-button type="primary" plain>download</el-button>&ndash;&gt;-->
<!--      </el-card>-->

      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
          <el-table-column type="expand">
            <template #default="scope">
              <h2>{{scope.row.id}}号 订单详细信息</h2>
              <el-descriptions border label-width="200px">
                <el-descriptions-item label="学生ID">{{scope.row.studentId}}</el-descriptions-item>
                <el-descriptions-item label="学生姓名">{{scope.row.studentName}}</el-descriptions-item>
                <el-descriptions-item label="学生用户名">{{scope.row.studentUsername}}</el-descriptions-item>
                <el-descriptions-item label="代取员ID">{{scope.row.courierId}}</el-descriptions-item>
                <el-descriptions-item label="代取员姓名">{{scope.row.courierName}}</el-descriptions-item>
                <el-descriptions-item label="代取员用户名">{{scope.row.courierUsername}}</el-descriptions-item>
                <el-descriptions-item label="代取类型ID">{{scope.row.typeId}}</el-descriptions-item>
                <el-descriptions-item label="代取类型">类型:{{scope.row.typeName}},价格:{{scope.row.price}}元</el-descriptions-item>
                <el-descriptions-item label="取快递地址">{{scope.row.pickupAddress}}</el-descriptions-item>
                <el-descriptions-item label="快递送达地址">{{scope.row.address}}</el-descriptions-item>
                <el-descriptions-item label="快递位置图片">
                  <el-image v-if="scope.row.image" :src="scope.row.image" style="width: 80px" :preview-src-list="[scope.row.image]" preview-teleported/>
                </el-descriptions-item>
                <el-descriptions-item label="快递描述">{{scope.row.description}}</el-descriptions-item>
                <el-descriptions-item label="快递状态">{{scope.row.status}}</el-descriptions-item>
                <el-descriptions-item label="创建时间">{{scope.row.createTime}}</el-descriptions-item>
                <el-descriptions-item label="接收时间">{{scope.row.accessTime}}</el-descriptions-item>
                <el-descriptions-item label="快递送达时间">{{scope.row.deliveryTime}}</el-descriptions-item>
                <el-descriptions-item label="签收时间">{{scope.row.signTime}}</el-descriptions-item>
                <el-descriptions-item label="评分">{{scope.row.rating}}</el-descriptions-item>
                <el-descriptions-item label="反馈">{{scope.row.feedback}}</el-descriptions-item>
              </el-descriptions>
            </template>
          </el-table-column>
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="订单号"/>
          <el-table-column prop="studentName" label="学生姓名" show-overflow-tooltip="true"/>
          <el-table-column prop="courierName" label="代取员姓名" show-overflow-tooltip="true"/>
          <el-table-column prop="typeName" label="快递种类" show-overflow-tooltip="true"/>
          <el-table-column prop="pickupAddress" label="取快递地址" show-overflow-tooltip="true"/>
          <el-table-column prop="address" label="快递送达地址" show-overflow-tooltip="true"/>
          <el-table-column label="快递号图片">
            <template #default="scope">
              <el-image v-if="scope.row.image" :src="scope.row.image" style="width: 80px" :preview-src-list="[scope.row.image]" preview-teleported/>
            </template>
          </el-table-column>
<!--          <el-table-column prop="status" label="快递状态" show-overflow-tooltip="true"/>-->
          <el-table-column label="操作">
            <template #default="scope">
<!--              <el-button link type="primary" size="small" :icon="Delete" @click="del(scope.row.id)">删除</el-button>-->
              <el-button link type="primary" size="large" @click="accept(scope.row)">接单</el-button>
            </template>
          </el-table-column>

        </el-table>
        <div style="margin-top: 10px;">
          <el-pagination
              @size-change="load()"
              @current-change="load()"
              v-model:current-page="data.pageNumber"
              v-model:page-size="data.pageSize"
              :page-sizes="[1, 3, 5, 7]"
              :background="background"
              layout="total, sizes, prev, pager, next, jumper"
              :total="data.total"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Search,Edit,Delete} from "@element-plus/icons-vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const data=reactive({
  user:JSON.parse(localStorage.getItem('user')),
  studentName:null,
  pickupAddress:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  ids:[],
  row:null,
})

const load=()=>{
  request.get('/orders/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      studentName:data.studentName,
      pickupAddress:data.pickupAddress
    }
  }).then(res=>{
    res.data.list=res.data.list.filter(item=>item.courierId===null)
    data.tableData=res.data.list
    data.total=res.data.total
    console.log(res.data.list)
  })
}
load()

const reset=()=>{
  data.studentName=null
  data.pickupAddress=null
  load()
}

const del=(id)=>{
  ElMessageBox.confirm("删除数据后无法恢复，确认删除吗","确认删除",{type:"warning"}).then(()=>{
    request.delete('/orders/deleteById',{
      params:{
        id:id
      }
    }).then(res=>{
      if(res.code=='200'){
        ElMessage.success("操作成功")
        load() //删除后重新加载数据
      } else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()

}

const handleSelectChange=(rows=>{
  console.log(rows)
  data.ids = rows.map(row=>row.id)
  console.log(data.ids)
})

const batchDelete=()=>{
  if(data.ids.length===0){
    ElMessage.warning("请选择要删除的数据")
    return
  }
  // request.delete('/admin/deleteBatch',{
  //   params:{
  //     ids:data.ids
  //   }
  // })
  ElMessageBox.confirm("批量删除删除数据后无法恢复，确认删除吗",{type:"warning"}).then(()=>{
    request.delete('/orders/deleteBatch',{data:data.ids}).then(res=>{
      if(res.code=='200'){
        ElMessage.success("操作成功")
        load() //新增后重新加载数据
      } else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()

}
const accept=(row)=>{
  // console.log(row)
  data.row=row
  const now=new Date().toISOString()
  data.row.courierId=data.user.id
  data.row.status='派送中'
  data.row.accessTime=now
  request.put('/orders/update',data.row).then(res=>{
    if(res.code==='200'){
      ElMessage.success("成功接单")
      load()
    }
    else{
      ElMessage.error(res.msg)
    }
  })
}
//可以修改传入的row参数 优化后:
// const accept = (row) => {
//   const now = new Date().toISOString()
//
//   request.put('/orders/update', {
//     ...row,  // 展开原有属性
//     courierId: data.user.id,
//     status: '派送中',
//     accessTime: now
//   }).then(res => {
//     if (res.code === '200') {
//       ElMessage.success("成功接单")
//       load()
//     } else {
//       ElMessage.error(res.msg)
//     }
//   })
// }
</script>