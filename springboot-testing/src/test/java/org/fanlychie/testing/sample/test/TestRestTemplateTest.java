package org.fanlychie.testing.sample.test;

import lombok.extern.slf4j.Slf4j;
import org.fanlychie.testing.sample.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@Slf4j
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
        log.info(responseEntity.toString());
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

    @Test
    public void testEchoJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        String requestBody = "{\"id\":\"1002\",\"content\":\"Hello World\"}";
        HttpEntity entity = new HttpEntity(requestBody, headers);
        ResponseEntity<Message> responseEntity = template.postForEntity("/demo/echo/json", entity, Message.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.hasBody()).isTrue();
        assertThat(responseEntity.getBody()).isNotNull().hasFieldOrProperty("id").hasFieldOrProperty("content");
    }

    @Test
    public void testForm() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("content", "Hello");
        HttpEntity entity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> responseEntity = template.postForEntity("/demo/form", entity, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.hasBody()).isTrue();
        assertThat(responseEntity.getBody()).isNotBlank().isEqualTo("SUCCESS");
    }

}