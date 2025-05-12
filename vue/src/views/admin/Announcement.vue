<template>
  <div>
    <div>
      <el-card class="card">
        <el-input style="margin-right: 5px; width: 240px" v-model="data.title" placeholder="请输入标题搜索" prefix-icon="Search">
        </el-input>
        <el-button type="primary" plain @click="load()">查询</el-button>
        <el-button type="primary" plain @click="reset()">重置</el-button>
      </el-card>

      <el-card class="card">
        <el-button type="primary" plain @click="handleAdd()">新增</el-button>
        <el-button type="primary" plain @click="batchDelete">批量删除</el-button>
      </el-card>

      <el-card>
        <el-table :data="data.tableData" stripe @selection-change="handleSelectChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="title" label="标题" show-overflow-tooltip/>
          <el-table-column prop="createdAt" label="创建时间"/>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button link type="primary" size="large" :icon="Edit" @click="handleUpdate(scope.row)">编辑</el-button>
              <el-button link type="primary" size="large" :icon="Delete" @click="del(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;">
          <el-pagination
              @size-change="onSizeChange"
              @current-change="onCurrentChange"
              v-model:current-page="data.pageNumber"
              v-model:page-size="data.pageSize"
              :page-sizes="[1, 3, 5, 7, 10]"
              :background="true"
              layout="total, sizes, prev, pager, next, jumper"
              :total="data.total"
          />
        </div>
      </el-card>
    </div>

    <el-dialog v-model="data.formVisible" title="公告信息" width="50%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="内容">
          <div style="border: 1px solid #ccc; width: 100%">
            <Editor
                style="height: 400px; overflow-y: hidden;"
                v-model="data.form.content"
                :mode="mode"
                :defaultConfig="editorConfig"
                @onCreated="handleCreated"/>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save()">保存</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="内容预览" v-model="data.viewVisible" width="50%" :close-on-click-modal="false" destroy-on-close>
      <div class="editor-content-view" style="padding: 20px" v-html="data.content"></div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.viewVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onBeforeUnmount, reactive, ref, shallowRef } from 'vue';
import { Search, Edit, Delete } from '@element-plus/icons-vue';
import request from '@/utils/request';
import { ElMessage, ElMessageBox } from 'element-plus';
import '@wangeditor/editor/dist/css/style.css';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';

const data = reactive({
  title: null,
  tableData: [],
  pageNumber: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  ids: [],
  content: null,
  viewVisible: false,
});

const formRef = ref();

const load = () => {
  request.get('/announcement/selectPage', {
    params: {
      pageNum: data.pageNumber,
      pageSize: data.pageSize,
      title: data.title,
    }
  }).then(res => {
    data.tableData = res.data.list;
    data.total = res.data.total;
  });
};
load();

const reset = () => {
  data.title = null;
  load();
};

const handleAdd = () => {
  data.formVisible = true;
  data.form = {};
};

const save = () => {
  formRef.value.validate(valid => {
    if (valid) {
      if (!data.form.id) add(); else update();
    }
  });
};
const add = () => {
  request.post('/announcement/add', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false;
      ElMessage.success('操作成功');
      load();
    } else ElMessage.error(res.msg);
  });
};
const update = () => {
  request.put('/announcement/update', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false;
      ElMessage.success('操作成功');
      load();
    } else ElMessage.error(res.msg);
  });
};

const handleUpdate = (row) => {
  data.form = JSON.parse(JSON.stringify(row));
  data.formVisible = true;
};

const del = (id) => {
  ElMessageBox.confirm('删除后无法恢复，确认删除吗', '确认删除', { type: 'warning' })
      .then(() => {
        request.delete('/announcement/deleteById', { params: { id } })
            .then(res => {
              if (res.code === '200') {
                ElMessage.success('操作成功');
                load();
              } else ElMessage.error(res.msg);
            });
      }).catch(() => {});
};

const handleSelectChange = (rows) => { data.ids = rows.map(r => r.id); };

const batchDelete = () => {
  if (!data.ids.length) {
    ElMessage.warning('请选择要删除的数据');
    return;
  }
  ElMessageBox.confirm('批量删除后无法恢复，确认删除吗', '确认删除', { type: 'warning' })
      .then(() => {
        request.delete('/announcement/deleteBatch', { data: data.ids })
            .then(res => {
              if (res.code === '200') {
                ElMessage.success('操作成功');
                load();
              } else ElMessage.error(res.msg);
            });
      }).catch(() => {});
};

const onSizeChange = (size) => { data.pageSize = size; load(); };
const onCurrentChange = (page) => { data.pageNumber = page; load(); };

const view = (content) => { data.content = content; data.viewVisible = true; };
</script>
