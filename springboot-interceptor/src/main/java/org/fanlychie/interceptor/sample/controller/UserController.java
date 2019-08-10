package org.fanlychie.interceptor.sample.controller;

import org.fanlychie.interceptor.sample.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by fanlychie on 2019/6/24.
 */
@RestController
public class UserController {

    private static final String DEFAULT_NAME = "admin";

    private static final String DEFAULT_PASSWORD = "admin123";

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpSession session, User user) {
        if (DEFAULT_NAME.equals(user.getName()) && DEFAULT_PASSWORD.equals(user.getPassword())) {
            session.setAttribute("CURRENT_USER", user);
            return "welcome " + user.getName();
        }
        return "error";
    }

    @GetMapping("/welcome")
    public String welcome(String name) {
        return "welcome " + name;
    }

}