package com.insart.titanium.concept.esper.events.generic;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Field;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:39:24 PM
 */
public abstract class TransactionEvent {

	@Id
	private String id;

	@Field
	private Date date;

	@Field
	private String account;
	
	private boolean deletedFlag = true;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
	public boolean isDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(boolean deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "TransactionEvent [id=" + id + ", date=" + date + ", account=" + account + "]";
	}
}
