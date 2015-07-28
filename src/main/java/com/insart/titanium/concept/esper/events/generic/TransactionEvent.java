package com.insart.titanium.concept.esper.events.generic;

import java.util.Date;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:39:24 PM
 */
public abstract class TransactionEvent {

	@Id
	private Long id;

	@Field
	private Date date;

	@Field
	private String account;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @param id
	 * @param date
	 * @param account
	 */
	public TransactionEvent(Date date, String account) {
		super();
		this.date = date;
		this.account = account;
	}

	@Override
	public String toString() {
		return "TransactionEvent [id=" + id + ", date=" + date + ", account=" + account + "]";
	}
}
