/**
 * 
 */
package com.appdynamics.cloud.modern.repos.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appdynamics.cloud.modern.repos.beans.HttpJsonTransport;
import com.appdynamics.cloud.modern.repos.model.cashaccounts.CashAccount;
import com.appdynamics.cloud.modern.repos.model.cashaccounts.CashAccountDetail;
import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanAccount;
import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanAccountDetail;
import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanApplication;
import com.appdynamics.cloud.modern.repos.repository.cashaccounts.CashAccountDetailsRepository;
import com.appdynamics.cloud.modern.repos.repository.cashaccounts.CashAccountsRepository;
import com.appdynamics.cloud.modern.repos.repository.loanaccounts.LoanAccountDetailsRepository;
import com.appdynamics.cloud.modern.repos.repository.loanaccounts.LoanAccountsRepository;
import com.appdynamics.cloud.modern.repos.repository.loanaccounts.LoanApplicationsRepository;
import com.appdynamics.cloud.modern.repos.utils.ApplicationConstants;
import com.appdynamics.cloud.modern.repos.utils.CloudServicesHelper;
import com.appdynamics.cloud.modern.repos.utils.ConfigHelper;
import com.appdynamics.cloud.modern.repos.utils.DataRepoUtils;
import com.appdynamics.cloud.modern.repos.utils.PerformanceModifierConfig;
import com.appdynamics.cloud.modern.repos.utils.ReposConfig;
import com.appdynamics.cloud.modern.repos.utils.SQLDataHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author James Schneider
 *
 */

@RestController
public class RepoController {

	@Autowired	
	CashAccountsRepository cashRepo;

	@Autowired	
	CashAccountDetailsRepository cashDetailsRepo;
	
	@Autowired
	LoanAccountsRepository loanRepo;
	
	@Autowired	
	LoanAccountDetailsRepository loanDetailsRepo;
	
	@Autowired
	LoanApplicationsRepository loanApplicationsRepo;
	
	public final Logger logger = LoggerFactory.getLogger(RepoController.class);
	
	private static SQLDataHelper SQL_HELPER = null;
	
	private static boolean RUNNING_IN_CLOUD = false;
	/**
	 * 
	 */
	public RepoController() {
		
		if (SQL_HELPER == null) {
			SQL_HELPER = new SQLDataHelper();
		}
		
		String ADWRKSHP_CLOUD_MODE = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_MODE).toLowerCase();
		
