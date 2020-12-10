package com.appdynamics.cloud.modern.repos.model.loanaccounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "loansummary", catalog = "loanaccounts")
public class LoanAccount {

	@Id @GeneratedValue
	private long id;
	
	@Column(name="memberid")
	private String memberId;
	
	@Column(name="accountname")
	private String accountName;
	
	@Column(name="accountnumber")
	private String accountNumber;
	
	@Column(name="currentbalance")
	private Double currentBalance;
	
	@Column(name="paymentdueamt")
	private Double paymentDueAmt;
	
	@Column(name="paymentduedate")
	private String paymentDueDate;
	
	@Column(name="accounttype")
	private String accountType;
	
	public LoanAccount() {
		super();
	}

	public LoanAccount(long id, String memberId, String accountName, String accountNumber, Double currentBalance, Double paymentDueAmt, String paymentDueDate, String accountType) {
		super();
		
		this.id = id;
		this.memberId = memberId;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.currentBalance = currentBalance;
		this.paymentDueAmt = paymentDueAmt;
		this.paymentDueDate = paymentDueDate;
		this.accountType = accountType;
		
	}

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
