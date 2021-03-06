package org.fanlychie.interceptor.sample.test;

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
public class InterceptorSampleApplicationTest {

    @Autowired
    private MockMvc mvc;

    /**
     * 用户登录
     */
    @Test
    public void testLogin() throws Exception {
        mvc.perform(get("/login")
                        .param("name", "admin")
                        .param("password", "admin123"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 匿名用户访问
     */
    @Test
    public void testWelcome() throws Exception {
        mvc.perform(get("/welcome"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andDo(print());
    }

}