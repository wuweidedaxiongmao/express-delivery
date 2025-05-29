
package com.example.springboot;

import com.example.springboot.entity.ReleaseApply;
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
public class ReleaseApplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testReleaseApplySuccess() throws Exception {
        ReleaseApply apply = new ReleaseApply();
        apply.setCourierId(1);  // 你可以换成真实的id
        apply.setUsername("test_user");
        apply.setName("测试用户");
        apply.setProblem("账户被误封");
        apply.setReason("我是无辜的");
        apply.setStatus("待审核");
        apply.setAdminId(null);
        apply.setCreatedAt(LocalDateTime.now());
        apply.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(post("/releaseApply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apply)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void testReleaseApplyMissingFields() throws Exception {
        ReleaseApply apply = new ReleaseApply();
        apply.setCourierId(null);  // 故意不填 courierId
        apply.setReason("test");

        mockMvc.perform(post("/releaseApply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apply)))
                .andExpect(status().is4xxClientError());
    }
}
