## Spring Boot Interceptor 拦截器

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 什么是拦截器

在 Spring 中，当一个请求发送到 Spring 的 Controller 时，在请求达到 Controller 前，会经过一系列的 Interceptor。Interceptor 能够让你在请求到达 Controller 之前执行某项任务，如认证用户身份、写日志等。你可以通过以下两种方式来实现一个 Interceptor：

* `implements` `org.springframework.web.servlet.HandlerInterceptor`
* `extends` `org.springframework.web.servlet.handler.HandlerInterceptorAdapter`

---

### 2. 拦截器的实现

日志拦截器样例，`implements` `org.springframework.web.servlet.HandlerInterceptor` 实现：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String methodName = ((HandlerMethod) handler).getMethod().getName();
        request.setAttribute("_startTimestamp_", System.currentTimeMillis());
        log.info("=====>> [preHandle      ] {}", methodName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("=====>> [postHandle     ] {}", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("=====>> [afterCompletion] {}", System.currentTimeMillis() - (long) request.getAttribute("_startTimestamp_"));
    }

}
```

登录拦截器样例，`extends` `org.springframework.web.servlet.handler.HandlerInterceptorAdapter`实现：

```java
import org.fanlychie.interceptor.sample.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=====>> [preHandle      ] LoginInterceptor");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("CURRENT_USER");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("=====>> [postHandle     ] LoginInterceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("=====>> [afterCompletion] LoginInterceptor");
    }

}
```

* `preHandle`  ：在 Controller 执行之前执行。返回`true`请求能够继续达到 Controller。返回`false`请求不会达到 Controller。
* `postHandle`：在 Controller 执行之后执行。
* `afterCompletion`：在`postHandle`执行之后执行。

---

### 3. 拦截器配置

```java
import org.fanlychie.interceptor.sample.interceptor.LoggingInterceptor;
import org.fanlychie.interceptor.sample.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器拦截所有请求
        registry.addInterceptor(new LoggingInterceptor())
                .addPathPatterns("/*");
        // 登录拦截器拦截所有请求, 除了 "/index", "/login"
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns("/index", "/login");
    }

}
```