package org.fanlychie.testing.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TestRestTemplateTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testInfo() throws Exception {
        String result = template.getForObject("/demo/info", String.class);
        assertThat(result).isNotBlank().isEqualTo("SpringBoot Testing");
    }

}