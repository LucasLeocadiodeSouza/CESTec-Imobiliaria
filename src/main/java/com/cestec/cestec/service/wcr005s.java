package com.cestec.cestec.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.corretorDTO;
import com.cestec.cestec.model.pcp_corretor;
import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.metaRepository;

import jakarta.transaction.Transactional;

@Service
public class wcr005s {
    
    @Autowired
    private metaRepository metaRepository;

    @Autowired
    private corretorRepository corretorRepository;

    public List<corretorDTO> findAllMetasGrid(){
        return metaRepository.findAllMetasGrid();
    }

    @Transactional
    public ResponseEntity<?> salvarMetaCorretor(corretorDTO meta) {
        pcp_corretor corretor = corretorRepository.findCorretorById(meta.getIdlogin());

        if (corretor == null) return ResponseEntity.ok("Deve ser informado um Corretor para registrar a meta!");
        if (meta.getVlrmeta() == 0) return ResponseEntity.ok("Deve ser informado um valor para a Meta!");
        if (meta.getDatiniciometa() == null || meta.getDatfinalmeta() == null) return ResponseEntity.ok("Deve ser informado um periodo para o comprimento da meta!");
        if (meta.getDatiniciometa().after(meta.getDatfinalmeta())) return ResponseEntity.ok("Data inicio da meta não pode ser superior a data final do comprimento da meta!");

        try{
            pcp_meta metaCorretor = new pcp_meta(corretor,
                                                 meta.getVlrmeta(),
                                                 meta.getDatiniciometa(),
                                                 meta.getDatfinalmeta(),
                                                 1,
                                                 Date.valueOf(LocalDate.now()),
                                                 meta.getNome()); //vou usar o nome para salvar o ideusu de quem adicionou ele
            metaRepository.save(metaCorretor);

            return ResponseEntity.ok("OK"); 
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar Meta: " + e.getMessage());    
        }
    }

}
