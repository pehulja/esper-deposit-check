package com.insart.titanium.concept;

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
import com.insart.titanium.concept.esper.events.BigATMTransactionEvent;
import com.insart.titanium.concept.esper.eventsProvider.TransactionEventGenerator;
import com.insart.titanium.concept.persistance.repository.ATMTransactionRepository;

/**
 * @author Eugene Pehulja
 * @since Aug 3, 2015 10:47:20 AM
 */
@Component
public class MemoryUsageTest {
	private final Logger logger = LoggerFactory.getLogger(MemoryUsageTest.class);

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
		MemoryUsageTest usedApproaches = ctx.getBean(MemoryUsageTest.class);

		usedApproaches.approach3();

		// Emulate event flow
		usedApproaches.eventGenerator.generateBigEvents(2);
	}

	public void approach1() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("BigATMTransactionEvent", BigATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach6.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach6.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach6.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					logger.info("Account: " + newEvents[0].get("account") + ", average transaction amount: " + newEvents[0].get("average"));
				}
			}
		});
	}
	
	public void approach3() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("BigATMTransactionEvent", BigATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach7.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach7.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach7.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					logger.info("Account: " + newEvents[0].get("account") + ", average transaction amount: " + newEvents[0].get("average"));
				}
			}
		});
	}
	
	public void approach2() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("BigATMTransactionEvent", BigATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach8.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach8.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach8.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					logger.info("Account: " + newEvents[0].get("account") + ", average transaction amount: " + newEvents[0].get("average"));
				}
			}
		});
	}
}
