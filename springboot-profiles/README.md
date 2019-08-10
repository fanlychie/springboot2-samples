## Spring Boot Profiles 配置

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 多环境配置

Spring Boot 约定使用`application-{profile}.[properties|yml]`作为某个`profile`环境的环境配置文件。

在没有激活任何`profile`环境的环境配置时，则默认使用`application.[properties|yml]`作为项目配置文件。

如果激活了某个`profile`环境的环境配置时，则使用多环境共用的`application.[properties|yml]`和`application-{profile}.[properties|yml]`共同作为项目配置文件，且`application-{profile}.[properties|yml]`中的配置会覆盖`application.[properties|yml]`中的配置。

#### 1.1 配置方式

开发环境配置 `application-dev.yml`

```yaml
message: it is the dev environment.
```

测试环境配置 `application-test.yml`

```yaml
message: it is the test environment.
```

生产环境配置 `application-prod.yml`

```yaml
message: it is the prod environment.
```

也可以在同一个文件`application.yml`中配置多个不同环境的配置。使用`---`作为不同环境之间的分隔。默认环境不需要指定`spring.profiles`，其它环境需要使用`spring.profiles=<profile_name>`来指定环境的`profile`。

```yaml
## 默认配置
spring:
  profiles:
    active: dev

---

## 开发环境配置
spring:
  profiles: dev
message: it is the dev environment.

---

## 测试环境配置
spring:
  profiles: test
message: it is the test environment.

---

## 生产环境配置
spring:
  profiles: prod
message: it is the prod environment.
```

#### 1.2 激活方式

使用`spring.profiles.active=<profile_name>[,<profile_name>...]`来激活特定环境的配置文件。

##### 1.2.1 配置文件激活

项目默认配置文件 `application.yml` 中配置：

```yaml
spring:
  profiles:
    active: dev
```

##### 1.2.2 命令行激活

```sh
java -jar springboot-profiles-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

#### 1.3 属性文件

参考 [项目配置文件](https://gitee.com/fanlychie/springboot2-samples/tree/develop/springboot-property#9-项目配置文件) 和 [命令行](https://gitee.com/fanlychie/springboot2-samples/tree/develop/springboot-property#8-命令行)

---

## 2. @Profile 注解

`@Profile`注解可以在`@Component`(`@Service` `@Repository`)或`@Configuration`标注的类中使用。它可以将不同`profile`环境的代码区分隔离开来。

```java
// 接口, 以下分别有MySQL和Oracle的实现
public interface DataSourceConfig {

    Object getDataSource();

}
```

```java
@Component
@Profile("mysql") // 若激活的profiles中包含mysql, 则实例化此类
public class MySqlDataSource implements DataSourceConfig {

    @Override
    public Object getDataSource() {
        return "[[ MySQL ]]";
    }

}
```

```java
@Component
@Profile("oracle") // 若激活的profiles中包含oracle, 则实例化此类
public class OracleDataSource implements DataSourceConfig {

    @Override
    public Object getDataSource() {
        return "[[ Oracle ]]";
    }

}
```

若激活的`profiles`中包含`mysql`环境，则注入`MySqlDataSource`，如包含`oracle`环境，则注入`OracleDataSource`。若都不包含，则会抛出异常。

```java
@Autowired
private DataSourceConfig dataSourceConfig;
```