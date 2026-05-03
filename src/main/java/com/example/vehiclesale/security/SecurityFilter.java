package com.example.usermanagement.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.usermanagement.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;
    private final UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(req, res);
            return;
        }

        String token = recoverToken(req);

        if (token != null) {
            String login = service.validateToken(token);

            if (login != null && !login.isBlank()) {
                // Log para debug: saber se o login foi extraído do token
                System.out.println("DEBUG: Login extraído do token: " + login);

                repository.findByLogin(login).ifPresentOrElse(user -> {
                    // Log para debug: saber as permissões que estão sendo carregadas
                    System.out.println("DEBUG: Usuário encontrado: " + user.getLogin());
                    System.out.println("DEBUG: Authorities carregadas: " + user.getAuthorities());

                    CustomAuthentication authentication = new CustomAuthentication(user);

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }, () -> System.out.println("DEBUG: ERRO - Usuário com login '" + login + "' não existe no banco!"));
            } else {
                System.out.println("DEBUG: ERRO - Token inválido ou expirado.");
            }
        }

        // Verifica se ao final do filtro o contexto está preenchido
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("DEBUG: Requisição prosseguindo SEM autenticação.");
        }

        filterChain.doFilter(req, res);
    }

    private String recoverToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || authHeader.contains("undefined") || authHeader.contains("null")) {
            return null;
        }

        return authHeader.replace("Bearer ", "").trim();
    }
}
