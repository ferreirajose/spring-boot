package com.jose.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {
    
    @Autowired
    private MailSender mailSender;
    @Autowired JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("ENVIANDO DE E-MAIL...");
        mailSender.send(msg);
        LOG.info("E-MAIL ENVIADO");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("ENVIANDO DE E-MAIL...");
        javaMailSender.send(msg);
        LOG.info("E-MAIL ENVIADO");
    }

}
