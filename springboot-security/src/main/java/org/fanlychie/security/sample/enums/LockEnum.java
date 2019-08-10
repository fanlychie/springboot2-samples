package org.fanlychie.security.sample.enums;

/**
 * Created by fanlychie on 2019/7/4.
 */
public enum LockEnum {

    UNLOCK("解锁"),

    LOCKED("锁定"),

    ;

    private final String value;

    LockEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}