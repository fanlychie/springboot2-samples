package org.fanlychie.mybatis.sample.test;

import org.fanlychie.mybatis.sample.MybatisSampleApplication;
import org.fanlychie.mybatis.sample.mapper.EmployeeMapper;
import org.fanlychie.mybatis.sample.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by fanlychie on 2019/6/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MybatisSampleApplication.class})
public class MybatisSampleApplicationTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void testFindAll() {
        List<Employee> employees = employeeMapper.findAll();
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    @Test
    public void testSave() {
        Employee e = new Employee();
        e.setName("赵六");
        e.setAge(18);
        long result = employeeMapper.save(e);
        System.out.println(result);
    }

}