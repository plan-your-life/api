package com.planyourlifeapp.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailBuilder(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String build(String template, String msg, String username){
        Context context = new Context();
        context.setVariable("message", msg);
        context.setVariable("user", username);
        return templateEngine.process(template,context);
    }
}
