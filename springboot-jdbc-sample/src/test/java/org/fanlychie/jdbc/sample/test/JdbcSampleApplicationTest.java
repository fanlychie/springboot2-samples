package org.fanlychie.jdbc.sample.test;

import org.fanlychie.jdbc.sample.JdbcSampleApplication;
import org.fanlychie.jdbc.sample.dao.EmployeeDao;
import org.fanlychie.jdbc.sample.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fanlychie on 2019/6/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JdbcSampleApplication.class})
public class JdbcSampleApplicationTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testFindAll() {
        List<Employee> employees = employeeDao.findAll();
        employees.forEach(System.out::println);
    }

    @Test
    public void testFindByName() {
        Employee employee = employeeDao.findByName("张三");
        System.out.println(employee);
    }

    @Test
    public void testInsert() {
        Employee employee = new Employee();
        employee.setName("张三丰");
        employee.setAge(24);
        int result = employeeDao.insert(employee);
        System.out.println(result);
    }

    @Test
    public void testSave() {
        Employee employee = new Employee();
        employee.setName("李四光");
        employee.setAge(25);
        employeeDao.save(employee);
        System.out.println(employee);
    }

    @Test
    public void testUpdate() {
        Employee employee = employeeDao.findByName("张三");
        employee.setAge(28);
        int result = employeeDao.update(employee);
        System.out.println(result);
    }

    @Test
    public void testBatchUpdate() {
        List<Employee> employees = employeeDao.findAll();
        employees.forEach(e -> e.setAge(29));
        int[] results = employeeDao.batchUpdate(employees);
        System.out.println(Arrays.toString(results));
    }

    @Test
    public void testDelete() {
        int result = employeeDao.delete(3L);
        System.out.println(result);
    }

}