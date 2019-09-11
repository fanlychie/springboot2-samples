package org.fanlychie.multiple.datasource.sample.mapper;

import org.apache.ibatis.annotations.Param;
import org.fanlychie.multiple.datasource.sample.annotation.DataSource;
import org.fanlychie.multiple.datasource.sample.entity.User;
import org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource.DataSourceEnum;

import java.util.List;

@DataSource(DataSourceEnum.DB1)
public interface UserMapper {

    List<User> findAll();

    User findByName(@Param("name") String name);

}