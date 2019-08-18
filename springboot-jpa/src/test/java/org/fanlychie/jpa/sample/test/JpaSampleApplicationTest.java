package org.fanlychie.jpa.sample.test;

import org.fanlychie.jpa.sample.dao.EmployeeRepository;
import org.fanlychie.jpa.sample.entity.Employee;
import org.fanlychie.jpa.sample.enums.Sex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Created by fanlychie on 2019/6/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void before() {
        for (int i = 1; i <= 5; i++) {
            Employee e = new Employee();
            e.setName("Fanlychie" + i);
            e.setCreateTime(new Date());
            e.setAge(ThreadLocalRandom.current().nextInt(10) + 18);
            e.setMarried(ThreadLocalRandom.current().nextBoolean());
            e.setSalary(new BigDecimal(String.valueOf(ThreadLocalRandom.current().nextInt(10000) + 8000)));
            e.setSex(ThreadLocalRandom.current().nextBoolean() ? Sex.MALE : Sex.FEMALE);
            e.setHiredate(Date.from(LocalDate.of(2018, Month.JUNE, ThreadLocalRandom.current().nextInt(30) + 1)
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
            e = employeeRepository.save(e);
            assertThat(e.getId()).isNotNull();
        }
    }

    /**
     * 根据主键查询
     */
    @Test
    public void testFindById() {
        Optional<Employee> employee = employeeRepository.findById(1L);
        assertThat(employee.isPresent()).isTrue();
        assertThat(employee.get()).isNotNull();
        System.out.println(employee.get());
    }

    /**
     * 删除一个不存在的ID记录, 抛出异常EmptyResultDataAccessException
     */
    @Test(expected = Exception.class)
    public void testDeleteById() {
        employeeRepository.deleteById(Long.MAX_VALUE);
    }

    /**
     * 删除一个不存在的实体记录, 直接返回, 不会报错
     */
    @Test
    public void testDelete() {
        Employee e = new Employee();
        employeeRepository.delete(e);
    }

    /**
     * 查询排序
     */
    @Test
    public void testFindAllByOrder() {
        Iterable<Employee> employees = employeeRepository.findAll(Sort.by(DESC, "salary")
                .and(Sort.by(ASC, "age")));
        assertThat(employees).isNotNull();
        employees.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void testFindByPage() {
        Page<Employee> page = employeeRepository.findAll(PageRequest.of(0, 3,
                Sort.by(DESC, "salary")
                        .and(Sort.by(ASC, "age"))));
        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotNull();
        assertThat(page.hasContent()).isTrue();
        assertThat(page.getNumberOfElements()).isGreaterThan(1);
        page.getContent().forEach(System.out::println);
        System.out.println(page.getSize());
        System.out.println(page.getNumberOfElements());
    }

    /**
     * 根据名称查询
     */
    @Test
    public void testFindByName() {
        Employee employee = employeeRepository.findByName("Fanlychie1");
        assertThat(employee).isNotNull();
        System.out.println(employee);
    }

    /**
     * 根据性别分页查询
     */
    @Test
    public void testFindBySexOrderByCreateTimeDesc() {
        Page<Employee> page = employeeRepository.findBySexOrderByCreateTimeDesc(Sex.MALE,
                PageRequest.of(0, 10));
        page.getContent().forEach(System.out::println);
    }

    /**
     * 根据性别查询
     */
    @Test
    public void testSelectBySex() {
        List<Employee> es = employeeRepository.selectBySex(Sex.FEMALE);
        es.forEach(System.out::println);
    }

    /**
     * 根据入职时间查询
     */
    @Test
    public void testSelectByHiredate() {
        List<Employee> es = employeeRepository.selectByHiredate(Date.from(
                LocalDate.of(2018, Month.JUNE, 1)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));
        es.forEach(System.out::println);
    }

    /**
     * SQL 查询
     */
    @Test
    public void testSelectNativeByName() {
        Employee employee = employeeRepository.selectNativeByName("Fanlychie1");
        assertThat(employee).isNotNull();
        System.out.println(employee);
    }

    /**
     * JPQL 分页查询
     */
    @Test
    public void testSelectPaginationBySex() {
        Page<Employee> page = employeeRepository.selectPaginationBySex(Sex.MALE,
                PageRequest.of(0, 10, DESC, "age"));
        page.getContent().forEach(System.out::println);
    }

    /**
     * SQL 分页查询
     */
    @Test
    public void testSelectNativePaginationBySex() {
        Page<Employee> page = employeeRepository.selectNativePaginationBySex(Sex.MALE.ordinal(),
                PageRequest.of(0, 10, DESC, "age"));
        page.getContent().forEach(System.out::println);
    }

    @Test
    public void testUpdateSalaryForName() {
        employeeRepository.updateSalaryForName("Tom", new BigDecimal("8888"));
    }

    @Test
    public void testDeleteByName() {
        employeeRepository.deleteByName("Tom");
    }

}