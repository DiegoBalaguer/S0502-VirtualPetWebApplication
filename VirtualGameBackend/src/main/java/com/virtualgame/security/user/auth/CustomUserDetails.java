package com.virtualgame.security.user.auth;

import com.virtualgame.entites.userEntity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long userId;
    private final String email;
    @Getter
    private final String name;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public CustomUserDetails(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authorities;

        this.accountNonExpired = user.getAccountNoExpired();
        this.accountNonLocked = user.getAccountNoLocked();
        this.credentialsNonExpired = user.getAccountNoExpired();
        this.enabled = user.getIsEnabled();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
