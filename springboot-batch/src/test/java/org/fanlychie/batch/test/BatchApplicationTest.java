package org.fanlychie.batch.test;

import org.fanlychie.batch.launcher.CustomerLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author fanlychie
 * @since 2019/8/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTest {

    @Autowired
    private CustomerLauncher launcher;

    @Test
    public void testStartup() throws Exception {
        launcher.startup("/customers1.xlsx");
        TimeUnit.SECONDS.sleep(10);
        launcher.startup("/customers2.xlsx");
        TimeUnit.SECONDS.sleep(30);
    }


}