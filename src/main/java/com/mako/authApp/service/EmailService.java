package com.mako.authApp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendStyledEmail(String to, String subject, String name, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = "<html>" +
                "<body>" +
                "<h3>Hi, " + name + "</h3>" +
                "<p>" + body + "</p>" +
                "<div style='text-align: center;'>" +
                "</div>" +
                "</body>" +
                "</html>";


        helper.setFrom("yourEmailAdress");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);

    }

}
