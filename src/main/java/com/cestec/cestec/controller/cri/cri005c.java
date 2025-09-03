package com.cestec.cestec.controller.cri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.corretorDTO;
import com.cestec.cestec.service.cri.cri005s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/wcr005")
public class cri005c {
    
    @Autowired
    private cri005s wcr005s;

    @GetMapping("/buscarMetasCorretoresGrid")
    public List<?> buscarMetasCorretoresGrid(){
        return wcr005s.findAllMetasGrid();
    }
    
    @PostMapping("/salvarMetaCorretor")
    public ResponseEntity<?> salvarMetaCorretor(@RequestBody corretorDTO meta) {
        return wcr005s.salvarMetaCorretor(meta);
    }
}
