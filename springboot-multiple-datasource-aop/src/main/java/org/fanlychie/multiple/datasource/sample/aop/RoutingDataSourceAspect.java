package org.fanlychie.multiple.datasource.sample.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.fanlychie.multiple.datasource.sample.annotation.DataSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource.*;

@Slf4j
@Aspect
@Component
@Order(1)
public class RoutingDataSourceAspect {

    @Around("execution(public * org.fanlychie.multiple.datasource.sample.mapper..*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 尝试获取方法的注解
        DataSource dataSource = method.getAnnotation(DataSource.class);
        // 方法没有注解则尝试获取接口的注解
        if (dataSource == null) {
            // 直接 getClass() 是代理对象
            dataSource = point.getTarget().getClass().getInterfaces()[0].getAnnotation(DataSource.class);
        }
        if (dataSource == null) {
            DataSourceContextHolder.setDataSource(DataSourceEnum.DB1);
        } else {
            DataSourceContextHolder.setDataSource(dataSource.value());
        }
        log.info("======> 当前数据源: {}", DataSourceContextHolder.getDataSource());
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }

}