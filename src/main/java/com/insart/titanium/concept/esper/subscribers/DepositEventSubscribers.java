package com.insart.titanium.concept.esper.subscribers;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPServiceProvider;
import com.insart.titanium.concept.esper.events.DepositIncomeEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:23:29 AM
 */
@Component
public class DepositEventSubscribers {
	private static final Logger logger;

	@Resource
	private Environment environment;

	@Autowired
	private EPServiceProvider provider;
	
	static {
		logger = Logger.getLogger(DepositEventSubscribers.class);
	}

	public void createCouchbaseWindow() {
		ConfigurationOperations configuration = provider.getEPAdministrator().getConfiguration();
		configuration.addEventType("DepositIncomeEvent", DepositIncomeEvent.class);
		provider.getEPAdministrator().createEPL(environment.getProperty("deposit.couchbase.window.create"));
		provider.getEPAdministrator().createEPL(environment.getProperty("deposit.couchbase.window.populate"));
		/*
		 * EPStatement statement =
		 * provider.getEPAdministrator().createEPL(properties.getProperty(
		 * "deposit.couchbase.window.listen")); statement.addListener(new
		 * DepositIncomeListener());
		 */
	}
}
