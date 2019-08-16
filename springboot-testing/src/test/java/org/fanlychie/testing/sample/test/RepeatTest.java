package org.fanlychie.testing.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Repeat(重复) 单元测试
 *
 * @author fanlychie
 * @since 2019/8/16
 */
@RunWith(SpringRunner.class)
public class RepeatTest {

    @Test
    @Repeat(3)
    public void testRepeat() {
        System.out.println("--- Hello ---");
    }

}