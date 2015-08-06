package com.insart.titanium.concept.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.mapping.model.MappingException;

@Configuration
@ComponentScan({ "com.insart.titanium.concept" })
@EnableCouchbaseRepositories(basePackages = "com.insart.titanium.concept.persistance.repository", repositoryImplementationPostfix = "CustomImpl")
@PropertySource(value = { "classpath:application.properties", "classpath:events.properties" })
public class SpringConfiguration extends AbstractCouchbaseConfiguration {

	@Value("${couchbase.host}")
	public String PROPERTY_COUCHBASE_LOCATION;

	@Value("${couchbase.backet}")
	public String PROPERTY_COUCHBASE_BACKET;

	@Value("${couchbase.password}")
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
	public ApplicationContextAware getApplicationContextProvider() {
		return new ApplicationContextProvider();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public MappingCouchbaseConverter mappingCouchbaseConverter() throws Exception {
		MappingCouchbaseConverter converter = new MappingCouchbaseConverter(couchbaseMappingContext(), typeKey()) {

			@Override
			public void write(Object source, CouchbaseDocument target) {
				try {
					super.write(source, target);
				} catch (MappingException ex) {
					if (target.getId() == null) {
						target.setId(Long.toString(this.applicationContext.getBean(CouchbaseTemplate.class).getCouchbaseBucket().counter("DOCUMENT_ID", 1, 1).content()));
					} else {
						throw ex;
					}
				}
			}
		};

		converter.setCustomConversions(customConversions());
		return converter;
	}

}
