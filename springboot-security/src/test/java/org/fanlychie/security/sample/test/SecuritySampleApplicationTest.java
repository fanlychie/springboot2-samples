package org.fanlychie.security.sample.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SecuritySampleApplicationTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void before() {
        // spring security test
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

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