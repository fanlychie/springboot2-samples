package org.fanlychie.profiles.sample.test;

import org.assertj.core.api.Assertions;
import org.fanlychie.profiles.sample.bean.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

/**
 * 单元测试
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"dev", "mysql"})
public class ProfilesSampleApplicationTest {

    @Value("${message}")
    private String message;

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Test
    public void doTest() {
        String source = dataSourceConfig.getDataSource().toString();
        assertThat(source).isEqualTo("[[ MySQL ]]");
        assertThat(message).isNotBlank().isEqualTo("it is the dev environment.");
    }

}