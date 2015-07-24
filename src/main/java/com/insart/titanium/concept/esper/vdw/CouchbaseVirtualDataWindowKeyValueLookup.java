package com.insart.titanium.concept.esper.vdw;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.insart.titanium.concept.esper.events.generic.GenericEvent;
import com.insart.titanium.concept.persistance.repository.GenericEventRepository;

/**
 * Created by David on 03/02/2015.
 */
@Component
@Scope("prototype")
public class CouchbaseVirtualDataWindowKeyValueLookup implements VirtualDataWindowLookup {
	private static final Log log = LogFactory.getLog(CouchbaseVirtualDataWindowFactory.class);

	private VirtualDataWindowContext context;
	private VirtualDataWindowLookupContext lookupContext;
	private String keyPropertyName = "id";

	public CouchbaseVirtualDataWindowKeyValueLookup() {
	}

	@Autowired
	GenericEventRepository eventRepository;

	@Override
	public Set<EventBean> lookup(Object[] keys, EventBean[] eventBeans) {
		Set<EventBean> obtainedBeans = new HashSet<>();
		/*
		 * If we use special query for DB either findAll query -> output of Esper will be same, 
		 * but size of serverside work + amount of transfered data -> different
		 */
		//Iterable<GenericEvent> lookedup = eventRepository.findByAccountNameAndIncome("Account1", 7);
		Iterable<GenericEvent> obtainedEvents = eventRepository.customFindByAccountNameAndIncome("Account1", 7);

		if (obtainedEvents != null) {
			Iterator<GenericEvent> iterator = obtainedEvents.iterator();
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