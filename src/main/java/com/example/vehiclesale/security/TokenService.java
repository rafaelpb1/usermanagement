package com.example.vehiclesale.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.vehiclesale.dto.LoginResponseDTO;
import com.example.vehiclesale.exception.GenerateTokenErrorException;
import com.example.vehiclesale.model.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public LoginResponseDTO generateToken(User user) {
        if (user == null) {
            throw new GenerateTokenErrorException("Usuário não pode ser nulo ao gerar token.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("cardealer")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            long expiresIn = generateExpirationDate().getEpochSecond() - Instant.now().getEpochSecond();

            return new LoginResponseDTO(token, expiresIn);

        } catch (JWTCreationException e) {
            throw new GenerateTokenErrorException("Error while generating token");
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("cardealer")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            System.out.println("Erro ao validar token");
            e.printStackTrace();
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
