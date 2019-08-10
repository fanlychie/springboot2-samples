package org.fanlychie.batch.config.reader;

import org.fanlychie.batch.entity.Customer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.nio.charset.StandardCharsets;

/**
 * Created by fanlychie on 2019/7/23.
 * @author fanlychie
 */
@Configuration
public class CustomerItemReader {

    // Flat File 是一种包含了用ASCII码记录的, 每个表单元由逗号分隔, 用行表示记录组的文件。这种文件页叫做用逗号分隔数值(CSV)的文件。
    // @Value("${input.file.name}") 是程序启动时通过参数[-Dinput.file.name=D:/demo.csv]或[--input.file.name=D:/demo.csv]注入
    @Bean
    public FlatFileItemReader<Customer> flatFileItemReader(@Value("${input.file.name}") String resource) {
        return new FlatFileItemReaderBuilder<Customer>()
                // 实例的名称
                .name("customer-csv-file-reader")
                // 采用的编码
                .encoding(StandardCharsets.UTF_8.name())
                // 读取的数据来源
                .resource(new FileSystemResource(resource))
                // 跳过的行, 通常第一行是标题行
                .linesToSkip(1)
                // 分隔值
                .delimited()
                    // 分隔符
                    .delimiter(",")
                    // 文件中单元格列按顺序映射到的实体字段名称列表
                    .names(new String[]{"name", "mobile", "age"})
                // 构建
                .build();
    }

}