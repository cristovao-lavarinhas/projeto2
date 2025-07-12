package com.drivesmart.controller;

import com.drivesmart.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal do cliente.
 *
 * Alterações principais:
 *  • Adicionada rota "/" e "/home" que devolve o dashboard (home.html).
 */
@Controller
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }


    @GetMapping({"/", "/home"})
    String home() {
        return "home"; // templates/home.html
    }

    @GetMapping("/viagem")
    public String viagem() { return "viagem"; }



    @GetMapping("/clientes")
    String listarClientes(Model model) {
        model.addAttribute("clientes", service.listarTodos());
        return "clientes/lista";
    }
}
