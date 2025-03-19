package com.cestec.cestec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.corretorDTO;
import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.service.wcr005s;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/wcr005")
public class wcr005c {
    
    @Autowired
    private wcr005s wcr005s;

    @GetMapping("/buscarMetasCorretoresGrid")
    public List<corretorDTO> buscarMetasCorretoresGrid(){
        return wcr005s.findAllMetasGrid();
    }
    
    @PostMapping("/salvarMetaCorretor")
    public pcp_meta salvarMetaCorretor(@RequestBody corretorDTO meta) {
        return wcr005s.salvarMetaCorretor(meta);
    }
}
