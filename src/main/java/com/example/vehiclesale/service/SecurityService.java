package com.example.vehiclesale.service;

import com.example.vehiclesale.model.users.User;
import com.example.vehiclesale.security.CustomAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    public User getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof CustomAuthentication customAuth) {
            return customAuth.getUser();
        }

        return null;
    }
}
