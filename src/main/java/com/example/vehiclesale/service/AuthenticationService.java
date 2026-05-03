package com.example.usermanagement.service;

import com.example.usermanagement.dto.LoginRequestDTO;
import com.example.usermanagement.dto.LoginResponseDTO;
import com.example.usermanagement.dto.RegisterDTO;
import com.example.usermanagement.exception.LoginAlreadyExists;
import com.example.usermanagement.model.users.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService service;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                dto.login(),
                dto.password());

        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        User user = (User) authenticate.getPrincipal();

        return service.generateToken(user);
    }

    public void register(RegisterDTO dto) {
        if (repository.findByLogin(dto.login()).isPresent()) {
            throw new LoginAlreadyExists("Login already exists");
        }

        String encode = passwordEncoder.encode(dto.password());

        User user = User.builder()
                .login(dto.login())
                .password(encode)
                .email(dto.email())
                .roles(List.of(dto.role().name()))
                .build();

        repository.save(user);
    }
}
