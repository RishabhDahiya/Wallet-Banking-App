package com.nextuple.walletapi.repository;

import com.nextuple.walletapi.model.Transactions;
import com.nextuple.walletapi.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class RepositoryTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void initUseCase() {
        List<Transactions> transactionsList = Stream.of(new
                        Transactions("wallet-recherge", "rishabh", "Bank", "wallet", 2000)
                , new Transactions("Debit", "rishabh",
                        "rishabh", "dipesh", 2000)).collect(Collectors.toList());
        transactionRepository.save(transactionsList.get(0));
        transactionRepository.save(transactionsList.get(1));

        User user = new User("rishabh", "12345678", "rishabh@gmail.com", 1000);
        userRepository.save(user);
    }

    @AfterEach
    public void deleteAll() {
        transactionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findAlLByUsernameTest() {
        when(transactionRepository.findAllByUsername("rishabh")).thenReturn(Stream.of(new
                        Transactions("wallet-recherge", "rishabh", "Bank", "wallet", 2000)
                , new Transactions("Debit", "rishabh",
                        "rishabh", "dipesh", 2000)).collect(Collectors.toList()));
        Assertions.assertEquals(2, transactionRepository.findAllByUsername("rishabh").size());
    }

    @Test
    public void findByUsernameTest() {
        User user = new User("rishabh", "12345678", "rishabh@gmail.com", 1000);
        when(userRepository.findByUsername("rishabh")).thenReturn(user);

        Assertions.assertEquals("rishabh", userRepository.findByUsername("rishabh").getUsername());
    }

    @Test
    public void existsByUsernameTest() {
        when(userRepository.existsByUsername("rishabh")).thenReturn(true);
        Assertions.assertEquals(true, userRepository.existsByUsername("rishabh"));
    }

    @Test
    public void existsByEmailTest() {
        when(userRepository.existsByEmail("rishabh@gmail.com")).thenReturn(true);
        Assertions.assertNotEquals(false
                , userRepository.existsByEmail("rishabh@gmail.com"));
    }
}
