package com.pehulja.esper.deposit_check.configuration;

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
	public EPRuntime configure(){
		Configuration configuration = new Configuration();
		EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(configuration);
		DepositEventSubscribers depositEventSubscribers = new DepositEventSubscribers();
		depositEventSubscribers.subscribeOftenDeposit(configuration, provider);
		
		return provider.getEPRuntime();
	}
}
