package org.fanlychie.multiple.datasource.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by fanlychie on 2019/6/26
 */
@SpringBootApplication
@MapperScan("org.fanlychie.multiple.datasource.sample.mapper")
public class MultipleDataSourceAOPSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleDataSourceAOPSampleApplication.class, args);
    }

}