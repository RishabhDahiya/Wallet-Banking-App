package com.nextuple.walletapi.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoginResponse {
    private String username;
    private String email;
    private String token;
}