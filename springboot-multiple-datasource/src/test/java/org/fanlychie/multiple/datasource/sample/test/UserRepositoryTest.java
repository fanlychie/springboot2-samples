package org.fanlychie.multiple.datasource.sample.test;

import org.fanlychie.multiple.datasource.sample.dao.test1.UserRepository;
import org.fanlychie.multiple.datasource.sample.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }

}