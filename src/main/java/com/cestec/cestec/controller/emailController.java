package com.cestec.cestec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cestec.cestec.model.email;
import com.cestec.cestec.service.emailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/email")
public class emailController {
    private final emailService emailservice;

    public emailController(emailService emailservice) {
        this.emailservice = emailservice;
    }

    @PostMapping()
    public void postMethodName(@RequestBody email email) {
        emailservice.sendemail(email);
    }
    

}
