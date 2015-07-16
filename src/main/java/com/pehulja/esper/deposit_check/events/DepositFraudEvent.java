package com.pehulja.esper.deposit_check.events;

/**
 * @author Eugene Pehulja
 * @since Jul 16, 2015 10:12:18 AM	
 */
public class DepositFraudEvent {
	private String accountName;
	private int amount;
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @param accountName
	 * @param amount
	 */
	public DepositFraudEvent(String accountName, int amount) {
		super();
		this.accountName = accountName;
		this.amount = amount;
	}
}