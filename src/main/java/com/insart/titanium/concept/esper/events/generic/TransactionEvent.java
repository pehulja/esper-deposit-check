package com.insart.titanium.concept.esper.events.generic;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.couchbase.client.java.repository.annotation.Field;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:39:24 PM
 */
public abstract class TransactionEvent {

	@Id
	private String id;

	@Field
	private Date date;

	@NotNull
	@Field
	private String account;

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

	@Override
	public String toString() {
		return "TransactionEvent [id=" + id + ", date=" + date + ", account=" + account + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionEvent other = (TransactionEvent) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}