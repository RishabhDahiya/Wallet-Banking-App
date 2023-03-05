package com.nextuple.walletapi.service;

import com.nextuple.walletapi.models.Transactions;
import com.nextuple.walletapi.payload.request.WalletAmountTransferRequest;
import com.nextuple.walletapi.payload.request.WalletRechargeRequest;
import com.nextuple.walletapi.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletService  {

    ResponseEntity<Object> walletRecharge(String token, WalletRechargeRequest walletRechargeRequest);
    ResponseEntity<Object> walletAmountTransfer(String token, WalletAmountTransferRequest walletAmountTransferRequest);
    ResponseEntity<Object> showAllTransactions(String token);

    ResponseEntity<Object> showBalance(String token);



}
