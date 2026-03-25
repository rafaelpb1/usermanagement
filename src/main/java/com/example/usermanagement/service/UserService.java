package com.example.usermanagement.service;

import com.example.usermanagement.model.users.User;
import com.example.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(User user) {

        if (user.getPassword() != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
//        String password = user.getPassword();
//        user.setPassword(encoder.encode(password));
//        user.setEmail(user.getEmail());
        repository.save(user);
    }

    public User getByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow( () -> new UsernameNotFoundException("User not found."));
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User createFromOAuth(String email) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(obterLoginApartirEmail(email));
        user.setPassword(encoder.encode(UUID.randomUUID().toString()));
        user.setRoles(List.of("ADMIN"));

        return repository.save(user);
    }

    private String obterLoginApartirEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
