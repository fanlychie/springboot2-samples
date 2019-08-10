package org.fanlychie.security.jwt.security.filter;

import org.fanlychie.security.jwt.entity.Role;
import org.fanlychie.security.jwt.security.UserInfoDetails;
import org.fanlychie.security.jwt.util.JWTTokentUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源授权处理
 * Created by fanlychie on 2019/7/16.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader(JWTTokentUtils.getHeaderName());
        UserInfoDetails userDetails = JWTTokentUtils.getUser(token);
        if (userDetails != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role : userDetails.getUserRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

}