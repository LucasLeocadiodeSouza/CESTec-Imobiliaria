package com.cestec.cestec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.infra.security.tokenService;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.spf.sp_bloqueia_acess;
import com.cestec.cestec.repository.cri.clienteRepository;
import com.cestec.cestec.repository.cri.proprietarioRepository;
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

    @Autowired
    private proprietarioRepository proprietarioRepository;

    @Autowired
    private clienteRepository clienteRepository;


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

    public String getNomeByIdeusu(String ideusu){
        return funcionarioRepository.findNomeByIdeusu(ideusu);
    }

    public String getNomeByCodFunc(Integer codfunc){
        String funcname = funcionarioRepository.findNameByUser(codfunc);

        if(funcname == null) throw new RuntimeException("Não encontrado nenhum funcionario com o código informado!");

        return funcname;
    }

    public String findCargoByIdeusu(String ideusu){
        String cargoname = funcionarioRepository.findCargoByIdeusu(ideusu);

        if(cargoname == null) throw new RuntimeException("Não encontrado o Cargo para o funcionario informado!");

        return cargoname;
    }

    public String findSetorByIdeusu(String ideusu){
        String setorname = funcionarioRepository.findSetorByIdeusu(ideusu);

        if(setorname == null) throw new RuntimeException("Não encontrado o Setor para o funcionario informado!");

        return setorname;
    } 

    public String findCodSetorByIdeusu(String ideusu){
        String codsetor = funcionarioRepository.findCodSetorByIdeusu(ideusu);

        if(codsetor == null) throw new RuntimeException("Não encontrado o Código do Setor para o funcionario informado!");

        return codsetor;
    }

    public String getDescricaoModulo(Integer codmodulo){
        String descmodulo = modulosRepository.findByIdModulos(codmodulo).getDescricao();

        if(descmodulo == null) throw new RuntimeException("Não encontrado a descricão do modulo com o código informado!");

        return descmodulo;
    } 

    public String getDescricaoAplicacao(Integer codmodulo){
        return aplicacoesRepository.findByCodApl(codmodulo).getDescricao();
    }

    public String getNomeProp(Integer codProprietario) {
        String propname = proprietarioRepository.findByCodproprietario(codProprietario).getNome();

        if(propname == "") throw new RuntimeException("Código do proprietario [" + codProprietario + "] não encontrado");

        return propname;
    }

    public String getNomeCliente(Integer codcliente) {
        String clientename = clienteRepository.findByCodcliente(codcliente).getNome();

        if(clientename == "") throw new RuntimeException("Código do Cliente [" + codcliente + "] não encontrado");

        return clientename;
    }

    public Boolean usuarioTemAcessoAplicacao(Integer idmodulo, Integer idaplicacao, String ideusu){
        funcionario funcionario    = funcionarioRepository.findFuncByIdeusu(ideusu);
        sp_bloqueia_acess bloqueio = bloqueioApl.findByModuloIhAplicacao(idmodulo, idaplicacao);

        return bloqueioUsuApl.findBloqueioUsuAtivoByCodFunc(bloqueio.getId(), funcionario.getCodfuncionario()) != null;
    }
}
