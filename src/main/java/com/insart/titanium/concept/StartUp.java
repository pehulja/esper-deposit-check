package com.insart.titanium.concept;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EventBean;
import com.insart.titanium.concept.configuration.SpringConfiguration;
import com.insart.titanium.concept.esper.events.DepositIncomeEvent;
import com.insart.titanium.concept.esper.subscribers.DepositEventSubscribers;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:38:26 AM
 */

@Component
public class StartUp {

	@Resource
	private Environment environment;

	@Autowired
	private DepositEventSubscribers depositEventSubscribers;
	
	@Autowired
	EPServiceProvider epServiceProvider;

	public void configure() {
		depositEventSubscribers.createCouchbaseWindow();
	}
	
	public void populate(){
		DepositIncomeEvent depositIncomeEvent = null;
		EPRuntime runtime = epServiceProvider.getEPRuntime();
		Random random = new Random();

		for(int i = 0; i < 5; i++){ 
			 depositIncomeEvent = new DepositIncomeEvent("Account" + random.nextInt(3), random.nextInt(10),	 "DEPOSIT_INCOME_EVENT"); runtime.sendEvent(depositIncomeEvent); 
			 runtime.sendEvent(depositIncomeEvent);
		}
		System.out.println("DONE");	

	}
	
	public void someTest(){

		EPRuntime runtime = epServiceProvider.getEPRuntime();

		EPOnDemandQueryResult result = runtime.executeQuery(environment.getProperty("deposit.couchbase.window.select.criteria"));
		for(EventBean eventBean : result.getArray()){
			System.out.println(eventBean.getUnderlying());
		}
		System.out.println("DONE");	
	}

	public void someTest2(){

		EPRuntime runtime = epServiceProvider.getEPRuntime();

		EPOnDemandQueryResult result = runtime.executeQuery(environment.getProperty("deposit.couchbase.window.select.criteria"));
		for(EventBean eventBean : result.getArray()){
			System.out.println(eventBean.getUnderlying());
		}
		System.out.println("DONE");	
	}
	
	public static void main(String[] argv) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		
		StartUp startUp = ctx.getBean(StartUp.class);
		startUp.configure();
		startUp.someTest();
		//startUp.populate();
	}
}
