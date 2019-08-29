package org.fanlychie.multiple.datasource.sample.config;

import org.fanlychie.multiple.datasource.sample.enums.DataSourceEnum;
import org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultipleDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("multiple.datasource.test1")
    public DataSource test1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("multiple.datasource.test2")
    public DataSource test2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public RoutingDataSource dataSource(DataSource test1DataSource, DataSource test2DataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceEnum.TEST1, test1DataSource);
        targetDataSource.put(DataSourceEnum.TEST2, test2DataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSource);
        routingDataSource.setDefaultTargetDataSource(test1DataSource);
        return routingDataSource;
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

}