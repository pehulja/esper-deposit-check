package com.insart.titanium.concept.persistance.repository.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import com.insart.titanium.concept.esper.events.generic.TransactionEvent;
import com.insart.titanium.concept.persistance.repository.custom.GenericTransactionRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 11:30:54 AM
 */
@Repository
public class GenericTransactionRepositoryCustomImpl implements GenericTransactionRepositoryCustom {
	@Autowired
	CouchbaseTemplate template;

	private final static String KEY_ID = "TRANSACTION_ID";

	@Override
	public TransactionEvent save(TransactionEvent genericEvent) {
		if (genericEvent.getId() == null) {
			/*
			 * JsonLongDocument counter =
			 * template.getCouchbaseBucket().counter(KEY_ID, 1, 1);
			 * genericEvent.setId(counter.content());
			 */
			genericEvent.setId(Long.toString(template.getCouchbaseClient().incr(KEY_ID, 1, 1)));
			template.save(genericEvent);
		} else {
			System.out.println("Exists");
		}
		return genericEvent;
	}
}
