package com.nextuple.walletapi.controller;

import com.nextuple.walletapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping(value="/showAllUsers",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showAllUsers()
    {
        ResponseEntity<?> responseEntity =  new ResponseEntity<>(walletService.showAllUsers(), HttpStatus.OK);
        return responseEntity;
    }

}
