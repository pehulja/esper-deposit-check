package com.insart.titanium.concept.persistance.repository.custom;

import java.util.List;

import com.insart.titanium.concept.esper.events.generic.GenericEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 11:28:34 AM
 */
public interface GenericEventRepositoryCustom<S, T> {
	public GenericEvent save(S genericEvent);
	public List<S> customFindByAccountNameAndIncome(String accountName, int income);
	public List<S> findAll();
}
