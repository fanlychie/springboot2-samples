package org.fanlychie.logback.sample.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by fanlychie on 2019/6/16.
 */
@Slf4j
@Component
public class LoggingDemoBean implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.debug("=====> log something ...");
    }

}