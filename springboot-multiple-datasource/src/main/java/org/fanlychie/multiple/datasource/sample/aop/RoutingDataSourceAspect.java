package org.fanlychie.multiple.datasource.sample.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.fanlychie.multiple.datasource.sample.context.DataSourceContext;
import org.fanlychie.multiple.datasource.sample.enums.DataSourceEnum;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoutingDataSourceAspect {

    @Before("execution(public * org.fanlychie.multiple.datasource.sample.dao.test1..*(..))")
    public void setDataSourceWithTest1() {
        DataSourceContext.setDataSource(DataSourceEnum.TEST1);
    }

    @Before("execution(public * org.fanlychie.multiple.datasource.sample.dao.test2..*(..))")
    public void setDataSourceWithTest2() {
        DataSourceContext.setDataSource(DataSourceEnum.TEST2);
    }

}