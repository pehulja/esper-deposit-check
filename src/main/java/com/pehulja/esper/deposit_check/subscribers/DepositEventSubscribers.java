package com.pehulja.esper.deposit_check.subscribers;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.pehulja.esper.deposit_check.listeners.DepositIncomeListener;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:23:29 AM
 */
public class DepositEventSubscribers {
	private static final Logger logger;
	private static final Properties properties;

	static {
		logger = Logger.getLogger(DepositEventSubscribers.class);
		properties = new Properties();
		ClassLoader classLoader = DepositEventSubscribers.class.getClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream("events.properties")) {
			properties.load(inputStream);
		} catch (IOException ex) {
			logger.error("", ex);
		}
	}

	public void createCouchbaseWindow(EPServiceProvider provider){
		provider.getEPAdministrator().createEPL(properties.getProperty("deposit.couchbase.window.create"));
		provider.getEPAdministrator().createEPL(properties.getProperty("deposit.couchbase.window.populate"));
		
		EPStatement statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.couchbase.window.listen"));
		statement.addListener(new DepositIncomeListener());
	}
}
