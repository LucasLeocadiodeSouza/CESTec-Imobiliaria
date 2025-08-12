package com.cestec.cestec.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.model.gridZoomRequest;
import com.cestec.cestec.service.zoomService;

@RestController
@RequestMapping("/zoom")
public class zoomController {
    @Autowired
    private zoomService zoomServ;

    @GetMapping("/carregarGridZoom")
    public List<?> carregarGridZoom(@RequestBody gridZoomRequest request) throws ClassNotFoundException{
        return zoomServ.carregarGridZoom(request.getQuery(), request.getParametros());
    }
}