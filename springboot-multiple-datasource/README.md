## Spring Boot JPA

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 概述

JPA（Java Persistence API）是一套 Java 持久化规范，用于将应用程序中的对象映射到关系型数据库。
应用程序的数据访问层通常为域对象提供创建、读取、更新和删除（CRUD）操作，Spring Data JPA 提供了这方面的通用接口以及持久化存储特定的实现，它选择目前最流行之一的 Hibernate 作为 JPA 实现的提供者，旨在简化数据访问层。作为应用程序的开发人员，你只需要编写数据库的存取接口，由 Spring 运行时自动生成这些接口的适当实现，开发人员不需要编写任何具体的实现代码。

---

### 2. 依赖配置

在 Spring Boot 中，通过使用`spring-boot-starter-data-jpa`启动器，就能快速开启和使用 Spring Data JPA

```xml
<!-- jpa -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
<!-- mysql -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version>
    <scope>runtime</scope>
</dependency>
<!-- lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

---

### 3. 项目配置

在项目 application.yml 中添加如下配置：

```yaml
spring:
  # 数据源配置
  datasource:
    url: jdbc:mysql://127.0.0.1/test?useSSL=false&useUnicode=true&characterEncoding=utf-8
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    # 连接池配置
    hikari:
      auto-commit: true          # 自动提交
      connection-timeout: 30000  # 连接超时
      idle-timeout: 600000       # 连接在池中闲置的最长时间
      max-lifetime: 1800000      # 连接在池中最长的生命周期
      minimum-idle: 30           # 池中维护的最小空闲连接数
      maximum-pool-size: 30      # 池中维护的最大连接数
  jpa:
    # hibernate配置
    hibernate:
      ddl-auto: update           # 应用启动时，如果表不存在则创建；如果已经存在则更新表结构，表中原有的数据行不会被删除
    show-sql: true               # 显示 SQL
    properties:
      hibernate:
        format_sql: true         # 格式化 SQL
```

> `hibernate.ddl-auto`可选值：
>> * `create`：应用启动时，删除数据库中的表然后创建；退出时，数据库表不会删除
>> * `create-drop`：应用启动时，删除数据库中的表然后创建；退出时，删除数据库中的表
>> * `update`：应用启动时，如果表不存在则创建；如果已经存在则更新表结构，表中原有的数据行不会被删除

---

### 4. 实体类编写

```java
import lombok.Data;
import org.fanlychie.jpa.sample.enums.Sex;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "EMPLOYEE")
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
```

运行程序，自动生成或更新数据库表结构。

> 实体类常用注解：
>> * `@Entity`：标明当前类是一个实体类。并使用默认的`ORM`规则（类名即表名，对象属性名即表的字段名）。name 默认是类的简单类名，常用于 JPQL 查询语句中
>> * `@Table`：用于替代默认规则，使用 name 指定的名称作为表的名称，默认是类的简单类名
>> * `@Column`：用于替代默认规则或用于约束字段
>>     - `name`：字段名称
>>     - `unique`：值是否唯一。默认为 false
>>     - `nullable`：字段值是否允许为 null。默认为 true
>>     - `insertable`：当使用`INSERT`语句插表时，是否将该字段的值插入。默认问 true
>>     - `updatable`：当使用`UPDATE`语句更新表数据时，是否将该字段的值更新进去。默认为 true
>>     - `columnDefinition`：定义建表时创建此列的 DDL 语句片段
>>     - `length`：针对字符类型字段，用于限定字符的最大长度。默认为 255
>>     - `precision`：针对浮点数类型字段，用于限定数字的总长度
>>     - `scale`：针对浮点数类型字段，用于限定数值小数点的位数
>> * `@Id`：声明字段为主键字段
>> * `@GeneratedValue`：主键字段的值的生成策略
>>     - `TABLE`：使用专门的数据库表来产生主键
>>     - `IDENTITY`：通过数据库产生，采用自增长（`Oracle`不支持）
>>     - `SEQUENCR`：通过数据库的序列产生主键（`MySQL`不支持）
>>     - `AUTO`：由持久化引擎自动选择合适的策略，默认选择此策略
>> * `@Enumerated`：用于处理枚举类型字段
>>     - `EnumType.STRING`：存储枚举的字符串值
>>     - `EnumType.ORDINAL`：存储枚举的次序值
>> * `@Temporal`：用于处理时间类型

---

### 5. JPA Repository

根据 Spring Data JPA 的规则，数据访问层接口只需要扩展`Repository<Entity, ID>`接口，并声明操纵实体的相关方法，Spring Data JPA 在运行时将会为这些接口创建具体的实现类。

![](https://gitee.com/fanlychie/images/raw/develop/jpa_repository.png)

常用的`Repository` `CrudRepository` `PagingAndSortingRepository` `JpaRepository`关系如上图示。

---

#### 5.1  Repository

它是一个标记接口。扩展该接口需要自己声明操纵实体的所有方法。

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.springframework.data.repository.Repository;

public interface EmployeeRepository extends Repository<Employee, Long> {

    Employee findById(Long id);

}
```

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindById() {
        System.out.println(employeeRepository.findById(1L));
    }

}
```

---

#### 5.2 CrudRepository

继承自`Repository`，它提供了一套 CRUD 操作的方法，你可以不需要再定义基础的 CRUD 操作方法而直接可以使用它们。

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
```

