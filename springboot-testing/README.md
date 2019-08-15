## Spring Boot Thymeleaf

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

`@SpringBootTest`默认是不会启动WEB容器，而是以MOCK的方式提供一个模拟的WEB环境。它可以和`@AutoConfigureMockMvc`配套使用，在模拟的WEB环境中进行应用测试。
`WebEnvironment.RANDOM_PORT`真正启动WEB容器，并使用随机的端口。

given 上下文
when 行为动作

`@MockBean`所有的行为动作都要预先设定好
`@SpyBean`包含一个真实的对象，可以在保留真实行为的基础上，只mock一部分行为。