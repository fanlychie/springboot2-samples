package org.fanlychie.mybatis.sample.mapper;

import org.fanlychie.mybatis.sample.model.Employee;

import java.util.List;

/**
 * Created by fanlychie on 2019/6/5.
 */
public interface EmployeeMapper {

    Long save(Employee employee);

    List<Employee> findAll();

}