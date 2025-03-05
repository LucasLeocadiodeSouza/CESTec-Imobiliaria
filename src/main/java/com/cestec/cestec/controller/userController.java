package com.cestec.cestec.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.sp_userService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class userController {
    
    @Autowired
    private sp_userService userService;

    @GetMapping("/role")
    public Collection<? extends GrantedAuthority> getRoleUser(@PathVariable String user) {
        return userService.getRoleUser(user);
    }
    

}
