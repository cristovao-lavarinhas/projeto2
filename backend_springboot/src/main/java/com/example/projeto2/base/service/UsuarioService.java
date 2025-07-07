package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Usuario;
import com.example.projeto2.base.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> procurarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> procurarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setTipo(usuarioAtualizado.getTipo());
            usuario.setMotorista(usuarioAtualizado.getMotorista());
            usuario.setCliente(usuarioAtualizado.getCliente());
            
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean verificarPassword(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return password.equals(usuario.get().getPassword());
        }
        return false;
    }
} 