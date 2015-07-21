package com.pehulja.esper.deposit_check.events;

import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.pehulja.esper.deposit_check.configuration.StartUp;
import com.pehulja.esper.deposit_check.subscribers.events.DepositIncomeEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:46:46 AM
 */
@RunWith(JUnit4ClassRunner.class)
public class DepositFraudEventTest {
	EPRuntime runtime = null;
	EPServiceProvider provider = null;
	@Before
	public void before(){
		provider = new StartUp().configure();
		runtime = provider.getEPRuntime();
	}
	
	@Test
	@Ignore
	public void plainTest() {
		DepositIncomeEvent depositIncomeEvent = null;
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			depositIncomeEvent = new DepositIncomeEvent("Account" + random.nextInt(3), random.nextInt(100));
			runtime.sendEvent(depositIncomeEvent);
		}
	}
}
