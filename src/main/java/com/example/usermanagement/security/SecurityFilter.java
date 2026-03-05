package com.example.usermanagement.security;

import com.example.usermanagement.model.users.User;
import com.example.usermanagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;
    private final UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(req);

        if (token != null) {
            String login = service.validateToken(token);

            if (login != null && !login.isBlank()) {

                repository.findByLogin(login).ifPresent(user -> {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                });
            }
        }
        filterChain.doFilter(req, res);
    }

    private String recoverToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
