package com.insart.titanium.concept;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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

	public void start() {
		final int DATA_WINDOW_LENGTH = 3;
		final int TOTAL_AMOUT_MEASURE = 2600;
		
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.create"), Integer.toString(DATA_WINDOW_LENGTH)));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("atmtransaction.window.subscribe"));
		
		EPStatement statement = epServiceProvider.getEPAdministrator().createEPL(MessageFormat.format(environment.getProperty("atmtransaction.window.fraud"), Integer.toString(TOTAL_AMOUT_MEASURE)));
		statement.addListener(new TransactionListener(transaction -> {
			System.out.println("Account: " + transaction.get("account") + ", Total transaction amount: " + transaction.get("total"));
		}));
		
		eventGenerator.generateEvents();
	}
}
