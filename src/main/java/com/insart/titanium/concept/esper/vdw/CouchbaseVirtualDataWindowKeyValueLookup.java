package com.insart.titanium.concept.esper.vdw;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.persistance.repository.ATMTransactionRepository;

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
	ATMTransactionRepository eventRepository;

	@Override
	public Set<EventBean> lookup(Object[] keys, EventBean[] eventBeans) {
		log.info("KeyValue Look up in Couchbase Data Base");
		/*
		 * If we use special query for DB either findAll query -> output of
		 * Esper will be same, but size of serverside work + amount of
		 * transfered data -> different
		 */
		// TODO: For now it should be only findAll, untill implementation of
		// query for Couchbase
		Iterable<ATMTransactionEvent> obtainedEvents = eventRepository.findAll();
		if (obtainedEvents != null) {
			return StreamSupport.stream(obtainedEvents.spliterator(), true).map(context.getEventFactory()::wrap).collect(Collectors.toSet());
		}
		return new HashSet<EventBean>();
	}

	public void setContext(VirtualDataWindowContext context) {
		this.context = context;
	}

	public void setLookupContext(VirtualDataWindowLookupContext lookupContext) {
		this.lookupContext = lookupContext;
	}
}