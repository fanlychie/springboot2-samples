package org.fanlychie.security.jwt.param;

import lombok.Data;

@Data
public class LoginParam {

    private String username;

    private String password;

}