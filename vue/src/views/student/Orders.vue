<template>
  <div>
    <div>

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
          <el-table-column prop="status" label="快递状态" show-overflow-tooltip="true"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="large"  @click="confirmOrder(scope.row)" v-if="scope.row.status==='已送达'">签收</el-button>
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
  id:null,
  tableData:[],
  pageNumber:1,
  pageSize:10,
  total:0,
  ids:[],
  orders:{},
})

const load=()=>{
  request.get('/orders/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      id:data.id,
      studentId:JSON.parse(localStorage.getItem('user')).id
    }
  }).then(res=>{
    res.data.list=res.data.list.filter(item=>item.status!=='已签收')
    data.tableData=res.data.list
    data.total=res.data.total
    console.log(res.data.list)
  })
}
load()

const reset=()=>{
  data.id=null
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
const confirmOrder=(row)=>{
  //签收时间
  const now = new Date().toISOString()
  row.status='已签收'
  row.signTime=now
  request.put('/orders/update', row).then(res => {
    if (res.code === '200') {
      //获取快递员对象
      const url='/student/selectById/'+row.courierId
      request.get(url).then(res=>{
        if(res.code==='200'){
          const courier=res.data
          courier.addMoney=courier.addMoney+row.price
          courier.orderCount=courier.orderCount+1
          request.put('/student/update',courier).then(res=>{
            if(res.code==='200'){
              ElMessage.success("成功签收")
              load()
            }else{
              ElMessage.error(res.msg)
            }
          })
        }else{
          ElMessage.error(res.msg)
        }
      })
    } else {
      ElMessage.error(res.msg)
    }
  })
}

</script>