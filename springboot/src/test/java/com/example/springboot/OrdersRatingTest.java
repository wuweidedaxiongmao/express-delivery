
package com.example.springboot;

import com.example.springboot.entity.Orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal; // 添加这个导入！

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersRatingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSubmitRatingSuccess() throws Exception {
        Orders order = new Orders();
        order.setId(14); // 请替换为数据库中已完成的订单 ID
        order.setRating(BigDecimal.valueOf(5)); // 必填字段：评分星数
        order.setFeedback("服务很及时，态度很好"); // 可选字段：评价内容

        mockMvc.perform(put("/orders/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSubmitRatingWithoutFeedback() throws Exception {
        Orders order = new Orders();
        order.setId(15); // 替换为已完成的订单 ID
        order.setRating(BigDecimal.valueOf(4)); // 只评分，不写反馈

        mockMvc.perform(put("/orders/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk()); // 预期应成功写入
    }
}
