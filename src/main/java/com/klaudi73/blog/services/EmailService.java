package com.klaudi73.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("emailService")
public class EmailService {


    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo("blog.klaudiusz@gmail.com");
        helper.setText("Message");
        helper.setSubject("Subject");
        mailSender.send(email);
    }

    @Async
    public void sendEmail(String to, String messageText, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setText(messageText);
        message.setSubject(subject);
        mailSender.send(message);
    }
}