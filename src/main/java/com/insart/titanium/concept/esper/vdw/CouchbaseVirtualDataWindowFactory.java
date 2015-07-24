package com.insart.titanium.concept.esper.vdw;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.client.hook.VirtualDataWindowFactoryContext;
import com.insart.titanium.concept.configuration.ApplicationContextProvider;

/**
 * Created by David on 03/02/2015.
 */
public class CouchbaseVirtualDataWindowFactory implements VirtualDataWindowFactory {

	private static final Log log = LogFactory.getLog(CouchbaseVirtualDataWindowFactory.class);
	private ConfigurableApplicationContext applicationContext;

	/**
	 * @param host
	 * @param backetName
	 * @param backetPassword
	 */
	public CouchbaseVirtualDataWindowFactory() {
		super();
		applicationContext = (ConfigurableApplicationContext) ApplicationContextProvider.getApplicationContext();
	}

	@Override
	public void initialize(VirtualDataWindowFactoryContext virtualDataWindowFactoryContext) {
		log.debug(virtualDataWindowFactoryContext);
	}

	@Override
	public VirtualDataWindow create(VirtualDataWindowContext context) {
		return applicationContext.getBean(CouchbaseVirtualDataWindow.class, context);
	}

	@Override
	public void destroyAllContextPartitions() {
		log.debug("destroyAllContextPartitions()");
	}

	@Override
	public Set<String> getUniqueKeyPropertyNames() {
		log.debug("getUniqueKeyPropertyNames()");
		return Collections.singleton("couchbaseId");
	}
}