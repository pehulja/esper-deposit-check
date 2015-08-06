package com.insart.titanium.concept;

import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.insart.titanium.concept.configuration.EsperConfiguration;
import com.insart.titanium.concept.configuration.SpringConfiguration;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.esper.eventsProvider.TransactionEventGenerator;
import com.insart.titanium.concept.esper.vdw.LookupUtility;
import com.insart.titanium.concept.persistance.repository.ATMTransactionRepository;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:38:26 AM
 */

@Component
public class CustomViewsTest {
	private final Logger logger = LoggerFactory.getLogger(CustomViewsTest.class);

	@Resource
	private Environment environment;

	@Autowired
	EPServiceProvider epServiceProvider;

	@Autowired
	TransactionEventGenerator eventGenerator;

	@Autowired
	ATMTransactionRepository repository;

	@Autowired
	LookupUtility lookupUtility;

	public static void main(String[] argv) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class, EsperConfiguration.class);
		CustomViewsTest startUp = ctx.getBean(CustomViewsTest.class);
		startUp.start();
	}

	public void testCluster() {
		epServiceProvider.getEPAdministrator().getConfiguration().addVariable("lookup", LookupUtility.class, lookupUtility);

		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);

		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("atmtransaction.cluster.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("atmtransaction.cluster.subscribe"));
	}

	public void testCustom() {
		epServiceProvider.getEPAdministrator().getConfiguration().addPlugInView("custom", "distributed_length", "com.insart.titanium.concept.views.CustomViewFactory");
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach5.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach5.populate"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach5.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					System.out.println(newEvents[0].getUnderlying());
				}
			}
		});
	}

	public void testCustomLength() {
		epServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach6.create"));
		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach6.populate"));

		epServiceProvider.getEPAdministrator().createEPL(environment.getProperty("vdw.approach6.listen")).addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					ATMTransactionEvent atmTransactionEvent = (ATMTransactionEvent) newEvents[0].getUnderlying();
					long time = System.currentTimeMillis();
					StreamSupport.stream(repository.findAll().spliterator(), false).forEach(System.out::println);
					System.out.println("Find all: " + (System.currentTimeMillis() - time));
					
					time = System.currentTimeMillis();
					repository.customFind1(atmTransactionEvent.getAccount()).stream().forEach(System.out::println);
					System.out.println("Custom 1: " + (System.currentTimeMillis() - time));
					
					time = System.currentTimeMillis();
					repository.findTransactionsByAccount(atmTransactionEvent.getAccount()).stream().forEach(System.out::println);
					System.out.println("Custom 2: " + (System.currentTimeMillis() - time));


				}
			}
		});
	}

	public void start() {
		testCustomLength();
		eventGenerator.generateEvents(2);
	}
}
