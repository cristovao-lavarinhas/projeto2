package com.example.projeto2.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            logger.info("Email enviado com sucesso para: {}", to);
            return true;
        } catch (Exception e) {
            logger.error("Erro ao enviar email para {}: {}", to, e.getMessage());
            return false;
        }
    }

    public boolean sendEmailWithFallback(String to, String subject, String text) {
        boolean sent = sendEmail(to, subject, text);
        if (!sent) {
            // Fallback: apenas logar o email que seria enviado
            logger.warn("Email não enviado (configuração de email inválida). Conteúdo que seria enviado:");
            logger.warn("Para: {}", to);
            logger.warn("Assunto: {}", subject);
            logger.warn("Texto: {}", text);
        }
        return sent;
    }
} 