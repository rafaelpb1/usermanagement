package com.example.vehiclesale.security;

import com.example.vehiclesale.model.users.User;
import com.example.vehiclesale.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService service;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken auth2Token = (OAuth2AuthenticationToken) authentication;

        String email = auth2Token.getPrincipal().getAttribute("email");

        User user = service.getByEmail(email);

        if (user == null) {
            user = service.createFromOAuth(email);
        }

        String token = tokenService.generateToken(user).token();

        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
