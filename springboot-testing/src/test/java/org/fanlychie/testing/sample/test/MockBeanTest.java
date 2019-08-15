package org.fanlychie.testing.sample.test;

import org.fanlychie.testing.sample.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockBeanTest {

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testForm() throws Exception {
        // 模拟方法的调用行为, 调用verify时总是返回true
        given(messageService.verify(any())).willReturn(true);
        // 不传Message相关参数
        mvc.perform(post("/demo/form"))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"))
                .andDo(print());
    }

}