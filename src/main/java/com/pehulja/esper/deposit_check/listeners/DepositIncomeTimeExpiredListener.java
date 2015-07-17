package com.pehulja.esper.deposit_check.listeners;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:33:16 AM
 */
public class DepositIncomeTimeExpiredListener implements UpdateListener {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(DepositIncomeTimeExpiredListener.class);
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
					logger.info(
							"EXPIRED: " + eventBean.get("accountName") + ", income amount " + eventBean.get("income"));
				}
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
	}

}
