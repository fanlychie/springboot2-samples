package org.fanlychie.multiple.datasource.sample.test;

import org.fanlychie.multiple.datasource.sample.entity.db2.Product;
import org.fanlychie.multiple.datasource.sample.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void testLogin() {
        List<Product> products = loginService.login("张三");
        assertThat(products).isNotEmpty().size().isEqualTo(2);
        products.forEach(System.out::println);
    }

}