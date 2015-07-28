package com.insart.titanium.concept.esper.listeners;

import com.espertech.esper.client.EventBean;

/**
 * @author Eugene Pehulja
 * @since Jul 27, 2015 5:41:24 PM
 */
@FunctionalInterface
public interface UpdateEventProcessor {
	public void processEvent(EventBean eventBean);
}
