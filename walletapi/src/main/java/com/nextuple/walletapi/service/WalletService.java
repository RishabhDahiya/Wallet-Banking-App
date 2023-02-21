package com.nextuple.walletapi.service;

import com.nextuple.walletapi.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WalletService {

    List<Users> showAllUsers();

}
