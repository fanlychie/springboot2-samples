package org.fanlychie.redis.sample.service;

import org.fanlychie.redis.sample.annotation.RedisCache;
import org.fanlychie.redis.sample.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    // 通过注解使用缓存
    @Cacheable(value = "CACHE_USER", keyGenerator = "genericKeyGenerator", cacheManager = "genericCacheManager")
    public User findByName(String name) {
        return queryBy(null, name);
    }

    @RedisCache
    public User fetchByName(String name) {
        return queryBy(null, name);
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