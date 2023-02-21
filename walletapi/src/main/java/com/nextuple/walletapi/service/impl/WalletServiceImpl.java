package com.nextuple.walletapi.service.impl;

import com.nextuple.walletapi.model.Users;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private UserRepository userRepository;
    @Override
    public List<Users> showAllUsers() {
        return userRepository.findAll();
    }
}
