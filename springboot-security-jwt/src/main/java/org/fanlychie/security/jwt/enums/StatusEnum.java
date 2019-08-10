package org.fanlychie.security.jwt.enums;

/**
 * Created by fanlychie on 2019/7/4.
 */
public enum StatusEnum {

    DISABLED("禁用"),

    ENABLED("启用"),

    ;

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}