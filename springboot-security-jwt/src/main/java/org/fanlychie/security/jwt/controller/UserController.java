package org.fanlychie.security.jwt.controller;

import org.fanlychie.security.jwt.model.ResponseResult;
import org.fanlychie.security.jwt.security.annotation.UserManageAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping
    public ResponseResult index() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseResult.success("Hi " + username);
    }

    @UserManageAuthorize
    @GetMapping("/user/add")
    public ResponseResult add() {
        return ResponseResult.success("Congratulations, you can add users.");
    }

}