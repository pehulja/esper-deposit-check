package com.insart.titanium.concept.persistance.repository.custom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.persistance.repository.custom.ATMTransactionRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Aug 6, 2015 5:37:57 PM
 */
@Repository
public class ATMTransactionRepositoryCustomImpl implements ATMTransactionRepositoryCustom {

	@Autowired
	CouchbaseTemplate template;

	@Override
	public List<ATMTransactionEvent> customFind2(String account) {
		List<ATMTransactionEvent> result = null;
		/*
		 * Query query = Query.parametrized(
		 * "select * from esper where _class = '' ", positionalParams)
		 * template.findByN1QL(n1ql, entityClass)
		 */

		return result;
	}

}
