package com.pehulja.esper.deposit_check.events;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.espertech.esper.client.EPRuntime;
import com.pehulja.esper.deposit_check.configuration.StartUp;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:46:46 AM
 */
@RunWith(JUnit4ClassRunner.class)
public class DepositFraudEventTest {
	EPRuntime runtime = null;
	@Before
	public void before(){
		runtime = new StartUp().configure();		
	}
	
	@Test
	public void plainTest1() {
		int amount = 0;
		for (int i = 0; i < 5; i++) {
			amount = i;
			DepositFraudEvent event;
			if (i % 2 == 0) {
				event = new DepositFraudEvent("AccountA", amount);
			} else {
				event = new DepositFraudEvent("AccountB", amount);
			}
			runtime.sendEvent(event);
		}
	}
}
