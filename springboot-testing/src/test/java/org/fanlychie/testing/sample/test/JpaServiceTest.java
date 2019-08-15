package org.fanlychie.testing.sample.test;

import org.fanlychie.testing.sample.model.User;
import org.fanlychie.testing.sample.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void before() {
        User user = new User();
        user.setName("fanlychie");
        user.setMail("fanlychie@yeah.net");
        userService.save(user);
    }

    @Test
    public void testFindAll() {
        List<User> users = userService.findAll();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
        System.out.println(users.get(0));
    }

}