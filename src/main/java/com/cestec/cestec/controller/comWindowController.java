package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.service.sp_userService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/home") // URL base para acessar
public class comWindowController {

    @Autowired
    private sp_userService sp_userService;

    @Autowired
    private tokenService tokenService;

    @GetMapping("/userlogin")
    public String getUserName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("authToken".equals(cookie.getName())){
                    String token = cookie.getValue();
                    String username = tokenService.getExtractedUsernameFromToken(token);
                    System.out.println("Username: " + username);
                    return username;
                }
            }
        }
        return "";
    }

    @GetMapping("/userid")
    public Integer getUserId(HttpServletRequest request) {
        Integer id = sp_userService.getIdByUserName(getUserName(request));
        return id;
    }
}