		if (!ADWRKSHP_CLOUD_MODE.equals(ApplicationConstants.ENV_CLOUD_MODE_NONE)) {
			RUNNING_IN_CLOUD = true;
		}
		
	}
	
	// Summary of multiple CASH accounts 
	@RequestMapping(value = "/balances/api/accounts-summary", method = RequestMethod.GET, produces = { "application/json" })
	public String getCashAccountsSummary(HttpServletRequest request) {
		
		String memberId  = request.getParameter("memberId");
		List<CashAccount> accts = cashRepo.findByMemberId(memberId);
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(accts,  new TypeToken<ArrayList<CashAccount>>() {}.getType());
		payloads.put("cashAccounts", payload);
		
		
//		try {
//			if (Math.random() < 1.0/100.0) {
//				SQL_HELPER.runCashQuery();
//			}					
//		} catch (Throwable e) {
//			
//		}
		
		trans.setJsonPayloads(payloads);
		
		try {
			if (RUNNING_IN_CLOUD) {
				CloudServicesHelper.addCloudStorageRecordCashAccts();
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		trans.setServiceOperation("cash-accounts-summary");
		trans.setResponseCode("200");
		
		
		return gson.toJson(trans);
	}
	
	
	// Summary of only one CASH account
	@RequestMapping(value = "/balances/api/account-summary", method = RequestMethod.GET, produces = { "application/json" })
	public String getCashAccountSummary(HttpServletRequest request) {
		
		String accountNumber  = request.getParameter("accountNumber");
		CashAccount acct = cashRepo.findByAccountNumber(accountNumber);
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountNumber", accountNumber);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(acct);
		payloads.put("cashAccount", payload);
		
		trans.setJsonPayloads(payloads);
		
		trans.setServiceOperation("cash-account-summary");
		trans.setResponseCode("200");
		
		
		return gson.toJson(trans);
	}
	
	// Transaction details of one CASH account
	@RequestMapping(value = "/balances/api/account-details", method = RequestMethod.GET, produces = { "application/json" })
	public String getCashAccountDetails(HttpServletRequest request) {
		
		String accountNumber  = request.getParameter("accountNumber");
		List<CashAccountDetail> details = cashDetailsRepo.findByAccountNumberOrderByTxnOrderAsc(accountNumber);
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountNumber", accountNumber);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(details,  new TypeToken<ArrayList<CashAccountDetail>>() {}.getType());
		payloads.put("cashAccountDetails", payload);
		
		trans.setJsonPayloads(payloads);
		
		trans.setServiceOperation("cash-account-details");
		trans.setResponseCode("200");
		
		
		return gson.toJson(trans);
	}

	
	
	// Summary of multiple LOAN accounts
	@RequestMapping(value = "/loans/api/accounts-summary", method = RequestMethod.GET, produces = { "application/json" })
	public String getLoanAccountsSummary(HttpServletRequest request) {
		String memberId  = request.getParameter("memberId");
		List<LoanAccount> accts = loanRepo.findByMemberId(memberId);
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(accts,  new TypeToken<ArrayList<LoanAccount>>() {}.getType());
		payloads.put("loanAccounts", payload);
		
		trans.setJsonPayloads(payloads);
		
		trans.setServiceOperation("loan-accounts-summary");
		trans.setResponseCode("200");
		
		
		return gson.toJson(trans);
	}

	
	// Summary of only one LOAN account
	@RequestMapping(value = "/loans/api/account-summary", method = RequestMethod.GET, produces = { "application/json" })
	public String getLoanAccountSummary(HttpServletRequest request) {
		
		String accountNumber  = request.getParameter("accountNumber");
		LoanAccount acct = loanRepo.findByAccountNumber(accountNumber);
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountNumber", accountNumber);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(acct);
		payloads.put("loanAccount", payload);
		
		trans.setJsonPayloads(payloads);
		
		trans.setServiceOperation("loan-account-summary");
		trans.setResponseCode("200");	
		
		return gson.toJson(trans);
	}
	
	// Transaction details of one LOAN account
	@RequestMapping(value = "/loans/api/account-details", method = RequestMethod.GET, produces = { "application/json" })
	public String getLoanAccountDetails(HttpServletRequest request) {
		
		String accountNumber  = request.getParameter("accountNumber");
		List<LoanAccountDetail> details = loanDetailsRepo.findByAccountNumberOrderByTxnOrderAsc(accountNumber);
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountNumber", accountNumber);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(details,  new TypeToken<ArrayList<LoanAccountDetail>>() {}.getType());
		payloads.put("loanAccountDetails", payload);
		
		trans.setJsonPayloads(payloads);
		
		trans.setServiceOperation("loan-account-details");
		trans.setResponseCode("200");
		
		
		return gson.toJson(trans);
	}

	
	// loanPayment
	@RequestMapping(value = "/loans/api/loanPayment", method = RequestMethod.GET, produces = { "application/json" })
	public String processLoanPayment(HttpServletRequest request) {
		
		String loanCategory  = request.getParameter("loanCategory");
		String loanServicesSplit  = request.getParameter("loanServicesSplit");

		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanCategory", loanCategory);
		params.put("loanServicesSplit", loanServicesSplit);
		trans.setRequestParameters(params);

		LoanApplication loanApp = DataRepoUtils.getRandomLoanApplication("1", loanApplicationsRepo);
		
		if (loanApp != null) {
			
			Map<String, String> payloads = new HashMap<String, String>();
			String payload = gson.toJson(loanApp);
			payloads.put("loanApplication", payload);
			
			trans.setJsonPayloads(payloads);
		}
				
		trans.setServiceOperation("loan-payment");
		trans.setResponseCode("200");
		
//		try {
//			SQL_HELPER.runLoanQuery();	
//		} catch (Throwable e) {
//		}
		
		return gson.toJson(trans);
		
	}
	
	
	// LOAN APPS - loanApplication
	@RequestMapping(value = "/loans/api/loanApplication", method = RequestMethod.GET, produces = { "application/json" })
	public String processLoanSaveApplication(HttpServletRequest request) {
		
		String loanCategory  = request.getParameter("loanCategory");
		String loanServicesSplit  = request.getParameter("loanServicesSplit");
		
		LoanApplication loanApp = DataRepoUtils.createLoanApplication(loanCategory, loanServicesSplit);
		loanApp = DataRepoUtils.processLoanSaveApplication(loanApp, loanApplicationsRepo);
		
		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanCategory", loanCategory);
		params.put("loanServicesSplit", loanServicesSplit);
		trans.setRequestParameters(params);
		
		Map<String, String> payloads = new HashMap<String, String>();
		String payload = gson.toJson(loanApp);
		payloads.put("loanApplication", payload);
		
		trans.setJsonPayloads(payloads);
		
		
		try {
			
			if (loanCategory.equals("business")) {
				if (RUNNING_IN_CLOUD) {
					CloudServicesHelper.addCloudStorageRecordBizLoans();
				}
				
			} else {
				if (RUNNING_IN_CLOUD) {
					CloudServicesHelper.addCloudStorageRecordPerLoans();
				}
				
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		trans.setServiceOperation("loan-application");
		trans.setResponseCode("200");
		
		xformJsonPayload("loan_save");
		
		return gson.toJson(trans);
		
	}
	
	
	// LOAN APPS - loanVerifyDocumentation
	@RequestMapping(value = "/loans/api/loanVerifyDocumentation", method = RequestMethod.GET, produces = { "application/json" })
	public String processLoanVerifyDocumentation(HttpServletRequest request) {
		
		String loanCategory  = request.getParameter("loanCategory");
		String loanServicesSplit  = request.getParameter("loanServicesSplit");

		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanCategory", loanCategory);
		params.put("loanServicesSplit", loanServicesSplit);
		trans.setRequestParameters(params);

		LoanApplication loanApp = DataRepoUtils.getRandomLoanApplication("1", loanApplicationsRepo);
		
		if (loanApp != null) {
						
			loanApp.setMsStepCount("2");
			loanApp.setMsDocsDone("true");
			
			loanApp = DataRepoUtils.processLoanVerifyDocumentation(loanApp, loanApplicationsRepo);
			
			Map<String, String> payloads = new HashMap<String, String>();
			String payload = gson.toJson(loanApp);
			payloads.put("loanApplication", payload);
			
			trans.setJsonPayloads(payloads);
		}
				
		trans.setServiceOperation("loan-verify-docs");
		trans.setResponseCode("200");

		try {
			SQL_HELPER.runLoanQuery();	
		} catch (Throwable e) {
		}		

		xformJsonPayload("loan_verifydoc");
		
		return gson.toJson(trans);
		
	}
	
	// LOAN APPS - loanCreditCheck
	@RequestMapping(value = "/loans/api/loanCreditCheck", method = RequestMethod.GET, produces = { "application/json" })
	public String processLoanCreditCheck(HttpServletRequest request) {
		
		String loanCategory  = request.getParameter("loanCategory");
		String loanServicesSplit  = request.getParameter("loanServicesSplit");

		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanCategory", loanCategory);
		params.put("loanServicesSplit", loanServicesSplit);
		trans.setRequestParameters(params);

		LoanApplication loanApp = DataRepoUtils.getRandomLoanApplication("2", loanApplicationsRepo);
		
		if (loanApp != null) {

			loanApp.setMsStepCount("3");
			loanApp.setMsCreditDone("true");
			
			loanApp = DataRepoUtils.processLoanCreditCheck(loanApp, loanApplicationsRepo);
			
			Map<String, String> payloads = new HashMap<String, String>();
			String payload = gson.toJson(loanApp);
			payloads.put("loanApplication", payload);
			
			trans.setJsonPayloads(payloads);
		}
				
		trans.setServiceOperation("loan-credit-check");
		trans.setResponseCode("500");
		
		// throw an error every so often
        if (Math.random() < 1.0/100.0) {
        	String nothin = "{\"id\" : 1112,\"version\" : 1name\" : \"ACC-21201830\",nameUnique\" : true,[ ],}";
        	gson.fromJson(nothin, HttpJsonTransport.class);
        	
        }					
		trans.setResponseCode("200");
		
		pullCreditCheck();
		
		
		xformJsonPayload("loan_credit");
		
		return gson.toJson(trans);
		
	}
	
	// LOAN APPS - loanUnderwritingComplete
	@RequestMapping(value = "/loans/api/loanUnderwritingComplete", method = RequestMethod.GET, produces = { "application/json" })
	public String processLoanUnderwriting(HttpServletRequest request) {
		
		String loanCategory  = request.getParameter("loanCategory");
		String loanServicesSplit  = request.getParameter("loanServicesSplit");

		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanCategory", loanCategory);
		params.put("loanServicesSplit", loanServicesSplit);
		trans.setRequestParameters(params);

		LoanApplication loanApp = DataRepoUtils.getRandomLoanApplication("3", loanApplicationsRepo);
		
		if (loanApp != null) {

			loanApp.setMsStepCount("4");
			loanApp.setMsUnderDone("true");
			
			loanApp = DataRepoUtils.processLoanUnderwriting(loanApp, loanApplicationsRepo);
			
			Map<String, String> payloads = new HashMap<String, String>();
			String payload = gson.toJson(loanApp);
			payloads.put("loanApplication", payload);
			
			trans.setJsonPayloads(payloads);
		}

		trans.setServiceOperation("loan-underwriting");
		trans.setResponseCode("500");
		
		// throw an error every so often
        if (Math.random() < 1.0/100.0) {
        	String nothin = "{\"id\" : 3451,\"version\" : 1name\" : \"ACC-88874901\",nameUnique\" : true,[ ],}";
        	gson.fromJson(nothin, HttpJsonTransport.class);
        	
        }			
		
		trans.setResponseCode("200");

		xformJsonPayload("loan_underwrite");
		
		return gson.toJson(trans);
		
	}

	// LOAN APPS - loanApproved
	@RequestMapping(value = "/loans/api/loanApproved", method = RequestMethod.GET, produces = { "application/json" })
	public String processLoanApproval(HttpServletRequest request) {
		
		String loanCategory  = request.getParameter("loanCategory");
		String loanServicesSplit  = request.getParameter("loanServicesSplit");

		Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanCategory", loanCategory);
		params.put("loanServicesSplit", loanServicesSplit);
		trans.setRequestParameters(params);

		LoanApplication loanApp = DataRepoUtils.getRandomLoanApplication("4", loanApplicationsRepo);
		
		if (loanApp != null) {

			loanApp.setMsStepCount("5");
			loanApp.setMsApprovalDone("true");
			
			loanApp = DataRepoUtils.processLoanApproval(loanApp, loanApplicationsRepo);
			
			Map<String, String> payloads = new HashMap<String, String>();
			String payload = gson.toJson(loanApp);
			payloads.put("loanApplication", payload);
			
			trans.setJsonPayloads(payloads);
		}
				
		trans.setServiceOperation("loan-approval");
		trans.setResponseCode("200");
		
//		try {	
//			SQL_HELPER.runLoanQuery();				
//		} catch (Throwable e) {}	
		
		xformJsonPayload("loan_approval");
		
		return gson.toJson(trans);
		
	}
	

	// dummy method used to introduce extra time in loan processing transactions for the pre-modern version of the application
	private String xformJsonPayload(String perfModifierName) {
		
		try {
			
			if (!RUNNING_IN_CLOUD) {
				ThreadLocalRandom rand = ThreadLocalRandom.current();
				ReposConfig reposConfig = ConfigHelper.getReposConfig();
				
				int lowerRandomInterval = 1;
				int upperRandomInterval = 7;
				long lowerSleepTimeMillis = 980;
				long upperSleepTimeMillis = 1300;
				
				List<PerformanceModifierConfig> perfModConfigs = reposConfig.getPerformanceModifiers();
				
				for (PerformanceModifierConfig pmConf : perfModConfigs) {
					
					if (pmConf.getName().equals(perfModifierName)) {
					
						lowerRandomInterval = pmConf.getLowerRandomInterval();
						upperRandomInterval = pmConf.getUpperRandomInterval();
						lowerSleepTimeMillis = pmConf.getLowerSleepTimeMillis();
						upperSleepTimeMillis = pmConf.getUpperSleepTimeMillis();

						//int randInterval = rand.nextInt(lowerRandomInterval, upperRandomInterval);
						
						//if (randInterval == upperRandomInterval) {
							Thread.currentThread().sleep(rand.nextLong(lowerSleepTimeMillis, upperSleepTimeMillis));
						//}
						
					}
					
				}
				
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		return "";
	}
	
	
	private void pullCreditCheck() {
		try {
			
	
//			ThreadLocalRandom rand = ThreadLocalRandom.current();
//			ReposConfig reposConfig = ConfigHelper.getReposConfig();
//			
//			long lowerRandomInterval = reposConfig.getCreditCheckLowerRandomInterval();
//			long upperRandomInterval = reposConfig.getCreditCheckUpperRandomInterval();
//			
//			long randInterval = rand.nextLong(lowerRandomInterval, upperRandomInterval);
//			
//			if (randInterval == upperRandomInterval) {
				
				
				CloseableHttpResponse res = null;
				String url = "https://www.equifax.com"; //reposConfig.getCreditCheckUrl();
				CloseableHttpClient client = HttpClientBuilder.create().build();
				URIBuilder builder = new URIBuilder(url);
				HttpGet req = new HttpGet(builder.build());
				res = client.execute(req);	
				logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
				
				
//			}

			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
	}

}


