package com.ahmet.accounts.service.client;

import com.ahmet.accounts.model.Customer;
import com.ahmet.accounts.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {

    @RequestMapping(method = RequestMethod.POST, path = "myLoans", consumes = "application/json")
    List<Loans> getLoanDetails(@RequestBody Customer customer);
}
