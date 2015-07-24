package com.insart.titanium.concept.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 10:31:36 AM
 */
public class ApplicationContextProvider implements ApplicationContextAware {
	private static ConfigurableApplicationContext ctx = null;

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = (ConfigurableApplicationContext) ctx;
	}
}
