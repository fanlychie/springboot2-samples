package org.fanlychie.security.jwt.handler;

import org.fanlychie.security.jwt.enums.ResponseEnum;
import org.fanlychie.security.jwt.model.ResponseResult;
import org.fanlychie.security.jwt.util.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 已登录用户访问没有权限的资源
 * Created by fanlychie on 2019/7/16.
 */
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtils.writeAsString(response, ResponseResult.error(ResponseEnum.ACCESS_DENIED));
    }

}