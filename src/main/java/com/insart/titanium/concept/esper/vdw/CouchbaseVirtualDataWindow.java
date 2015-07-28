package com.insart.titanium.concept.esper.vdw;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowEvent;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.insart.titanium.concept.esper.events.generic.TransactionEvent;
import com.insart.titanium.concept.persistance.repository.GenericTransactionRepository;

/**
 * Created by David on 03/02/2015.
 */

@Component
@Scope(value = "prototype")
public class CouchbaseVirtualDataWindow implements VirtualDataWindow, InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(CouchbaseVirtualDataWindowFactory.class);
	private VirtualDataWindowContext context;

	private String[] propertyNames;

	// Type Of events that will be managed by Esper
	private Class type;

	@Autowired
	GenericTransactionRepository eventRepository;

	@Autowired
	private CouchbaseVirtualDataWindowKeyValueLookup couchbaseVirtualDataWindowKeyValueLookup;

	public CouchbaseVirtualDataWindow(VirtualDataWindowContext context) {
		this.context = context;
		this.type = context.getEventType().getUnderlyingType();
		this.propertyNames = context.getEventType().getPropertyNames();
		log.debug("CouchbaseVirtualDataWindow(): " + type);
	}

	public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext lookupContext) {
		this.couchbaseVirtualDataWindowKeyValueLookup.setLookupContext(lookupContext);
		return this.couchbaseVirtualDataWindowKeyValueLookup;
	}

	@Override
	public void handleEvent(VirtualDataWindowEvent virtualDataWindowEvent) {
		log.debug("handleEvent()");
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		if (newData != null && newData.length > 0) {
			for (EventBean eventBean : newData) {
				eventRepository.save((TransactionEvent) eventBean.getUnderlying());
			}
		}
		if (oldData != null && oldData.length > 0) {
			for (EventBean deleteBean : oldData) {
				eventRepository.delete((TransactionEvent) deleteBean.getUnderlying());
			}
		}
		context.getOutputStream().update(newData, oldData);
	}

	public void destroy() {
	}

	/*
	 * Should return all events related to some specific event Type
	 * 
	 * @see com.espertech.esper.client.hook.VirtualDataWindow#iterator()
	 */
	@Override
	public Iterator<EventBean> iterator() {
		List<EventBean> obtainedBeans = null;
		obtainedBeans = Collections.<EventBean> emptyList();

		try {
			Iterable<TransactionEvent> obtainedEvents = eventRepository.findAll();
			if (obtainedEvents == null) {
				obtainedBeans = Collections.<EventBean> emptyList();
			} else {
				obtainedBeans = new LinkedList<>();
				for (TransactionEvent genericEvent : obtainedEvents) {
					obtainedBeans.add(context.getEventFactory().wrap(genericEvent));
				}
			}
		} catch (Exception ex) {
			log.error("", ex);
			obtainedBeans = Collections.<EventBean> emptyList();
		}
		return obtainedBeans.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		couchbaseVirtualDataWindowKeyValueLookup.setContext(context);
	}
}