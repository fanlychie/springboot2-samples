package org.fanlychie.security.jwt.enums;

/**
 * Created by fanlychie on 2019/7/4.
 */
public enum ResponseEnum {

    USERNAME_CAN_NOT_BE_EMPTY(10001, "用户名不能为空"),

    PASSWORD_CAN_NOT_BE_EMPTY(10002, "密码不能为空"),

    ACCOUNT_IS_LOCKED(10003, "密码输入错误次数超过3次，您的账户已经被锁定"),

    WRONG_USERNAME_OR_PASSWORD(10004, "用户名或密码错误"),

    ACCESS_DENIED(10005, "权限不足，你无法访问该资源"),

    TOKEN_INVALID(10006, "你还没有登录系统，无法访问该资源"),


    ;

    private final int code;

    private final String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}