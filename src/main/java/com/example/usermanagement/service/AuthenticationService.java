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

        String token = service.generateToken((User) authenticate.getPrincipal());

        return new LoginResponseDTO(token);
    }

    public void register(RegisterDTO dto) {
        if (repository.findByLogin(dto.login()) != null) {
            throw new LoginAlreadyExists("Login already exists");
        }

        String encode = passwordEncoder.encode(dto.password());

        User user = new User(
                dto.login(),
                encode,
                dto.role()
        );

        repository.save(user);
    }
}
