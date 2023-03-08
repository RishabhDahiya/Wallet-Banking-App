package com.nextuple.walletapi.service;

import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.response.LoginResponse;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.security.service.UserDetailsImpl;
import com.nextuple.walletapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthServiceImpl authService;
    @InjectMocks
    private AuthenticationManager authenticationManager;


    @Test
    public void signupTest_WhenUsernameIsAlreadyTaken()
    {
          when(userRepository.existsByUsername(any())).thenReturn(true);
        SignupRequest signupRequest = new SignupRequest("rishabh","123456","rishabh@gmail.com");
        ResponseEntity<Object> entity= authService.signup(signupRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,entity.getStatusCode());
        verify(userRepository,times(0)).save(any());
    }
    @Test
    public void signupTest_WhenUsernameIsNotTaken()
    {
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        SignupRequest signupRequest = new SignupRequest("rishabh","123456","rishabh@gmail.com");
        when(userRepository.save(any())).thenReturn(new User("rishabh","123456","rishabh@gmail.com",0));
        ResponseEntity<Object> entity= authService.signup(signupRequest);
        Assertions.assertEquals(HttpStatus.OK,entity.getStatusCode());
        verify(userRepository,times(1)).save(any());
    }


    @Test
    public void signupTest_WhenEmailIsAlreadyTaken(){
        when(userRepository.existsByEmail(any())).thenReturn(true);
        SignupRequest signupRequest = new SignupRequest("rishabh","123456","rishabh@gmail.com");
        ResponseEntity<Object> entity= authService.signup(signupRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,entity.getStatusCode());
        verify(userRepository,times(0)).save(any());
    }

    @Test
    public void signupTest_WhenEmailIsNotTaken(){

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        when(userRepository.save(any())).thenReturn(new User("rishabh","123456","rishabh@gmail.com",0));
        ResponseEntity<Object> entity= authService.signup(new SignupRequest("Shashank","SHahsank","shashank@gmail.com"));
        Assertions.assertEquals(HttpStatus.OK,entity.getStatusCode());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    public void signupTest_Exception(){
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        when(userRepository.save(any())).thenThrow(new Error("Error in saving the user"));
        Assertions.assertThrows(Error.class,()->authService.signup(new SignupRequest("Shashank","SHahsank","shashank@gmail.com"))) ;
    }
    @Test
    public void signupTest_Exception2(){
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        when(userRepository.save(any())).thenThrow(new Error("Error in saving the user"));
        Assertions.assertThrows(Error.class,()->authService.signup(new SignupRequest("Shashank","SHahsank","shashank@gmail.com"))) ;
    }
}