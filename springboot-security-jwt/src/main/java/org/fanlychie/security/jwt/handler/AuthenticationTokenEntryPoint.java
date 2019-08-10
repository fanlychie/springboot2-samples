package org.fanlychie.security.jwt.handler;

import org.fanlychie.security.jwt.enums.ResponseEnum;
import org.fanlychie.security.jwt.model.ResponseResult;
import org.fanlychie.security.jwt.util.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户(未登录用户)访问未经授权的资源
 * Created by fanlychie on 2019/7/16.
 */
public class AuthenticationTokenEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtils.writeAsString(response, ResponseResult.error(ResponseEnum.TOKEN_INVALID));
    }

}