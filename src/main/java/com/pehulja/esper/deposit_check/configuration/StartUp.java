package com.pehulja.esper.deposit_check.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.couchbase.esper.CouchbaseVirtualDataWindowFactory;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.pehulja.esper.deposit_check.subscribers.DepositEventSubscribers;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:38:26 AM
 */
public class StartUp {
	public EPServiceProvider configure() {
		Configuration configuration = new Configuration();
		configuration.addPlugInVirtualDataWindow("couchbase", "couchbasevdw", CouchbaseVirtualDataWindowFactory.class.getName());

		EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(configuration);
		

		DepositEventSubscribers depositEventSubscribers = new DepositEventSubscribers();
		depositEventSubscribers.subscribeOftenDeposit(provider);
		depositEventSubscribers.subscribeXMLEvents(provider);
		depositEventSubscribers.subscribeIncomeEvent(provider);

		depositEventSubscribers.createNamedWindow(provider);
		
		return provider;
	}
}
