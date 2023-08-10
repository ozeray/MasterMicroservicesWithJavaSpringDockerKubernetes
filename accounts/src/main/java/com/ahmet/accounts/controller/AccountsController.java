package com.ahmet.accounts.controller;

import com.ahmet.accounts.config.AccountsServiceConfig;
import com.ahmet.accounts.model.*;
import com.ahmet.accounts.repository.AccountsRepository;
import com.ahmet.accounts.service.client.CardsFeignClient;
import com.ahmet.accounts.service.client.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
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
//    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "getCustomerDetailsFallback")
    @Retry(name = "retryForCustomerDetails", fallbackMethod = "getCustomerDetailsFallback")
    public CustomerDetails getCustomerDetails(@RequestHeader("ahmet-correlation-id") String correlationId, @RequestBody Customer customer) {
        log.info("CorrelationID in accounts: {}", correlationId);
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoanDetails(correlationId, customer);
        List<Cards> cards = cardsFeignClient.getCardDetails(correlationId, customer);
        return CustomerDetails.builder()
                .accounts(accounts)
                .cards(cards)
                .loans(loans)
                .build();
    }

    public CustomerDetails getCustomerDetailsFallback(String correlationid, Customer customer, Throwable t) {
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoanDetails(correlationid, customer);
        return CustomerDetails.builder()
                .accounts(accounts)
                .loans(loans)
                .build();
    }

    @GetMapping("/hello")
    @RateLimiter(name = "sayHello", fallbackMethod = "sayWelcomeFallback")
    public String sayWelcome() {
        return "Welcome back";
    }

    public String sayWelcomeFallback(Throwable t) {
        return "Hi, welcome";
    }
}