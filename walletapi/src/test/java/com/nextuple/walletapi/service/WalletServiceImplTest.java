package com.nextuple.walletapi.service;

import com.nextuple.walletapi.jwt.JWTUtil;
import com.nextuple.walletapi.model.Transactions;
import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.request.WalletAmountTransferRequest;
import com.nextuple.walletapi.payload.request.WalletRechargeRequest;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.payload.response.SignupResponse;
import com.nextuple.walletapi.repository.TransactionRepository;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    private WalletServiceImpl walletService;
    @Mock
    private JWTUtil jwtUtil;
    @Test
    public void walletRechargeTest()
    {
        User savedUser = new User("rishabh","123456","rishabh@gmail.com",1000);
        WalletRechargeRequest rechargeRequest = new WalletRechargeRequest(100);
        User updatedUser = new User("rishabh","123456","rishabh@gmail.com",1100);

        when(userRepository.findByUsername(any())).thenReturn(savedUser);
        when(userRepository.save(any())).thenReturn(updatedUser);
        when(transactionRepository.save(any())).thenReturn(new Transactions("wallet-recharge","rishabh","Bank","Wallet",100));

        ResponseEntity<Object> responseEntity = walletService.walletRecharge(anyString(),rechargeRequest);
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
       verify(transactionRepository,times(1)).save(any());
        verify(userRepository,times(1)).save(any());
    }
    @Test
    public void walletAmountTransfer_WhenRecieverDoesNotExists()
    {
        WalletAmountTransferRequest amountTransferRequest = new WalletAmountTransferRequest("rishabh",1000);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        ResponseEntity<Object> entity = walletService.walletAmountTransfer(anyString(),amountTransferRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,entity.getStatusCode());
        verify(userRepository,times(0)).save(any());
    }

    @Test
    public void walletAmountTransfer_InsufficientBalance()
    {
        WalletAmountTransferRequest amountTransferRequest = new WalletAmountTransferRequest("goyal",1000);
        User sender = new User("rishabh","123456","rishabh@gmail.com",200);
        User receiver = new User("goyal","123456","goyal@gmail.com",100);
        when(userRepository.findByUsername(any())).thenReturn(sender);
        when(userRepository.findByUsername(any())).thenReturn(receiver);
        ResponseEntity<Object> responseEntity = walletService.walletAmountTransfer(anyString(),amountTransferRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        verify(userRepository,times(0)).save(any());
    }

    @Test
    public void walletAmountTransfer_Success()
    {
        User sender = new User("rishabh","123456","rishabh@gmail.com",2000);
        User receiver = new User("goyal","123456","goyal@gmail.com",100);
        WalletAmountTransferRequest amountTransferRequest = new WalletAmountTransferRequest("goyal",1000);

        User updatedSender = new User("rishabh","123456","rishabh@gmail.com",1000);
        User updatedReceiver = new User("goyal","123456","goyal@gmail.com",1100);

        Transactions senderTransaction = new Transactions("Debit","rishabh","rishabh","goyal",1000);
        Transactions receiverTransaction = new Transactions("Credit","goyal","rishabh","goyal",1000);

        when(userRepository.findByUsername(any())).thenReturn(receiver);
        when(userRepository.findByUsername(any())).thenReturn(sender);

        when(userRepository.save(any())).thenReturn(updatedSender);
        when(userRepository.save(any())).thenReturn(updatedReceiver);

        when(transactionRepository.save(any())).thenReturn(senderTransaction);
        when(transactionRepository.save(any())).thenReturn(receiverTransaction);

        ResponseEntity<Object> responseEntity = walletService.walletAmountTransfer(anyString(),amountTransferRequest);
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        verify(userRepository,times(2)).save(any());
        verify(transactionRepository,times(2)).save(any());
    }

    @Test
    public void showAllTransactionsTest()
    {
        List<Transactions> transactionsList = Stream.of(new
                        Transactions("wallet-recherge", "rishabh", "Bank", "wallet", 2000)
                , new Transactions("Debit", "rishabh",
                        "rishabh", "dipesh", 2000)).collect(Collectors.toList());
        when(transactionRepository.findAllByUsername(any())).thenReturn(transactionsList);
        ResponseEntity<Object> responseEntity = walletService.showAllTransactions(any());
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
    @Test
    public void showBalanceTest()
    {
        User savedUser = new User("rishabh","123456","rishabh@gmail.com",2000);
        when(userRepository.findByUsername(any())).thenReturn(savedUser);
        ResponseEntity<Object> responseEntity = walletService.showBalance(any());
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }


}
