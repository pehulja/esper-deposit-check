package com.insart.titanium.concept.esper.vdw;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.insart.titanium.concept.esper.events.generic.GenericEvent;
import com.insart.titanium.concept.persistance.repository.GenericEventRepository;

/**
 * Created by David on 03/02/2015.
 */

@Component
@Scope(value = "prototype")
public class CouchbaseVirtualDataWindow implements VirtualDataWindow, InitializingBean {
	private static final Log log = LogFactory.getLog(CouchbaseVirtualDataWindowFactory.class);
	private VirtualDataWindowContext context;
	private String[] propertyNames;

	private Class type;

	@Autowired
	GenericEventRepository eventRepository;

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
				eventRepository.save((GenericEvent) eventBean.getUnderlying());
			}
		}
		if (oldData != null && oldData.length > 0) {
			log.debug("Starting delete...");
			for (EventBean deleteBean : oldData) {
				eventRepository.delete((GenericEvent) deleteBean.getUnderlying());
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

		/*
		 * try { Iterable<GenericEvent> obtainedEvents =
		 * eventRepository.findByEventType(type.getName()); if (obtainedEvents
		 * == null) { obtainedBeans = Collections.<EventBean> emptyList(); }
		 * else { obtainedBeans = new LinkedList<>(); for (GenericEvent
		 * genericEvent : obtainedEvents) {
		 * obtainedBeans.add(context.getEventFactory().wrap(genericEvent)); } }
		 * } catch (Exception ex) { log.error(ex); obtainedBeans =
		 * Collections.<EventBean> emptyList(); }
		 */
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