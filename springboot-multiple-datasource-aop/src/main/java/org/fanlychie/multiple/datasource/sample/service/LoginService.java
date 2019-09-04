package org.fanlychie.multiple.datasource.sample.service;

import org.fanlychie.multiple.datasource.sample.entity.db2.Product;
import org.fanlychie.multiple.datasource.sample.mapper.db1.UserMapper;
import org.fanlychie.multiple.datasource.sample.mapper.db2.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    public List<Product> login(String name) {
        if (userMapper.findByName(name) != null) {
            return productMapper.findAll();
        }
        throw new RuntimeException("用户不存在");
    }

}
