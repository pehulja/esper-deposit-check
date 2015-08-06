package com.insart.titanium.concept.persistance.repository;

import java.util.Set;

import org.springframework.data.couchbase.core.view.Query;
import org.springframework.data.repository.CrudRepository;

import com.insart.titanium.concept.esper.events.ATMTransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 30, 2015 11:22:04 AM
 */
public interface ATMTransactionRepository extends CrudRepository<ATMTransactionEvent, Long> {
	@Query("$SELECT_ENTITY$ WHERE account = $1 AND $FILTER_TYPE$")
	public Set<ATMTransactionEvent> customFind1(String account);

	public Set<ATMTransactionEvent> findTransactionsByAccount(String account);
}
