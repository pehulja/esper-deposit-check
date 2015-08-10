package com.insart.titanium.concept.esper.eventsProvider;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPServiceProvider;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.esper.events.BigATMTransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 27, 2015 6:01:19 PM
 */
@Component
public class TransactionEventGenerator {
	final Logger logger = LoggerFactory.getLogger(TransactionEventGenerator.class);

	@Autowired
	EPServiceProvider epServiceProvider;

	private final int DEFAULT_UNIQUE_ACCOUNT_NUMBER = 4;

	public void generateEvents(int uniqueAccountNumber) {
		final EPServiceProvider epServiceProvider = this.epServiceProvider;
		// ATMTransactionRepository.deleteAll();

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
			Random random = new Random();

			@Override
			public void run() {
				while (true) {
					ATMTransactionEvent atmTransactionEvent = new ATMTransactionEvent(new Date(), "Account_" + random.nextInt(uniqueAccountNumber), random.nextInt(1000),
							"Address" + random.nextInt(5));
					epServiceProvider.getEPRuntime().sendEvent(atmTransactionEvent);
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						logger.error("", ex);
					}
				}
			}
		});
	}
	
	public void generateBigEvents(int uniqueAccountNumber) {
		final EPServiceProvider epServiceProvider = this.epServiceProvider;
		// ATMTransactionRepository.deleteAll();

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
			Random random = new Random();

			@Override
			public void run() {
				while (true) {
					ATMTransactionEvent atmTransactionEvent = new BigATMTransactionEvent(new Date(), "Account_" + random.nextInt(uniqueAccountNumber), random.nextInt(1000),
							"Address" + random.nextInt(100));
					epServiceProvider.getEPRuntime().sendEvent(atmTransactionEvent);
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						logger.error("", ex);
					}
				}
			}
		});
	}

	public void generateEvents() {
		this.generateEvents(DEFAULT_UNIQUE_ACCOUNT_NUMBER);
	}
}
