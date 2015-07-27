package com.insart.titanium.concept.persistance.repository;

import java.util.List;

import org.springframework.data.couchbase.core.view.View;
import org.springframework.data.repository.CrudRepository;

import com.insart.titanium.concept.esper.events.generic.GenericEvent;
import com.insart.titanium.concept.persistance.repository.custom.GenericEventRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 9:47:19 AM
 */
public interface GenericEventRepository extends CrudRepository<GenericEvent, Long>, GenericEventRepositoryCustom<GenericEvent, Long> {

	@View
	public List<GenericEvent> findByType(String eventType);
	
}
