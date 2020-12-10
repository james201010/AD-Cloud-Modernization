/**
 * 
 */
package com.appdynamics.cloud.modern.analytics.json;

/**
 * @author James Schneider
 *
 */
public class LoanEvent {

	public transient String uuid;
	public transient boolean published = false;
	private String loanid;
	private String acctnumber;
	private String loancategory;
	private String email;
	private String firstname;
	private String lastname;
	private String orgbank;
	private String loantype;
	private Integer loanamount;
	private String creditcheckr;
	private String locationcity;
	private Boolean ms1appsubdone;
	private Boolean ms2docsdone;
	private Boolean ms3creditdone;
	private Boolean ms4underdone;
	private Boolean ms5approvaldone;
	
	/**
	 * 
	 */
	public LoanEvent() {
		
	}

	public String getLoanid() {
		return loanid;
	}

	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}

	public String getAcctnumber() {
		return acctnumber;
	}

	public void setAcctnumber(String acctnumber) {
		this.acctnumber = acctnumber;
	}

	public String getLoancategory() {
		return loancategory;
	}

	public void setLoancategory(String loancategory) {
		this.loancategory = loancategory;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getOrgbank() {
		return orgbank;
	}

	public void setOrgbank(String orgbank) {
		this.orgbank = orgbank;
	}

	public String getLoantype() {
		return loantype;
	}

	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	public Integer getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(Integer loanamount) {
		this.loanamount = loanamount;
	}

	public String getCreditcheckr() {
		return creditcheckr;
	}

	public void setCreditcheckr(String creditcheckr) {
		this.creditcheckr = creditcheckr;
	}

	public String getLocationcity() {
		return locationcity;
	}

	public void setLocationcity(String locationcity) {
		this.locationcity = locationcity;
	}

	public Boolean getMs1appsubdone() {
		return ms1appsubdone;
	}

	public void setMs1appsubdone(Boolean ms1appsubdone) {
		this.ms1appsubdone = ms1appsubdone;
	}

	public Boolean getMs2docsdone() {
		return ms2docsdone;
	}

	public void setMs2docsdone(Boolean ms2docsdone) {
		this.ms2docsdone = ms2docsdone;
	}

	public Boolean getMs3creditdone() {
		return ms3creditdone;
	}

	public void setMs3creditdone(Boolean ms3creditdone) {
		this.ms3creditdone = ms3creditdone;
	}

	public Boolean getMs4underdone() {
		return ms4underdone;
	}

	public void setMs4underdone(Boolean ms4underdone) {
		this.ms4underdone = ms4underdone;
	}

	public Boolean getMs5approvaldone() {
		return ms5approvaldone;
	}

	public void setMs5approvaldone(Boolean ms5approvaldone) {
		this.ms5approvaldone = ms5approvaldone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanEvent other = (LoanEvent) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}


	public LoanEvent cloneLoanEvent(String uuid) {
		
		LoanEvent le = new LoanEvent();
		
		le.uuid = uuid;
		le.setAcctnumber(this.getAcctnumber());
		le.setCreditcheckr(this.getCreditcheckr());
		le.setEmail(this.getEmail());
		le.setFirstname(this.getFirstname());
		le.setLastname(this.getLastname());
		le.setLoanamount(this.getLoanamount());
		le.setLoancategory(this.getLoancategory());
		le.setLoanid(this.getLoanid());
		le.setLoantype(this.getLoantype());
		le.setLocationcity(this.getLocationcity());
		le.setOrgbank(this.getOrgbank());
		le.setMs1appsubdone(this.getMs1appsubdone());
		le.setMs2docsdone(this.getMs2docsdone());
		le.setMs3creditdone(this.getMs3creditdone());
		le.setMs4underdone(this.getMs4underdone());
		le.setMs5approvaldone(this.getMs5approvaldone());
		
		return le;
	}

	
}
