package com.couchbase.esper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowEvent;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.pehulja.esper.deposit_check.subscribers.events.DepositIncomeEvent;

/**
 * Created by David on 03/02/2015.
 */
public class CouchbaseVirtualDataWindow implements VirtualDataWindow {
	private static final Log log = LogFactory.getLog(CouchbaseVirtualDataWindowFactory.class);
	private final VirtualDataWindowContext context;
	private final Class type;
	private final Bucket bucket;
	private final ObjectMapper mapper;

	public CouchbaseVirtualDataWindow(Bucket bucket, VirtualDataWindowContext context) {
		this.context = context;
		this.bucket = bucket;
		this.type = context.getEventType().getUnderlyingType();
		this.mapper = new ObjectMapper();

		log.debug("CouchbaseVirtualDataWindow(): " + type);
	}

	public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext desc) {
		// Place any code that interrogates the hash-index and btree-index
		// fields here.
		// Return the index representation.
		log.debug("getLookup()" + desc.getHashFields() + " : " + desc.getBtreeFields());

		return new CouchbaseVirtualDataWindowKeyValueLookup(bucket, type, context);
	}

	@Override
	public void handleEvent(VirtualDataWindowEvent virtualDataWindowEvent) {
		log.debug("handleEvent()");
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		// Insert events into window
		if (newData != null && newData.length > 0) {
			for (EventBean insertBean : newData) {
				try {
					String json = mapper.writeValueAsString(insertBean.get("value"));
					bucket.upsert(RawJsonDocument.create(insertBean.get("key").toString(), json));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		// Delete events from window
		if (oldData != null && oldData.length > 0) {
			log.debug("Starting delete...");
			for (EventBean deleteBean : oldData) {
				bucket.remove(deleteBean.get("key").toString());
			}
		}
		context.getOutputStream().update(newData, oldData);
	}

	public void destroy() {
		bucket.close();
	}

	/*
	 * Should return all events related to some specific event Type
	 * @see com.espertech.esper.client.hook.VirtualDataWindow#iterator()
	 */
	@Override
	public Iterator<EventBean> iterator() {
/*		DepositIncomeEvent depositIncomeEvent = new DepositIncomeEvent("1111", 2222);
		EventBean event = context.getEventFactory().wrap(depositIncomeEvent);
        return Collections.singleton(event).iterator();*/
        
        return Collections.<EventBean>emptyList().iterator();
	}
}