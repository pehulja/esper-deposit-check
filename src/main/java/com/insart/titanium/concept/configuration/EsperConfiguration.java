package com.insart.titanium.concept.configuration;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.insart.titanium.concept.esper.events.ATMTransactionEvent;
import com.insart.titanium.concept.esper.vdw.CouchbaseVirtualDataWindowFactory;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:23:29 AM
 */
@Configuration
public class EsperConfiguration{
	private static final Logger logger;

	@Resource
	private Environment environment;
	
	private EPServiceProvider epServiceProvider;

	static {
		logger = Logger.getLogger(EsperConfiguration.class);
	}

	@Bean
	public EPServiceProvider getEPServiceProvider() {
		com.espertech.esper.client.Configuration configuration = new com.espertech.esper.client.Configuration();
		configuration.addPlugInVirtualDataWindow("couchbase", "couchbasevdw", CouchbaseVirtualDataWindowFactory.class.getName());
		
		epServiceProvider =  EPServiceProviderManager.getDefaultProvider(configuration);
		return epServiceProvider;
	}

}