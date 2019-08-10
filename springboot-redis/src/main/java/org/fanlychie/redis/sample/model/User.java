package org.fanlychie.redis.sample.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;

    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}