package com.cestec.cestec.service.cri;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.pcp_meta;
import com.cestec.cestec.model.cri.corretorDTO;
import com.cestec.cestec.model.cri.pcp_corretor;
import com.cestec.cestec.repository.corretorRepository;
import com.cestec.cestec.repository.metaRepository;
import com.cestec.cestec.util.utilForm;

import jakarta.transaction.Transactional;

@Service
public class cri006s {
    
    @Autowired
    private metaRepository metaRepository;

    @Autowired
    private corretorRepository corretorRepository;

    public String getDescSituacao(Integer status) {
        switch (status) {
            case 1:
                return "Não paga";
            case 2:
                return "Concluída";
        }
        return "Descricão não encontrada";
    }

    public List<?> findAllMetasGrid(){
        List<corretorDTO> metas = metaRepository.findAllMetasGrid();

        utilForm.initGrid();
        for (int i = 0; i < metas.size(); i++) {
            utilForm.criarRow();
            utilForm.criarColuna(metas.get(i).getCodmeta().toString());
            utilForm.criarColuna(metas.get(i).getCodcorretor().toString());
            utilForm.criarColuna(metas.get(i).getNome());
            utilForm.criarColuna(String.valueOf(metas.get(i).getVlrmeta()));
            utilForm.criarColuna(metas.get(i).getDatiniciometa() + " - " + metas.get(i).getDatfinalmeta());
            utilForm.criarColuna(getDescSituacao(metas.get(i).getSituacao()));
            utilForm.criarColuna(metas.get(i).getDatiniciometa().toString());
            utilForm.criarColuna(metas.get(i).getDatfinalmeta().toString());
        }

        return utilForm.criarGrid();
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
