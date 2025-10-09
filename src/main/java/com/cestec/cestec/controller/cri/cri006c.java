package com.cestec.cestec.controller.cri;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.cri.cri006s;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.cestec.cestec.service.genService;

@RestController
@RequestMapping("/cri006")
public class cri006c {
    
    @Autowired
    private cri006s wcr006s;

    @Autowired
    private genService gen;

    @GetMapping("/buscarMetasCorretoresGrid")
    public List<?> buscarMetasCorretoresGrid(){
        return wcr006s.findAllMetasGrid();
    }
    
    @PostMapping("/salvarMetaCorretor")
    public ResponseEntity<?> salvarMetaCorretor(@RequestParam(value = "ideusucoor", required = false) String ideusucoor,
                                                @RequestParam(value = "datini", required = false)     LocalDate datini,
                                                @RequestParam(value = "datfim", required = false)     LocalDate datfim,
                                                @RequestParam(value = "vlrmeta", required = false)    BigDecimal vlrmeta,
                                                HttpServletRequest request) {
        try {
            String ideusu = gen.getUserName(request);

            wcr006s.salvarMetaCorretor(ideusucoor, ideusu, Date.valueOf(datini), Date.valueOf(datfim), vlrmeta);

            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar chamados cadastrados: " + e.getMessage());
        }
    }
}
