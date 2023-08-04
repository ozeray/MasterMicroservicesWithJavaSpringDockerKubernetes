/**
 * 
 */
package com.ahmet.accounts.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @Builder
public class CustomerDetails {

	private Accounts accounts;
	private List<Loans> loans;
	private List<Cards> cards;

}
