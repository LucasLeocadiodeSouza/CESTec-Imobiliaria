package com.cestec.cestec.controller.cri;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.cri.corretorDTO;
import com.cestec.cestec.service.cri.cri006s;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cri006")
public class cri006c {
    
    @Autowired
    private cri006s wcr006s;

    @GetMapping("/buscarMetasCorretoresGrid")
    public List<?> buscarMetasCorretoresGrid(){
        return wcr006s.findAllMetasGrid();
    }
    
    // @PostMapping("/salvarMetaCorretor")
    // public ResponseEntity<?> salvarMetaCorretor(@RequestBody corretorDTO meta) {
    //     return wcr006s.salvarMetaCorretor(meta);
    // }
}
