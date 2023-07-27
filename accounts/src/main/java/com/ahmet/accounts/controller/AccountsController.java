package com.ahmet.accounts.controller;

import com.ahmet.accounts.model.Accounts;
import com.ahmet.accounts.model.Customer;
import com.ahmet.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {
        Optional<Accounts> accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        return accounts.orElse(null);
    }

}