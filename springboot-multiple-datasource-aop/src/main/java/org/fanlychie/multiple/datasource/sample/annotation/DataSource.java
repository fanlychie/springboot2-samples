package org.fanlychie.multiple.datasource.sample.annotation;

import org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource.DataSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataSource {

    DataSourceEnum value();

}