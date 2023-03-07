package com.nextuple.walletapi.service.impl;

import com.nextuple.walletapi.jwt.JWTUtil;
import com.nextuple.walletapi.model.Transactions;
import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.WalletAmountTransferRequest;
import com.nextuple.walletapi.payload.request.WalletRechargeRequest;
import com.nextuple.walletapi.repository.TransactionRepository;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
@Autowired
private UserRepository userRepository;

@Autowired
private TransactionRepository transactionRepository;
@Autowired
private JWTUtil jwtUtil;

    @Override
    public ResponseEntity<Object> walletRecharge(String token, WalletRechargeRequest walletRechargeRequest) {
        User savedUser = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
//        System.out.println(savedUser.getUsername());
        Transactions transaction = new Transactions("wallet-recharge", savedUser.getUsername(), "Bank", "Wallet", walletRechargeRequest.getAmount());
        int amount = savedUser.getWalletAmount() + walletRechargeRequest.getAmount();
        savedUser.setWalletAmount(amount);
        userRepository.save(savedUser);
        transactionRepository.save(transaction);
        return new ResponseEntity<>(("Recharged Succesfully"),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> walletAmountTransfer(String token, WalletAmountTransferRequest walletAmountTransferRequest) {
        User reciever = userRepository.findByUsername(walletAmountTransferRequest.getUsername());

           if(reciever==null)
               return new ResponseEntity<>(("No user is available with username-"+walletAmountTransferRequest.getUsername()), HttpStatus.BAD_REQUEST);

            User sender = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
              if(sender.getWalletAmount() < walletAmountTransferRequest.getAmount())
                  return new ResponseEntity<>(("Insufficient Balance"),HttpStatus.BAD_REQUEST);
              sender.setWalletAmount(sender.getWalletAmount() - walletAmountTransferRequest.getAmount());
              reciever.setWalletAmount(reciever.getWalletAmount() + walletAmountTransferRequest.getAmount());
            Transactions transaction1= new Transactions("Debit", sender.getUsername(), sender.getUsername(), reciever.getUsername(),walletAmountTransferRequest.getAmount());
            Transactions transaction2= new Transactions("Credit", reciever.getUsername(), sender.getUsername(), reciever.getUsername(),walletAmountTransferRequest.getAmount());
            userRepository.save(sender);
            userRepository.save(reciever);
            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            return new ResponseEntity<>(("Transaction Successfull"),HttpStatus.OK);
    }

    public ResponseEntity<Object> showAllTransactions(String token)
    {
           return new ResponseEntity<>((transactionRepository.findAllByUsername(jwtUtil.getUsernameFromToken(token))),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> showBalance(String token) {
//        System.out.println(token);
        return new ResponseEntity<>((userRepository.findByUsername(jwtUtil.getUsernameFromToken(token))).getWalletAmount(),HttpStatus.OK);
    }


}

