package org.fanlychie.multiple.datasource.sample.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource;
import org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource.DataSourceEnum;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultipleDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.db1")
    public DataSource test1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.db2")
    public DataSource test2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public RoutingDataSource routingDataSource(@Qualifier("test1DataSource") DataSource test1DataSource,
                                               @Qualifier("test2DataSource") DataSource test2DataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceEnum.DB1, test1DataSource);
        targetDataSource.put(DataSourceEnum.DB2, test2DataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSource);
        routingDataSource.setDefaultTargetDataSource(test1DataSource);
        return routingDataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(@Qualifier("routingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return factory.getObject();
    }

}