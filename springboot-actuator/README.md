## Spring Boot Actuator 应用监控

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 概要

Spring Boot Actuator 是 Spring Boot 项目的子项目。它用于收集和监控应用程序信息。 

---

### 2. 依赖配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

监控应用程序信息需要访问 Spring Boot Actuator 提供的内置 `Endpoints` (端点)，考虑到 Actuator 暴露的监控接口的安全性，可以使用 Security 来验证访问者的身份。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

### 3. 内置 Endpoints

Spring Boot Actuator 支持 RESTful 风格的 HTTP Endpoints 来监控和管理应用程序内部的运行状况。它提供许多内置的 Endpoints，每一个 Endpoints 你都可以选择开启或关闭，开启的 Endpoints 可以通过 `/actuator/<ID>`地址来访问。

| ID | HTTP Method | 描述 |
| :---- | :----: | :---- |
| `auditevents` | GET | 显示应用的审计事件 |
| `beans` | GET | 显示 Spring Bean 列表 |
| `caches` | GET | 显示可用的缓存 |
| `conditions` | GET | 显示自动配置的评估条件，列出匹配和不匹配的原因 |
| `configprops` | GET | 显示所有的`@ConfigurationProperties`列表信息 |
| `env` | GET | 列出 Spring 的`ConfigurableEnvironment`中的属性 |
| `flyway` | GET | 显示`Flyway`数据库迁移信息 |
| `health` | GET | 显示应用的健康状况信息 |
| `httptrace` | GET | 显示 HTTP 跟踪信息(最近100个HTTP Request/Repsponse) |
| `info` | GET | 显示配置的`info.*`信息 |
| `integrationgraph` | GET | 显示 Spring Integration 图 |
| `loggers` | GET | 显示和修改 loggers 配置 |
| `liquibase` | GET | 显示`Liquibase`数据库迁移信息 |
| `metrics` | GET | 显示应用程序的指标信息 |
| `mappings` | GET | 显示`@RequestMapping`路径列表 |
| `scheduledtasks` | GET | 显示计划任务信息 |
| `sessions` | GET | 允许检索和删除用户会话 |
| `shutdown` | POST | 允许应用优雅关闭 |
| `threaddump` | GET | `dump`活动线程信息 |
| `heapdump` | GET | `dump`堆信息 |
| `logfile` | GET | 返回日志文件内容(如果已设置`logging.file`或`logging.path`) |

详见 [https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/htmlsingle/#production-ready-endpoints](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/htmlsingle/#production-ready-endpoints)

---

### 4. 暴露 Endpoints

由于 Endpoints 可能包含敏感信息，Spring Boot Actuator 默认只开放 `health` 和 `info` 两个 Endpoints。

每个 Endpoints 都可以通过`management.endpoint.<ID>.enabled=true/false` 来配置启动或是关闭。例如：

```yaml
management:
  endpoint:
    shutdown:
      enabled: true
```

`management.endpoints.web.exposure.include=Endpoint1[,Endpoint2...]`用于配置要开启的 Endpoints。如：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info
```

`*`可以包含所有的 Endpoints：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

`management.endpoints.web.exposure.exclude=Endpoint1[,Endpoint2...]`用于配置要关闭的 Endpoints。如：

 ```yaml
 management:
   endpoints:
     web:
       exposure:
         exclude: health,info
 ```

---

### 5. 安全 Endpoints

如果开启 Endpoints，我们需要保护好可能含有敏感信息的 Endpoints。如果项目中存在 Spring Security，Spring Boot 默认会使用它来保护 Endpoints。 

项目 `pom.xml` 配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

项目 `application.yml` 配置：

```yaml
# 安全验证
spring:
  security:
    user:
      name: admin
      password: admin123
```

访问所有的`/actuator/<ID>`地址时，系统都会要求验证身份。

---

### 6. 配置文件

```yaml
## actuator 配置
management:
  server:
    # 监控应用端口
    port: 9090
    servlet:
      # 上下文访问路径
      context-path: /
  endpoint:
    shutdown:
      # 允许通过接口(POST方式)安全关闭SpringBoot应用
      enabled: true
    health:
      # 显示健康信息详情
      show-details: always
  endpoints:
    web:
      exposure:
        # 激活所有的Endpoints
        include: "*"

# 安全验证
spring:
  security:
    user:
      name: admin
      password: admin123
```

> Spring Boot 2.0 开始 Actuator 的访问前缀是`/actuator`，如果`context-path`设为`/`，则访问 Endpoints 的地址为`/actuator/<ID>`。若`context-path`设为其它如`/path`，则访问 Endpoints 的地址为`/path/actuator/<ID>`。

---

### 7. 自定义 Endpoints

```java
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "simple")
public class SimpleEndpoint {

    @ReadOperation
    public Object simple(@Selector String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        data.put("description", "it is a simple endpoint");
        return data;
    }

}
```

该 Endpoints 的访问地址为`/actuator/simple/your_message`

* `@Endpoint`：用于定义 Endpoints 的 ID
* `@ReadOperation` / `@WriteOperation` / `@DeleteOperation`：用于定义请求的方法类型
* `@Selector`：用于定义访问 Endpoints 的参数

| Annotation | HTTP Method |
| :---- | :---- |
| `@ReadOperation` | GET |
| `@WriteOperation` | POST |
| `@DeleteOperation` | DELETE |