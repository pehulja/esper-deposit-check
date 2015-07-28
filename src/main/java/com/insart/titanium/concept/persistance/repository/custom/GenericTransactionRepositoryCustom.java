package com.insart.titanium.concept.persistance.repository.custom;

import com.insart.titanium.concept.esper.events.generic.TransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 11:28:34 AM
 */
public interface GenericTransactionRepositoryCustom {
	public TransactionEvent save(TransactionEvent genericEvent);
}
