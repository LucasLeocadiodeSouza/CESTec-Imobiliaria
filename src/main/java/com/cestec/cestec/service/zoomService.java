package com.cestec.cestec.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.repository.zoomRepository;

@Service
public class zoomService {

    @Autowired
    private zoomRepository zoomRepo;

    public List<?> carregarGridZoom(String query, List<modelUtilForm> parametros) throws ClassNotFoundException{
        List<?> zoom = zoomRepo.carregarQuery(query, parametros);

        return zoom;
    }
}
