package org.fanlychie.security.jwt.security;

import org.fanlychie.security.jwt.entity.Role;
import org.fanlychie.security.jwt.enums.LockEnum;
import org.fanlychie.security.jwt.enums.StatusEnum;
import org.fanlychie.security.jwt.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by fanlychie on 2019/7/8.
 */
public class UserInfoDetails implements UserDetails {

    private static final long serialVersionUID = 9092499507746161929L;

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public UserInfoDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public Long getId() {
        return user.getId();
    }

    public List<Role> getUserRoles() {
        return user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked().ordinal() == LockEnum.UNLOCK.ordinal();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().ordinal() == StatusEnum.ENABLED.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserInfoDetails) {
            return getUsername().equals(((UserInfoDetails) o).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

}