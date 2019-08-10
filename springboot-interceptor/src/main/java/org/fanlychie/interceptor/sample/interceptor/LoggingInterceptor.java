package org.fanlychie.interceptor.sample.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fanlychie on 2019/6/4.
 */
public class LoggingInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String methodName = ((HandlerMethod) handler).getMethod().getName();
        request.setAttribute("_startTimestamp_", System.currentTimeMillis());
        log.info("=====>> [preHandle      ] {}", methodName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("=====>> [postHandle     ] {}", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("=====>> [afterCompletion] {}", System.currentTimeMillis() - (long) request.getAttribute("_startTimestamp_"));
    }

}