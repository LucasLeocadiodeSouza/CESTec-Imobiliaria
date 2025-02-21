package com.cestec.cestec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {
    @GetMapping("/home") 
    public String home() {
        return "comWindow";
    }
}
