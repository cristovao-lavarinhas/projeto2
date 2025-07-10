package com.example.projeto2.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        
        // IMPORTANTE: Configurar estas variáveis de ambiente ou alterar diretamente
        // Para usar Gmail, ativar "Acesso a app menos seguras" ou usar password de app
        String email = System.getenv("EMAIL_USERNAME");
        String password = System.getenv("EMAIL_PASSWORD");
        
        if (email == null || password == null) {
            // Fallback para configuração local (ALTERAR ESTES VALORES)
            email = "clavarinhas@gmail.com"; // <-- ALTERA para o teu email
            password = "oofp vvjd dmnf ngcw"; // <-- ALTERA para a tua password de app
        }
        
        mailSender.setUsername(email);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        return mailSender;
    }
} 