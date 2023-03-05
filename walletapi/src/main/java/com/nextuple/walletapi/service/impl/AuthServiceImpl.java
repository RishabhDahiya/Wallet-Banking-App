package com.nextuple.walletapi.service.impl;

import com.nextuple.walletapi.jwt.JWTUtil;
import com.nextuple.walletapi.models.User;
import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.response.LoginResponse;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.payload.response.SignupResponse;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.security.service.UserDetailsImpl;
import com.nextuple.walletapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    public  ResponseEntity<Object> login(LoginRequest loginRequest) {
//        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtUtil.generateTokenFromUsername(loginRequest.getUsername());
//        System.out.println(loginRequest);
        return ResponseEntity.ok().body(
                new LoginResponse(userDetails.getUsername(), userDetails.getEmail(),token)
        );
    }

    public ResponseEntity<Object> signup(SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("User Already exists for this username"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("User Already exists for this email"), HttpStatus.BAD_REQUEST);
        }

        try {
            User user = new User(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getEmail(),0);
            User savedUser = userRepository.save(user);
            return new ResponseEntity<>(new SignupResponse( savedUser.getUsername(), savedUser.getEmail(), "User registration successful"), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new MessageResponse("Something went wrong at server side"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}