package com.cestec.cestec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.securityLogin.AuthenticationDTO;
import com.cestec.cestec.model.securityLogin.RegisterDTO;
import com.cestec.cestec.model.securityLogin.loginResponseDTO;
import com.cestec.cestec.model.securityLogin.sp_user;
import com.cestec.cestec.repository.userRepository;
import com.cestec.cestec.service.sp_userService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class authentController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private sp_userService userservice;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private tokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data, HttpServletResponse response) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.passkey());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generatedToken((sp_user) auth.getPrincipal());

        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/"); //disponivel em todo dominio
        cookie.setMaxAge(1 * 24 * 60 * 60); //expira em 1 dia
        response.addCookie(cookie);

        //System.out.println(userservice.getRoleUser(data.login()));

        return ResponseEntity.ok(new loginResponseDTO(token));
    }   

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPasskey = new BCryptPasswordEncoder().encode(data.passkey());
        sp_user user            = new sp_user(data.login(), encryptedPasskey, data.role());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
    
}
