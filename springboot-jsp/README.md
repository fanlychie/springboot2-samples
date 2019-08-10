## Spring Boot JSP

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 概要

Spring 支持许多展示层技术（如`JSP`，`Thymeleaf`，`Freemarker`，`Mustache`）。Spring Boot 默认采用`Thymeleaf`作为展示层的模板引擎技术，并由 Spring Boot 自动配置。如果展示层选择`JSP`，则需要对其进行配置，Spring Boot 并不推荐使用`JSP`作为模板引擎。

---

### 2. 依赖配置

在 pom.xml 中添加：

```xml
<!-- 依赖只需声明groupId和artifactId,version由父模块管理 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- JSP编译依赖 -->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
</dependency>
<!-- 热加载, 禁用模板缓存 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

---

### 3. 项目配置

Spring Boot 对`JSP`模板引擎没有提供自动配置的支持，使用`JSP`引擎需要自行配置一些信息。在 application.yml 中配置：

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
  mvc:
    # JSP 配置
    view:
      prefix: /WEB-INF/jsp/
      suffix: .jsp
```

---

### 4. Controller

```java
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(ModelMap model) {
        model.put("message", "--- Welcome ---");
        return "index";
    }

}
```

---

### 5. JSP

JSP 文件存放的位置为：

`src/main/webapp` + `${spring.mvc.view.prefix}` + `${controller_return_string}` + `${spring.mvc.view.suffix}`

> 其中`${controller_return_string}`为 Controller 方法返回的字符串值。

创建文件：src/main/webapp/WEB-INF/jsp/index.jsp

```jsp
<%@ page pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Index Page</title>
        <link href="/css/main.css" rel="stylesheet"/>
    </head>
    <body>
        <h1>${message}</h1>
    </body>
</html>
```

---

### 6. 静态资源文件

Spring Boot 默认将静态资源文件映射到类路径的目录下：

* `/META-INF/resources/`
* `/resources/`
* `/static/`
* `/public/`

详见 [ResourceProperties.java#L41](https://github.com/spring-projects/spring-boot/blob/v2.1.6.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/ResourceProperties.java#L41)。

因此我们可以将 css、js、images 等静态资源文件放在`src/main/resources/static/`目录下。

创建文件：src/main/resources/static/css/main.css

```css
h1 {
    color: #0000FF;
}
```

---

### 7. 热加载

用于解决在 IDE 上修改模板文件和静态资源文件时浏览器刷新没有效果，必须要重启项目才有效果的问题。

pom.xml 添加 Spring Boot Dev Tools 支持：

```xml
<!-- 热加载, 禁用模板缓存 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

IntelliJ IDEA 打开设置项(`Ctrl` `Alt` `S`)：

`Build, Execution, Deployment` --> `Compiler` --> 勾选 `Build project automatically`

面板打开 Actions 项(`Ctrl` `Shift` `A`)，输入查找`registry...`，勾选`compiler.automake.allow.when.app.running`








