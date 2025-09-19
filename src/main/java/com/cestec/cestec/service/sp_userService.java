package com.cestec.cestec.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import com.cestec.cestec.model.funcionario;
import com.cestec.cestec.model.securityLogin.sp_user;
import com.cestec.cestec.model.spf.sp_usu_aplfav;
import com.cestec.cestec.model.spf.sp_usu_preferencia;
import com.cestec.cestec.repository.userRepository;
import com.cestec.cestec.repository.generico.funcionarioRepository;
import com.cestec.cestec.repository.spf.usuPreferenciaRepo;

@Service
public class sp_userService implements UserDetailsService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private usuPreferenciaRepo usuPreferenciaRepo;

    @Autowired
    private funcionarioRepository funcionarioRepository;

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

    public sp_user getUserByIdeusu(String username){
        return userRepository.findIdByLogin(username);
    }

    public Boolean getMinimizarCabecalho(String username){
        sp_usu_preferencia preferencia = usuPreferenciaRepo.findByPreferencia(username);
        if(preferencia == null) return false;

        return preferencia.getMini_cab();
    }

    public void setMinimizarCabecalho(String username, Boolean ativo){
        sp_usu_preferencia preferencia = usuPreferenciaRepo.findByPreferencia(username);
        
        funcionario funcionario = funcionarioRepository.findFuncByIdeusu(username);

        if(preferencia == null) {    
            if(funcionario == null) throw new RuntimeException("Preferencia nao encontrada para o usuário [" + username + "]!");

            preferencia = new sp_usu_preferencia();
            preferencia.setFunc(funcionario);
            preferencia.setMini_cab(false);
            
            usuPreferenciaRepo.save(preferencia);
            return;
        }

        preferencia.setMini_cab(ativo);
        usuPreferenciaRepo.save(preferencia);
    }

    public void setTemaMenu(String username, String tema){
        sp_usu_preferencia preferencia = usuPreferenciaRepo.findByPreferencia(username);
        
        funcionario funcionario = funcionarioRepository.findFuncByIdeusu(username);

        if(preferencia == null) {    
            if(funcionario == null) throw new RuntimeException("Preferencia nao encontrada para o usuário [" + username + "]!");

            preferencia = new sp_usu_preferencia();
            preferencia.setFunc(funcionario);
        }

        preferencia.setTema_menu(tema);
        usuPreferenciaRepo.save(preferencia);
    }

    public void setFontTextMenu(String username, String tamanho){
        sp_usu_preferencia preferencia = usuPreferenciaRepo.findByPreferencia(username);
        
        funcionario funcionario = funcionarioRepository.findFuncByIdeusu(username);

        if(preferencia == null) {    
            if(funcionario == null) throw new RuntimeException("Preferencia nao encontrada para o usuário [" + username + "]!");

            preferencia = new sp_usu_preferencia();
            preferencia.setFunc(funcionario);
        }

        preferencia.setFonte_texto(tamanho);
        usuPreferenciaRepo.save(preferencia);
    }

    public String getTemaUsuPref(String ideusu){
        sp_usu_preferencia preferencia = usuPreferenciaRepo.findByPreferencia(ideusu);
        if(preferencia == null || preferencia.getTema_menu() == null) return "claro";
        
        return preferencia.getTema_menu();
    }

    public String getFonteTextoUsuPref(String ideusu){
        sp_usu_preferencia preferencia = usuPreferenciaRepo.findByPreferencia(ideusu);
        if(preferencia == null || preferencia.getFonte_texto() == null) return "normal";
        
        return preferencia.getFonte_texto();
    }
}