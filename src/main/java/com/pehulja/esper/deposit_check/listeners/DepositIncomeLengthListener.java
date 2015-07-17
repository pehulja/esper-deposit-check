package com.pehulja.esper.deposit_check.listeners;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:33:16 AM
 */
public class DepositIncomeLengthListener implements UpdateListener {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(DepositIncomeLengthListener.class);
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
		try {
			logger.info("LENGTH OF WINDOW AFTER EACH EVENT: " + newEvents[0].get("count(*)"));
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

}
