package org.fanlychie.redis.sample.annotation;

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