package com.pehulja.esper.deposit_check.subscribers.events.generic;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:39:24 PM
 */
public abstract class GenericEvent<T> {
	
	public abstract String getKey();
	
	@JsonIgnore
	public T getValue() {
		return (T) this;
	};
}
