package com.cestec.cestec.controller.mrb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.service.mrb.zoommrb001s;

@RestController
@RequestMapping("/zoommrb")
public class zoommrb001c {
    // @Autowired
    // private zoommrb001s zoom;

    // @PostMapping("/zoomCodApl")
    // public List<?> zoomCodApl(@RequestBody List<modelUtilForm> request) throws ClassNotFoundException{
    //     return zoom.zoomCodApl(request);
    // }
}
