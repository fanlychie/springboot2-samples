package org.fanlychie.interceptor.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanlychie on 2019/6/4.
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public Object index() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Hello World");
        return map;
    }

}