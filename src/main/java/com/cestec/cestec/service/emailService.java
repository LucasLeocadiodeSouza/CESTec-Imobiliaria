package com.cestec.cestec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cestec.cestec.model.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class emailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendemail(email email) throws MessagingException{
        //var message = new SimpleMailMessage();
        MimeMessage message = mailSender.createMimeMessage(); //essa classe MimeMessage permite anexos PDF,Imagens,etc.. tbm permite textos multiplos de HTML
        MimeMessageHelper mHelper = new MimeMessageHelper(message, true,"UTF-8");

        mHelper.setFrom("noreply@gmail.com");
        mHelper.setTo(email.to());
        mHelper.setSubject(email.subject());
        mHelper.setText(email.body(),true);
        
        mailSender.send(message);
    }
}
