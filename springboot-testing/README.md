## Spring Boot Unit Testing

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### SpringRunner

基于Spring环境的Junit单元测试，通常会标注`@RunWith(SpringRunner.class)`来改变Junit的运行环境，让它运行在Spring的测试环境中，这样Junit能够得到Spring上下文环境的支持（如使可以用`@Autowired`注解装配的依赖对象）。

### @SpringBootTest

当你的测试代码中需要用到Spring Boot功能时，需要使用该注解。

`@SpringBootTest`默认是不会启动WEB容器，而是以MOCK的方式提供一个模拟的WEB环境。它可以和`@AutoConfigureMockMvc`配套使用，在模拟的WEB环境中进行应用测试。

如果测试需要启动WEB容器，可以使用`@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`标注。它会真正启动一个WEB容器，并使用一个随机的端口。

当运行Spring Boot应用程序测试时，它会自动的从当前测试类所在的包起一层一层向上搜索，直到找到一个`@SpringBootApplication`注解的类为止。以此来确定如何装载Spring应用程序的上下文资源。只要你以合理的方式组织你的代码，你项目的主配置类通常是可以被发现的。

given 上下文

when 行为动作

`@MockBean`所有的行为动作都要预先设定好

`@SpyBean`包含一个真实的对象，可以在保留真实行为的基础上，只mock一部分行为。