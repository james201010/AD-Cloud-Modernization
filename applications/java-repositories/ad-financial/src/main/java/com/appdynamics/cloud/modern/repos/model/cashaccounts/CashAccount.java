package com.appdynamics.cloud.modern.repos.model.cashaccounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "cashsummary", catalog = "cashaccounts")
public class CashAccount {

	@Id @GeneratedValue
	private long id;
	
	@Column(name="memberid")
	private String memberId;
	
	@Column(name="accountname")
	private String accountName;
	
	@Column(name="accountnumber")
	private String accountNumber;
	
	@Column(name="avalbalance")
	private Double avalBalance;
	
	@Column(name="currentbalance")
	private Double currentBalance;
	
 
    
	public CashAccount() {
		super();
	}

	public CashAccount(long id, String memberId, String accountName, String accountNumber,  Double avalBalance, Double currentBalance) {
		super();
		
		this.id = id;
		this.memberId = memberId;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.avalBalance = avalBalance;
		this.currentBalance = currentBalance;
		
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

	public Double getAvalBalance() {
		return avalBalance;
	}

	public void setAvalBalance(Double avalBalance) {
		this.avalBalance = avalBalance;
	}

	
	
}
