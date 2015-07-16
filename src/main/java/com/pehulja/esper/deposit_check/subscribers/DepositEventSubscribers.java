package com.pehulja.esper.deposit_check.subscribers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.pehulja.esper.deposit_check.events.DepositFraudEvent;
import com.pehulja.esper.deposit_check.listeners.DepositFraudListener;

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
		try (InputStream inputStream = classLoader.getResourceAsStream("events.properties")){
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void subscribeOftenDeposit(Configuration configuration, EPServiceProvider provider){
		configuration.addEventType("DepositFraudEvent", DepositFraudEvent.class);
		EPStatement statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.fraud.event"));
		statement.addListener(new DepositFraudListener());
	}
}
