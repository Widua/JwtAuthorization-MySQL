package me.widua.jwtauthorization.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity(name = "users")
public class UserModel implements UserDetails {

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<SimpleGrantedAuthority> authorities;
    private String password;
    @Id
    private String username ;
    private boolean isEnabled ;
    private String secretCode ;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public UserModel() {}

    public UserModel(String username, String password, Set<SimpleGrantedAuthority> authorities, boolean isEnabled) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "authorities=" + authorities +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", isEnabled=" + isEnabled +
                ", secretCode='" + secretCode + '\'' +
                '}';
    }
}
