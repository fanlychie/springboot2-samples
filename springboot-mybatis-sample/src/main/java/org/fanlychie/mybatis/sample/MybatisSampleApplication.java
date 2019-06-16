package org.fanlychie.mybatis.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by fanlychie on 2019/6/5.
 */
@SpringBootApplication
@MapperScan("org.fanlychie.mybatis.sample.mapper")
public class MybatisSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisSampleApplication.class, args);
    }

}