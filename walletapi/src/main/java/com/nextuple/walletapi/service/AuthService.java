package com.nextuple.walletapi.service;

import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Object> login(LoginRequest loginRequest);
    ResponseEntity<Object> signup(SignupRequest signupRequest);
}