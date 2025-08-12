package com.cestec.cestec.service.mrb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.service.zoomService;

public class zoommrb001s {
    
    @Autowired
    private zoomService zoomServ;

    public List<?> zoomCodApl(List<modelUtilForm> parametros) throws ClassNotFoundException{
        String query = "SELECT apl FROM sp_aplicacoes apl WHERE apl.ideusu = :ideusu ORDER BY apl.modulo.id";

        List<?> lista = zoomServ.carregarGridZoom(query, parametros);



        return null;
    }

    // /zoom/carregarGridZoom
}
