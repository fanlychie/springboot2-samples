package org.fanlychie.jpa.sample.entity;

import lombok.Data;
import org.fanlychie.jpa.sample.enums.Sex;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by fanlychie on 2019/6/26.
 */
@Data
@Entity
@Table(name = "EMPLOYEE")
@NamedQueries({
        @NamedQuery(
                name = "Employee.selectBySex",
                query = "SELECT E FROM Employee E WHERE E.sex = ?1 ORDER BY E.createTime DESC")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Employee.selectByHiredate",
                query = "SELECT * FROM EMPLOYEE WHERE HIREDATE = ?1",
                resultClass = Employee.class)
})
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 16, nullable = false)
    private String name;

    @Column(columnDefinition = "smallint", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    private Integer age;

    private Boolean married;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hiredate;

    @Column(nullable = false)
    private Date createTime;

}