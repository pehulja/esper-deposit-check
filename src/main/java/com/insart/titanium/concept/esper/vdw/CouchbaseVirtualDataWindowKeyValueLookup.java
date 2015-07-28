package com.insart.titanium.concept.esper.vdw;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.insart.titanium.concept.esper.events.generic.TransactionEvent;
import com.insart.titanium.concept.persistance.repository.GenericTransactionRepository;

/**
 * Created by David on 03/02/2015.
 */
@Component
@Scope("prototype")
public class CouchbaseVirtualDataWindowKeyValueLookup implements VirtualDataWindowLookup {
	private static final Logger log = LoggerFactory.getLogger(CouchbaseVirtualDataWindowKeyValueLookup.class);

	private VirtualDataWindowContext context;
	private VirtualDataWindowLookupContext lookupContext;

	public CouchbaseVirtualDataWindowKeyValueLookup() {
	}

	@Autowired
	GenericTransactionRepository eventRepository;

	@Override
	public Set<EventBean> lookup(Object[] keys, EventBean[] eventBeans) {
		Set<EventBean> obtainedBeans = new HashSet<>();
		/*
		 * If we use special query for DB either findAll query -> output of
		 * Esper will be same, but size of serverside work + amount of
		 * transfered data -> different
		 */
		// TODO: For now it should be only findAll, untill implementation of
		// query for Couchbase
		Iterable<TransactionEvent> obtainedEvents = eventRepository.findAll();
		/*
		 * Iterable<GenericEvent> obtainedEvents =
		 * eventRepository.customFindByAccountNameAndIncome("Account1", 7); try{
		 * obtainedEvents = eventRepository.findAll(); }catch(Exception ex){
		 * log.error(ex); } obtainedEvents =
		 * eventRepository.findByType("DEPOSIT_INCOME_EVENT");
		 */

		if (obtainedEvents != null) {
			Iterator<TransactionEvent> iterator = obtainedEvents.iterator();
			// Esper manipulates with EventBean, so need to wrap our event to
			// EventBean
			while (iterator.hasNext()) {
				obtainedBeans.add(context.getEventFactory().wrap(iterator.next()));
			}
		}
		return obtainedBeans;
	}

	public void setContext(VirtualDataWindowContext context) {
		this.context = context;
	}

	public void setLookupContext(VirtualDataWindowLookupContext lookupContext) {
		this.lookupContext = lookupContext;
	}
}