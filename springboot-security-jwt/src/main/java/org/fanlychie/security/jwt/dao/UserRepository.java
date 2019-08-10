package org.fanlychie.security.jwt.dao;

import org.fanlychie.security.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fanlychie on 2019/7/4.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}