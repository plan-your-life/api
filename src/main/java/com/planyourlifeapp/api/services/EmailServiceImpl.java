package com.planyourlifeapp.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;


//service for sending mails
@Service
public class EmailServiceImpl {

    private JavaMailSender sender;

    @Autowired
    private MailBuilder mailBuilder;
    //Define the properties
    public EmailServiceImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.precode.eu");
        mailSender.setPort(587);

        mailSender.setUsername("noreply@precode.eu");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        this.sender = mailSender;
    }

    //method for sending the verify email
    public void sendVerifyEmail(String to, String verify, String username){

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setFrom("noreply@precode.eu");
            messageHelper.setSubject("Please verify your E-Mail");
            messageHelper.setText(mailBuilder.build("verify","http://localhost:3000/verify?key="+ verify, username), true);
        };

        sender.send(messagePreparator);

    }
}
