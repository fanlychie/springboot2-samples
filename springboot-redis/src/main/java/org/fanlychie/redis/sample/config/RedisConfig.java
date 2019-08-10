package org.fanlychie.redis.sample.config;

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

/**
 * Created by fanlychie on 2019/7/12.
 */
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