// src/main/java/com/drivesmart/controller/AjudaController.java
package com.drivesmart.controller;

import com.drivesmart.dto.PedidoAjuda;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AjudaController {

    private final JavaMailSender mailSender;

    @Value("${support.to}")          // teu e-mail (definido em application.properties)
    private String destinoSuporte;

    public AjudaController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /* form GET */
    @GetMapping("/ajuda")
    public String formAjuda(Model model){
        model.addAttribute("pedido", new PedidoAjuda());
        return "help";
    }

    /* submit POST */
    @PostMapping("/ajuda")
    public String submeter(@ModelAttribute("pedido") @Valid PedidoAjuda pedido,
                           Model model){
        /* envia e-mail */
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destinoSuporte);
        mail.setSubject("Novo pedido de ajuda - DriveSmart");
        mail.setText("""
                Nome: %s
                E-mail: %s
                
                %s
                """.formatted(pedido.getNome(), pedido.getEmail(), pedido.getMensagem()));
        mailSender.send(mail);

        model.addAttribute("enviado", true);
        return "help";
    }
}
