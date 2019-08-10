package org.fanlychie.security.jwt.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fanlychie.security.jwt.dao.UserRepository;
import org.fanlychie.security.jwt.entity.User;
import org.fanlychie.security.jwt.enums.LockEnum;
import org.fanlychie.security.jwt.enums.ResponseEnum;
import org.fanlychie.security.jwt.model.ResponseResult;
import org.fanlychie.security.jwt.param.LoginParam;
import org.fanlychie.security.jwt.security.UserInfoDetails;
import org.fanlychie.security.jwt.util.JWTTokentUtils;
import org.fanlychie.security.jwt.util.ResponseUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录处理
 * Created by fanlychie on 2019/7/16.
 */
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private UserRepository userRepository;

    private static ThreadLocal<Map<Object, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public JWTLoginFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userRepository = userRepository;
        super.setAuthenticationManager(authenticationManager);
    }

    // 登录前执行认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginParam loginParam = new ObjectMapper().readValue(request.getInputStream(), LoginParam.class);
            if (checkLoginParam(loginParam, response)) {
                threadLocal.get().put(LoginParam.class, loginParam);
                return getAuthenticationManager().authenticate(
                        new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword(), Collections.emptyList()));
            }
        } catch (IOException e) {
            log.error("读取请求数据异常", e);
        }
        return null;
    }

    // 登录成功执行
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserInfoDetails user = (UserInfoDetails) authResult.getPrincipal();
        String token = JWTTokentUtils.createToken(user);
        ResponseUtils.writeAsString(response, ResponseResult.success(token));
    }

    // 登录失败执行
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof LockedException) {
            ResponseUtils.writeAsString(response, ResponseResult.error(ResponseEnum.ACCOUNT_IS_LOCKED));
        } else if (failed instanceof BadCredentialsException) {
            String username = ((LoginParam)threadLocal.get().get(LoginParam.class)).getUsername();
            User user = userRepository.findByUsername(username);
            if (user != null) {
                int passwordErrorTimes = user.getPasswordErrorTimes();
                if (passwordErrorTimes >= 3) {
                    if (user.getLocked().ordinal() == LockEnum.UNLOCK.ordinal()) {
                        user.setLocked(LockEnum.LOCKED);
                    }
                } else {
                    user.setPasswordErrorTimes(passwordErrorTimes + 1);
                }
                userRepository.save(user);
            }
            ResponseUtils.writeAsString(response, ResponseResult.error(ResponseEnum.WRONG_USERNAME_OR_PASSWORD));
        }
    }

    // 检查登录参数
    private boolean checkLoginParam(LoginParam loginParam, HttpServletResponse response) {
        if (!StringUtils.hasText(loginParam.getUsername())) {
            ResponseUtils.writeAsString(response, ResponseResult.error(ResponseEnum.USERNAME_CAN_NOT_BE_EMPTY));
            return false;
        }
        if (!StringUtils.hasText(loginParam.getPassword())) {
            ResponseUtils.writeAsString(response, ResponseResult.error(ResponseEnum.PASSWORD_CAN_NOT_BE_EMPTY));
            return false;
        }
        return true;
    }

}