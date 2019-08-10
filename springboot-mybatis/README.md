## Spring Boot MyBatis

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 依赖配置

在 pom.xml 配置文件中添加：

```xml
<dependencies>
    <!-- 依赖只需声明groupId和artifactId,version由父模块管理 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <!-- mybatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.0.1</version>
    </dependency>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.46</version>
        <scope>runtime</scope>
    </dependency>
    <!-- druid 数据库连接池 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.1.16</version>
    </dependency>
    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

---

### 2. 数据源配置

这里采用 druid 数据库连接池。在 application.yml 配置文件中添加：

```yaml
spring:
  datasource:
    # 连接池配置（https://github.com/alibaba/druid/blob/master/druid-spring-boot-starter/src/test/resources/application.properties）
    druid:
      # 数据源名称（用于监控（http://ip:port/druid/）时区分多个不同的数据源）
      name: test-database
      # 数据库链接地址
      url: jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true&useUnicode=true&characterEncoding=utf-8
      # 数据库连接用户名
      username: root
      # 数据库连接密码
      password: root
      # 数据库链接驱动
      driver-class-name: com.mysql.jdbc.Driver
      # 初始化连接池个数
      initial-size: 2
      # 最小连接池个数
      min-idle: 2
      # 最大连接池个数
      max-active: 20
      # 配置获取连接等待超时的时间，单位毫秒，缺省启用公平锁，并发效率会有所下降
      max-wait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      time-between-eviction-runs-millis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      min-evictable-idle-time-millis: 300000
      # 用来检测连接是否有效的sql，要求是一个查询语句。
      # 如果validationQuery为 null，testOnBorrow、testOnReturn、testWhileIdle 都不会起作用
      validation-query: select 1
      # 申请连接的时候检测链接是否有效
      test-while-idle: true
      # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
      test-on-borrow: false
      # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
      test-on-return: false
      # 启用 PreparedStatement Cache
      pool-prepared-statements: true
      # PreparedStatement Cache 最大数量
      max-pool-prepared-statement-per-connection-size: 20
      # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
      connection-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
      # 合并多个DruidDataSource的监控数据
      use-global-data-source-stat: true
      # 通过别名的方式配置扩展插件，多个英文逗号分隔，常用的插件有：
      # 监控统计用的filter:stat
      # 日志用的filter:log4j
      # 防御sql注入的filter:wall
      filters: stat,wall,log4j2
      # StatFilter 配置
      web-stat-filter:
        # 启用
        enabled: true
        url-pattern: "/*"
        exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
      # StatViewServlet 配置
      stat-view-servlet:
        # 启用
        enabled: true
        url-pattern: "/druid/*"
        # 允许访问的IP
        allow: 127.0.0.1,192.168.1.222
        # 登录账户
        login-username: admin
        # 登录密码
        login-password: admin
        # 禁止的IP
        deny: 192.168.2.223
        # 是否允许重置
        reset-enable: true
      # Spring监控AOP切入点，多个用英文逗号分隔
      aop-patterns: org.fanlychie.mybatis.sample.mapper.*

mybatis:
  # mybatis 配置文件
  config-location: classpath:mybatis-config.xml
  # mapper.xml 配置文件路径
  mapper-locations: classpath:mapper/*.xml
  # 别名
  type-aliases-package: org.fanlychie.mybatis.sample.model
```

---

### 3. 数据访问

```sql
-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk COMMENT='员工表';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '张三', '20');
INSERT INTO `employee` VALUES ('2', '李四', '23');
```

```java
@Data
public class Employee {

    private Long id;

    private String name;

    private Integer age;

}
```

```java
public interface EmployeeMapper {

    Long save(Employee employee);

    List<Employee> findAll();

}
```

```xml
<mapper namespace="org.fanlychie.mybatis.sample.mapper.EmployeeMapper">

	<insert id="save" useGeneratedKeys="true" keyProperty="id" parameterType="Employee">
		INSERT INTO EMPLOYEE (
			ID, NAME, AGE
		) VALUES (
			#{id}, #{name}, #{age}
		)
	</insert>

	<select id="findAll" resultMap="EmployeeResultMap">
		SELECT * FROM EMPLOYEE
	</select>

	<resultMap id="EmployeeResultMap" type="Employee">
		<id property="id" column="ID" />
		<result property="name" column="NAME" />
		<result property="age" column="AGE" />
	</resultMap>

</mapper>
```

---

### 4. 单元测试

```java
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
```