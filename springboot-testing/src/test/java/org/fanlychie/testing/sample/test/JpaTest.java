package org.fanlychie.testing.sample.test;

import org.fanlychie.testing.sample.dao.UserRepository;
import org.fanlychie.testing.sample.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * JPA 单元测试
 *
 * @author fanlychie
 * @since 2019/08/16
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() {
        User user = new User();
        user.setName("fanlychie");
        user.setMail("fanlychie@yeah.net");
        userRepository.save(user);
    }

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
        System.out.println(users.get(0));
    }

}