package org.fanlychie.multiple.datasource.sample.dao.test1;

import org.fanlychie.multiple.datasource.sample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User

}