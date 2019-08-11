package org.fanlychie.security.sample.controller;

import org.fanlychie.security.sample.model.User;
import org.fanlychie.security.sample.security.Principal;
import org.fanlychie.security.sample.security.annotation.UserManageAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String center(ModelMap model) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("authorities", principal.getAuthorities());
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

    @GetMapping("/info")
    @UserManageAuthorize
    public @ResponseBody User info() {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

}