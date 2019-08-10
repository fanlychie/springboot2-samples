package org.fanlychie.security.jwt.service.impl;

import org.fanlychie.security.jwt.dao.UserRepository;
import org.fanlychie.security.jwt.entity.Role;
import org.fanlychie.security.jwt.entity.User;
import org.fanlychie.security.jwt.security.UserInfoDetails;
import org.fanlychie.security.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanlychie on 2019/7/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.hasText(username)) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (Role role : user.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                return new UserInfoDetails(user, authorities);
            }
        }
        throw new UsernameNotFoundException("user \"" + username + "\" does not exits.");
    }

}