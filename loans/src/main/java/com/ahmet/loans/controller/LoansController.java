/**
 * 
 */
package com.ahmet.loans.controller;

import com.ahmet.loans.model.Customer;
import com.ahmet.loans.model.Loans;
import com.ahmet.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

	@Autowired
	private LoansRepository loansRepository;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		return loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
	}

}
