package com.pehulja.esper.deposit_check.subscribers.events;

import java.util.Random;

import com.pehulja.esper.deposit_check.subscribers.events.generic.GenericEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 20, 2015 6:41:09 PM	
 */
public class DepositIncomeEvent extends GenericEvent<DepositIncomeEvent>{
	private String accountName;
	private int income;
	private String key;
	/**
	 * @param accountName
	 * @param income
	 */
	
	
	public DepositIncomeEvent(String accountName, int income) {
		super();
		this.accountName = accountName;
		this.income = income;
		key = Integer.toString(new Random().nextInt());
	}
	
	/**
	 * 
	 */
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

	/* (non-Javadoc)
	 * @see com.pehulja.esper.deposit_check.subscribers.events.wrapper.Wrapper#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

}
