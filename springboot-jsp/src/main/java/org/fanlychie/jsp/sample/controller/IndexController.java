package org.fanlychie.jsp.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by fanlychie on 2019/6/24.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(ModelMap model) {
        model.put("message", "--- Welcome ---");
        return "index";
    }

}