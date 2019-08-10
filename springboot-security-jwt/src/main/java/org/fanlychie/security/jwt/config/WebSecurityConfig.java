package org.fanlychie.security.jwt.config;

import org.fanlychie.security.jwt.dao.UserRepository;
import org.fanlychie.security.jwt.handler.AuthenticationAccessDeniedHandler;
import org.fanlychie.security.jwt.handler.AuthenticationTokenEntryPoint;
import org.fanlychie.security.jwt.security.filter.JWTAuthenticationFilter;
import org.fanlychie.security.jwt.security.filter.JWTLoginFilter;
import org.fanlychie.security.jwt.util.JWTTokentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security 配置
 * Created by fanlychie on 2019/7/4.
 */
@Configuration
@EnableConfigurationProperties(JWTTokentUtils.class)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 加载用户信息
    // 实现类：UserServiceImpl
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    // 用户密码采用 BCRYPT 加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 基于 DAO 认证
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁止 CSRF
            .csrf().disable()
            // 允许跨域访问
            .cors().and()
            // 授权请求配置
            .authorizeRequests()
                // 设置访问不需要授权的资源地址
                // ant 匹配模式：
                // '?'： 匹配一个字符；
                // '*'： 匹配 0 到多个字符；
                // '**'：匹配 0 到多个目录；
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                // 其它资源需要授权才能访问
                .anyRequest().authenticated()
                .and()
            // 表单登录配置
            .formLogin()
                // 处理登录页面提交表单登录的地址(POST)
                .loginProcessingUrl("/login")
                // 登录页面表单中用户名输入框的名称
                .usernameParameter("username")
                // 登录页面表单中密码输入框的名称
                .passwordParameter("password")
                .and()
            // 登出配置
            .logout()
                // 登出地址
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
            // 登录过滤器
            .addFilterBefore(new JWTLoginFilter(authenticationManager(), userRepository), UsernamePasswordAuthenticationFilter.class)
            // 授权认证过滤器
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            // 已登录用户访问没有权限的资源
            .exceptionHandling().accessDeniedHandler(new AuthenticationAccessDeniedHandler())
            // 匿名用户(未登录用户)访问未经授权的资源
            .authenticationEntryPoint(new AuthenticationTokenEntryPoint())
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}