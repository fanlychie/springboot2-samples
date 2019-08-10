package org.fanlychie.jpa.sample.enums;

/**
 * Created by fanlychie on 2019/6/26.
 */
public enum Sex {

    MALE("男"),

    FEMALE("女"),

    ;

    private final String value;

    Sex(String value) {
        this.value = value;
    }

}