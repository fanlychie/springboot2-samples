## Spring Boot Redis

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 依赖配置

pom.xml 依赖配置：

```xml
<dependencies>
    <!-- redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!-- redis lettuce 连接池依赖 -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
    <!-- jackson -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-json</artifactId>
    </dependency>
    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
    <!-- test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

### 2. Redis 连接配置

application.yml 配置：

```yaml
spring:
  # redis 配置
  redis:
    # 主机
    host: 192.168.182.101
    # 端口
    port: 6379
    # 使用的数据库索引
    database: 0
    # 如果有密码
    password: 123456
    # 连接超时时间
    timeout: 3000
    # lettuce 连接池配置
    lettuce:
      pool:
        # 池中允许的最大连接数(负值表示无限制)
        max-active: 8
        # 池中允许的最大空闲连接数(负值表示无限制)
        max-idle: 8
        # 当池中没有可用连接时, 获取连接等待的时间(负值表示无限制)
        max-wait: -1ms
        # 池中维护的最小连接数
        min-idle: 2
  # 缓存配置
  cache:
    redis:
      # 是否允许缓存 null 值
      cache-null-values: true
      # 对象过期时间
      time-to-live: 10m
      # 缓存对象的 KEY 的前缀
      key-prefix: "RDS_"
      # 是否允许使用前缀
      use-key-prefix: false
