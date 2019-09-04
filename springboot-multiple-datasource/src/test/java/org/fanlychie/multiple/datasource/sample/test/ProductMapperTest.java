package org.fanlychie.multiple.datasource.sample.test;

import org.fanlychie.multiple.datasource.sample.entity.db2.Product;
import org.fanlychie.multiple.datasource.sample.mapper.db2.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testFindAll() {
        List<Product> users = productMapper.findAll();
        users.forEach(System.out::println);
    }

}