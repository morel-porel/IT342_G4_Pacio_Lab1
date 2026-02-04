package com.example.backend.service;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.TokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }
    public User register(RegisterRequest request){
        if(userRepository.existsByUsername(request.username))throw new RuntimeException("Username taken");
        User user = new User();
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setLastName(request.lastName);
        user.setFirstName(request.firstName);
        user.setPasswordHash(passwordEncoder.encode(request.password));
        return  userRepository.save(user);
    }
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username).orElseThrow(()-> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.password, user.getPasswordHash())){
            throw new RuntimeException("Invalid Credentials");
        }
        String token = tokenProvider.createToken(user.getId());
        return  new AuthResponse(token,user);
    }
}
