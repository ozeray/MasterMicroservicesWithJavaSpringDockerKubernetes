/**
 * 
 */
package com.ahmet.cards.controller;

import com.ahmet.cards.config.CardsServiceConfig;
import com.ahmet.cards.model.Cards;
import com.ahmet.cards.model.Customer;
import com.ahmet.cards.model.Properties;
import com.ahmet.cards.repository.CardsRepository;
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
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	private CardsServiceConfig cardsConfig;
	
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		return cardsRepository.findByCustomerId(customer.getCustomerId());
	}

	@GetMapping("/cards/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = Properties.builder()
				.msg(cardsConfig.getMsg())
				.buildVersion(cardsConfig.getBuildVersion())
				.mailDetails(cardsConfig.getMailDetails())
				.activeBranches(cardsConfig.getActiveBranches())
				.build();
		return ow.writeValueAsString(properties);
	}

}
