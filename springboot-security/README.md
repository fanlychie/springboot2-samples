## Spring Boot Security

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 依赖配置

在 pom.xml 文件中配置：

```xml
<dependencies>
    <!-- web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <!-- security taglib -->
    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity5</artifactId>
    </dependency>
    <!-- jpa -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <!-- jdbc -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.46</version>
        <scope>runtime</scope>
    </dependency>
    <!-- thymeleaf -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
    <!-- 用于禁用模板文件缓存和启动热加载 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

### 2. 项目配置

在项目配置文件 application.yml 中配置：

```yaml
## tomcat 配置
server:
  tomcat:
    uri-encoding: UTF-8
  # 运行时端口
  port: 8080
  # 连接超时时间
  connection-timeout: 5000
  servlet:
    # 上下文访问路径
    context-path: /

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
        enable_lazy_load_no_trans: true # 懒加载
  # 禁用缓存, 否则修改页面等需要重新编译或重启
  thymeleaf:
    cache: false
```

---

### 3. 日志配置

采用 logback，配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <!-- 自定义属性信息 -->
    <property name="LOG_PATTERN" value="%date{yyyy-MM-dd HH:mm:ss} - [%-5level] [%-8thread] %-36logger{36} : %msg%n"/>
    <!-- 控制台日志配置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- 日志文件输出格式 -->
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
    </appender>
    <root level="INFO">
        <!-- 写到控制台 -->
        <appender-ref ref="CONSOLE"/>
    </root>
    <!-- 数据库连接池日志 -->
    <logger name="com.zaxxer.hikari.HikariConfig" level="DEBUG"/>
    <!-- hibernate引擎日志 -->
    <logger name="org.hibernate.engine" level="DEBUG"/>
    <!-- hibernate DDL 日志 -->
    <logger name="org.hibernate.tool.hbm2ddl" level="DEBUG"/>
    <!-- SQL 参数绑定日志 -->
    <logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE"/>
</configuration>
```

---

### 4. 数据库表设计

采用 MySQL，建表语句如下：

```sql
-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` datetime NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `remark` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ROLE_ADMIN', '管理员');
INSERT INTO `sys_role` VALUES ('2', 'ROLE_SALESMAN', '业务员');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(60) NOT NULL,
  `password_error_times` int(11) NOT NULL,
  `locked` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$MqKEw55C4Zaki1bsT8B6Lu565H9LwrVhMPAvz5Y5pd0qcn9a2/BoK', '1', '0', '1', '2019-07-05 14:23:39');
INSERT INTO `sys_user` VALUES ('2', 'salesman', '$2a$10$MBU9yqkMiSkq6W0znSCkPuNWKv4KWSlCaLGVOgjYG0u58wPbNEzJ6', '0', '0', '1', '2019-07-08 09:56:20');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKb40xxfch70f5qnyfw8yme1n1s` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
```

---

### 5. 实体类

persistent_logins 是用于记住我 (remember me)，它由`JdbcTokenRepositoryImpl`自动管理，不需要建立对应的实体类。sys_user_role 是中间表，不需要建立对应的实体类。只需要为 sys_user、sys_role 建立对应的实体类即可。

```java
import lombok.Data;
import org.fanlychie.security.sample.enums.LockEnum;
import org.fanlychie.security.sample.enums.StatusEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "SYS_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer passwordErrorTimes;

    @Column(nullable = false)
    private LockEnum locked;

    @Column(nullable = false)
    private StatusEnum status;

    @Column(nullable = false)
    private Date createTime;

    @ManyToMany
    @JoinTable(name = "SYS_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

}
```

```java
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "SYS_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 64)
    private String remark;

}
```

---

### 6. 用户信息

用户登入系统时，Spring Security 会将用户信息包装成一个`UserDetails`实例。因此可以自行实现该接口，并进行自定义逻辑处理。

```java
import org.fanlychie.security.sample.enums.LockEnum;
import org.fanlychie.security.sample.enums.StatusEnum;
import org.fanlychie.security.sample.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class UserInfoDetails implements UserDetails {

    private static final long serialVersionUID = 9092499507746161929L;

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public UserInfoDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked().ordinal() == LockEnum.UNLOCK.ordinal();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().ordinal() == StatusEnum.ENABLED.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserInfoDetails) {
            return getUsername().equals(((UserInfoDetails) o).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

}
```

> 注：实现`UserDetails`接口需要重写`equals`和`hashCode`方法，否则下面的限制只能一个用户同时登录会失败。Spring Security 判断两个用户是否相同是调用`equals`，因此需要重写。

用户登入系统后，UserDetails 可以通过 SecurityContextHolder 获取：

```java
UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
```

---

### 7. 登入成功处理

通过实现`AuthenticationSuccessHandler`接口，可以对用户登入系统成功后做一些其它的事情。

