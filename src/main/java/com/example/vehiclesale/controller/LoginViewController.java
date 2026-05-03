package com.example.vehiclesale.controller;

import com.example.vehiclesale.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if (authentication == null) {
            return "Olá, visitante! Você não está logado.";
        }

        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println("Usuário carregado do banco: " + customAuth.getUser());
        }

        return "Olá " + authentication.getName();
    }
}
