package com.nextuple.walletapi.controller;

import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }



    @PostMapping("/login")
    public  ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
//        System.out.println(loginRequest);
        return authService.login(loginRequest);
    }
}
