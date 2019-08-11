package org.fanlychie.security.sample.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理器
 *
 * Created by fanlychie on 2019/7/8.
 */
@Component
public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = ((Principal) authentication.getPrincipal()).getUsername();
        System.out.println("======>> " + username + " 已退出登录");
        super.onLogoutSuccess(request, response, authentication);
    }

}