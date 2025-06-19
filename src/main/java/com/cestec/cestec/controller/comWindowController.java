package com.cestec.cestec.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.sp_aplicacoes;
import com.cestec.cestec.service.comWindowService;
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

    @Autowired
    private comWindowService comWindowService;

    @GetMapping("/userlogin")
    public String getUserName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("authToken".equals(cookie.getName())){
                    String token = cookie.getValue();
                    String username = tokenService.getExtractedUsernameFromToken(token);
                    return username;
                }
            }
        }
        return "";
    }

    @GetMapping("/getBotoesAplMenu")
    public Map<Integer, List<sp_aplicacoes>> getBotoesAplMenu() {
        return comWindowService.getAplicacoesAgrupadasPorModulo();
    }

    @GetMapping("/userid")
    public Integer getUserId(HttpServletRequest request) {
        return sp_userService.getIdByUserName(getUserName(request));        
    }

    @GetMapping("/{ideusu}/valorMetaMensal")
    public Double valorMetaMensal(@PathVariable String ideusu) {
        return comWindowService.getMetaCorretorMensal(ideusu);
    }
    
    @GetMapping("/getVlrEfetivadoCorretor")
    public Double getVlrEfetivadoCorretor(HttpServletRequest request) {
        return comWindowService.getVlrEfetivadoCorretor(getUserName(request));
    }
    
    @GetMapping("/getPeriodoMeta")
    public String getPeriodoMeta(HttpServletRequest request) {
        return comWindowService.findMesMetaByIdeusu(getUserName(request));
    }

    @GetMapping("/getPercentMetaMes")
    public Double getPercentMetaMes(HttpServletRequest request) {
        return comWindowService.getPercentMetaMes(getUserName(request));
    }
    
    @GetMapping("/getCargoIdeusu")
    public String getCargoIdeusu(HttpServletRequest request) {
        return comWindowService.getCargoFuncionario(getUserName(request));
    }
}