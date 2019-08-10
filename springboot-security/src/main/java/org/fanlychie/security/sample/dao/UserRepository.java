package org.fanlychie.security.sample.dao;

import org.fanlychie.security.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fanlychie on 2019/7/4.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}