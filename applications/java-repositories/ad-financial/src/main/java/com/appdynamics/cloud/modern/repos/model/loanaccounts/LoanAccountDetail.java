package com.appdynamics.cloud.modern.repos.model.loanaccounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "loandetail", catalog = "loanaccounts")
public class LoanAccountDetail {

	@Id @GeneratedValue
	private long id;
	
	@Column(name="memberid")
	private String memberId;
		
	@Column(name="accountnumber")
	private String accountNumber;

	@Column(name="monthmulti")
	private int monthMulti;

	@Column(name="txndate")
	private String txnDate;

	@Column(name="txndescription")
	private String txnDescription;
	
	@Column(name="txnamount")
	private Double txnAmount;
	
	@Column(name="runbalance")
	private Double runBalance;
	
	@Column(name="txnorder")
	private int txnOrder;
	
     
	public LoanAccountDetail() {
		super();
	}

	public LoanAccountDetail(long id, String memberId, String accountNumber, int monthMulti, String txnDate, String txnDescription, Double txnAmount, Double runBalance, int txnOrder) {
		super();
		
		this.id = id;
		this.memberId = memberId;
		this.accountNumber = accountNumber;
		this.monthMulti = monthMulti;
		this.txnDate = txnDate;
		this.txnDescription = txnDescription;
		this.txnAmount = txnAmount;
		this.runBalance = runBalance;
		this.txnOrder = txnOrder;
		
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

	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getMonthMulti() {
		return monthMulti;
	}

	public void setMonthMulti(int monthMulti) {
		this.monthMulti = monthMulti;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnDescription() {
		return txnDescription;
	}

	public void setTxnDescription(String txnDescription) {
		this.txnDescription = txnDescription;
	}

	public Double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Double getRunBalance() {
		return runBalance;
	}

	public void setRunBalance(Double runBalance) {
		this.runBalance = runBalance;
	}

	public int getTxnOrder() {
		return txnOrder;
	}

	public void setTxnOrder(int txnOrder) {
		this.txnOrder = txnOrder;
	}

	

	
}
