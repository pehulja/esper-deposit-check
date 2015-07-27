package com.insart.titanium.concept.persistance.repository.custom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.view.ViewQuery;
import com.insart.titanium.concept.esper.events.generic.GenericEvent;
import com.insart.titanium.concept.persistance.repository.custom.GenericEventRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 11:30:54 AM
 */
@Repository
public class GenericEventRepositoryCustomImpl implements GenericEventRepositoryCustom<GenericEvent, Long> {
	@Autowired
	CouchbaseTemplate template;

	private final static String KEY_ID = "EVENT_ID";

	@Override
	public GenericEvent save(GenericEvent genericEvent) {
		JsonLongDocument counter = template.getCouchbaseBucket().counter(KEY_ID, 1, 1);
		genericEvent.setId(counter.content());
		template.save(genericEvent);
		return genericEvent;
	}

	/* (non-Javadoc)
	 * @see com.couchbase.esper.persistance.repository.custom.GenericEventRepositoryCustom#findByAccountNameAndIncome(java.lang.String, int)
	 */
	@Override
	public List<GenericEvent> customFindByAccountNameAndIncome(String accountName, int income) {
		ViewQuery viewQuery = ViewQuery.from("genericEvent", "byAccountNameAndIncome").key(JsonArray.from(accountName, income));
		return template.findByView(viewQuery, GenericEvent.class);
	}

	/* (non-Javadoc)
	 * @see com.insart.titanium.concept.persistance.repository.custom.GenericEventRepositoryCustom#findAll()
	 */
	@Override
	public List<GenericEvent> findAll() {
		ViewQuery viewQuery = ViewQuery.from("genericEvent", "all");
		return template.findByView(viewQuery, GenericEvent.class);	
	}

	/*
	 * @Override public List<GenericEvent> findByEventType(String eventType) {
	 * Query query = new Query(); query.setKey(ComplexKey.of(eventType));
	 * List<GenericEvent> list = template.findByView("genericevent",
	 * "findByEventType", query, GenericEvent.class); return list; }
	 */

	/*
	 * @Override public List<GenericEvent> findByProperyName(String properyName,
	 * String propertyValue) { Query query = new Query(); String viewName =
	 * "findBy"+ Character.toUpperCase(properyName.charAt(0)) +
	 * properyName.substring(1);
	 * 
	 * query.setKey(ComplexKey.of(propertyValue)); List<GenericEvent> list =
	 * template.findByView("genericevent", viewName, query, GenericEvent.class);
	 * return list; }
	 */
}
