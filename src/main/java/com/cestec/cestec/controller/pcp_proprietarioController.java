package com.cestec.cestec.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.ImovelProprietarioDTO;
import com.cestec.cestec.model.pcp_imovel;
import com.cestec.cestec.model.pcp_proprietario;
import com.cestec.cestec.service.pcp_proprietarioService;


@RestController
@RequestMapping("/contratosCadastroClientes/proprietario")
public class pcp_proprietarioController {
    
    @Autowired
    private pcp_proprietarioService pcp_proprietarioService;

    @PostMapping
    public ResponseEntity<?> salvarProprietario(@RequestBody pcp_proprietario proprietario) {
        return pcp_proprietarioService.salvarProprietario(proprietario);
    }

    @PostMapping("/{codProprietario}/salvarImovel")
    public pcp_imovel salvarImovel(@RequestBody pcp_imovel imovel, @PathVariable Integer codProprietario) {
        return pcp_proprietarioService.salvarImovel(imovel, codProprietario);
    }

    @GetMapping()
    public List<pcp_proprietario> listarProprietarios() {
        return pcp_proprietarioService.listarProprietarios();
    }

    @PostMapping("/{index}/buscarPropriGrid")
    public pcp_proprietario buscarProprietario(@PathVariable Integer index){
        return pcp_proprietarioService.buscarProprietario(index);
    }

    @GetMapping("/{codProprietario}/imoveis")
    public List<pcp_imovel> listarImoveisPorProprietario(@PathVariable Integer codProprietario) {
        return pcp_proprietarioService.listarImoveisPorProprietario(codProprietario);
    }

    @GetMapping("/buscarImoveis")
    public List<ImovelProprietarioDTO> buscarImoveis() {
        return pcp_proprietarioService.buscarImoveis();
    }

    @PostMapping("/{codProprietario}/nomepropri")
    public String getNomeProp(@PathVariable Integer codProprietario) {
        return pcp_proprietarioService.getNomeProp(codProprietario);
    }
    
    @PostMapping("/{index}/buscarImovelGrid")
    public ImovelProprietarioDTO buscarImovelGrid(@PathVariable Integer index){        
        return pcp_proprietarioService.buscarImovelGrid(index);
    }
    
    
}
