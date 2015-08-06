package com.insart.titanium.concept;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.insart.titanium.concept.configuration.EsperConfiguration;
import com.insart.titanium.concept.configuration.SpringConfiguration;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.esper.eventsProvider.TransactionEventGenerator;
import com.insart.titanium.concept.persistance.repository.ATMTransactionRepository;

/**
 * @author Eugene Pehulja
 * @since Aug 3, 2015 10:47:20 AM
 */
@Component
public class UsedApproachesForVirtualDataWindow {
	private final Logger logger = LoggerFactory.getLogger(UsedApproachesForVirtualDataWindow.class);

	@Resource
	private Environment environment;

	@Autowired
	EPServiceProvider epServiceProvider;

	@Autowired
	ATMTransactionRepository repository;

	@Autowired
	TransactionEventGenerator eventGenerator;

	public static void main(String[] argv) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class, EsperConfiguration.class);
		UsedApproachesForVirtualDataWindow usedApproaches = ctx.getBean(UsedApproachesForVirtualDataWindow.class);

		usedApproaches.approach1();

		// Emulate event flow
		usedApproaches.eventGenerator.generateEvents(2);
	}

	/*
	 * This approach uses
	 * .std:groupwin(account).win:length(5).couchbase:couchbasevdw()
	 */
	public void approach1() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach1.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach1.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach1.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					logger.info("OVER LIMIT TRANSACTION: Account: " + newEvents[0].get("name") + ", total transaction amount: " + newEvents[0].get("total"));
				}
			}
		});
	}

	/*
	 * This approach uses .couchbase:couchbasevdw()
	 */
	public void approach2() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach2.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach2.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach2.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					logger.info("OVER LIMIT TRANSACTION: Account: " + newEvents[0].get("name") + ", total transaction amount: " + newEvents[0].get("total"));
				}
			}
		});
	}

	/*
	 * This approach uses .couchbase:couchbasevdw()
	 */
	public void approach3() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach3.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach3.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach3.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					logger.info("OVER LIMIT TRANSACTION: Account: " + newEvents[0].get("name") + ", total transaction amount: " + newEvents[0].get("total"));
				}
			}
		});
	}

	/*
	 * This combined approach with direct queries to DB
	 */
	public void approach4() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach4.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach4.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach4.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				ATMTransactionEvent transactionEvent = (ATMTransactionEvent) newEvents[0].getUnderlying();
				Set<ATMTransactionEvent> events = repository.customFind1(transactionEvent.getAccount());
				if (!events.contains(newEvents[0].getUnderlying())) {
					events.add((ATMTransactionEvent) newEvents[0].getUnderlying());
				}
				try {
					double totalTransactionAmount = events.stream().parallel().filter(p -> p != null).mapToDouble(tx -> tx.getTransactionAmount()).sum();
					if (totalTransactionAmount > 5000) {
						logger.info("OVER LIMIT TRANSACTION: Account: " + transactionEvent.getAccount() + ", total transaction amount: " + totalTransactionAmount);
					}
				} catch (Exception ex) {
					logger.error("Something goes wrong when counting total amount of transactions by account", ex);
				}
			}
		});
	}
}
