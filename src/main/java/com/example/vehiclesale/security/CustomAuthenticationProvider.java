package com.example.usermanagement.security;

import com.example.usermanagement.model.users.User;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String passwordEntered = authentication.getCredentials().toString();

        User userFound = service.getByLogin(login);

        String encryptedPassword = userFound.getPassword();

        boolean passwordMatches = encoder.matches(passwordEntered, encryptedPassword);

        if (passwordMatches) {
            return new CustomAuthentication(userFound);
        }

        throw getErrorUserNotFound();
    }

    private UsernameNotFoundException getErrorUserNotFound() {
        return new UsernameNotFoundException("Invalid user or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
