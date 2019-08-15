package org.fanlychie.testing.sample.test;

import org.fanlychie.testing.sample.model.Message;
import org.fanlychie.testing.sample.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpyBeanTest {

    @SpyBean
    private MessageService messageService;

    @Test
    public void testSend() {
        Message message = null;
        // 模拟方法的调用行为, 调用verify时总是返回true
        when(messageService.verify(any())).thenReturn(true);
        boolean result = false;
        if (messageService.verify(message)) {
            result = messageService.send(message);
        }
        assertThat(result).isTrue();
    }

}