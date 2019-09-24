package org.fanlychie.async.sample.test;

import org.fanlychie.async.sample.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncServiceTest {

    @Autowired
    private DemoService demoMethod;

    @Test
    public void testDemoMethod() throws Exception {
        demoMethod.demoMethod();
        TimeUnit.SECONDS.sleep(5);
    }

}