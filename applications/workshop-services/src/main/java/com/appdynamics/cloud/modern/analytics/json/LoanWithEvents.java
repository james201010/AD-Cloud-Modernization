/**
 * 
 */
package com.appdynamics.cloud.modern.analytics.json;

/**
 * @author James Schneider
 *
 */
public class LoanWithEvents {

	public static final int MILESTONE_CLASS_0 = 0;
	public static final int MILESTONE_CLASS_1 = 1;
	public static final int MILESTONE_CLASS_2 = 2;
	public static final int MILESTONE_CLASS_3 = 3;
	public static final int MILESTONE_CLASS_4 = 4;
	public static final int MILESTONE_CLASS_5 = 5;
	
	private String loanid;
	private LoanEvent ms1appsub;
	private LoanEvent ms2docs;
	private LoanEvent ms3credit;
	private LoanEvent ms4under;
	private LoanEvent ms5approval;
	private boolean published = false;
	private boolean selectedToPublish = false;
	
	/**
	 * 
	 */
	public LoanWithEvents() {
		
	}

	public String getLoanid() {
		return loanid;
	}

	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}

	public LoanEvent getMs1appsub() {
		return ms1appsub;
	}

	public void setMs1appsub(LoanEvent ms1appsub) {
		this.ms1appsub = ms1appsub;
	}

	public LoanEvent getMs2docs() {
		return ms2docs;
	}

	public void setMs2docs(LoanEvent ms2docs) {
		this.ms2docs = ms2docs;
	}

	public LoanEvent getMs3credit() {
		return ms3credit;
	}

	public void setMs3credit(LoanEvent ms3credit) {
		this.ms3credit = ms3credit;
	}

	public LoanEvent getMs4under() {
		return ms4under;
	}

	public void setMs4under(LoanEvent ms4under) {
		this.ms4under = ms4under;
	}

	public LoanEvent getMs5approval() {
		return ms5approval;
	}

	public void setMs5approval(LoanEvent ms5approval) {
		this.ms5approval = ms5approval;
	}

	
	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
	
	public boolean isSelectedToPublish() {
		return selectedToPublish;
	}

	public void setSelectedToPublish(boolean selectedToPublish) {
		this.selectedToPublish = selectedToPublish;
	}

	public boolean isMs1Candidate() {
		if (this.getMs1appsub().getMs1appsubdone() == false) {
			return true;
		} 
		return false;
		
	}
	
	public boolean isMs2Candidate() {
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == false) {
			
			return true;
		}
		return false;
	}
	
	public boolean isMs3Candidate() {
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == false) {
			
			return true;
		}
		return false;
	}
	
	public boolean isMs4Candidate() {
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == true &&
				this.getMs4under().getMs4underdone() == false) {
			
			return true;
		}
		return false;
	}
	
	public boolean isMs5Candidate() {
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == true &&
				this.getMs4under().getMs4underdone() == true &&
				this.getMs5approval().getMs5approvaldone() == false) {
			
			return true;
		}
		return false;
	}
	
	
	public int getMilestoneClassification() {

		if (this.getMs1appsub().getMs1appsubdone() == false && 
				this.getMs2docs().getMs2docsdone() == false && 
				this.getMs3credit().getMs3creditdone() == false &&
				this.getMs4under().getMs4underdone() == false &&
				this.getMs5approval().getMs5approvaldone() == false) {
			
			return MILESTONE_CLASS_0;
		}		
		
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == false && 
				this.getMs3credit().getMs3creditdone() == false &&
				this.getMs4under().getMs4underdone() == false &&
				this.getMs5approval().getMs5approvaldone() == false) {
			
			return MILESTONE_CLASS_1;
		}		

		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == false &&
				this.getMs4under().getMs4underdone() == false &&
				this.getMs5approval().getMs5approvaldone() == false) {
			
			return MILESTONE_CLASS_2;
		}		
		
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == true &&
				this.getMs4under().getMs4underdone() == false &&
				this.getMs5approval().getMs5approvaldone() == false) {
			
			return MILESTONE_CLASS_3;
		}		
		
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == true &&
				this.getMs4under().getMs4underdone() == true &&
				this.getMs5approval().getMs5approvaldone() == false) {
			
			return MILESTONE_CLASS_4;
		}		
		
		if (this.getMs1appsub().getMs1appsubdone() == true && 
				this.getMs2docs().getMs2docsdone() == true && 
				this.getMs3credit().getMs3creditdone() == true &&
				this.getMs4under().getMs4underdone() == true &&
				this.getMs5approval().getMs5approvaldone() == true) {
			
			return MILESTONE_CLASS_5;
		}		
		
		
		return MILESTONE_CLASS_0;
	}






}
