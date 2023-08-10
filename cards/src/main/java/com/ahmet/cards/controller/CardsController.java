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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	private CardsServiceConfig cardsConfig;
	
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestHeader("ahmet-correlation-id") String correlationId, @RequestBody Customer customer) {
		log.info("CorrelationID in cards: {}", correlationId);
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
