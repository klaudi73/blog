package com.klaudi73.blog.util;

import com.klaudi73.blog.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;



public class MailUtil {

    @Autowired
    private Environment env;

    public SimpleMailMessage constructEmail(String subject, String body, UserEntity user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    //
    /*
    static public final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event,

                                                         final UserEntity userEntity, final String token) {
        final String recipientAddress = userEntity.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        final String message = messages.getMessage("message.regSucc", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    */

    /*
    static public final SimpleMailMessage constructEmailMessage(MessageSource messages, Locale locale,
                                                                final UserEntity userEntity, final String token) {
        final String recipientAddress = userEntity.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        final String message = messages.getMessage("message.regSucc", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    */
}
