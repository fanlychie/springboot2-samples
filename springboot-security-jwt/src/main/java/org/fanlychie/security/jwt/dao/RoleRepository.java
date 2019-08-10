package org.fanlychie.security.jwt.dao;

import org.fanlychie.security.jwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fanlychie on 2019/7/4.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}