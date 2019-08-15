package org.fanlychie.testing.sample.test;

import org.fanlychie.testing.sample.service.EnvironmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileTest {

    @Autowired
    private EnvironmentService environmentService;

    @Test
    public void testGetEnvironment() {
        String source = environmentService.getEnvironment();
        assertThat(source).isNotBlank().isEqualTo("DEVELOP");
    }

}