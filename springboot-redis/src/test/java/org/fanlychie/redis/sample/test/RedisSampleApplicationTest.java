package org.fanlychie.redis.sample.test;

import org.fanlychie.redis.sample.RedisSampleApplication;
import org.fanlychie.redis.sample.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisSampleApplication.class})
public class RedisSampleApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testFindById() {
        System.out.println(userService.findById(1));
    }

    @Test
    public void testFindByName() {
        System.out.println(userService.findByName("Tom"));
    }

    @Test
    public void testFetchByName() {
        System.out.println(userService.fetchByName("Cat"));
    }

}