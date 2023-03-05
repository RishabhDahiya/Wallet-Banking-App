package com.nextuple.walletapi;

import com.nextuple.walletapi.models.User;
import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.WalletAmountTransferRequest;
import com.nextuple.walletapi.payload.request.WalletRechargeRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ControllerTest extends AbstractTest{

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void signupTestNewUser() throws Exception{
        String uri ="/api/auth/signup";
        User user =new User("RishabDahiyazz","12345678","rishabhzzzz@gmail.com",0);
        String inputJson = super.mapToJson(user);

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200,status);
//        String content=mvcResult.getResponse().getContentAsString();
//        Assertions.assertEquals(content,"User registration successful");
    }
    @Test
    public void signupTestWhenUsernameIsTaken() throws Exception{
        String uri ="/api/auth/signup";
        User user =new User("RishabDahiya","12345678","rishabh@gmail.com",0);
        String inputJson = super.mapToJson(user);

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400,status);
//        String content=mvcResult.getResponse().getContentAsString();
//        Assertions.assertEquals(content,"{"message":"User Already exists for this username s);
    }

    @Test
    public void signupTestWhenEmailIsTaken() throws Exception{
        String uri ="/api/auth/signup";
        User user =new User("RishabDahiya","12345678","rishabh@gmail.com",0);
        String inputJson = super.mapToJson(user);

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400,status);
//        String content=mvcResult.getResponse().getContentAsString();
//        Assertions.assertEquals(content,"{"message":"User Already exists for this username s);
    }

    @Test
    public void login() throws Exception {
        String uri ="/api/auth/login";
        LoginRequest loginRequest=new LoginRequest("RishabhDahiya","12345678");
        String inputJson = super.mapToJson(loginRequest);

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200,status);
    }


    @Test
    public void walletRechargeTest() throws  Exception
    {
        String uri="/api/wallet/recharge";
        WalletRechargeRequest rechargeRequest = new WalletRechargeRequest(1000);
        String inputJson = super.mapToJson(rechargeRequest);

        String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEaXBlc2hKb2hyZSIsImlhdCI6MTY3Nzk5NDc3MCwiZXhwIjoxNjc4ODU4NzcwfQ.fxl63vuufLgiTFd9BzvRcE3Xfl60ir7pmjW2uuyqppK3FCTeyT3c1DVxsG2cdagnB2WB1O2_IuEZQiODNtDPWg";

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", token).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200,status);
    }


    @Test
    public void walletAmountTransferTestWhenRecieverNotAvailable() throws  Exception
    {
        String uri="/api/wallet/transfer";
        WalletAmountTransferRequest amountTransferRequest=new WalletAmountTransferRequest("animeshSingh",3000);
        String inputJson = super.mapToJson(amountTransferRequest);

        String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEaXBlc2hKb2hyZSIsImlhdCI6MTY3Nzk5NDc3MCwiZXhwIjoxNjc4ODU4NzcwfQ.fxl63vuufLgiTFd9BzvRcE3Xfl60ir7pmjW2uuyqppK3FCTeyT3c1DVxsG2cdagnB2WB1O2_IuEZQiODNtDPWg";

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", token).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400,status);
    }

    @Test
    public void walletAmountTransferTestWhenInsufficientBalance() throws  Exception
    {
        String uri="/api/wallet/transfer";
        WalletAmountTransferRequest amountTransferRequest=new WalletAmountTransferRequest("RishabhDahiya",30000);
        String inputJson = super.mapToJson(amountTransferRequest);

        String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEaXBlc2hKb2hyZSIsImlhdCI6MTY3Nzk5NDc3MCwiZXhwIjoxNjc4ODU4NzcwfQ.fxl63vuufLgiTFd9BzvRcE3Xfl60ir7pmjW2uuyqppK3FCTeyT3c1DVxsG2cdagnB2WB1O2_IuEZQiODNtDPWg";

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", token).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400,status);
    }

    @Test
    public void walletAmountTransferTestPositiveCases() throws  Exception
    {
        String uri="/api/wallet/transfer";
        WalletAmountTransferRequest amountTransferRequest=new WalletAmountTransferRequest("RishabhDahiya",300);
        String inputJson = super.mapToJson(amountTransferRequest);

        String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEaXBlc2hKb2hyZSIsImlhdCI6MTY3Nzk5NDc3MCwiZXhwIjoxNjc4ODU4NzcwfQ.fxl63vuufLgiTFd9BzvRcE3Xfl60ir7pmjW2uuyqppK3FCTeyT3c1DVxsG2cdagnB2WB1O2_IuEZQiODNtDPWg";

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", token).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200,status);
    }


    @Test
    public void showBalanceTest() throws  Exception
    {
        String uri ="/api/wallet/show-balance";
        String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEaXBlc2hKb2hyZSIsImlhdCI6MTY3Nzk5NDc3MCwiZXhwIjoxNjc4ODU4NzcwfQ.fxl63vuufLgiTFd9BzvRcE3Xfl60ir7pmjW2uuyqppK3FCTeyT3c1DVxsG2cdagnB2WB1O2_IuEZQiODNtDPWg";

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.get(uri).header("Authorization", token).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200,status);
    }

    @Test
    public void showTransactionsTest() throws  Exception
    {
        String uri ="/api/wallet/show-all-transactions";
        String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEaXBlc2hKb2hyZSIsImlhdCI6MTY3Nzk5NDc3MCwiZXhwIjoxNjc4ODU4NzcwfQ.fxl63vuufLgiTFd9BzvRcE3Xfl60ir7pmjW2uuyqppK3FCTeyT3c1DVxsG2cdagnB2WB1O2_IuEZQiODNtDPWg";

        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.get(uri).header("Authorization", token).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200,status);
    }



}
