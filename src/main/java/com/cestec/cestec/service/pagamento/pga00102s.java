package com.cestec.cestec.service.pagamento;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cestec.cestec.repository.cri.contratoRepository;
import com.cestec.cestec.repository.cri.imovelRepository;
import com.cestec.cestec.model.contasAPagar.faturamentoDTO;
import com.cestec.cestec.model.cri.pcp_contrato;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.repository.pagamento.pagamentoCustomRepo;
import com.cestec.cestec.util.utilForm;

@Service
public class pga00102s {
    
    @Autowired
    private pagamentoCustomRepo pagamentoCustomRepo;

    @Autowired
    private imovelRepository imovelRepository;

    @Autowired
    private contratoRepository contratoRepository;

    public BigDecimal getValorLiberadoContrato(Integer codcontrato) {
        pcp_contrato contrato = contratoRepository.findByCodContrato(codcontrato);

        return contrato.getValorliberado();
    }

    public BigDecimal getValorDescontoContrato(Integer codcontrato) {
        pcp_contrato contrato = contratoRepository.findByCodContrato(codcontrato);

        return contrato.getPcp_imovel().getPreco().subtract(contrato.getValorliberado());
    }

    public List<?> buscarFaturaCliente(){
        List<faturamentoDTO> faturamento = pagamentoCustomRepo.buscarFaturaCliente();

        utilForm.initGrid();
        for (int i = 0; i < faturamento.size(); i++) {
            String documento;
            if(faturamento.get(i).getDocumento().length() == 11) documento = utilForm.formatDocToCpf(faturamento.get(i).getDocumento());
            else documento = utilForm.formatDocToCnpj(faturamento.get(i).getDocumento());

            utilForm.criarRow();
            utilForm.criarColuna(faturamento.get(i).getNumdoc());
            utilForm.criarColuna(faturamento.get(i).getCodcliente().toString());
            utilForm.criarColuna(faturamento.get(i).getNomecliente());
            utilForm.criarColuna(documento);
            utilForm.criarColuna(faturamento.get(i).getCodcontrato().toString());
            utilForm.criarColuna(faturamento.get(i).getCodfatura().toString());
            utilForm.criarColuna(utilForm.formatVlr(faturamento.get(i).getValor().doubleValue(), 2));
            utilForm.criarColuna(utilForm.formatarDataBrasil(Date.valueOf(faturamento.get(i).getVencimento())));
            utilForm.criarColuna(faturamento.get(i).getTipopag().name());
            utilForm.criarColuna(faturamento.get(i).getSituacao().name());
            utilForm.criarColuna(faturamento.get(i).getCodconta().toString());
            utilForm.criarColuna(faturamento.get(i).getCodimovel().toString());
            utilForm.criarColuna(utilForm.formatVlr(getValorDescontoContrato(faturamento.get(i).getCodcontrato()).doubleValue(), 2));
            utilForm.criarColuna(faturamento.get(i).getVencimento().toString());
        }

        return utilForm.criarGrid();
    }

}
