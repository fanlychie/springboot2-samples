package org.fanlychie.batch.config.writer;

import org.fanlychie.batch.dto.CustomerDto;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by fanlychie on 2019/7/23.
 * @author fanlychie
 */
@Configuration
public class CustomerItemWriter {

    @Bean
    public JdbcBatchItemWriter<CustomerDto> jdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CustomerDto>()
                // 设置数据源
                .dataSource(dataSource)
                // 写库的SQL
                .sql("INSERT INTO CUSTOMER(ID, NAME, MOBILE, AGE) values (:id, :name, :mobile, :age)")
                // 使用实例的字段值作为SQL语句参数的值
                .beanMapped()
                // 构建
                .build();
    }

}