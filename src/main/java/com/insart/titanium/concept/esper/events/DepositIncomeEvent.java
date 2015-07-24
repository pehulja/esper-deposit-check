package com.insart.titanium.concept.esper.events;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.insart.titanium.concept.esper.events.generic.GenericEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:41:09 PM
 */
@Document
public class DepositIncomeEvent extends GenericEvent {
	@Field
	private String accountName;

	@Field
	private int income;

	/**
	 * @param accountName
	 * @param income
	 */

	public DepositIncomeEvent(String accountName, int income, String type) {
		super();
		this.accountName = accountName;
		this.income = income;
		this.setType(type);
	}

	public DepositIncomeEvent() {
		super();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	@Override
	public String toString() {
		return "DepositIncomeEvent [accountName=" + accountName + ", income=" + income + "]";
	}

}
