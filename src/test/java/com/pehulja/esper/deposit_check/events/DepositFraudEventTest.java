package com.pehulja.esper.deposit_check.events;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EventBean;
import com.pehulja.esper.deposit_check.configuration.StartUp;
import com.pehulja.esper.deposit_check.configuration.Utils;
import com.pehulja.esper.deposit_check.subscribers.events.DepositIncomeEvent;
import com.pehulja.esper.deposit_check.subscribers.events.wrapper.Wrapper;

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
		DepositIncomeEvent depositIncomeEvent = null;
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			depositIncomeEvent = new DepositIncomeEvent("Account" + random.nextInt(3), random.nextInt(100));
			runtime.sendEvent(depositIncomeEvent);
		}
	}
	
	@Test
	public void plainTest4() {
		String stmt = "select accountName from CouchbaseWindow where key in ('Account0')";
		EPOnDemandQueryResult result = runtime.executeQuery(stmt);
		for (EventBean eventBean : result.getArray()) {
			System.out.println("COUNT: " + eventBean.get("accountName"));
		}
	}
	
	@Test
	@Ignore
	public void plainTest3() {
		Document document1 = Utils.getXMLEvent("xmlEvents/events/event1.xml");
		Document document2 = Utils.getXMLEvent("xmlEvents/events/event2.xml");
		
		runtime.sendEvent(document1);
		runtime.sendEvent(document2);
	}
}
