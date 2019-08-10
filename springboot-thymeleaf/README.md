## Spring Boot Thymeleaf

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 概述

`Thymeleaf`是一个 XML/XHTML/HTML5 模板引擎，它能完全替代`JSP`模板引擎。`Spring Boot`采用`Thymeleaf`作为视图技术的默认模板引擎。

---

### 2. 依赖配置

在 pom.xml 中添加：

```xml
<!-- 依赖只需声明groupId和artifactId,version由父模块管理 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- thymeleaf 3.0 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<!-- 用于禁用模板文件缓存和启动热加载 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

---

### 3. Controller

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index(ModelMap model) {
        model.put("message", "--- Hello Thymeleaf ---");
        return "index";
    }

}
```

---

### 4. thymeleaf

Spring Boot 对`Thymeleaf`模板引擎提供了自动配置的支持，详见 [ThymeleafProperties.java#L42](https://github.com/spring-projects/spring-boot/blob/v2.1.6.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/thymeleaf/ThymeleafProperties.java#L42)。

我们只需遵循约定，在`/src/main/resources/templates/`目录创建相应的页面模板文件（`*.html`）即可。

创建 src/main/resources/templates/index.html 文件

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Thymeleaf Index</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/main.css}">
</head>
<body>
    <h1 th:text="${message}"></h1>
</body>
</html>
```

---

### 5. 静态资源文件

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

### 6. 热加载

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
