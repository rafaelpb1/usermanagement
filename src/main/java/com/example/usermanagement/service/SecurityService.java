package com.example.usermanagement.service;

import com.example.usermanagement.model.users.User;
import com.example.usermanagement.security.CustomAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService service;

    public User getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof CustomAuthentication customAuth) {
            return customAuth.getUser();
        }

        return null;
    }
}
