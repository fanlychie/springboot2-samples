package org.fanlychie.thymeleaf.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by fanlychie on 2019/6/4.
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(ModelMap model) {
        model.put("message", "--- Hello Thymeleaf ---");
        return "index";
    }

}