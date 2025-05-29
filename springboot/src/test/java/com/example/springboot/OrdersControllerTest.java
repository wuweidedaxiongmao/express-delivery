
package com.example.springboot;

import com.example.springboot.entity.Orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddOrderSuccess() throws Exception {
        Orders order = new Orders();
        order.setStudentId(1); // 使用存在的学生ID
        order.setPickupAddress("测试取件地址");
        order.setAddressId(1); // 假设地址ID存在
        order.setTypeId(1); // 快递类型ID
        order.setDescription("这是一个测试快递任务");
        order.setStatus("待接单");
        order.setCreateTime(LocalDateTime.now());

        mockMvc.perform(post("/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void testAddOrderMissingTypeId() throws Exception {
        Orders order = new Orders();
        order.setStudentId(1);
        order.setPickupAddress("缺少快递类型ID的任务");
        order.setAddressId(1);
        order.setDescription("测试异常情况");

        mockMvc.perform(post("/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().is4xxClientError());
    }
}
