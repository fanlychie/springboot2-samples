package org.fanlychie.logback.sample.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by fanlychie on 2019/6/16.
 */
@Component
public class LoggingDemoBean implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(LoggingDemoBean.class);

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 20000; i++) {
            log.debug("=====>> log " + i);
        }
//        log.debug("=====> log something ...");
    }

}