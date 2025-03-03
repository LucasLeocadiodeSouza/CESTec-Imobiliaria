package com.cestec.cestec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.AuthenticationDTO;
import com.cestec.cestec.model.RegisterDTO;
import com.cestec.cestec.model.loginResponseDTO;
import com.cestec.cestec.model.sp_user;
import com.cestec.cestec.repository.userRepository;

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
    private userRepository userRepository;

    @Autowired
    private tokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.passkey());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generatedToken((sp_user) auth.getPrincipal());
        
        System.out.println("token: " + token);

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
