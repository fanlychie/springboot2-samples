## Spring Boot Quick Start

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 概述

Spring Boot 不是一个新的框架，它是提供一种使我们更易于创建基于 Spring 的最小或零配置的独立应用和服务的方式。Spring Boot 的主要目标：

* 为所有的Spring开发提供一个更快，更广泛的入门体验。
* 开箱即用，以最小或零配置的方式，使我们更专注于解决应用程序的功能需求。
* 提供一些非功能性的常见的大型项目类特性（如内嵌服务器、安全、度量、健康检查、外部化配置）。
* 绝对没有代码生成，也不需要XML配置，可以完全避免XML配置
* 为了避免定义更多的注释配置（它将一些现有的 Spring 框架注释组合成一个简单的单个注释）
* 提供一些默认值，以便在任何时间内快速启动新项目。

---

### 2. 依赖配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!-- 继承spring-boot-starter-parent的依赖管理 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/>
    </parent>
    <groupId>org.fanlychie</groupId>
    <artifactId>springboot-quickstart</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>
    <dependencies>
        <!-- 依赖只需声明groupId和artifactId,version由父模块管理 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <!-- 构建可直接运行的Jar包插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### 3. Controller

```java
package org.fanlychie.quickstart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/hello")
    public Object sayHello() {
        return "Hello, World!";
    }

}
```

---

### 4. 主应用程序

Spring Boot 建议我们将主应用程序类置于其他类之上的根包名之下。

```java
package org.fanlychie.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickstartApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickstartApplication.class, args);
	}

}
```

---

### 5. 启动

快速入门样例：

1. 运行`QuickstartApplication.main()`
2. 访问`http://localhost:8080/hello/`