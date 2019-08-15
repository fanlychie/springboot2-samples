package org.fanlychie.testing.sample.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.*;

@Data
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 64, nullable = false)
    private String mail;

}