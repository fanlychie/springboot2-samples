package org.fanlychie.async.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Autowired
    private AsyncService asyncService;

    public void demoMethod() throws Exception {
        System.out.println("======>> 1");
        asyncService.asyncMethod();
        System.out.println("======>> 4");
    }

}