package com.project.ticket_service.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ticket_service.dto.request.LoginRequest;
import com.project.ticket_service.dto.request.RegisterRequest;
import com.project.ticket_service.dto.response.AuthResponse;
import com.project.ticket_service.model.entity.User;
import com.project.ticket_service.repository.UserRepository;
import com.project.ticket_service.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public void register(RegisterRequest registerRequest) {

        userRepository.findByUserName(registerRequest.getUsername()).ifPresent(user -> {throw new RuntimeException("Username Already exists");

        });

        User user =  User.builder()
                    .userName(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getRole())
                    .createdAt(Instant.now())
                    .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest){
        User user = userRepository.findByUserName(loginRequest.getUsername()).orElseThrow(()-> new RuntimeException("Inavlid Credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getUserName(), user.getRole());

        return new AuthResponse(token);
    }

}
