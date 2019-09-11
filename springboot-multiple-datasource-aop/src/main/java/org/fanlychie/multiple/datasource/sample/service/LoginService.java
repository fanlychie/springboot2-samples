package org.fanlychie.multiple.datasource.sample.service;

import org.fanlychie.multiple.datasource.sample.entity.Product;
import org.fanlychie.multiple.datasource.sample.mapper.UserMapper;
import org.fanlychie.multiple.datasource.sample.mapper.ProductMapper;
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
