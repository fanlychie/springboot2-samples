package org.fanlychie.thymeleaf.sample.test;

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
/**
 * 单元测试
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ThymeleafSampleApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        mvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("index"))
                .andDo(print());
    }

}