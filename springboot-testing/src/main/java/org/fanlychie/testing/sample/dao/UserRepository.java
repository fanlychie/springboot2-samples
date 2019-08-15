package org.fanlychie.testing.sample.dao;

import org.fanlychie.testing.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}