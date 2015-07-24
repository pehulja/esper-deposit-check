package com.insart.titanium.concept.esper.events.generic;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:39:24 PM
 */
public abstract class GenericEvent {

	@Id
	private Long id;
	@Field
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GenericEvent [id=" + id + ", type=" + type + "]";
	}

}
