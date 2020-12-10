/**
 * 
 */
package com.appdynamics.cloud.modern.web.utils;

/**
 * @author James Schneider
 *
 */
public class ConfigHelper {

	/**
	 * 
	 */
	public ConfigHelper() {
		
	}
	
	
	
	// AuthenticationServices URL methods
	public static String getAuthenticationServicesUrl() {
		return "http://authentication-services:8080/auth/api/";
	}	
	public static String getLoginUrlPart() {
		return "login/";
	}
	
	
	// AccountManagement URL methods
	public static String getAccountManagementUrl() {
		return "http://account-management:8080/accounts/api/";
	}	
	public static String getAccountsSummmaryUrlPart() {
		return "accounts-summary/";
	}	
	public static String getCashAccountDetailsUrlPart() {
		return "cashacctdetails/";
	}
	public static String getLoanAccountDetailsUrlPart() {
		return "loanacctdetails/";
	}
	public static String getPolicyPaymentUrlPart() {
		return "policypayment/";
	}

	// Loan URL Parts
	public static String getLoanPaymentUrlPart() {
		return "loanPayment/";
	}
	public static String getLoanApplicationUrlPart() {
		return "loanApplication/";
	}
	public static String getLoanVerifyDocUrlPart() {
		return "loanVerifyDocumentation/";
	}
	public static String getLoanCreditUrlPart() {
		return "loanCreditCheck/";
	}
	public static String getLoanUnderwritingUrlPart() {
		return "loanUnderwritingComplete/";
	}
	public static String getLoanApprovedUrlPart() {
		return "loanApproved/";
	}

	// Personal Loan URL Parts
	public static String getPerLoanPaymentUrlPart() {
		return "perLoanPayment/";
	}
	public static String getPerLoanApplicationUrlPart() {
		return "perLoanApplication/";
	}
	public static String getPerLoanVerifyDocUrlPart() {
		return "perLoanVerifyDocumentation/";
	}
	public static String getPerLoanCreditUrlPart() {
		return "perLoanCreditCheck/";
	}
	public static String getPerLoanUnderwritingUrlPart() {
		return "perLoanUnderwritingComplete/";
	}
	public static String getPerLoanApprovedUrlPart() {
		return "perLoanApproved/";
	}	
	
	
	// Business Loan URL Parts
	public static String getBizLoanPaymentUrlPart() {
		return "bizLoanPayment/";
	}
	public static String getBizLoanApplicationUrlPart() {
		return "bizLoanApplication/";
	}
	public static String getBizLoanVerifyDocUrlPart() {
		return "bizLoanVerifyDocumentation/";
	}
	public static String getBizLoanCreditUrlPart() {
		return "bizLoanCreditCheck/";
	}
	public static String getBizLoanUnderwritingUrlPart() {
		return "bizLoanUnderwritingComplete/";
	}
	public static String getBizLoanApprovedUrlPart() {
		return "bizLoanApproved/";
	}	

	
	
	
	// QuoteServices URL methods
	public static String getQuoteServicesUrl() {
		return "http://quote-services:8080/quotes/api/";
	}	
	public static String getQuoteUrlPart() {
		return "getquote/";
	}

	
	// OrderProcessing URL methods
	public static String getOrderProcessingUrl() {
		return "http://order-processing:8080/orders/api/";
	}	
	public static String getOrderUrlPart() {
		return "processtrade/";
	}


	
}
