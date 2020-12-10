/**
 * 
 */
package com.appdynamics.cloud.modern.web.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appdynamics.cloud.modern.web.beans.CashAccount;
import com.appdynamics.cloud.modern.web.beans.CashAccountDetail;
import com.appdynamics.cloud.modern.web.beans.HttpJsonTransport;
import com.appdynamics.cloud.modern.web.beans.LoanAccount;
import com.appdynamics.cloud.modern.web.beans.LoanAccountDetail;
import com.appdynamics.cloud.modern.web.utils.ApplicationConstants;
import com.appdynamics.cloud.modern.web.utils.ConfigHelper;
import com.appdynamics.cloud.modern.web.utils.DateUtils;
import com.appdynamics.cloud.modern.web.utils.JsonHelper;
import com.appdynamics.cloud.modern.web.utils.LoginHelper;
import com.appdynamics.cloud.modern.web.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author James Schneider
 *
 */

@Controller
public class WebController {

	protected static boolean IS_BIZ_LOAN = false;
	
	private static String APP_CLOUD_MODE;
	private static String LOAN_SRVCS_SPLIT;
	
	public final Logger logger = LoggerFactory.getLogger(WebController.class);
	/**
	 * 
	 */
	public WebController() {
		
		APP_CLOUD_MODE = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_MODE);
		if (APP_CLOUD_MODE.equalsIgnoreCase("none")) {
			LOAN_SRVCS_SPLIT = "false";
		} else {
			LOAN_SRVCS_SPLIT = "true";
		}
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String showHomePage(HttpServletRequest request, ModelMap model) {
		
		//request.getSession().setAttribute("memberId", "98362534");
		return "home";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showAccountsSummaryPage(HttpServletRequest request, ModelMap model, @RequestParam String username, 
			@RequestParam String password, @RequestParam(defaultValue = "false") Boolean remember) {
		
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAuthenticationServicesUrl() + ConfigHelper.getLoginUrlPart();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("username", "" + username);
			builder.setParameter("password", "" + password);
			builder.setParameter("remember", "" + remember);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);
			
			
			
			String[] login_data = StringUtils.split(LoginHelper.getLogin(username), ":");
			String firstName = login_data[0];
			String lastName = login_data[1];
			String memberId = login_data[2];
			
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("memberId", memberId);
			
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				
				String json = JsonHelper.getJsonFromHttpResponse(res);
				
				url = ConfigHelper.getAccountManagementUrl() + ConfigHelper.getAccountsSummmaryUrlPart();
				client = HttpClientBuilder.create().build();
				builder = new URIBuilder(url);
				builder.setParameter("memberId", "" + request.getSession().getAttribute("memberId"));
				
				req = new HttpGet(builder.build());
				res = client.execute(req);
				
				if (res.getStatusLine().getStatusCode() == 200) {

					json = JsonHelper.getJsonFromHttpResponse(res);
					
					Gson gson = new Gson();
					
					HttpJsonTransport trans = gson.fromJson(json, HttpJsonTransport.class);
					
					String jsonCashList = trans.getJsonPayloads().get("cashAccounts");
					String jsonLoanList = trans.getJsonPayloads().get("loanAccounts");
					
					List<CashAccount> cashAccts = gson.fromJson(jsonCashList, new TypeToken<ArrayList<CashAccount>>() {}.getType());
					List<LoanAccount> loanAccts = gson.fromJson(jsonLoanList, new TypeToken<ArrayList<LoanAccount>>() {}.getType());
					for (LoanAccount acct : loanAccts) {
						
						acct.setPaymentDueDate(DateUtils.getNextPaymentDate(acct.getAccountType()));
					}
					
					
					// here we should be pushing the account summary beans into the model map
					model.put("firstName", firstName);
					model.put("lastName", lastName);
					model.put("username", username);
					model.put("memberId", memberId);
					model.put("cashAccounts", cashAccts);
					model.put("loanAccounts", loanAccts);
					model.put("lastLogin", DateUtils.getLastLogin());
					//model.put("json", json);
				}
				
				return "accounts-summary";
				
			} else {
				logger.error("Had some error");
				return "error";
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}
		
	}
	
	
	
	@RequestMapping(value="/cashacctdetails", method = RequestMethod.GET) 
	public String showCashAccountDetailPage(HttpServletRequest request, ModelMap model, @RequestParam String accountNumber) {
		
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl() + ConfigHelper.getCashAccountDetailsUrlPart();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("accountNumber", "" + accountNumber);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);
						
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				
				String json = JsonHelper.getJsonFromHttpResponse(res);
				
				Gson gson = new Gson();
				
				HttpJsonTransport trans = gson.fromJson(json, HttpJsonTransport.class);
				
				String jsonCashAcct = trans.getJsonPayloads().get("cashAccount");
				String jsonCashAcctDetailsList = trans.getJsonPayloads().get("cashAccountDetails");
				
				CashAccount cashAcct = gson.fromJson(jsonCashAcct, CashAccount.class);
				List<CashAccountDetail> cashDetails = gson.fromJson(jsonCashAcctDetailsList, new TypeToken<ArrayList<CashAccountDetail>>() {}.getType());
				
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("MMM d");
				
				for (CashAccountDetail detail : cashDetails) {
					
					//logger.info("Month Day: " + detail.getTxnDate() + "  Descr: " + detail.getTxnDescription() + "  Amt: " + detail.getTxnAmount() + "  Balance: " + detail.getRunBalance());
					
					detail.setTxnDescription(detail.getTxnDescription() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						
					if (detail.getMonthMulti() == -1) {
						cal.roll(Calendar.MONTH, -1);
					}
					
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(detail.getTxnDate()));
					detail.setTxnDate(df.format(cal.getTime()).toUpperCase());
				}
					
				// here we should be pushing the account summary beans into the model map
				model.put("lastLogin", DateUtils.getLastLogin());
				model.put("firstName", request.getSession().getAttribute("firstName"));
				model.put("lastName", request.getSession().getAttribute("lastName"));
				model.put("username", request.getSession().getAttribute("username"));
				model.put("memberId", request.getSession().getAttribute("memberId"));
				
				model.put("cashAccount", cashAcct);
				model.put("cashAccountDetails", cashDetails);
				
				//model.put("json", json);
			
				
				return "cash-account-detail";
				
			} else {
				logger.error("Had some error");
				return "error";
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
		
	
	}

	
	@RequestMapping(value="/loanacctdetails", method = RequestMethod.GET) 
	public String showLoanAccountDetailPage(HttpServletRequest request, ModelMap model, @RequestParam String accountNumber) {
		
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl() + ConfigHelper.getLoanAccountDetailsUrlPart();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("accountNumber", "" + accountNumber);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);
						
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				
				String json = JsonHelper.getJsonFromHttpResponse(res);
				
				Gson gson = new Gson();
				
				HttpJsonTransport trans = gson.fromJson(json, HttpJsonTransport.class);
				
				String jsonLoanAcct = trans.getJsonPayloads().get("loanAccount");
				String jsonLoanAcctDetailsList = trans.getJsonPayloads().get("loanAccountDetails");
				
				LoanAccount loanAcct = gson.fromJson(jsonLoanAcct, LoanAccount.class);
				List<LoanAccountDetail> loanDetails = gson.fromJson(jsonLoanAcctDetailsList, new TypeToken<ArrayList<LoanAccountDetail>>() {}.getType());
				
				loanAcct.setPaymentDueDate(DateUtils.getNextPaymentDate(loanAcct.getAccountType()));
				
