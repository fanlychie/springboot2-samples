package org.fanlychie.multiple.datasource.sample.service;

import org.fanlychie.multiple.datasource.sample.dao.test1.UserRepository;
import org.fanlychie.multiple.datasource.sample.dao.test2.ProductRepository;
import org.fanlychie.multiple.datasource.sample.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> login(String name) {
        if (userRepository.findByName(name) != null) {
            return productRepository.findAll();
        }
        throw new RuntimeException("用户不存在");
    }

}
