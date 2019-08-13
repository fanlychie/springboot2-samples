package org.fanlychie.testing.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testInfo() throws Exception {
        mvc.perform(get("/demo/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("SpringBoot Testing"))
                .andDo(print());
    }

    @Test
    public void testQuery() throws Exception {
        mvc.perform(post("/demo/query")
                    .param("keyword", "SpringBoot"))
                .andExpect(status().isOk())
                .andExpect(content().string("SpringBoot"))
                .andDo(print());
    }

    @Test
    public void testHello() throws Exception {
        mvc.perform(post("/demo/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andDo(print());
    }


}