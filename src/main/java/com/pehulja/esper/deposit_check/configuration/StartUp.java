package com.pehulja.esper.deposit_check.configuration;

import java.util.Random;

import com.couchbase.esper.CouchbaseVirtualDataWindowFactory;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.pehulja.esper.deposit_check.subscribers.DepositEventSubscribers;
import com.pehulja.esper.deposit_check.subscribers.events.DepositIncomeEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:38:26 AM
 */
public class StartUp {
	public EPServiceProvider configure() {
		Configuration configuration = new Configuration();
		configuration.addEventType(DepositIncomeEvent.class);

		configuration.addPlugInVirtualDataWindow("couchbase", "couchbasevdw", CouchbaseVirtualDataWindowFactory.class.getName());
		
		EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(configuration);

		DepositEventSubscribers depositEventSubscribers = new DepositEventSubscribers();
		depositEventSubscribers.createCouchbaseWindow(provider);
		
		return provider;
	}
	
	public static void main(String [] argv) throws InterruptedException{
		StartUp startUp = new StartUp();
		EPServiceProvider provider = startUp.configure();
		
		DepositIncomeEvent depositIncomeEvent = null;
		EPRuntime runtime = provider.getEPRuntime();
		Random random = new Random();
/*		
		for (int i = 0; i < 2; i++) {
			depositIncomeEvent = new DepositIncomeEvent("Account" + random.nextInt(3), random.nextInt(10));
			runtime.sendEvent(depositIncomeEvent);
		}*/
		
        String fireAndForget = "select * from CouchbaseWindow where key = \"1176695671\""; // see values in SampleVirtualDataWindowIndex
        EPOnDemandQueryResult result = provider.getEPRuntime().executeQuery(fireAndForget);
        System.out.println(result.getArray()[0].get("value"));
        
		depositIncomeEvent = new DepositIncomeEvent("Account" + random.nextInt(3), random.nextInt(10));
		runtime.sendEvent(depositIncomeEvent);


	}
}
