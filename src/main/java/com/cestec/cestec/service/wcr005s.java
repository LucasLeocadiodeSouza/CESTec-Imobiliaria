package com.cestec.cestec.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.corretorDTO;
import com.cestec.cestec.model.pcp_corretor;
import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.metaRepository;

@Service
public class wcr005s {
    
    @Autowired
    private metaRepository metaRepository;

    @Autowired
    private corretorRepository corretorRepository;

    public List<corretorDTO> findAllMetasGrid(){
        return metaRepository.findAllMetasGrid();
    }

    public pcp_meta salvarMetaCorretor(corretorDTO meta) {
        pcp_corretor corretor = corretorRepository.findCorretorByIdeusu(meta.getLogin());

        pcp_meta metaCorretor = new pcp_meta(corretor, 
                                     meta.getVlrmeta(),
                                     meta.getDatiniciometa(),
                                     meta.getDatfinalmeta(),
                                     1,
                                     Date.valueOf(LocalDate.now()),
                                     meta.getNome()); //vou usar o nome para salvar o ideusu de quem adicionou ele
        return metaRepository.save(metaCorretor);
    }

}
