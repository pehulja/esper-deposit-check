package com.insart.titanium.concept.persistance.repository.custom;

import java.util.List;

import com.insart.titanium.concept.esper.events.ATMTransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Aug 6, 2015 5:33:59 PM
 */
public interface ATMTransactionRepositoryCustom {

	public List<ATMTransactionEvent> customFind2(String account);
}
