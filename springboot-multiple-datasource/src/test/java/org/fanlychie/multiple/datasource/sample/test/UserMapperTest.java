package org.fanlychie.multiple.datasource.sample.test;

import org.fanlychie.multiple.datasource.sample.entity.db1.User;
import org.fanlychie.multiple.datasource.sample.mapper.db1.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> users = userMapper.findAll();
        assertThat(users).isNotEmpty().size().isEqualTo(2);
        users.forEach(System.out::println);
    }

}