这里选择继承`SavedRequestAwareAuthenticationSuccessHandler`（内置提供的`AuthenticationSuccessHandler`的实现类）。它提供的`onAuthenticationSuccess`方法可以在用户登入系统成功后跳转到用户上次访问的页面地址。

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfoDetails user = (UserInfoDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.getSession().setAttribute("user", user);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
```

---

### 8. 登入失败处理

通过实现`AuthenticationFailureHandler`接口，可以对用户登入系统失败后做一些其它的事情。

这里选择继承`SimpleUrlAuthenticationFailureHandler`（内置提供的`AuthenticationFailureHandler`的实现类）。它提供的`onAuthenticationFailure`方法可以重定向到指定的登入错误页面。

```java
import org.fanlychie.security.sample.dao.UserRepository;
import org.fanlychie.security.sample.enums.LockEnum;
import org.fanlychie.security.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private static final String AUTHENTICATION_EXCEPTION = "AUTHENTICATION_EXCEPTION";

    @Autowired
    private UserRepository userRepository;

    public AuthenticationFailureHandlerImpl() {
        super("/login?error=true");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (exception instanceof LockedException) {
            session.setAttribute(AUTHENTICATION_EXCEPTION, "密码输入错误次数超过3次，您的账户已经被锁定！");
        } else if (exception instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            User user = userRepository.findByUsername(username);
            if (user != null) {
                int passwordErrorTimes = user.getPasswordErrorTimes();
                if (passwordErrorTimes >= 3) {
                    if (user.getLocked().ordinal() == LockEnum.UNLOCK.ordinal()) {
                        user.setLocked(LockEnum.LOCKED);
                    }
                } else {
                    user.setPasswordErrorTimes(passwordErrorTimes + 1);
                }
                userRepository.save(user);
            }
            session.setAttribute(AUTHENTICATION_EXCEPTION, "用户名或密码错误！");
        }
        super.onAuthenticationFailure(request, response, exception);
    }

}
```

---

### 9. 退出登录处理

通过实现`LogoutSuccessHandler`接口，可以对用户登入系统失败后做一些其它的事情。

这里选择继承`SimpleUrlLogoutSuccessHandler`（内置提供的`LogoutSuccessHandler`的实现类）。它提供的`onLogoutSuccess`方法可以重定向到应用的首页。

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        System.out.println("======>> " + username + " 已退出登录");
        super.onLogoutSuccess(request, response, authentication);
    }

}
```

---

### 10. session 过期处理

通过实现`SessionInformationExpiredStrategy`接口，可以对 session 过期的用户做一些其它的处理。

```java
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class SessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/expired");
    }

}
```

---

### 11. 查询用户信息

用户登入系统时，Spring Security 会根据用户名到数据库中查找对应的用户信息。我们需要实现`UserDetailsService`接口，以实现查询用户信息的逻辑。

```java
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

}
```

```java
import org.fanlychie.security.sample.dao.UserRepository;
import org.fanlychie.security.sample.model.Role;
import org.fanlychie.security.sample.model.User;
import org.fanlychie.security.sample.security.Principal;
import org.fanlychie.security.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.hasText(username)) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (Role role : user.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                return new Principal(user, authorities);
            }
        }
        throw new UsernameNotFoundException("user \"" + username + "\" does not exits.");
    }

}
```

---

### 12. 自定义 Spring Security 配置
        
通过继承`WebSecurityConfigurerAdapter`对 Spring Security 进行个性化配置：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 会话过期策略
    // 实现类：SessionExpiredStrategy
    @Autowired
    private SessionInformationExpiredStrategy sessionExpiredStrategy;

    // 加载用户信息
    // 实现类：UserServiceImpl
    @Autowired
    private UserDetailsService userDetailsService;

    // 用于记住我功能
    // 采用内置的 JdbcTokenRepositoryImpl
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 登录成功处理
    // 实现类：AuthenticationSuccessHandlerImpl
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    // 登录失败处理
    // 实现类：AuthenticationFailureHandlerImpl
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    // 登出成功处理
    // 实现类：LogoutSuccessHandlerImpl
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    // 用户密码采用 BCRYPT 加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 基于 DAO 认证
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // 用于记住我功能
    // 需要先建立 persistent_logins 表, JdbcTokenRepositoryImpl 源码中有建表语句
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 授权请求配置
            .authorizeRequests()
                // 设置访问不需要授权的资源地址
                // ant 匹配模式：
                // '?'： 匹配一个字符；
                // '*'： 匹配 0 到多个字符；
                // '**'：匹配 0 到多个目录；
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**", "/**/favicon.ico").permitAll()
                // 其它资源需要授权才能访问
                .anyRequest().authenticated()
                .and()
            // 表单登录配置
            .formLogin()
                // 登录页面
                .loginPage("/login")
                // 处理登录页面提交表单登录的地址(POST)
                .loginProcessingUrl("/login")
                // 登录页面表单中用户名输入框的名称
                .usernameParameter("username")
                // 登录页面表单中密码输入框的名称
                .passwordParameter("password")
                // 登录成功跳转页面(简单的跳转, 这里采用Handler处理更加复杂的逻辑)
                // .successForwardUrl("/center")
                // 登录成功后的处理
                .successHandler(authenticationSuccessHandler)
                // 登录失败跳转页面(简单的跳转, 这里采用Handler处理更加复杂的逻辑)
                // .failureUrl("/login?error=true")
                // 登录失败后的处理
                .failureHandler(authenticationFailureHandler)
                .and()
            // 登出配置
            .logout()
                // 登出地址
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // 登出成功跳转的页面(简单的跳转, 这里采用Handler处理更加复杂的逻辑)
                // .logoutSuccessUrl("/")
                // 使会话过期(会使登出后的页面跳转到登录页面, 而非设置的 logoutSuccessUrl)
                // .invalidateHttpSession(true)
                // 退出登录的处理
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
            // 记住我
            .rememberMe()
                // 登录页面表单中记住我复选框的名称
                .rememberMeParameter("remember-me")
                // 持久化token
                .tokenRepository(persistentTokenRepository())
                // 过期时间
                .tokenValiditySeconds(120)
                .userDetailsService(userDetailsService)
                .and()
            // 会话配置
            .sessionManagement()
                // 会话过期跳转页面
                .invalidSessionUrl("/login")
                // 同一个账户只允许在一处登录, 后来者登录系统会踢出先前登录的用户
                .maximumSessions(1).expiredSessionStrategy(sessionExpiredStrategy)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}
