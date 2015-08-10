package com.insart.titanium.concept.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import com.insart.titanium.concept.esper.events.BigATMTransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 30, 2015 11:22:04 AM
 */
public interface BigATMTransactionRepository extends CrudRepository<BigATMTransactionEvent, Long> {
}
