package com.nextuple.walletapi.repository;

import com.nextuple.walletapi.models.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transactions,String> {
    List<Transactions> findAllByUsername(String name);
}
