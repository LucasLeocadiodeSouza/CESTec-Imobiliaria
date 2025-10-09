package com.cestec.cestec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.securityLogin.AuthenticationDTO;
import com.cestec.cestec.model.securityLogin.RegisterDTO;
import com.cestec.cestec.model.securityLogin.loginResponseDTO;
import com.cestec.cestec.model.securityLogin.sp_user;
import com.cestec.cestec.repository.userRepository;
import com.cestec.cestec.service.genService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

    @Autowired
    private genService gen;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data, HttpServletResponse response) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.passkey());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generatedToken((sp_user) auth.getPrincipal());

        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/"); //disponivel em todo dominio
        cookie.setMaxAge(1 * 24 * 60 * 60); //expira em 1 dia
        response.addCookie(cookie);

        return ResponseEntity.ok(new loginResponseDTO(token));
    }   

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); //expira imaediatamente
        response.addCookie(cookie);

        return ResponseEntity.ok("OK");
    }   

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPasskey = new BCryptPasswordEncoder().encode(data.passkey());
        sp_user user            = new sp_user(data.login(), encryptedPasskey, data.role());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/pathAplication")
    public ResponseEntity pathAplication(@RequestParam(value = "path", required = false) String path, @RequestParam(value = "codmod", required = false) Integer codmod, @RequestParam(value = "codapl", required = false) Integer codapl, HttpServletResponse response) {
        var tokenModulo  = codmod.toString();
        var tokenDescMod = URLEncoder.encode(gen.getDescricaoModulo(codmod), StandardCharsets.UTF_8);
        var tokenApl     = codapl.toString();
        var tokenDescApl = URLEncoder.encode(gen.getDescricaoAplicacao(codapl), StandardCharsets.UTF_8);

        Cookie cookieMod = new Cookie("codmodulo", tokenModulo);
        cookieMod.setHttpOnly(false);
        cookieMod.setSecure(true);
        cookieMod.setPath(path);

        Cookie cookieDescMod = new Cookie("descmodulo", tokenDescMod);
        cookieDescMod.setHttpOnly(false);
        cookieDescMod.setSecure(true);
        cookieDescMod.setPath(path);

        Cookie cookieApl = new Cookie("codaplicacao", tokenApl);
        cookieApl.setHttpOnly(false);
        cookieApl.setSecure(true);
        cookieApl.setPath(path);

        Cookie cookieDescApl = new Cookie("descapl", tokenDescApl);
        cookieDescApl.setHttpOnly(false);
        cookieDescApl.setSecure(true);
        cookieDescApl.setPath(path);

        response.addCookie(cookieMod);
        response.addCookie(cookieDescMod);
        response.addCookie(cookieApl);
        response.addCookie(cookieDescApl);

        return ResponseEntity.ok().build();
    } 
}
