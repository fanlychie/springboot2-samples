package org.fanlychie.security.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecuritySampleApplicationTest {

    @Autowired
    private MockMvc mvc;

    /**
     * 匿名用户, 自动跳到登录也
     */
    @Test
    @WithAnonymousUser
    public void testAnonymousUser() throws Exception {
        mvc.perform(get("/info"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"))
                .andDo(print());
    }

    /**
     * 管理员账户, 可以正常访问/info
     */
    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userServiceImpl")
    public void testAdminInfo() throws Exception {
        mvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.username").value("admin"))
                .andDo(print());
    }

    /**
     * 非管理员账户，访问报403
     */
    @Test
    @WithUserDetails(value = "salesman", userDetailsServiceBeanName = "userServiceImpl")
    public void testNonAdminInfo() throws Exception {
        mvc.perform(get("/info"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    /**
     * 测试视图
     */
    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userServiceImpl")
    public void testCenter() throws Exception {
        mvc.perform(get("/center"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authorities"))
                .andExpect(view().name("center"))
                .andDo(print());
    }

}