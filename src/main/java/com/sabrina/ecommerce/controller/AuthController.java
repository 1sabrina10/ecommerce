package com.sabrina.ecommerce.controller;

import com.sabrina.ecommerce.controller.api.AuthApi;
import com.sabrina.ecommerce.mapper.AuthApiMapper;
import com.sabrina.ecommerce.service.AuthService;
import com.sabrina.ecommerce.service.model.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.sabrina.ecommerce.controller.model.LoginApiRequest;
import com.sabrina.ecommerce.controller.model.RegisterApiRequest;
import com.sabrina.ecommerce.controller.model.AuthApiResponse;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Override
    public ResponseEntity<AuthApiResponse> login(LoginApiRequest loginApiRequest) {
        // api.model → service.model
        AuthResponse serviceResponse = authService.login(
                AuthApiMapper.toServiceRequest(loginApiRequest)
        );
        // service.model → api.model
        return ResponseEntity.ok(AuthApiMapper.toApiResponse(serviceResponse));
    }

    @Override
    public ResponseEntity<Void> register(RegisterApiRequest registerApiRequest) {
        authService.register(AuthApiMapper.toServiceRequest(registerApiRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> registerAdmin(RegisterApiRequest registerApiRequest) {
        authService.registerAdmin(AuthApiMapper.toServiceRequest(registerApiRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
