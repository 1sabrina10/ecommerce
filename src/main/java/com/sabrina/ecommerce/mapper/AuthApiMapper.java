package com.sabrina.ecommerce.mapper;

import com.sabrina.ecommerce.controller.model.AuthApiResponse;
import com.sabrina.ecommerce.controller.model.LoginApiRequest;
import com.sabrina.ecommerce.controller.model.RegisterApiRequest;
import com.sabrina.ecommerce.service.model.request.LoginRequest;
import com.sabrina.ecommerce.service.model.request.RegisterRequest;
import com.sabrina.ecommerce.service.model.response.AuthResponse;

public class AuthApiMapper {

    public static LoginRequest toServiceRequest(LoginApiRequest api) {
        LoginRequest service = new LoginRequest();
        service.setEmail(api.getEmail());
        service.setPassword(api.getPassword());
        return service;
    }

    public static RegisterRequest toServiceRequest(RegisterApiRequest api) {
        RegisterRequest service = new RegisterRequest();
        service.setEmail(api.getEmail());
        service.setUsername(api.getUsername());
        service.setPassword(api.getPassword());
        return service;
    }

    public static AuthApiResponse toApiResponse(AuthResponse service) {
        AuthApiResponse api = new AuthApiResponse();
        api.setToken(service.getToken());
        api.setEmail(service.getEmail());
        api.setRole(service.getRole());
        return api;
    }
}