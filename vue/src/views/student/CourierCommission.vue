<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.courierLevel" placeholder="请输入代取员等级搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()">重置</el-button>
      </el-card>

      <el-card>
        <el-table :data="data.tableData"  stripe="true" @selection-change="handleSelectChange">
          <el-table-column prop="courierLevel" label="代取员等级" show-overflow-tooltip="true"/>
          <el-table-column prop="commissionRate" label="体现比例" show-overflow-tooltip="true"/>
          <el-table-column prop="info" label="具体说明"/>

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
  courierLevel:null,
  tableData:[],
  pageNumber:1,
  pageSize:5,
  total:0,
  rules:{
    courierLevel:[
      {required:true, message:"请输入代取员等级", trigger: "blur"}
    ],
    commissionRate:[
      {required:true, message:"请输入体现比例", trigger: "blur"}
    ],
  }
})

const formRef=ref()

const load=()=>{
  request.get('/courierCommission/selectPage',{  //?pageNum=1&pageSize=10
    params:{
      pageNum:data.pageNumber,
      pageSize:data.pageSize,
      courierLevel:data.courierLevel
    }
  }).then(res=>{
    data.tableData=res.data.list
    data.total=res.data.total
    //console.log(res.data)
  })
}
load()

const reset=()=>{
  data.courierLevel=null
  load()
}

</script>