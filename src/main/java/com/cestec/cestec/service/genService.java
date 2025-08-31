package com.cestec.cestec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.spf.sp_bloqueia_acess;
import com.cestec.cestec.repository.generico.aplicacoesRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.generico.modulosRepository;
import com.cestec.cestec.repository.spf.bloqueioAcessRepo;
import com.cestec.cestec.repository.spf.bloqueioAcessUsuRepo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class genService {
    @Autowired
    private tokenService tokenService;

    @Autowired
    private funcionarioRepository funcionarioRepository;

    @Autowired
    private modulosRepository modulosRepository;

    @Autowired
    private aplicacoesRepository aplicacoesRepository;

    @Autowired
    private bloqueioAcessRepo bloqueioApl;

    @Autowired
    private bloqueioAcessUsuRepo bloqueioUsuApl;

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

    public Integer getCodModuloUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                System.out.println(cookie.getName());

                if("codmodulo".equals(cookie.getName())){
                    String codmodulo = cookie.getValue();
                    return Integer.parseInt(codmodulo);
                }
            }
        }
        return 0;
    }

    public Integer getCodAplicacaoUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("codaplicacao".equals(cookie.getName())){
                    String codaplicacao = cookie.getValue();
                    return Integer.parseInt(codaplicacao);
                }
            }
        }
        return 0;
    }

    public String getNomeByIdeusu(String ideusu){
        return funcionarioRepository.findNomeByIdeusu(ideusu);
    }

    public String findCargoByIdeusu(String ideusu){
        return funcionarioRepository.findCargoByIdeusu(ideusu);
    }

    public String findSetorByIdeusu(String ideusu){
        return funcionarioRepository.findSetorByIdeusu(ideusu);
    } 

    public String findCodSetorByIdeusu(String ideusu){
        return funcionarioRepository.findCodSetorByIdeusu(ideusu);
    }

    public String getDescricaoModulo(Integer codmodulo){
        return modulosRepository.findByIdModulos(codmodulo).getDescricao();
    } 

    public String getDescricaoAplicacao(Integer codmodulo){
        return aplicacoesRepository.findByCodApl(codmodulo).getDescricao();
    }

    public Boolean usuarioTemAcessoAplicacao(Integer idmodulo, Integer idaplicacao, String ideusu){
        funcionario funcionario    = funcionarioRepository.findFuncByIdeusu(ideusu);
        sp_bloqueia_acess bloqueio = bloqueioApl.findByModuloIhAplicacao(idmodulo, idaplicacao);

        return bloqueioUsuApl.findBloqueioUsuAtivoByCodFunc(bloqueio.getId(), funcionario.getCodfuncionario()) != null;
    }
}
