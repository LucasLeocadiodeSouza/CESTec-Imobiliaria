package com.cestec.cestec.model.securityLogin;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sp_user")
public class sp_user implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String  login;
    private String  passkey;
    
    @Enumerated(EnumType.STRING)
    private userRole  role;

    public sp_user(){}

    public sp_user(String login, String passkey, userRole role){
        this.login   = login;
        this.passkey = passkey;
        this.role    = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == userRole.SALER) return List.of(new SimpleGrantedAuthority("ROLE_SALER"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role == userRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role == userRole.SUPER) return List.of(new SimpleGrantedAuthority("ROLE_SUPER"), new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role == userRole.COORD) return List.of(new SimpleGrantedAuthority("ROLE_COORD"), new SimpleGrantedAuthority("ROLE_SUPER"), new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if(this.role == userRole.DIREC) return List.of(new SimpleGrantedAuthority("ROLE_DIREC"), new SimpleGrantedAuthority("ROLE_COORD"), new SimpleGrantedAuthority("ROLE_SUPER"), new SimpleGrantedAuthority("ROLE_SALER"), new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return passkey;
    }
    @Override
    public String getUsername() {
        return login;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPasskey() {
        return passkey;
    }
    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }
}
