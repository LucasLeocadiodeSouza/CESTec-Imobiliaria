package com.cestec.cestec.controller.cri;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.cri.pcp_cliente;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.cri.cri003s;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cri003")
public class cri003c {
    
    @Autowired
    private cri003s pcp_clienteService;

    @Autowired
    private genService gen;

    @PostMapping("/salvarCliente")
    public ResponseEntity<?> salvarCliente(@RequestBody pcp_cliente pcp_cliente, HttpServletRequest request){
        try {
            pcp_clienteService.salvarClientes(pcp_cliente, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }
    
    @GetMapping("/buscarClientes")
    public List<?> buscarCliente(@RequestParam(value = "codcliente", required = false) Integer codcliente){
        return pcp_clienteService.buscarClientes(codcliente);
    }
}