//				String paymentType = "mortgage";
//				String acctName = loanAcct.getAccountName().toLowerCase();
//				
//				if (acctName.startsWith("auto")) {
//					paymentType = "auto";
//				}
				
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("MMM d");
				
				for (LoanAccountDetail detail : loanDetails) {
					
					//logger.info("Month Day: " + detail.getTxnDate() + "  Descr: " + detail.getTxnDescription() + "  Amt: " + detail.getTxnAmount() + "  Balance: " + detail.getRunBalance());
					
					detail.setTxnDescription(detail.getTxnDescription() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					
					
					if (detail.getMonthMulti() == -1) {
						cal.roll(Calendar.MONTH, -1);
					}
					
					cal.set(Calendar.DAY_OF_MONTH, DateUtils.getPaymentDayOfMonth(loanAcct.getAccountType()));
					detail.setTxnDate(df.format(cal.getTime()).toUpperCase());
				}
					
				// here we should be pushing the account summary beans into the model map
				model.put("lastLogin", DateUtils.getLastLogin());
				model.put("firstName", request.getSession().getAttribute("firstName"));
				model.put("lastName", request.getSession().getAttribute("lastName"));
				model.put("username", request.getSession().getAttribute("username"));
				model.put("memberId", request.getSession().getAttribute("memberId"));
				
				model.put("loanAccount", loanAcct);
				model.put("loanAccountDetails", loanDetails);
				
				//model.put("json", json);
			
				
				return "loan-account-detail";
				
			} else {
				logger.error("Had some error");
				return "error";
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
		
	
	}
	
	
	
	
	// REST CALLS FROM LOAD GEN
	@RequestMapping(value="/rest/logon", method = RequestMethod.GET) 
	public String processRestLogon(HttpServletRequest request, ModelMap model) {
		try {
			return "home";
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "home";
		}					
	}

	@RequestMapping(value="/rest/accountsHome", method = RequestMethod.GET) 
	public String processRestAccountsHome(HttpServletRequest request, ModelMap model) {
		try {
			
			return this.showAccountsSummaryPage(request, model, "batman", "3875682", false);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "home";
		}					
	}

	@RequestMapping(value="/rest/researchStock", method = RequestMethod.GET) 
	public String processRestResearchStock(HttpServletRequest request, ModelMap model) {
		try {
			return "home";
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "home";
		}					
	}

	@RequestMapping(value="/rest/loanPayment", method = RequestMethod.GET) 
	public String processRestLoanPayment(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl();
			String loanCategory = "";
			
			if (LOAN_SRVCS_SPLIT.equalsIgnoreCase("true")) {
				if (IS_BIZ_LOAN) {
					url = url + ConfigHelper.getBizLoanPaymentUrlPart();
					loanCategory = "business";
				} else {
					url = url + ConfigHelper.getPerLoanPaymentUrlPart();
					loanCategory = "personal";
				}
			} else {
				url = url + ConfigHelper.getLoanPaymentUrlPart();
				if (IS_BIZ_LOAN) {
					loanCategory = "business";
				} else {
					loanCategory = "personal";
				}				
			}
			IS_BIZ_LOAN = !IS_BIZ_LOAN;
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("loanCategory", loanCategory);
			builder.setParameter("loanServicesSplit", LOAN_SRVCS_SPLIT);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);	
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				String json = JsonHelper.getJsonFromHttpResponse(res);
				logger.info("JSON = " + json);
				return "home";
			} else {
				logger.error("Had some error");
				return "error";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}

	@RequestMapping(value="/rest/policyPayment", method = RequestMethod.GET) 
	public String processRestPolicyPayment(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl() + ConfigHelper.getPolicyPaymentUrlPart();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("policyId", "347298234");
			builder.setParameter("paymentAmt", "327.80");
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);
						
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				
				String json = JsonHelper.getJsonFromHttpResponse(res);
				
				logger.info("JSON = " + json);
				
				return "home";
				
			} else {
				logger.error("Had some error");
				return "error";
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}

	@RequestMapping(value="/rest/getQuote", method = RequestMethod.GET) 
	public String processRestGetQuote(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getQuoteServicesUrl() + ConfigHelper.getQuoteUrlPart();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("stockTicket", "CSCO");
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);
						
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				
				String json = JsonHelper.getJsonFromHttpResponse(res);
				
				logger.info("JSON = " + json);
				
				return "home";
				
			} else {
				logger.error("Had some error");
				return "error";
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}

	@RequestMapping(value="/rest/processTrade", method = RequestMethod.GET) 
	public String processRestProcessTrade(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getOrderProcessingUrl() + ConfigHelper.getOrderUrlPart();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("stockTicket", "CSCO");
			builder.setParameter("sharesToBuy", "100");
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);
						
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				
				String json = JsonHelper.getJsonFromHttpResponse(res);
				
				logger.info("JSON = " + json);
				
				return "home";
				
			} else {
				logger.error("Had some error");
				return "error";
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}

	

	// LOAN BIZ JOURNEY METHODS
	@RequestMapping(value="/rest/loanApplication", method = RequestMethod.POST) 
	public String processRestLoanApplication(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl();
			String loanCategory = "";
			
			if (LOAN_SRVCS_SPLIT.equalsIgnoreCase("true")) {
				if (IS_BIZ_LOAN) {
					url = url + ConfigHelper.getBizLoanApplicationUrlPart();
					loanCategory = "business";
				} else {
					url = url + ConfigHelper.getPerLoanApplicationUrlPart();
					loanCategory = "personal";
				}
			} else {
				url = url + ConfigHelper.getLoanApplicationUrlPart();
				if (IS_BIZ_LOAN) {
					loanCategory = "business";
				} else {
					loanCategory = "personal";
				}				
			}
			IS_BIZ_LOAN = !IS_BIZ_LOAN;
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("loanCategory", loanCategory);
			builder.setParameter("loanServicesSplit", LOAN_SRVCS_SPLIT);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);	
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				String json = JsonHelper.getJsonFromHttpResponse(res);
				logger.info("JSON = " + json);
				return "home";
			} else {
				logger.error("Had some error");
				return "error";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}

	@RequestMapping(value="/rest/loanVerifyDocumentation", method = RequestMethod.GET) 
	public String processRestLoanVerifyDocs(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl();
			String loanCategory = "";
			
			if (LOAN_SRVCS_SPLIT.equalsIgnoreCase("true")) {
				if (IS_BIZ_LOAN) {
					url = url + ConfigHelper.getBizLoanVerifyDocUrlPart();
					loanCategory = "business";
				} else {
					url = url + ConfigHelper.getPerLoanVerifyDocUrlPart();
					loanCategory = "personal";
				}
			} else {
				url = url + ConfigHelper.getLoanVerifyDocUrlPart();
				if (IS_BIZ_LOAN) {
					loanCategory = "business";
				} else {
					loanCategory = "personal";
				}				
			}
			IS_BIZ_LOAN = !IS_BIZ_LOAN;
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("loanCategory", loanCategory);
			builder.setParameter("loanServicesSplit", LOAN_SRVCS_SPLIT);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);	
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				String json = JsonHelper.getJsonFromHttpResponse(res);
				logger.info("JSON = " + json);
				return "home";
			} else {
				logger.error("Had some error");
				return "error";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}
	
	
	@RequestMapping(value="/rest/loanCreditCheck", method = RequestMethod.GET) 
	public String processRestLoanCreditCheck(HttpServletRequest request, ModelMap model) {
		
		// throw an error every so often
        if (Math.random() < 1.0/100.0) {
        	String nothin = "{\"id\" : 3451,\"version\" : 1name\" : \"ACC-88874901\",nameUnique\" : true,[ ],}";
        	Gson gson = new Gson();
        	gson.fromJson(nothin, HttpJsonTransport.class);
        	
        }		
        
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl();
			String loanCategory = "";
			
			if (LOAN_SRVCS_SPLIT.equalsIgnoreCase("true")) {
				if (IS_BIZ_LOAN) {
					url = url + ConfigHelper.getBizLoanCreditUrlPart();
					loanCategory = "business";
				} else {
					url = url + ConfigHelper.getPerLoanCreditUrlPart();
					loanCategory = "personal";
				}
			} else {
				url = url + ConfigHelper.getLoanCreditUrlPart();
				if (IS_BIZ_LOAN) {
					loanCategory = "business";
				} else {
					loanCategory = "personal";
				}				
			}
			IS_BIZ_LOAN = !IS_BIZ_LOAN;
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("loanCategory", loanCategory);
			builder.setParameter("loanServicesSplit", LOAN_SRVCS_SPLIT);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);	
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				String json = JsonHelper.getJsonFromHttpResponse(res);
				logger.info("JSON = " + json);
				return "home";
			} else {
				logger.error("Had some error");
				return "error";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}
	
	
	@RequestMapping(value="/rest/loanUnderwritingComplete", method = RequestMethod.GET) 
	public String processRestLoanUnderwriting(HttpServletRequest request, ModelMap model) {
		
		// throw an error every so often
        if (Math.random() < 1.0/100.0) {
        	String nothin = "{\"id\" : 3451,\"version\" : 1name\" : \"ACC-88874901\",nameUnique\" : true,[ ],}";
        	Gson gson = new Gson();
        	gson.fromJson(nothin, HttpJsonTransport.class);
        	
        }	
        
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl();
			String loanCategory = "";
			
			if (LOAN_SRVCS_SPLIT.equalsIgnoreCase("true")) {
				if (IS_BIZ_LOAN) {
					url = url + ConfigHelper.getBizLoanUnderwritingUrlPart();
					loanCategory = "business";
				} else {
					url = url + ConfigHelper.getPerLoanUnderwritingUrlPart();
					loanCategory = "personal";
				}
			} else {
				url = url + ConfigHelper.getLoanUnderwritingUrlPart();
				if (IS_BIZ_LOAN) {
					loanCategory = "business";
				} else {
					loanCategory = "personal";
				}				
			}
			IS_BIZ_LOAN = !IS_BIZ_LOAN;
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("loanCategory", loanCategory);
			builder.setParameter("loanServicesSplit", LOAN_SRVCS_SPLIT);
			
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);	
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				String json = JsonHelper.getJsonFromHttpResponse(res);
				logger.info("JSON = " + json);
				return "home";
			} else {
				logger.error("Had some error");
				return "error";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}	
	
	}
	

	@RequestMapping(value="/rest/loanApproved", method = RequestMethod.GET) 
	public String processRestLoanApproved(HttpServletRequest request, ModelMap model) {
		try {
			
			CloseableHttpResponse res = null;
			String url = ConfigHelper.getAccountManagementUrl();
			String loanCategory = "";
			
			if (LOAN_SRVCS_SPLIT.equalsIgnoreCase("true")) {
				if (IS_BIZ_LOAN) {
					url = url + ConfigHelper.getBizLoanApprovedUrlPart();
					loanCategory = "business";
				} else {
					url = url + ConfigHelper.getPerLoanApprovedUrlPart();
					loanCategory = "personal";
				}
			} else {
				url = url + ConfigHelper.getLoanApprovedUrlPart();
				if (IS_BIZ_LOAN) {
					loanCategory = "business";
				} else {
					loanCategory = "personal";
				}				
			}
			IS_BIZ_LOAN = !IS_BIZ_LOAN;
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			URIBuilder builder = new URIBuilder(url);
			
			builder.setParameter("loanCategory", loanCategory);
			builder.setParameter("loanServicesSplit", LOAN_SRVCS_SPLIT);
						
			HttpGet req = new HttpGet(builder.build());
			res = client.execute(req);	
			logger.info("HTTP response code = " + res.getStatusLine().getStatusCode());
			
			if (res.getStatusLine().getStatusCode() == 200) {
				String json = JsonHelper.getJsonFromHttpResponse(res);
				logger.info("JSON = " + json);
				return "home";
			} else {
				logger.error("Had some error");
				return "error";
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "error";
		}		
	}

	
	
}


//import com.github.javafaker.Faker;
//Faker faker = new Faker();		
//faker.finance().
