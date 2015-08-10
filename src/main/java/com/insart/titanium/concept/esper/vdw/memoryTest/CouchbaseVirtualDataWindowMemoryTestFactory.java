package com.insart.titanium.concept.esper.vdw.memoryTest;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.client.hook.VirtualDataWindowFactoryContext;
import com.insart.titanium.concept.configuration.ApplicationContextProvider;

/**
 * Created by David on 03/02/2015.
 */
public class CouchbaseVirtualDataWindowMemoryTestFactory implements VirtualDataWindowFactory {

	private static final Logger log = LoggerFactory.getLogger(CouchbaseVirtualDataWindowMemoryTestFactory.class);
	private ConfigurableApplicationContext applicationContext;

	/**
	 * @param host
	 * @param backetName
	 * @param backetPassword
	 */
	public CouchbaseVirtualDataWindowMemoryTestFactory() {
		super();
		applicationContext = (ConfigurableApplicationContext) ApplicationContextProvider.getApplicationContext();
	}

	@Override
	public void initialize(VirtualDataWindowFactoryContext virtualDataWindowFactoryContext) {
		log.debug("", virtualDataWindowFactoryContext);
	}

	@Override
	public VirtualDataWindow create(VirtualDataWindowContext context) {
		CouchbaseVirtualDataWindowMemoryTest couchbaseVirtualDataWindow = (CouchbaseVirtualDataWindowMemoryTest) applicationContext.getBean("CouchbaseVirtualDataWindowMemory", context);
		return couchbaseVirtualDataWindow;
	}

	@Override
	public void destroyAllContextPartitions() {
		log.debug("destroyAllContextPartitions()");
	}

	@Override
	public Set<String> getUniqueKeyPropertyNames() {
		return Collections.singleton("id");
	}
}