/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.util.List;
import java.util.Random;

import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanApplication;
import com.appdynamics.cloud.modern.repos.repository.loanaccounts.LoanApplicationsRepository;

/**
 * @author James Schneider
 *
 */
public class DataRepoUtils {

	/**
	 * 
	 */
	public DataRepoUtils() {
		
	}

	public static LoanApplication createLoanApplication(String loanCategory, String loanServicesSplit) {
		
		LoanApplication loanApp = new LoanApplication();
		
		String fName;
		String lName;
		String loanType;		

		fName = DataGenUtils.getFirstName();
		lName = DataGenUtils.getLastName();	
		if (loanCategory.equalsIgnoreCase("personal")) {
			loanType = DataGenUtils.getRetailLoanType();
		} else {
			loanType = DataGenUtils.getCommercialLoanType();
		}

		loanApp.setLoanId(DataGenUtils.getLoanId());
		loanApp.setAccountNumber(DataGenUtils.getAccountNumber());
		loanApp.setLoanCategory(loanCategory);
		loanApp.setEmail(DataGenUtils.getEmail(fName, lName));
		loanApp.setFirstName(fName);
		loanApp.setLastName(lName);
		loanApp.setOrgBank(DataGenUtils.getOriginatingBank());
		loanApp.setLoanType(loanType);
		loanApp.setLoanAmount(DataGenUtils.getLoanAmount(loanType));
		loanApp.setCreditCheck(DataGenUtils.getCreditCheckProvider());
		loanApp.setCityLocation(DataGenUtils.getLocationCity());
		loanApp.setMsAppSubDone("true");
		loanApp.setMsDocsDone("false");
		loanApp.setMsCreditDone("false");
		loanApp.setMsUnderDone("false");
		loanApp.setMsApprovalDone("false");
		loanApp.setMsStepCount("1");
		
		return loanApp;
	}
	
	public static LoanApplication getRandomLoanApplication(String msStepCount, LoanApplicationsRepository repo) {
		
		LoanApplication loanApp = null;
		List<LoanApplication> apps = repo.findByMsStepCount(msStepCount);
		
		if (apps != null) {
			
			if (apps.size() > 1) {
				Random rand = new Random();
				int selectedIndex = rand.nextInt(apps.size() - 1);
				loanApp = apps.get(selectedIndex);
			} else if (!apps.isEmpty()) {
				loanApp = apps.get(0);
			}
			
		}
		
		return loanApp;
		
	}
	
	
	public static LoanApplication processLoanSaveApplication(LoanApplication loanApp, LoanApplicationsRepository repo) {
		LoanApplication app = repo.saveAndFlush(loanApp);
		return app;
	}
	
	public static LoanApplication processLoanVerifyDocumentation(LoanApplication loanApp, LoanApplicationsRepository repo) {
		LoanApplication app = repo.saveAndFlush(loanApp);
		return app;		
	}
	
	public static LoanApplication processLoanCreditCheck(LoanApplication loanApp, LoanApplicationsRepository repo) {
		LoanApplication app = repo.saveAndFlush(loanApp);
		return app;
	}

	public static LoanApplication processLoanUnderwriting(LoanApplication loanApp, LoanApplicationsRepository repo) {
		LoanApplication app = repo.saveAndFlush(loanApp);
		return app;
	}

	public static LoanApplication processLoanApproval(LoanApplication loanApp, LoanApplicationsRepository repo) {
		LoanApplication app = repo.saveAndFlush(loanApp);
		return app;
	}
	
}
