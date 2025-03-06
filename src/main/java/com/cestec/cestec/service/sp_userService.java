package com.cestec.cestec.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import com.cestec.cestec.repository.userRepository;

@Service
public class sp_userService implements UserDetailsService {

    @Autowired
    private userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
    
    @GetMapping()
    public Collection<? extends GrantedAuthority> getRoleUser(String username){
        return userRepository.findByLogin(username).getAuthorities();
    }

    public Integer getIdByUserName(String username){
        return userRepository.findIdByLogin(username).getId();
    }
}
