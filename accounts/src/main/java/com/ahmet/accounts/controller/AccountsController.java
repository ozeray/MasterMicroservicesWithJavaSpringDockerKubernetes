package com.ahmet.accounts.controller;

import com.ahmet.accounts.config.AccountsServiceConfig;
import com.ahmet.accounts.model.Accounts;
import com.ahmet.accounts.model.Customer;
import com.ahmet.accounts.model.Properties;
import com.ahmet.accounts.repository.AccountsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsServiceConfig accountsConfig;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {
        Optional<Accounts> accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        return accounts.orElse(null);
    }

    @GetMapping("/accounts/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = Properties.builder()
                .msg(accountsConfig.getMsg())
                .buildVersion(accountsConfig.getBuildVersion())
                .mailDetails(accountsConfig.getMailDetails())
                .activeBranches(accountsConfig.getActiveBranches())
                .build();
        return ow.writeValueAsString(properties);
    }

}