package com.insart.titanium.concept.persistance.repository.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.document.JsonLongDocument;
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
			JsonLongDocument counter = template.getCouchbaseBucket().counter(KEY_ID, 1, 1);
			genericEvent.setId(counter.content());
		}
		if (template.exists(genericEvent.getId().toString())) {
			template.update(genericEvent);
		} else {
			template.save(genericEvent);
		}

		return genericEvent;
	}
}
