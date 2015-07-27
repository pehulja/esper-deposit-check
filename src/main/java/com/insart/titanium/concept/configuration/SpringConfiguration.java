package com.insart.titanium.concept.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.insart.titanium.concept.esper.vdw.CouchbaseVirtualDataWindowFactory;

@Configuration
@ComponentScan({"com.insart.titanium.concept"})
@EnableCouchbaseRepositories(basePackages = "com.insart.titanium.concept.persistance.repository", repositoryImplementationPostfix = "CustomImpl")
@PropertySource(value = { "classpath:application.properties", "classpath:events.properties" })
public class SpringConfiguration extends AbstractCouchbaseConfiguration{

	@Value("${host}")
	public String PROPERTY_COUCHBASE_LOCATION;
	
	@Value("${backet}")
	public String PROPERTY_COUCHBASE_BACKET;
	
	@Value("${password}")
	public String PROPERTY_COUCHBASE_PASSWORD;
	
	@Override
	protected String getBucketName() {
		return PROPERTY_COUCHBASE_BACKET;
	}

	@Override
	protected String getBucketPassword() {
		return PROPERTY_COUCHBASE_PASSWORD;
	}
	@Override
	protected List<String> getBootstrapHosts() {
		return Arrays.asList(PROPERTY_COUCHBASE_LOCATION);
	}
	
	@Bean
	public ApplicationContextProvider getApplicationContextProvider() {
		return new ApplicationContextProvider();
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
