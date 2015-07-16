package com.pehulja.esper.deposit_check.events;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import com.espertech.esper.client.EPRuntime;
import com.pehulja.esper.deposit_check.configuration.StartUp;
import com.pehulja.esper.deposit_check.configuration.Utils;

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
	@Ignore
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
	
	@Test
	@Ignore
	public void plainTest2() {
		Map<String, Object> event = new HashMap<>();
		event.put("accountName", "Account1");
		event.put("income", 5);
		runtime.sendEvent(event, "depositIncomeEvent");

		event.put("accountName", "Account1");
		event.put("income", 25);
		runtime.sendEvent(event, "depositIncomeEvent");

		event.put("accountName", "Account2");
		event.put("income", 30);
		runtime.sendEvent(event, "depositIncomeEvent");

	}
	
	@Test
	public void plainTest3() {
		Document document1 = Utils.getXMLEvent("xmlEvents/events/event1.xml");
		Document document2 = Utils.getXMLEvent("xmlEvents/events/event2.xml");
		
		runtime.sendEvent(document1);
		runtime.sendEvent(document2);
	}
}
