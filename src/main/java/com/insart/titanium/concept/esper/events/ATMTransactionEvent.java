package com.insart.titanium.concept.esper.events;

import java.util.Date;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

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

}
