/**
 * 
 */
package com.ahmet.loans.controller;

import com.ahmet.loans.config.LoansServiceConfig;
import com.ahmet.loans.model.Customer;
import com.ahmet.loans.model.Loans;
import com.ahmet.loans.model.Properties;
import com.ahmet.loans.repository.LoansRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

	@Autowired
	private LoansRepository loansRepository;

	@Autowired
	LoansServiceConfig loansConfig; 
	
	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		return loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
	}

	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = Properties.builder()
				.msg(loansConfig.getMsg())
				.buildVersion(loansConfig.getBuildVersion())
				.mailDetails(loansConfig.getMailDetails())
				.activeBranches(loansConfig.getActiveBranches())
				.build();
		return ow.writeValueAsString(properties);
	}

}
