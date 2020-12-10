package com.appdynamics.cloud.modern.web.beans;

public class LoanAccount {

	
	private String accountName;
	private String accountType;
	private String accountNumber;
	private Double currentBalance;
	private Double paymentDueAmt;
	private String paymentDueDate;
	
	
	public LoanAccount() {
		
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


	public Double getCurrentBalance() {
		return currentBalance;
	}


	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}


	public Double getPaymentDueAmt() {
		return paymentDueAmt;
	}


	public void setPaymentDueAmt(Double paymentDueAmt) {
		this.paymentDueAmt = paymentDueAmt;
	}


	public String getPaymentDueDate() {
		return paymentDueDate;
	}


	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	
}
