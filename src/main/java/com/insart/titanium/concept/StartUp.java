package com.insart.titanium.concept;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.insart.titanium.concept.configuration.EsperConfiguration;
import com.insart.titanium.concept.configuration.SpringConfiguration;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.esper.eventsProvider.TransactionEventGenerator;
import com.insart.titanium.concept.esper.listeners.TransactionListener;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:38:26 AM
 */

@Component
public class StartUp {

	@Resource
	private Environment environment;

	@Autowired
	EPServiceProvider epServiceProvider;

	@Autowired
	TransactionEventGenerator eventGenerator;

	public static void main(String[] argv) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class, EsperConfiguration.class);
		StartUp startUp = ctx.getBean(StartUp.class);
		startUp.start();
	}
	
	public void vdwTest(){
		final int DATA_WINDOW_LENGTH = 2;
		int TOTAL_AMOUT_MEASURE = 200;

		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);

		int virtualDataWindowId = 0;
		
		// Virtual Data Window 1
		virtualDataWindowId = 1;
		epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.create"), Integer.toString(virtualDataWindowId)));
		epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.subscribe"), Integer.toString(virtualDataWindowId)));

		EPStatement statement = epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.fraud"), Integer.toString(virtualDataWindowId), Integer.toString(TOTAL_AMOUT_MEASURE)));
		statement.addListener(new TransactionListener(transaction -> {
			System.out.println("VURTUAL DATA WINDOW1: Account: " + transaction.get("name") + ", Total transaction amount: " + transaction.get("total"));
		}));
	}
	
	public void vdwTestManualInsert(){
		final int DATA_WINDOW_LENGTH = 2;
		int TOTAL_AMOUT_MEASURE = 3000;

		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);

		int virtualDataWindowId = 0;
		
		// Virtual Data Window 1
		virtualDataWindowId = 1;
		epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.create"), Integer.toString(virtualDataWindowId)));
		epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.merge"), Integer.toString(virtualDataWindowId)));
	}
	
	public void namedWindowTest(){
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("atmtransaction.named.window.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("atmtransaction.named.window.subscribe"));

		EPStatement statement = epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("atmtransaction.named.window.fraud"));
		statement.addListener(new TransactionListener(transaction -> {
			System.out.println("NAMED DATA WINDOW: Account: " + transaction.get("account") + ", Number: " + transaction.get("number"));
		}));
	}
	
	public void start() {
		vdwTest();
		
		// Virtual Data Window 2
/*		virtualDataWindowId = 2;
		TOTAL_AMOUT_MEASURE = 1500;
		epServiceProvider.getEPAdministrator().createEPL(
				MessageFormat.format(environment.getProperty("atmtransaction.window.create"), Integer.toString(virtualDataWindowId), Integer.toString(DATA_WINDOW_LENGTH)));
		epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.subscribe"), Integer.toString(virtualDataWindowId)));

		statement = epServiceProvider.getEPAdministrator().createEPL(
				MessageFormat.format(environment.getProperty("atmtransaction.window.fraud"), Integer.toString(virtualDataWindowId), Integer.toString(TOTAL_AMOUT_MEASURE)));
		statement.addListener(new TransactionListener(transaction -> {
			System.out.println("VURTUAL DATA WINDOW2: Account: " + transaction.get("account") + ", Total transaction amount: " + transaction.get("total"));
		}));*/

		eventGenerator.generateEvents(2);
	}
}
