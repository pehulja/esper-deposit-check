package com.insart.titanium.concept.esper.vdw;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.persistance.repository.ATMTransactionRepository;

/**
 * @author Eugene Pehulja
 * @since Jul 30, 2015 3:55:53 PM
 */
@Component
public class LookupUtility {
	@Autowired
	ATMTransactionRepository repository;

	public Set<ATMTransactionEvent> getATMTransactions(String account) {
		Set<ATMTransactionEvent> atmTransactionEvents = null;
		try {
			atmTransactionEvents = repository.customFind1(account);
		} catch (Exception ex) {
			System.out.println("Exception");
		}
		return atmTransactionEvents;
	}
}
