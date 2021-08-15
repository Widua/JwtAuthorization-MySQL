package me.widua.jwtauthorization.authorization;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum Roles {
    USER
    ;

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
