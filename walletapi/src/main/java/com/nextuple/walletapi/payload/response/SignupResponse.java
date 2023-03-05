package com.nextuple.walletapi.payload.response;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    private String username;
    private String email;
    private String message;
}