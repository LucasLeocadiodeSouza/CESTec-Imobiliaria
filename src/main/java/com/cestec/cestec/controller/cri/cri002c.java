package com.cestec.cestec.controller.cri;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.model.cri.pcp_proprietario;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.cri.cri002s;

@RestController
@RequestMapping("/cri002")
public class cri002c {
    
    @Autowired
    private cri002s cri002s;

    @Autowired
    private genService gen;

    @PostMapping("/salvarproprietario")
    public ResponseEntity<?> salvarProprietario(@RequestBody pcp_proprietario proprietario) {
        try {
            cri002s.salvarProprietario(proprietario);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao cadastrar bloqueio: " + e.getMessage());
        }
    }

    @GetMapping()
    public List<pcp_proprietario> listarProprietarios() {
        return cri002s.listarProprietarios();
    }

    @GetMapping("/buscarPropriGrid")
    public List<?> buscarProprietario(@RequestParam(value = "codprop", required = false) Integer codprop){
        return cri002s.buscarProprietario(codprop);
    }

    @GetMapping("/{codProprietario}/imoveis")
    public List<pcp_imovel> listarImoveisPorProprietario(@PathVariable Integer codProprietario) {
        return cri002s.listarImoveisPorProprietario(codProprietario);
    }

    @GetMapping("/{codProprietario}/getOptionImovel")
    public String getOptionImovel(@PathVariable Integer codProprietario) {
        List<pcp_imovel> listaImovel = cri002s.listarImoveisPorProprietario(codProprietario);

        String fselect       = "0|Selecione um Imovel";
        for (pcp_imovel imovel : listaImovel) {
            fselect = fselect + "#" + imovel.getCodimovel() + "|" + imovel.getCodimovel();         
        }
        return fselect;
    }
    
    @GetMapping("/{codProprietario}/nomepropri")
    public String getNomeProp(@PathVariable Integer codProprietario) {
        return gen.getNomeProp(codProprietario);
    }    
}
