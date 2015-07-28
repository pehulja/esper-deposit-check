package com.insart.titanium.concept.persistance.repository;

import java.util.List;

import org.springframework.data.couchbase.core.view.View;
import org.springframework.data.repository.CrudRepository;

import com.insart.titanium.concept.esper.events.generic.TransactionEvent;
import com.insart.titanium.concept.persistance.repository.custom.GenericTransactionRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 9:47:19 AM
 */
public interface GenericTransactionRepository extends CrudRepository<TransactionEvent, Long>, GenericTransactionRepositoryCustom {

	@View
	public List<TransactionEvent> findByType(String eventType);

}
