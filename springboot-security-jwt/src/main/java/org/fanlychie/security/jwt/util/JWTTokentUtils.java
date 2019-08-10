package org.fanlychie.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.fanlychie.security.jwt.security.UserInfoDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 令牌工具
 * Created by fanlychie on 2019/7/16.
 */
@ConfigurationProperties("jwt.token")
public final class JWTTokentUtils {

    // header 名称
    @Getter
    private static String headerName;

    // 密钥
    private static String secret;

    // 过期时间
    private static long expire;

    private static final String JWT_CLAIMS_NAME_KEY = "JWT_CLAIMS_NAME";

    // 创建令牌
    public static String createToken(UserInfoDetails user) {
        return Jwts.builder()
                .setClaims(asMap(user))
                .setSubject(user.getUsername())
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(expire)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 获取用户名
    public static String getUserName(String token) {
        Claims claims = getClaims(token);
        return claims == null ? null : claims.getSubject();
    }

    // 获取用户信息
    public static UserInfoDetails getUser(String token) {
        Claims claims = getClaims(token);
        return claims == null ? null : claims.get(JWT_CLAIMS_NAME_KEY, UserInfoDetails.class);
    }

    private static Map<String, Object> asMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_CLAIMS_NAME_KEY, obj);
        return map;
    }

    private static Claims getClaims(String token) {
        if (StringUtils.hasText(token)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        }
        return null;
    }

    public static String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        JWTTokentUtils.headerName = headerName;
    }

    public void setSecret(String secret) {
        JWTTokentUtils.secret = secret;
    }

    public void setExpire(long expire) {
        JWTTokentUtils.expire = expire;
    }

}
