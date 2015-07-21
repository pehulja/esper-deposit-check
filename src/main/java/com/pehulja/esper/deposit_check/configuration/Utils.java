package com.pehulja.esper.deposit_check.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.pehulja.esper.deposit_check.subscribers.DepositEventSubscribers;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 6:16:54 PM	
 */
public class Utils {
	private static final Logger logger;
	private static final Properties properties;

	static {
		logger = Logger.getLogger(Utils.class);
		properties = new Properties();
		ClassLoader classLoader = DepositEventSubscribers.class.getClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream("events.properties")) {
			properties.load(inputStream);
		} catch (IOException ex) {
			logger.error("", ex);
		}
	}
	
	public static final void readNamedWindow(EPServiceProvider provider){
		EPStatement statement = provider.getEPAdministrator().createEPL(properties.getProperty("deposit.window.named1.select"));
		Iterator<EventBean> iterator =  statement.iterator();
		while(iterator.hasNext()){
			EventBean bean = iterator.next();
			System.out.println(bean.get("accountName"));
		}
	}
}