```

---

### 13. 登录页面

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>登录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/main.css}">
</head>
<body>
<form th:action="@{/login}" method="POST">
    <fieldset>
        <legend>User Login</legend>
        <p class="error" th:if="${param.error}">
            <th:block th:text="${session.AUTHENTICATION_EXCEPTION}"/>
        </p>
        <p>
            <label for="username">用户名</label>
            <input type="text" name="username" id="username" autofocus="autofocus">
        </p>
        <p>
            <label for="password">密码</label>
            <input type="password" name="password" id="password">
        </p>
        <p>
            <input type="checkbox" name="remember-me" id="remember-me" checked="checked"/><label for="remember-me">记住我</label>
        </p>
        <button type="submit">登录</button>
    </fieldset>
</form>
</body>
</html>
```

表单以`POST`的方式提交，不需要自己编写对应的 Controller 方法。

---

### 13. 基于表达式的访问控制

Spring Security 基于根对象`SecurityExpressionRoot`提供了一些常用的表达式，用于实现访问权限控制。

| 表达式 | 描述 |
| :---- | :---- |
| `hasRole([role])` | 如果当前用户拥有指定的角色，则返回true。角色名称默认以`ROLE_`开头，如果没有则会自动加上 |
| `hasAnyRole([role1,role2])` | 和上类似。如果当前用户拥有指定角色中的任意一个，则返回true |
| `hasAuthority([authority])` | 如果当前用户拥有指定的权限，则返回true |
| `hasAnyAuthority([authority1,authority2])` | 和上类似。如果当前用户拥有指定权限中的任意一个，则返回true |
| `principal` | 访问代表当前用户的主体对象 (`UserDetails`) |
| `authentication` | 访问代表当前用户的`Authentication`对象 |
| `permitAll` | 总是返回true |
| `denyAll` | 总是返回false |
| `isAnonymous()` | 如果当前用户是匿名用户，则返回true |
| `isRememberMe()` | 如果当前用户是通过`remember-me`登入系统，则返回true |
| `isAuthenticated()` | 如果用户不是匿名用户，则返回true |
| `isFullyAuthenticated()` | 如果用户不是匿名用户或`remember-me`，则返回true |
| `hasPermission(Object target, Object permission)` | 如果用户拥有permission权限，且以该权限可以访问目标对象target，则返回true |
| `hasPermission(Object targetId, String targetType, Object permission)` | 如果用户拥有permission权限，且以该权限可以访问目标ID为targetId且目标类型为targetType，则返回true |

---

### 14. Spring Security 标签

声明依赖：

```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```

在页面中引入命名空间：

```html
<html xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
```

示例：

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>个人中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/main.css}">
</head>
<body>
    <fieldset>
        <legend>User Info</legend>
        <p>
            Welcome <th:block sec:authentication="principal.username"/> ( id = <th:block th:text="${session.user.id}"/> ).
        </p>
        <p>
            <th:block sec:authorize="hasAnyRole('ROLE_ADMIN')">
                <a href="javascript:alert('这是个测试')" th:text="编辑个人信息"/>
            </th:block>
            <a th:href="@{/logout}" th:text="退出"/>
        </p>
    </fieldset>
</body>
</html>
```

> `sec:authentication`：用于输出用户相关信息，如用户名、角色权限等。

> `sec:authorize`：用于权限认证，当表达式评估的结果为true时，显示内容。

---

### 15. 方法访问控制

通过使用`@PreAuthorize`注解，可以控制方法只能被指定角色的用户访问到。

```java
@PreAuthorize("hasRole('ROLE_READ')")
@GetMapping("/user/list")
public String list() {
    return "list";
}
```

如果当前用户没拥有 ROLE_READ 角色，访问该方法会报 403 错误。

---

### 16. 自定义注解

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public @interface UserManageAuthorize {

}
```

使用自定义注解需要通过`@EnableGlobalMethodSecurity`开启注解功能。见 [自定义Spring Security配置](#12-自定义-spring-security-配置)。

```java
@UserManageAuthorize
@GetMapping("/add")
public String add() {
    return "add";
}
```