import axios from "axios";
import { ElMessage } from "element-plus";

const request = axios.create({
    baseURL: 'http://localhost:9091',
    timeout: 30000 // 超时时间30秒
})

// 请求拦截器
request.interceptors.request.use(
    (config) => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
request.interceptors.response.use(
    (response) => {
        let res = response.data;
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res;
        }
        return res;
    },
    (error) => {
        if (error.response) {
            switch (error.response.status) {
                case 404:
                    ElMessage.error('未找到请求接口');
                    break;
                case 500:
                    ElMessage.error('系统异常，请查看后端控制台报告');
                    break;
                default:
                    console.error(error.message);
            }
        } else {
            ElMessage.error('网络连接异常');
        }
        return Promise.reject(error);
    }
);

export default request;