> `CrudRepository<Entity, ID>`更新记录方法：
>> * `save(S entity)`：保存或更新数据。如果参数实体对象是新的（主键没有设置有效的值）则将数据插入表。如果参数实体对象不是新的（主键设置了有效的值）则将数据更新到表
>> * `deleteById(ID id)`：根据主键删除。主键字段不能为空，并且在数据库中必须得有与主键对应的行记录（通过 SELECT 查询判断，如果查询不到则抛出异常），然后将查询出的行记录删除
>> * `delete(T entity)`：根据实体删除。实体对象不能为空，依据实体的主键标识判断数据库中是否有与之对应的行记录，如果有，则将此行删除；如果没有，则什么都不做

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSave() {
        Employee e = new Employee();
        e.setAge(26);
        e.setCreateTime(new Date());
        e.setMarried(false);
        e.setName("Lychie Fan");
        e.setSalary(new BigDecimal("20100"));
        e.setSex(Sex.MALE);
        e.setHiredate(Date.from(
                LocalDate.of(2018, Month.JUNE, 1)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()));
        employeeRepository.save(e);
    }

}
```

---

#### 5.3 PagingAndSortingRepository

继承自`CrudRepository`，它提供了一个常用的分页和排序的操作方法。

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

}
```

排序示例：

```java
import org.fanlychie.jpa.sample.JpaSampleApplication;
import org.fanlychie.jpa.sample.dao.EmployeeRepository;
import org.fanlychie.jpa.sample.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Test
    public void testFindAllByOrder() {
        Iterable<Employee> employees = employeeRepository.findAll(
                Sort.by(DESC, "salary")
                        .and(Sort.by(ASC, "age")));
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

}
```

分页示例：

```java
import org.fanlychie.jpa.sample.JpaSampleApplication;
import org.fanlychie.jpa.sample.dao.EmployeeRepository;
import org.fanlychie.jpa.sample.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Test
    public void testFindByPage() {
        Page<Employee> page = employeeRepository.findAll(PageRequest.of(0, 2,
                Sort.by(DESC, "salary")
                        .and(Sort.by(ASC, "age"))));
        for (Employee employee : page.getContent()) {
            System.out.println(employee);
        }
    }

}
```

> `Page<Entity>`属性（方法）列表：
>> * `totalElements`：条件筛选匹配数据库表行的总条数
>> * `totalPages`：分页的总页数
>> * `content`：当前页的内容，是实体类的List集合
>> * `number`：当前页的页码，第一页从0开始
>> * `numberOfElements`：当前页的条数（最后一页可能比每页条数要少）
>> * `size`：每页的条数
>> * `sort`：排序条件
>> * `hasContent()`：当前页是否有数据
>> * `hasNext()`：是否有下一页
>> * `hasPrevious()`：是否有上一页
>> * `isEmpty()`：当前页是否有数据
>> * `isFirst()`：是否是第一页
>> * `isLast()`：是否是最后一页

---

#### 5.4 JpaRepository

