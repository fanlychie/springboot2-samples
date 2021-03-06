package org.fanlychie.security.sample.security;

import org.fanlychie.security.sample.enums.LockEnum;
import org.fanlychie.security.sample.enums.StatusEnum;
import org.fanlychie.security.sample.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by fanlychie on 2019/7/8.
 */
public class Principal implements UserDetails {

    private static final long serialVersionUID = 9092499507746161929L;

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public Principal(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
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
        if (o instanceof Principal) {
            return getUsername().equals(((Principal) o).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

}