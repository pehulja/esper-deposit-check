package com.insart.titanium.concept.esper.listeners;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:33:16 AM
 */
public class TransactionListener implements UpdateListener {
	private static final Logger logger;
	UpdateEventProcessor updateEventProcessor;

	public TransactionListener(UpdateEventProcessor updateEventProcessor) {
		this.updateEventProcessor = updateEventProcessor;
	}

	static {
		logger = Logger.getLogger(TransactionListener.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
	 * client.EventBean[], com.espertech.esper.client.EventBean[])
	 */
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			try {
				logger.info("----------------");
				for (EventBean eventBean : newEvents) {
					updateEventProcessor.processEvent(eventBean);
				}
				logger.info("----------------");

			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
	}
}