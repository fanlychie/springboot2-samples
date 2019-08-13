package org.fanlychie.testing.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fanlychie on 2019/6/4.
 */
@Controller
public class SimpleController {

    @GetMapping("/")
    public String index(ModelMap model) {
        model.put("message", "--- Hello Thymeleaf ---");
        return "index";
    }

    @GetMapping("/info")
    public @ResponseBody String info() {
        return "Springboot Testing";
    }

}