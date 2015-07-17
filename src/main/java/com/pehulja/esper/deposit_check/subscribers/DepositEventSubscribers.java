package com.pehulja.esper.deposit_check.subscribers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.pehulja.esper.deposit_check.events.DepositFraudEvent;
import com.pehulja.esper.deposit_check.listeners.DepositBrokeListener;
import com.pehulja.esper.deposit_check.listeners.DepositFraudListener;
import com.pehulja.esper.deposit_check.listeners.DepositIncomeLengthListener;
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

	public void subscribeOftenDeposit(EPServiceProvider provider) {
		ConfigurationOperations configuration = provider.getEPAdministrator().getConfiguration();
		configuration.addEventType("DepositFraudEvent", DepositFraudEvent.class);
		EPStatement statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.fraud.event"));
		statement.addListener(new DepositFraudListener());
		
	}

	public void subscribeIncomeEvent(EPServiceProvider provider) {
		ConfigurationOperations configuration = provider.getEPAdministrator().getConfiguration();

		EPStatement statement;
		Map<String, Object> depositIncomeEvent = new HashMap<String, Object>();
		depositIncomeEvent.put("accountName", String.class);
		depositIncomeEvent.put("income", int.class);

		configuration.addEventType("depositIncomeEvent", depositIncomeEvent);
		statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.income.event"));
		statement.addListener(new DepositIncomeListener());
		
		//TODO: change to create schema
		statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.income.event.length"));
		statement.addListener(new DepositIncomeLengthListener());
	}
	
	public void subscribeXMLEvents(EPServiceProvider provider){
		ConfigurationOperations configuration = provider.getEPAdministrator().getConfiguration();
		URL url = this.getClass().getClassLoader().getResource("xmlEvents/schema/depositBrakeEventSchema.xsd");
		
		ConfigurationEventTypeXMLDOM typeXMLDOM = new ConfigurationEventTypeXMLDOM();
		typeXMLDOM.setRootElementName("DepositBrokeEvent");
		typeXMLDOM.setSchemaResource(url.toString());
		
		configuration.addEventType("DepositBrokeEvent", typeXMLDOM);
		
		EPStatement statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.broke.event"));
		statement.addListener(new DepositBrokeListener());
	}
	
	public void createNamedWindow(EPServiceProvider provider){
		EPStatement statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.window.named1"));
		provider.getEPAdministrator().createEPL(properties.getProperty("deposit.window.named1.populate"));
	}
	
}
