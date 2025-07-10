package com.example.projeto2.base.controller;

import com.example.projeto2.base.model.Usuario;
import com.example.projeto2.base.model.Motorista;
import com.example.projeto2.base.service.EmailService;
import com.example.projeto2.base.service.UsuarioService;
import com.example.projeto2.base.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Optional;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MotoristaService motoristaService;

    // Registo de utilizador (motorista)
    @PostMapping("/register-motorista")
    public ResponseEntity<?> registerMotorista(@RequestBody Map<String, String> req) {
        try {
            String nome = req.get("nome");
            String email = req.get("email");
            String password = req.get("password");
            String telefone = req.get("telefone");
            String cartaConducao = req.get("cartaConducao");

            if (nome == null || email == null || password == null || telefone == null || cartaConducao == null) {
                return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
            }

            if (usuarioService.emailExiste(email)) {
                return ResponseEntity.badRequest().body("Email já registado");
            }

            // Criar motorista
            Motorista motorista = new Motorista();
            motorista.setNome(nome); // <-- guardar nome
            motorista.setTel(telefone);
            motorista.setCartaConducao(cartaConducao);
            motorista.setDtNascimento(new Date()); // ou pedir no formulário
            motorista.setEstado("PENDENTE"); // <-- garantir estado pendente
            motorista = motoristaService.guardar(motorista);

            // Criar usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setPassword(password); // (podes encriptar se quiseres)
            usuario.setTipo("MOTORISTA");
            usuario.setMotorista(motorista);
            usuario.setNome(nome); // <-- guardar nome também no usuario
            usuarioService.guardar(usuario);

            emailService.sendEmailWithFallback(email, "Registo de Motorista", 
                "Olá " + nome + ",\n\nO seu registo como motorista foi recebido e está em análise.\n" +
                "Receberá uma notificação quando for aprovado.\n\n" +
                "Dados do registo:\n" +
                "- Nome: " + nome + "\n" +
                "- Email: " + email + "\n" +
                "- Telefone: " + telefone + "\n" +
                "- Carta de Condução: " + cartaConducao + "\n\n" +
                "Obrigado por se registar!");

            return ResponseEntity.ok("Motorista registado com sucesso. Aguarde aprovação.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registar motorista: " + e.getMessage());
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");
        Optional<Usuario> usuarioOpt = usuarioService.procurarPorEmail(email);
        if (usuarioOpt.isPresent() && usuarioService.verificarPassword(email, password)) {
            Usuario usuario = usuarioOpt.get();
            // Se for motorista, só permite login se estado for APROVADO
            if (usuario.getMotorista() != null) {
                Motorista motorista = usuario.getMotorista();
                if (!"APROVADO".equalsIgnoreCase(motorista.getEstado())) {
                    return ResponseEntity.status(403).body("A sua inscrição ainda não foi aprovada pelo admin.");
                }
            }
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Login realizado com sucesso");
            String nome = usuario.getMotorista() != null ? usuario.getMotorista().getNome() : usuario.getEmail();
            resp.put("nome", nome);
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    // Listar motoristas pendentes
    @GetMapping("/admin/pedidos-inscricao")
    public List<Motorista> listarPedidosPendentes() {
        return motoristaService.listarPorEstado("PENDENTE");
    }

    // Aprovar/rejeitar inscrição
    @PostMapping("/admin/validar-inscricao")
    public ResponseEntity<?> validarInscricao(@RequestBody Map<String, Object> req) {
        Long idMotorista = Long.valueOf(req.get("idMotorista").toString());
        String novoEstado = req.get("estado").toString(); // "APROVADO" ou "REJEITADO"
        motoristaService.atualizarEstado(idMotorista, novoEstado);
        return ResponseEntity.ok("Estado atualizado com sucesso");
    }
} 