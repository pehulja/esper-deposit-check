package com.insart.titanium.concept.esper.events;

import java.util.Date;
import java.util.Random;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:41:09 PM
 */
@Document
public class BigATMTransactionEvent extends ATMTransactionEvent {

	@Field
	private Long[] buffer = new Long[99999];
	
	/**
	 * @param date
	 * @param account
	 * @param transactionAmount
	 * @param address
	 */
	public BigATMTransactionEvent(Date date, String account, double transactionAmount, String address) {
		super(date, account, transactionAmount, address);
		Random random = new Random();
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = random.nextLong();
		}
	}

	public Long[] getBuffer() {
		return buffer;
	}

	public void setBuffer(Long[] buffer) {
		this.buffer = buffer;
	}

}
