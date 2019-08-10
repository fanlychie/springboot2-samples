## Spring Boot application*.[yml|properties] 配置文件配置

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 配置加载的优先级顺序

Spring Boot 允许通过`*.properties`文件、`*.yml`文件、环境变量、命令行参数等来外部化应用程序的配置，加载的优先级顺序为：

01. 测试类上标注的`@TestPropertySource`
02. 测试类上标注的`@SpringBootTest(properties=...)`
03. 命令行参数(`--property_name=...`)
04. `ServletConfig`初始化参数
05. `ServletContext`初始化参数
06. 来自`java:comp/env`的`JNDI`属性
07. `JAVA`系统属性(通过`System.getProperties()`能获取到的)
08. 操作系统环境变量
09. 含`random.*`的属性
10. `JAR`包外部的`application-{profile}.{properties|yml}`
11. `JAR`包内部的`application-{profile}.{properties|yml}`
12. `JAR`包外部的`application.{properties|yml}`
13. `JAR`包内部的`application.{properties|yml}`
14. 类上标注的`@PropertySource`
15. 启动类设置的默认属性`SpringApplication.setDefaultProperties(...)`

Spring Boot 加载`application.{properties|yml}`配置文件的优先级顺序为：

01. 项目根路径下的`/config`目录
02. 项目根路径
03. 项目类路径下的`/config`目录
04. 项目类路径

---

### 2. @ConfigurationProperties 注解

`@ConfigurationProperties("前缀限定名")`注解可以将配置文件中配置的属性绑定到结构化的对象中。

```yaml
administrators:
  username: admin
  password: admin
```

```java
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("administrators")
public class AdministratorsBean {

    private String username;

    private String password;

}
```

---

### 3. @Value 注解

`@Value("${属性名称}")`注解可以将属性的值注入到`Bean`对象的字段中。

```yaml
project: Spring Boot 2.x Property Sample
author-email: fanlychie@yeah.net
```

```java
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ValueBean {

    @Value("${project}")
    private String project;

    @Value("${author-email}")
    private String authorEmail;

}
```

---

### 4. 属性绑定

Spring Boot 对`*.properties`和`*.yml`配置文件中配置的属性名称提供了松绑定，它不要求配置文件中的属性名称与`Bean`中的字段名称完全一致。如`Bean`中的`firstName`字段，在配置文件中以下几种命名方式都能与其绑定：

| 属性名称 | 描述 |
| :---- | :---- |
| `firstName` | 标准的驼峰式命名 |
| `first-name` | 单词之间通过`-`分隔，Spring Boot 推荐这种 |
| `first_name` | 单词之间通过`_`分隔 |
| `FIRST_NAME` | 单词全部大写并通过`_`分隔，在使用系统环境变量时，推荐这种 |

---

### 5. 随机种子

Spring Boot 内部提供了一个`random.*`属性，专门用于生成随机种子。

| 随机种子 | 描述 |
| :---- | :---- |
| `random.int` | `Random.nextInt()`产生的一个随机整数(含负数) |
| `random.int(n)` | `Random.nextInt(n)`产生的[0, n)区间的整数 |
| `random.int(n,m)` | `n + Random.nextInt(m)`产生的[n, m)区间的整数 |
| `random.uuid` | 产生一个`UUID`字符串 |
| `random.value` | 产生一个32位长度的字符串 |

Spring Boot 也支持`random.long`，用法和`random.int`相同。

另外`random.int(n)`等类型的解析只与括号内部的内容有关, 与是否是括号无关，你也可以写成`random.int[n]`。

详见[RandomValuePropertySource.java](https://github.com/spring-projects/spring-boot/blob/v2.1.6.RELEASE/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/env/RandomValuePropertySource.java)

---

### 6. @PropertySource 注解

`@PropertySource("文件位置")`注解可以用来声明当前所使用的特定的属性配置文件(`*.properties`)。默认的，Spring Boot 会到项目的根目录`/`下查找该文件，可以使用`classpath:`指定到类路径下查找。

information.properties 文件配置如下：

```properties
my.name = fanlychie
my.mail = fanlychie@yeah.net
```

```java
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:information.properties")
@ConfigurationProperties("my")
public class InformationBean {

    private String name;

    private String mail;

}
```

---

### 7. @ConfigurationProperties 校验数据

在`@ConfigurationProperties`注解标注的类中，可以使用`JSR-303`相关的约束注解对绑定的属性值进行验证。

```yaml
contact:
  name: fanlychie
  mail: fanlychie@yeah.net
  addr: 广东省广州市天河区天河路228号
```

```java
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Component
@ConfigurationProperties("contact")
@Validated // 开启校验
public class ContactJSR303Bean {

    @NotBlank(message = "联系人姓名不能为空")
    private String name;

    @Email(message = "联系人邮箱不能为空")
    private String mail;

    @Size(min = 10, max = 18, message = "联系人地址长度应在[10,18]区间")
    private String addr;

}
```

---

### 8. 命令行

使用 JAVA 命令行参数`-D<参数>=<值>`来设置系统属性，可以改变配置文件中配置的属性或系统默认的属性。

```sh
java -jar -Dcontact.name="Lychie Fan" springboot-property-0.0.1-SNAPSHOT.jar
```

使用命令行参数`--<参数>=<值>`来设置 Spring 环境的属性，可以改变配置文件中配置的属性或默认的属性。

```sh
java -jar springboot-property-0.0.1-SNAPSHOT.jar --contact.name="Lychie Fan"
```

在命令行中，`-D`参数需要写在`JAR`包之前，`--`参数需要写在`JAR`包之后。

---

### 9. 项目配置文件

Spring Boot 默认使用`application.properties|yml`作为项目的配置文件。

可以通过`--spring.config.name=name[,name2...]`参数来改变项目使用的配置文件名称。

```sh
java -jar springboot-property-0.0.1-SNAPSHOT.jar --spring.config.name=my
```

Spring Boot 按照[配置加载的优先级顺序](#1-配置加载的优先级顺序)加载配置文件。

可以通过`--spring.config.location=location[,location2...]`参数来改变 Spring Boot 加载配置文件的位置。

```sh
java -jar springboot-property-0.0.1-SNAPSHOT.jar --spring.config.location=D:/data/configuration/
```

> 注：如果`--spring.config.location`指定的路径中包含目录分隔符`/`，则必须要以`/`结束，否则加载不到指定目录的配置文件。

> `--spring.config.name`和`--spring.config.location`常用于多环境项目启动时的参数设置。