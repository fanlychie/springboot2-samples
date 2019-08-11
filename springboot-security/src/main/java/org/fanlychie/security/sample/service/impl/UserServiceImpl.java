package org.fanlychie.security.sample.service.impl;

import org.fanlychie.security.sample.dao.UserRepository;
import org.fanlychie.security.sample.model.Role;
import org.fanlychie.security.sample.model.User;
import org.fanlychie.security.sample.security.Principal;
import org.fanlychie.security.sample.service.UserService;
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
                return new Principal(user, authorities);
            }
        }
        throw new UsernameNotFoundException("user \"" + username + "\" does not exits.");
    }

}