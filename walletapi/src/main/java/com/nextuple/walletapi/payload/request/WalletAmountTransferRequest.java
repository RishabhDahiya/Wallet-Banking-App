package com.nextuple.walletapi.payload.request;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletAmountTransferRequest {

    private String username;
    private int amount;
}
