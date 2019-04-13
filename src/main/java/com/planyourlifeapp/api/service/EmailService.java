package com.planyourlifeapp.api.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


//service for sending mails
@Service
public class EmailService {

    private JavaMailSender sender;
    private MailBuilder mailBuilder;

    public EmailService(JavaMailSender sender, MailBuilder mailBuilder) {
        this.sender = sender;
        this.mailBuilder = mailBuilder;
    }

    //method for sending the verify email
    public void sendVerifyEmail(String to, String verify, String username) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setFrom("noreply@precode.eu");
            messageHelper.setSubject("Please verify your E-Mail");
            messageHelper.setText(mailBuilder.build("verify", "http://localhost:3000/verify?key=" + verify, username), true);
        };

        sender.send(messagePreparator);

    }
}
