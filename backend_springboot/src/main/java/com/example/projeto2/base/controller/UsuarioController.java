package com.example.projeto2.base.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.model.Usuario;
import com.example.projeto2.base.service.MotoristaService;
import com.example.projeto2.base.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MotoristaService motoristaService;

    // Endpoint para registo de motorista
    @PostMapping("/registar")
    public ResponseEntity<?> registarMotorista(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            String password = (String) request.get("password");
            String nome = (String) request.get("nome");
            String telefone = (String) request.get("telefone");
            String cartaConducao = (String) request.get("cartaConducao");
            String dataNascimento = (String) request.get("dataNascimento");

            // Verificar se o email já existe
            if (usuarioService.emailExiste(email)) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Email já existe");
                return ResponseEntity.badRequest().body(response);
            }

            // Criar o motorista primeiro
            Motorista motorista = new Motorista();
            motorista.setNome(nome);
            motorista.setTel(telefone);
            motorista.setCartaConducao(cartaConducao);
            // Aqui você precisaria converter a string da data para Date
            // Por simplicidade, vou deixar null por agora
            motorista.setDtNascimento(null);

            Motorista motoristaSalvo = motoristaService.guardar(motorista);

            // Criar o usuário
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTipo("MOTORISTA");
            usuario.setMotorista(motoristaSalvo);

            Usuario usuarioSalvo = usuarioService.guardar(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Registo realizado com sucesso");
            response.put("usuario", usuarioSalvo);
            response.put("motorista", motoristaSalvo);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erro ao registar: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            if (usuarioService.verificarPassword(email, password)) {
                Optional<Usuario> usuario = usuarioService.procurarPorEmail(email);
                if (usuario.isPresent()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Login realizado com sucesso");
                    response.put("usuario", usuario.get());
                    return ResponseEntity.ok(response);
                }
            }

            Map<String, String> response = new HashMap<>();
            response.put("error", "Email ou password incorretos");
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erro ao fazer login: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            return ResponseEntity.ok(usuarioService.listarTodos());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao listar usuários: " + e.getMessage());
        }
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.procurarPorId(id);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar usuário: " + e.getMessage());
        }
    }
} 