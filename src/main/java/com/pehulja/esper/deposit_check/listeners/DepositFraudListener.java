package com.pehulja.esper.deposit_check.listeners;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:33:16 AM
 */
public class DepositFraudListener implements UpdateListener {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(DepositFraudListener.class);
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
				for (EventBean eventBean : newEvents) {
					logger.info("ACCOUNT: " + eventBean.get("accountName") + ", exceedid the sum, actual "
							+ eventBean.get("sum(amount)"));
				}
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
	}

}