继承自`PagingAndSortingRepository`，它提供了一组实用的操作方法，如批量操作等。

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
```

---

### 6. 定义查询方法

Spring Data JPA 在运行时会为接口创建代理对象并为接口声明的方法提供具体的实现。代理提供了两种方式来从方法名中提取查询，一种是从方法名中直接提取查询，另外一种是从方法中提取手工定义的查询语句。代理如何创建查询是由具体的策略来决定的。

| 策略 | 描述 |
| :---- | :---- |
| `CREATE` | 根据方法名构造出一个查询语句 |
| `USE_DECLARED_QUERY` | 使用注解定义的查询语句（`@Query` `@NamedQuery` `@NamedNativeQuery`） |
| `CREATE_IF_NOT_FOUND` | 默认使用的策略。它组合了`CREATE`和`USE_DECLARED_QUERY`两个策略。它首先使用`USE_DECLARED_QUERY`策略查找，如果找不到再使用`CREATE`策略 |

#### 6.1 创建查询

Spring Data JPA 提供了一种可以根据方法名称直接构造出查询语句的方式，在数据访问层接口中定义的方法名称只需按照约定命名：

* 以 `findBy` `find...By` `readBy` `read...By` `queryBy` `query...By` `countBy` `count...By` `getBy` `get...By` 前缀开始命名
* 在第一个 `By` 之后可以添加实体对象的属性名称或组合支持的关键字对查询进行条件筛选过滤
* 在第一个 `By` 之前可以添加 `First` 或 `Top` 关键字（`First` `Top` 的后面可以携带数字表示返回前多少条的数据，如：Top10），表示返回查询结果的第多少条数据
* 在第一个 `By` 之前可以添加 `Distinct` 关键字，去掉查询结果中重复的数据
* 查询方法如果设定了 X 个检索条件，那么，查询方法的参数个数也必须是 X 个，并且参数必须按与检索条件相同的顺序给出
* 查询方法同时还可以使用特殊的 Pageable 或 Sort 参数，用于分页或排序，该参数不算在X之内

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.fanlychie.jpa.sample.enums.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 根据名称查询
    Employee findByName(String name);

    // 根据性别分页查询
    Page<Employee> findBySexOrderByCreateTimeDesc(Sex sex, Pageable pageable);

}
```

