package com.insart.titanium.concept.esper.events;

import java.util.Date;
import java.util.Random;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.insart.titanium.concept.esper.events.generic.TransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:41:09 PM
 */
@Document
public class ATMTransactionEvent extends TransactionEvent {
	@Field
	private double transactionAmount;

	@Field
	private String address;

	@Field
	private Long[] buffer = new Long[99999];

	/**
	 * @param date
	 * @param account
	 * @param transactionAmount
	 * @param address
	 */
	public ATMTransactionEvent(Date date, String account, double transactionAmount, String address) {
		super(date, account);
		this.transactionAmount = transactionAmount;
		this.address = address;
		Random random = new Random();
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = random.nextLong();
		}
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long[] getBuffer() {
		return buffer;
	}

	public void setBuffer(Long[] buffer) {
		this.buffer = buffer;
	}

	@Override
	public String toString() {
		return "ATMTransactionEvent [transactionAmount=" + transactionAmount + ", address=" + address + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		long temp;
		temp = Double.doubleToLongBits(transactionAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ATMTransactionEvent other = (ATMTransactionEvent) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (Double.doubleToLongBits(transactionAmount) != Double.doubleToLongBits(other.transactionAmount))
			return false;
		return true;
	}

}
