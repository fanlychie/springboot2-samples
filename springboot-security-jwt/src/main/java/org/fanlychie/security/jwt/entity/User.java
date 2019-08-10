package org.fanlychie.security.jwt.entity;

import lombok.Data;
import org.fanlychie.security.jwt.enums.LockEnum;
import org.fanlychie.security.jwt.enums.StatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by fanlychie on 2019/7/4.
 */
@Data
@Entity
@Table(name = "SYS_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer passwordErrorTimes;

    @Column(nullable = false)
    private LockEnum locked;

    @Column(nullable = false)
    private StatusEnum status;

    @Column(nullable = false)
    private Date createTime;

    @ManyToMany
    @JoinTable(name = "SYS_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

}