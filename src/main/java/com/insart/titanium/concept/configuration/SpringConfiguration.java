package com.insart.titanium.concept.configuration;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.insart.titanium.concept.esper.vdw.CouchbaseVirtualDataWindowFactory;

@Configuration
@ComponentScan({"com.insart.titanium.concept"})
@EnableCouchbaseRepositories(basePackages = "com.insart.titanium.concept.persistance.repository", repositoryImplementationPostfix = "CustomImpl")
@PropertySource(value = { "classpath:application.properties", "classpath:events.properties" })
public class SpringConfiguration extends AbstractCouchbaseConfiguration {

	private static final String PROPERTY_COUCHBASE_LOCATION = "couchbase.host";
	private static final String PROPERTY_COUCHBASE_BACKET = "couchbase.backet";
	private static final String PROPERTY_COUCHBASE_PASSWORD = "couchbase.password";

	@Resource
	private Environment env;

	@Override
	protected String getBucketName() {
		return env.getProperty(PROPERTY_COUCHBASE_BACKET);
	}

	@Override
	protected String getBucketPassword() {
		return env.getProperty(PROPERTY_COUCHBASE_PASSWORD);
	}

	@Bean
	public ApplicationContextProvider getApplicationContextProvider() {
		return new ApplicationContextProvider();
	}

	@Override
	protected List<String> getBootstrapHosts() {
		return Arrays.asList(env.getProperty(PROPERTY_COUCHBASE_LOCATION));
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public EPServiceProvider getEPServiceProvider(){
		com.espertech.esper.client.Configuration configuration = new com.espertech.esper.client.Configuration();
		configuration.addPlugInVirtualDataWindow("couchbase", "couchbasevdw", CouchbaseVirtualDataWindowFactory.class.getName());
		return EPServiceProviderManager.getDefaultProvider(configuration);
	}
}
