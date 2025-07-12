package com.drivesmart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.drivesmart.service.ClienteService;

/**
 * Configuração de segurança Spring Security.
 *
 * Alterações principais:
 *  • Adicionada permissão pública para "/img/**" (logo).
 *  • Página inicial autenticada redireciona agora para "/home".
 */
@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authProvider(ClienteService svc, PasswordEncoder encoder) {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(svc);
        dao.setPasswordEncoder(encoder);
        return dao;
    }

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilitar CSRF para API JWT
                .authorizeHttpRequests(cfg -> cfg
                        .requestMatchers("/", "/home", "/css/**", "/js/**", "/img/**", "/register", "/login", "/dashboard", "/error").permitAll()
                        .anyRequest().permitAll()) // Permitir tudo por enquanto
                .formLogin(f -> f.disable()) // Desabilitar form login tradicional
                .logout(l -> l.disable()); // Desabilitar logout tradicional

        return http.build();
    }
}