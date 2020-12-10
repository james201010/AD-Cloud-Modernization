package com.appdynamics.cloud.modern.web.beans;

public class CashAccount {

	private String accountName;
	private String accountNumber;
	private Double avalBalance;
	private Double currentBalance;
	
	public CashAccount() {
		
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getAvalBalance() {
		return avalBalance;
	}

	public void setAvalBalance(Double availableBalance) {
		this.avalBalance = availableBalance;

	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}


	
	
}
