
package com.example.springboot;

import com.example.springboot.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentRechargeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRechargePositiveAmount() throws Exception {
        Student student = new Student();
        student.setId(3); // 替换为存在的学生 ID
        student.setAddMoney(100); // 正常充值金额
        student.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(put("/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRechargeNegativeAmount() throws Exception {
        Student student = new Student();
        student.setId(3); // 替换为存在的学生 ID
        student.setAddMoney(-50); // 非法负数充值
        student.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(put("/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().is4xxClientError()); // 期望拒绝负数充值
    }
}
