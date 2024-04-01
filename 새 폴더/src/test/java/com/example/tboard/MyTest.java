package com.example.tboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("add 기능 테스트")
    void t1() throws Exception {
        mockMvc.perform(post("/add").param("title", "aaa").param("body", "bbb"))
                .andExpect(status().isOk());
    }

}
