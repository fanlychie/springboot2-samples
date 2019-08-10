package org.fanlychie.security.sample.dao;

import org.fanlychie.security.sample.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}