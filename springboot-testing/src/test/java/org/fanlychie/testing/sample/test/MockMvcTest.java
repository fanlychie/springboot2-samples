package org.fanlychie.testing.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void testEcho() throws Exception {
        mvc.perform(post("/demo/echo")
                    .param("msg", "SpringBoot"))
                .andExpect(status().isOk())
                .andExpect(content().string("SpringBoot"))
                .andDo(print());
    }

    @Test
    public void testEchoJson() throws Exception {
        mvc.perform(post("/demo/echo/json")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\"id\":\"1002\",\"content\":\"Hello World\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content").value("Hello World"))
                .andDo(print());
    }

    @Test
    public void testHello() throws Exception {
        mvc.perform(post("/demo/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello"))
                .andDo(print());
    }


}