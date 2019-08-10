package org.fanlychie.security.sample.security;

import org.fanlychie.security.sample.dao.UserRepository;
import org.fanlychie.security.sample.enums.LockEnum;
import org.fanlychie.security.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录失败处理器
 *
 * Created by fanlychie on 2019/7/8.
 */
@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private static final String AUTHENTICATION_EXCEPTION = "AUTHENTICATION_EXCEPTION";

    @Autowired
    private UserRepository userRepository;

    public AuthenticationFailureHandlerImpl() {
        super("/login?error=true");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (exception instanceof LockedException) {
            session.setAttribute(AUTHENTICATION_EXCEPTION, "密码输入错误次数超过3次，您的账户已经被锁定！");
        } else if (exception instanceof BadCredentialsException) {
            String username = request.getParameter("username");
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
            session.setAttribute(AUTHENTICATION_EXCEPTION, "用户名或密码错误！");
        }
        super.onAuthenticationFailure(request, response, exception);
    }

}