package com.example.springboot;

import com.example.springboot.entity.Identification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class IdentificationApplyTest {

    @Autowired
    private MockMvc mockMvc;

    // 手动注册 JavaTimeModule 支持 LocalDateTime 的序列化
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void testApplyCourierSuccess() throws Exception {
        Identification application = new Identification();
        application.setStudentId(1);
        application.setName("111");
        application.setImage("/images/1.png");
        application.setStatus("待审核");
        application.setAdminId(-1); // 虚拟值 -1 代替 null
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(post("/identification/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testApplyCourierMissingImage() throws Exception {
        Identification application = new Identification();
        application.setStudentId(1);
        application.setName("111");
        application.setStatus("待审核");
        application.setAdminId(-1); // 虚拟值 -1 代替 null
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(post("/identification/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
