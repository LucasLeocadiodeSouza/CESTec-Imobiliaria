package com.cestec.cestec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.service.wcr005s;

@RestController
@RequestMapping("/wcr005")
public class wcr005c {
    
    @Autowired
    private wcr005s wcr005s;

}
