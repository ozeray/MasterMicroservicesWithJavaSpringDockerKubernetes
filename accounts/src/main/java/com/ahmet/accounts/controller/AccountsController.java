package com.ahmet.accounts.controller;

import com.ahmet.accounts.config.AccountsServiceConfig;
import com.ahmet.accounts.model.*;
import com.ahmet.accounts.repository.AccountsRepository;
import com.ahmet.accounts.service.client.CardsFeignClient;
import com.ahmet.accounts.service.client.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsServiceConfig accountsConfig;

    @Autowired
    private LoansFeignClient loansFeignClient;

    @Autowired
    private CardsFeignClient cardsFeignClient;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {
        return accountsRepository.findByCustomerId(customer.getCustomerId());
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
    @PostMapping("/myCustomerDetails")
    public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoanDetails(customer);
        List<Cards> cards = cardsFeignClient.getCardDetails(customer);
        return CustomerDetails.builder()
                .accounts(accounts)
                .cards(cards)
                .loans(loans)
                .build();
    }
}