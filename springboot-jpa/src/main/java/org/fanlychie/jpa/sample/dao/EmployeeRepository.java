package org.fanlychie.jpa.sample.dao;

import org.fanlychie.jpa.sample.entity.Employee;
import org.fanlychie.jpa.sample.enums.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fanlychie on 2019/6/26.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 根据名称查询
    Employee findByName(String name);

    // 根据性别分页查询
    Page<Employee> findBySexOrderByCreateTimeDesc(Sex sex, Pageable pageable);

    // @NamedQuery
    List<Employee> selectBySex(Sex sex);

    // @NamedNativeQuery
    List<Employee> selectByHiredate(Date hiredate);

    @Query("SELECT E FROM Employee E WHERE E.name = ?1")
    Employee selectByName(String name);

    @Query(value = "SELECT * FROM EMPLOYEE WHERE NAME = ?1", nativeQuery = true)
    Employee selectNativeByName(String name);

    @Query("SELECT E FROM Employee E WHERE E.sex = ?1")
    Page<Employee> selectPaginationBySex(Sex sex, Pageable pageable);

    @Query(value = "SELECT * FROM EMPLOYEE WHERE SEX = ?1",
            countQuery = "SELECT COUNT(1) FROM EMPLOYEE WHERE SEX = ?1 ",
            nativeQuery = true)
    Page<Employee> selectNativePaginationBySex(Integer sex, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Employee E SET E.salary = ?2 WHERE E.name = ?1")
    int updateSalaryForName(String name, BigDecimal salary);

    @Modifying
    @Transactional
    @Query("DELETE FROM Employee E WHERE E.name = ?1")
    int deleteByName(String name);

}