package com.nextuple.walletapi.service;

import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class serviceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void test(){

        when(userRepository.existsByEmail(any())).thenReturn(true);
        ResponseEntity<Object> t= authService.signup(new SignupRequest("Shashank","SHahsank","shashank@gmail.com"));
        Assertions.assertEquals(t.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(userRepository,times(0)).save(any());
    }

    @Test
    public void test2(){

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        when(userRepository.save(any())).thenReturn(new User("Shashank","SHahsank","shashank@gmail.com",1234));
        ResponseEntity<Object> t= authService.signup(new SignupRequest("Shashank","SHahsank","shashank@gmail.com"));
        Assertions.assertEquals(t.getStatusCode(), HttpStatus.OK);
        verify(userRepository,times(1)).save(any());
    }

    @Test
    public void test3(){

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        when(userRepository.save(any())).thenThrow(new Error("Error in saving the user"));
        Assertions.assertThrows(Error.class,()->authService.signup(new SignupRequest("Shashank","SHahsank","shashank@gmail.com"))) ;
    }
}