```

> Spring Boot 2.0 之前 Redis 默认是采用 Jedis 作为客户端连接，Spring Boot 2.0 之后默认采用的是 Lettuce 作为 Redis 的客户端连接。

> Jedis 采用的是阻塞的I/O，所有方法的调用都是同步的，不支持异步操作，且是线程不安全的，所以通常需要使用连接池来为每个 Redis 实例增加物理连接。

> Lettuce 是基于 Netty 框架的事件驱动通信，方法的调用是异步，且是线程安全的。

---

### 3. Redis 缓存配置

```java
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.time.Duration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class RedisConfig {

    /**
     * RedisTemplate 配置, 如果代码中需要使用到 RedisTemplate 时, 可配置
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    /**
     * 缓存键的生成策略, eg.
     * com.xxx.Sample#sayHello(fanlychie, 23)
     */
    @Bean
    public KeyGenerator genericKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder builder = new StringBuilder();
                builder.append(target.getClass().getName()).append("#")
                        .append(method.getName()).append("(");
                if (!ObjectUtils.isEmpty(params)) {
                    for (Object param : params) {
                        builder.append(param.toString()).append(", ");
                    }
                    int length = builder.length();
                    builder.delete(length - 2, length);
                }
                String cacheKeyName = builder.append(")").toString();
                // 考虑到 KEY 可能过长, 可以做一次信息摘要处理
                cacheKeyName = DigestUtils.md5DigestAsHex(cacheKeyName.getBytes(Charset.forName("UTF-8")));
                return cacheKeyName;
            }
        };
    }

    /**
     * 缓存管理配置
     */
    @Bean
    public CacheManager genericCacheManager(RedisConnectionFactory factory, CacheProperties cacheProperties) {
        return RedisCacheManager.builder(factory)
                .cacheDefaults(redisCacheConfiguration(cacheProperties, Duration.ofSeconds(60))).build();
    }

    // Redis 缓存配置
    private RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties, Duration defaultTimeToLive) {
        // spring.cache.redis 配置
        CacheProperties.Redis cacheRedisConfig = cacheProperties.getRedis();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                // KEY 序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // VALUE 序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(defaultTimeToLive);
        if (cacheRedisConfig.getTimeToLive() != null) {
            configuration = configuration.entryTtl(cacheRedisConfig.getTimeToLive());
        }
        if (cacheRedisConfig.getKeyPrefix() != null) {
            configuration = configuration.prefixKeysWith(cacheRedisConfig.getKeyPrefix());
        }
        if (!cacheRedisConfig.isCacheNullValues()) {
            configuration = configuration.disableCachingNullValues();
        }
        if (!cacheRedisConfig.isUseKeyPrefix()) {
            configuration = configuration.disableKeyPrefix();
        }
        return configuration;
    }

}
```

> `KeyGenerator` 是用于生成缓存对象的 KEY 字符串。默认装配的 SimpleKeyGenerator 是根据调用方法时传递的参数值作为缓存对象的键值，当调用不同的方法传递相同的参数值时，会造成缓存数据存取错误的问题。

> `CacheManager` 用于管理缓存。默认装配的 RedisCacheManager 是采用 JdkSerializationRedisSerializer 作为对象的序列化实现。这里换成 Jackson，并设置缓存对象的过期时间。CacheManager 也可以不配置而采用默认的装配。

> `@EnableCaching` 用于开启支持缓存注解。

> `@EnableConfigurationProperties` 用于装配 CacheProperties 配置，即 YAML/Properties 配置文件中的 spring.cache 的配置。

---

### 4. RedisTemplate 运用

```java
import org.fanlychie.redis.sample.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger log = LoggerFactory.getLogger(UserService.class);

    private static final List<User> USERS = Arrays.asList(
            new User(1, "Tom"),
            new User(2, "Jerry")
    );

    // 通过 RedisTemplate 操作缓存
    public User findById(Integer id) {
        User user = null;
        String key = "USER_" + id;
        if (redisTemplate.hasKey(key)) {
            log.info("=======>> [Cache] hit " + key);
            user = (User) redisTemplate.opsForValue().get(key);
        } else {
            log.info("=======>> [Cache] miss " + key);
            user = queryBy(id, null);
            log.info("=======>> [Cache] put " + key);
            redisTemplate.opsForValue().set(key, user);
        }
        return user;
    }

    // 模拟查库
    private User queryBy(Integer id, String name) {
        log.info("=======>> [Query] query user");
        if (id != null && id > 0) {
            for (User user : USERS) {
                if (user.getId().equals(id)) {
                    return user;
                }
            }
        }
        if (StringUtils.hasText(name)) {
            for (User user : USERS) {
                if (user.getName().equals(name)) {
                    return user;
                }
            }
        }
        return null;
    }

}
```

---

### 5. 注解应用

```java
// 通过注解使用缓存
@Cacheable(value = "CACHE_USER", keyGenerator = "genericKeyGenerator", cacheManager = "genericCacheManager")
public User findByName(String name) {
    return queryBy(null, name);
}
```

> genericKeyGenerator 是 RedisConfig 中注册的 KeyGenerator Bean 的名称。

> genericCacheManager 是 RedisConfig 中注册的 CacheManager Bean 的名称。

---

### 6. 自定义注解

像<br>`@Cacheable(value = "CACHE_USER", keyGenerator = "genericKeyGenerator", cacheManager = "genericCacheManager")`<br>这样的注解不太易于编写，可以将其重新包装成另外一个标注的注解类：

```java
import org.springframework.cache.annotation.Cacheable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Cacheable(cacheNames = "DEFAULT_CACHE", keyGenerator = "genericKeyGenerator", cacheManager = "genericCacheManager")
public @interface RedisCache {
}
```

使用：

```java
@RedisCache
public User fetchByName(String name) {
    return queryBy(null, name);
}
```

---

### 7. 哨兵模式

其它不变，application.yml 配置如下：

```yaml
spring:
  # redis 配置
  redis:
    # 使用的数据库索引
    database: 0
    # 如果有密码
    password: 123456
    # 连接超时时间
    timeout: 3000
    # 哨兵配置
    sentinel:
      master: mymaster
      nodes:
        - 192.168.182.101:26379
        - 192.168.182.101:26380
        - 192.168.182.101:26381
    # lettuce 连接池配置
    lettuce:
      pool:
        # 池中允许的最大连接数(负值表示无限制)
        max-active: 8
        # 池中允许的最大空闲连接数(负值表示无限制)
        max-idle: 8
        # 当池中没有可用连接时, 获取连接等待的时间(负值表示无限制)
        max-wait: -1ms
        # 池中维护的最小连接数
        min-idle: 2
  # 缓存配置
  cache:
    redis:
      # 是否允许缓存 null 值
      cache-null-values: true
      # 对象过期时间
      time-to-live: 10m
      # 缓存对象的 KEY 的前缀
      key-prefix: "RDS_"
      # 是否允许使用前缀
      use-key-prefix: false
```