package com.nextuple.walletapi.controller;

import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.request.WalletAmountTransferRequest;
import com.nextuple.walletapi.payload.request.WalletRechargeRequest;
import com.nextuple.walletapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/recharge")
    public ResponseEntity<Object>  recharge(@RequestHeader("Authorization") String token,@RequestBody WalletRechargeRequest walletRechargeRequest){
//        System.out.println(token);
//        System.out.println(walletRechargeRequest);
//        System.out.println(token);
//        System.out.println(walletRechargeRequest);
//        return authService.signup(signupRequest);

        return walletService.walletRecharge(token,walletRechargeRequest);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object>  walletTransfer(@RequestHeader("Authorization") String token, @RequestBody WalletAmountTransferRequest walletAmountTransferRequest){
//        System.out.println(walletAmountTransferRequest);
//        System.out.println(walletAmountTransferRequest);
        return walletService.walletAmountTransfer(token,walletAmountTransferRequest);
    }

    @GetMapping("/show-balance")
      public ResponseEntity<Object> showBalance(@RequestHeader ("Authorization") String token)
    {
//        System.out.println(token);
        return walletService.showBalance(token);
    }

    @GetMapping("/show-all-transactions")
    public ResponseEntity<Object>  showTransactions(@RequestHeader("Authorization") String token){
        return walletService.showAllTransactions(token);
    }


}
