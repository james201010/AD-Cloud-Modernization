package com.appdynamics.cloud.modern.repos.model.loanaccounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "loanapplication", catalog = "loanaccounts")
public class LoanApplication {

	@Id
	@Column(name="loanid")
	private String loanId;
	
	@Column(name="accountnumber")
	private String accountNumber;
		
	@Column(name="loancategory")
	private String loanCategory;

	@Column(name="email")
	private String email;

	@Column(name="firstname")
	private String firstName;

	@Column(name="lastname")
	private String lastName;
	
	@Column(name="orgbank")
	private String orgBank;
	
	@Column(name="loantype")
	private String loanType;
	
	@Column(name="loanamount")
	private String loanAmount;
	
	@Column(name="creditcheck")
	private String creditCheck;
	
	@Column(name="citylocation")
	private String cityLocation;
	
	@Column(name="msappsubdone")
	private String msAppSubDone;
	
	@Column(name="msdocsdone")
	private String msDocsDone;
	
	@Column(name="mscreditdone")
	private String msCreditDone;
	
	@Column(name="msunderdone")
	private String msUnderDone;
     
	@Column(name="msapprovaldone")
	private String msApprovalDone;
     
	@Column(name="msstepcount")
	private String msStepCount;
	
	public LoanApplication() {
		super();
	}

	public LoanApplication(String loanId, String accountNumber, String loanCategory, String email, String firstName, String lastName, String orgBank, 
			String loanType, String loanAmount, String creditCheck, String cityLocation, String msAppSubDone, String msDocsDone, String msCreditDone, 
			String msUnderDone, String msApprovalDone, String msStepCount) {
		super();
		
		this.loanId = loanId;
		this.accountNumber = accountNumber;
		this.loanCategory = loanCategory;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.orgBank = orgBank;
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.creditCheck = creditCheck;
		this.cityLocation = cityLocation;
		this.msAppSubDone = msAppSubDone;
		this.msDocsDone = msDocsDone;
		this.msCreditDone = msCreditDone;
		this.msUnderDone = msUnderDone;
		this.msApprovalDone = msApprovalDone;
		this.msStepCount = msStepCount;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getLoanCategory() {
		return loanCategory;
	}

	public void setLoanCategory(String loanCategory) {
		this.loanCategory = loanCategory;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrgBank() {
		return orgBank;
	}

	public void setOrgBank(String orgBank) {
		this.orgBank = orgBank;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getCreditCheck() {
		return creditCheck;
	}

	public void setCreditCheck(String creditCheck) {
		this.creditCheck = creditCheck;
	}

	public String getCityLocation() {
		return cityLocation;
	}

	public void setCityLocation(String cityLocation) {
		this.cityLocation = cityLocation;
	}

	public String getMsAppSubDone() {
		return msAppSubDone;
	}

	public void setMsAppSubDone(String msAppSubDone) {
		this.msAppSubDone = msAppSubDone;
	}

	public String getMsDocsDone() {
		return msDocsDone;
	}

	public void setMsDocsDone(String msDocsDone) {
		this.msDocsDone = msDocsDone;
	}

	public String getMsCreditDone() {
		return msCreditDone;
	}

	public void setMsCreditDone(String msCreditDone) {
		this.msCreditDone = msCreditDone;
	}

	public String getMsUnderDone() {
		return msUnderDone;
	}

	public void setMsUnderDone(String msUnderDone) {
		this.msUnderDone = msUnderDone;
	}

	public String getMsApprovalDone() {
		return msApprovalDone;
	}

	public void setMsApprovalDone(String msApprovalDone) {
		this.msApprovalDone = msApprovalDone;
	}

	public String getMsStepCount() {
		return msStepCount;
	}

	public void setMsStepCount(String msStepCount) {
		this.msStepCount = msStepCount;
	}
	
	

	
}
