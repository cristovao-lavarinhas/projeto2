package com.example.projeto2.base.service;

import com.example.projeto2.base.model.User;
import com.example.projeto2.base.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Map para guardar códigos 2FA temporários: email -> [código, expiração]
    private Map<String, TwoFACode> twoFACodes = new HashMap<>();
    // Map para guardar códigos de recuperação temporários: email -> [código, expiração]
    private Map<String, TwoFACode> recoveryCodes = new HashMap<>();

    public User register(String nome, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) throw new RuntimeException("Email já registado");
        User user = new User();
        user.setNome(nome);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // 2FA
    public String generate2FACode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        twoFACodes.put(email, new TwoFACode(code, expiresAt));
        return code;
    }

    public boolean validate2FACode(String email, String code) {
        TwoFACode entry = twoFACodes.get(email);
        if (entry == null) return false;
        if (entry.expiresAt.isBefore(LocalDateTime.now())) {
            twoFACodes.remove(email);
            return false;
        }
        boolean valid = entry.code.equals(code);
        if (valid) twoFACodes.remove(email);
        return valid;
    }

    // Recuperação de password
    public String generateRecoveryCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        recoveryCodes.put(email, new TwoFACode(code, expiresAt));
        return code;
    }

    public boolean validateRecoveryCode(String email, String code) {
        TwoFACode entry = recoveryCodes.get(email);
        if (entry == null) return false;
        if (entry.expiresAt.isBefore(LocalDateTime.now())) {
            recoveryCodes.remove(email);
            return false;
        }
        boolean valid = entry.code.equals(code);
        if (valid) recoveryCodes.remove(email);
        return valid;
    }

    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private static class TwoFACode {
        String code;
        LocalDateTime expiresAt;
        TwoFACode(String code, LocalDateTime expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }
} 