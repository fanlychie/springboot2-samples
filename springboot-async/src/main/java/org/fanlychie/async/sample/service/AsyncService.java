package org.fanlychie.async.sample.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AsyncService {

    @Async
    public void asyncMethod() throws Exception {
        System.out.println("======>> 2");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("======>> 3");
    }

}