> 创建查询的优点是，不用编写查询语句，处理检索条件简单的查询非常方便，而且方法的可读性很高。但是对于检索条件过多的查询方法，很容易导致方法名称过长，可读性降低。

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByName() {
        System.out.println(employeeRepository.findByName("Lychie Fan"));
    }

    @Test
    public void testFindBySexOrderByCreateTimeDesc() {
        Page<Employee> page = employeeRepository.findBySexOrderByCreateTimeDesc(Sex.MALE,
                PageRequest.of(0, 10));
        for (Employee employee : page.getContent()) {
            System.out.println(employee);
        }
    }

}
```

##### 6.1.1 查询方法支持的关键字表

![](https://gitee.com/fanlychie/images/raw/develop/spring_data_jpa_supported_keywords_inside_method_names.png)

##### 6.1.2 查询方法支持的返回值表

![](https://gitee.com/fanlychie/images/raw/develop/spring_data_jpa_supported_return_types.png)

---

### 7. 注解

Spring Data JPA 提供了一种可以将查询语句从数据访问层接口中独立出来的方式。通过 `@NamedQuery` 或 `@NamedNativeQuery` 将查询语句直接绑定到目标方法。这种方式可以使得查询语句集中，便于维护，查询方法的名称不受约束，编写复杂的查询只要合理命名也不会导致产生过长的方法名称。但是由于命名查询的注解都是标注在实体类中，因此它不适合用于大量定义查询语句，这样会使得实体类变得过于臃肿。

#### 7.1 @NamedQuery

注解在实体类上，使用 JPQL（Java Persistence Query Language，Java 持久化查询语言）查询语言。JPQL 和 SQL 之间有很多相似之处，它们之间主要的区别在于前者处理 JPA 实体类，而后者则直接涉及关系数据。在 JPQL 中，可以使用`SELECT`、`UPDATE`和`DELETE`语法来定义查询。

```java
import lombok.Data;
import org.fanlychie.jpa.sample.enums.Sex;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "EMPLOYEE")
@NamedQueries({
        @NamedQuery(
                name = "Employee.selectBySex", 
                query = "SELECT E FROM Employee E WHERE E.sex = ?1 ORDER BY E.createTime DESC")
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
```

> `@NamedQuery`参数：
>> * `name`：定义查询的方法名称（全局范围的），为避免不同的实体定义了相同的方法名称而导致的查询冲突，JPA 约定自定义的方法名称为：`实体类简单类名.自定义查询方法名称`
>> * `query`：定义 JPQL 查询语句
>> * `lockMode`：锁模式类型，详见下。
>>> * `READ`：等同于`OPTIMISTIC`
>>> * `WRITE`：等同于`OPTIMISTIC_FORCE_INCREMENT`
>>> * `OPTIMISTIC`：乐观锁
>>> * `OPTIMISTIC_FORCE_INCREMENT`：乐观锁，通过版本号递增控制
>>> * `PESSIMISTIC_READ`：悲观锁，支持多读，与写互斥
>>> * `PESSIMISTIC_WRITE`：悲观锁，与其他读写都互斥
>>> * `PESSIMISTIC_FORCE_INCREMENT`：悲观锁，通过版本号递增控制
>>> * `NONE`：默认

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> selectBySex(Sex sex);

}
```

#### 7.2 @NamedNativeQuery

注解在实体类上，与`@NamedQuery`的用法和作用相类似。不同的是，`@NamedQuery`使用的是 JPQL 查询语言，可以做到跨数据库平台。而`@NamedNativeQuery`使用的是 SQL 查询语言，与特定的数据库平台紧密相关。

```java
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "EMPLOYEE")
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
```

> `resultClass`：用于声明查询结果返回的数据类型

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> selectByHiredate(Date hiredate);

}
```

#### 7.3 @Query

`@Query`注解可以直接将查询语句绑定到存储库接口的方法上，它同时支持 JPQL 和 SQL 查询语言。它对方法名称的命名没有约束，查询语句编写在方法的上方。

##### 7.3.1 JPQL 查询

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT E FROM Employee E WHERE E.name = ?1")
    Employee selectByName(String name);

}
```

##### 7.3.2 SQL 查询

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM EMPLOYEE WHERE NAME = ?1", nativeQuery = true)
    Employee selectNativeByName(String name);

}
```

##### 7.3.3 JPQL 分页查询

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT E FROM Employee E WHERE E.sex = ?1")
    Page<Employee> selectPaginationBySex(Sex sex, Pageable pageable);

}
```

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Test
    public void testSelectPaginationBySex() {
        Page<Employee> page = employeeRepository.selectPaginationBySex(Sex.MALE,
                PageRequest.of(0, 10, DESC, "age"));
        for (Employee employee : page.getContent()) {
            System.out.println(employee);
        }
    }

}
```

##### 7.3.4 SQL 分页查询

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM EMPLOYEE WHERE SEX = ?1",
            countQuery = "SELECT COUNT(1) FROM EMPLOYEE WHERE SEX = ?1 ",
            nativeQuery = true)
    Page<Employee> selectNativePaginationBySex(Integer sex, Pageable pageable);

}
```

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaSampleApplication.class})
public class JpaSampleApplicationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Test
    public void testSelectNativePaginationBySex() {
        Page<Employee> page = employeeRepository.selectNativePaginationBySex(Sex.MALE.ordinal(),
                PageRequest.of(0, 10, DESC, "age"));
        for (Employee employee : page.getContent()) {
            System.out.println(employee);
        }
    }

}
```

##### 7.3.5 更新和删除

```java
import org.fanlychie.jpa.sample.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Employee E SET E.salary = ?2 WHERE E.name = ?1")
    int updateSalaryForName(String name, BigDecimal salary);

    @Modifying
    @Transactional
    @Query("DELETE FROM Employee E WHERE E.name = ?1")
    int deleteByName(String name);

}
```

### 8. 日志配置

logback.xml 配置片段：

```xml
<!-- 数据库连接池日志 -->
<logger name="com.zaxxer.hikari.HikariConfig" level="DEBUG"/>
<!-- hibernate引擎日志 -->
<logger name="org.hibernate.engine" level="DEBUG"/>
<!-- hibernate DDL 日志 -->
<logger name="org.hibernate.tool.hbm2ddl" level="DEBUG"/>
<!-- SQL 参数绑定日志 -->
<logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE"/>
```