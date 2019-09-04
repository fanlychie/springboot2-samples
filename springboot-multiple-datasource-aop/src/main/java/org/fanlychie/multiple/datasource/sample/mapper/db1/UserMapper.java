package org.fanlychie.multiple.datasource.sample.mapper.db1;

import org.apache.ibatis.annotations.Param;
import org.fanlychie.multiple.datasource.sample.entity.db1.User;

import java.util.List;

public interface UserMapper {

    List<User> findAll();

    User findByName(@Param("name") String name);

}