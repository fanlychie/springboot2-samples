package org.fanlychie.testing.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TestRestTemplateTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testInfo() {
        ResponseEntity<String> responseEntity = template.getForEntity("/demo/info", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.hasBody()).isTrue();
        assertThat(responseEntity.getBody()).isNotBlank().isEqualTo("SpringBoot Testing");
    }

    @Test
    public void testEcho() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("msg", "SpringBoot");
        ResponseEntity<String> responseEntity = template.postForEntity("/demo/echo", params, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.hasBody()).isTrue();
        assertThat(responseEntity.getBody()).isNotBlank().isEqualTo("SpringBoot");
    }

}