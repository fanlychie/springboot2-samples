package org.fanlychie.security.sample.controller;

import org.fanlychie.security.sample.security.annotation.UserManageAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by fanlychie on 2019/6/27.
 */
@Controller
public class UserCenterController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/center")
    public String center() {
        return "center";
    }

    @UserManageAuthorize
    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/expired")
    public String expired() {
        return "expired";
    }

}