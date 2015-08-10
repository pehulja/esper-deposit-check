package com.insart.titanium.concept.esper.vdw.memoryTest;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowEvent;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.insart.titanium.concept.esper.events.BigATMTransactionEvent;
import com.insart.titanium.concept.persistance.repository.BigATMTransactionRepository;

@Component(value = "CouchbaseVirtualDataWindowMemory")
@Scope(value = "prototype")
public class CouchbaseVirtualDataWindowMemoryTest implements VirtualDataWindow {
	private static final Logger log = LoggerFactory.getLogger(CouchbaseVirtualDataWindowMemoryTestFactory.class);
	private VirtualDataWindowContext context;

	private String[] propertyNames;

	// Type Of events that will be managed by Esper
	private Class type;

	@Autowired
	BigATMTransactionRepository eventRepository;

	@Autowired
	private CouchbaseVirtualDataWindowMemoryTestKeyValueLookup couchbaseVirtualDataWindowKeyValueLookup;

	public CouchbaseVirtualDataWindowMemoryTest(VirtualDataWindowContext context) {
		this.context = context;
		this.type = context.getEventType().getUnderlyingType();
		this.propertyNames = context.getEventType().getPropertyNames();
		log.debug("CouchbaseVirtualDataWindow(): " + type);
	}

	public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext lookupContext) {
		this.couchbaseVirtualDataWindowKeyValueLookup.setContext(context);
		this.couchbaseVirtualDataWindowKeyValueLookup.setLookupContext(lookupContext);
		return this.couchbaseVirtualDataWindowKeyValueLookup;
	}

	@Override
	public void handleEvent(VirtualDataWindowEvent virtualDataWindowEvent) {
		log.info("handleEvent()");
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		if (newData != null && newData.length > 0) {
			for (EventBean eventBean : newData) {
				eventRepository.save((BigATMTransactionEvent) eventBean.getUnderlying());
			}
		}
		if (oldData != null && oldData.length > 0) {
			for (EventBean deleteBean : oldData) {
				eventRepository.delete((BigATMTransactionEvent) deleteBean.getUnderlying());
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
			Iterable<BigATMTransactionEvent> obtainedEvents = eventRepository.findAll();
			if (obtainedEvents == null) {
				obtainedBeans = Collections.<EventBean> emptyList();
			} else {
				obtainedBeans = new LinkedList<>();
				for (BigATMTransactionEvent genericEvent : obtainedEvents) {
					obtainedBeans.add(context.getEventFactory().wrap(genericEvent));
				}
			}
		} catch (Exception ex) {
			log.error("", ex);
			obtainedBeans = Collections.<EventBean> emptyList();
		}
		return obtainedBeans.iterator();
	}
}