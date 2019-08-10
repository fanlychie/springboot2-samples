package org.fanlychie.security.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by fanlychie on 2019/6/27.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 会话过期策略
    // 实现类：SessionExpiredStrategy
    @Autowired
    private SessionInformationExpiredStrategy sessionExpiredStrategy;

    // 加载用户信息
    // 实现类：UserServiceImpl
    @Autowired
    private UserDetailsService userDetailsService;

    // 用于记住我功能
    // 采用内置的 JdbcTokenRepositoryImpl
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 登录成功处理
    // 实现类：AuthenticationSuccessHandlerImpl
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    // 登录失败处理
    // 实现类：AuthenticationFailureHandlerImpl
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    // 登出成功处理
    // 实现类：LogoutSuccessHandlerImpl
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

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

    // 用于记住我功能
    // 需要先建立 persistent_logins 表, JdbcTokenRepositoryImpl 源码中有建表语句
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 授权请求配置
            .authorizeRequests()
                // 设置访问不需要授权的资源地址
                // ant 匹配模式：
                // '?'： 匹配一个字符；
                // '*'： 匹配 0 到多个字符；
                // '**'：匹配 0 到多个目录；
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**", "/**/favicon.ico").permitAll()
                // 其它资源需要授权才能访问
                .anyRequest().authenticated()
                .and()
            // 表单登录配置
            .formLogin()
                // 登录页面
                .loginPage("/login")
                // 处理登录页面提交表单登录的地址(POST)
                .loginProcessingUrl("/login")
                // 登录页面表单中用户名输入框的名称
                .usernameParameter("username")
                // 登录页面表单中密码输入框的名称
                .passwordParameter("password")
                // 登录成功跳转页面(简单的跳转, 这里采用Handler处理更加复杂的逻辑)
                // .successForwardUrl("/center")
                // 登录成功后的处理
                .successHandler(authenticationSuccessHandler)
                // 登录失败跳转页面(简单的跳转, 这里采用Handler处理更加复杂的逻辑)
                // .failureUrl("/login?error=true")
                // 登录失败后的处理
                .failureHandler(authenticationFailureHandler)
                .and()
            // 登出配置
            .logout()
                // 登出地址
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // 登出成功跳转的页面(简单的跳转, 这里采用Handler处理更加复杂的逻辑)
                // .logoutSuccessUrl("/")
                // 使会话过期(会使登出后的页面跳转到登录页面, 而非设置的 logoutSuccessUrl)
                // .invalidateHttpSession(true)
                // 退出登录的处理
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
            // 记住我
            .rememberMe()
                // 登录页面表单中记住我复选框的名称
                .rememberMeParameter("remember-me")
                // 持久化token
                .tokenRepository(persistentTokenRepository())
                // 过期时间
                .tokenValiditySeconds(120)
                .userDetailsService(userDetailsService)
                .and()
            // 会话配置
            .sessionManagement()
                // 会话过期跳转页面
                .invalidSessionUrl("/login")
                // 同一个账户只允许在一处登录, 后来者登录系统会踢出先前登录的用户
                .maximumSessions(1).expiredSessionStrategy(sessionExpiredStrategy)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}