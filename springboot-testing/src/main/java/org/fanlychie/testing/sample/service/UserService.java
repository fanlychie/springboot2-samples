package org.fanlychie.testing.sample.service;

import org.fanlychie.testing.sample.dao.UserRepository;
import org.fanlychie.testing.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        throw new NullPointerException("user can not be null");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}