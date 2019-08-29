package org.fanlychie.multiple.datasource.sample.entity;

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
    private Long id;

    @Column(length = 16, nullable = false)
    private String name;

}