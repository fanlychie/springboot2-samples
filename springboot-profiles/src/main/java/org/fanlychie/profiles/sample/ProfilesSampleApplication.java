package org.fanlychie.profiles.sample;

import org.fanlychie.profiles.sample.bean.DataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by fanlychie on 2019/6/22.
 */
@SpringBootApplication
public class ProfilesSampleApplication implements CommandLineRunner {

    @Value("${message}")
    private String message;

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public static void main(String[] args) {
        SpringApplication.run(ProfilesSampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========================================");
        System.out.println(message);
        System.out.println(dataSourceConfig.getDataSource());
        System.out.println("========================================");
    }

}