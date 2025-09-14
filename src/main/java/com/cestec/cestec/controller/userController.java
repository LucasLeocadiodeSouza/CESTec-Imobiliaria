package com.cestec.cestec.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.sp_userService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private sp_userService userService;

    @Autowired
    private genService gen;

    @GetMapping("/role")
    public Collection<? extends GrantedAuthority> getRoleUser(@PathVariable String user) {
        return userService.getRoleUser(user);
    }
    
    @GetMapping("/getMinimizarCabecalho")
    public ResponseEntity<?> getMinimizarCabecalho(HttpServletRequest request){
        try {
            return ResponseEntity.ok(userService.getMinimizarCabecalho(gen.getUserName(request)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar a preferencia do usuario: " + e.getMessage());
        }
    }

    @GetMapping("/setMinimizarCabecalho")
    public ResponseEntity<?> setMinimizarCabecalho(@RequestParam(value = "ativo", required = false) Boolean ativo, HttpServletRequest request){
        try {
            userService.setMinimizarCabecalho(gen.getUserName(request), ativo);

            return ResponseEntity.ok().body("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar a preferencia do usuario: " + e.getMessage());
        }
    }
}
