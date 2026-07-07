package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Role;
import com.sabrina.ecommerce.entity.User;
import com.sabrina.ecommerce.exception.BadRequestException;
import com.sabrina.ecommerce.exception.ResourceNotFoundException;
import com.sabrina.ecommerce.repository.UserRepository;
import com.sabrina.ecommerce.security.JwtService;
import com.sabrina.ecommerce.service.model.request.LoginRequest;
import com.sabrina.ecommerce.service.model.request.RegisterRequest;
import com.sabrina.ecommerce.service.model.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
         this.userRepository = userRepository;
         this.passwordEncoder = passwordEncoder;
         this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email déjà utilisé : " + request.getEmail());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT);

        userRepository.save(user);
        log.info("Nouvel utilisateur créé : {}", user.getEmail());
        return user;
    }

    public User registerAdmin(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email déjà utilisé : " + request.getEmail());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
        log.info("Nouvel admin créé : {}", user.getEmail());
        return user;
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Mot de passe incorrect");
        }

        log.info("Connexion réussie : {}", user.getEmail());
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }


}
