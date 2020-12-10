package com.appdynamics.cloud.modern.repos.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.appdynamics.cloud.modern.repos.model.cashaccounts.CashAccount;
import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


//TODO move this class somewhere else

public class Test {

	public Test() {
		
	}

//	public static void main(String[] args) {
//		
//		
//		try {
//			Gson gson = new Gson();
//			String cashStr;
//			String loanStr;
//			
//			
//			
//			List<CashAccount> cashAccounts = new ArrayList<CashAccount>();
//			
//			CashAccount cacct;
//			
//			cacct = new CashAccount();
//			cacct.setAccountName("Personal Checking");
//			cacct.setAccountNumber("577662211-1");
//			//cacct.setAvailableBalance(Double.valueOf("3201.33"));
//			cacct.setCurrentBalance(Double.valueOf("3345.02"));
//			
//			cashAccounts.add(cacct);
//			
//			cacct = new CashAccount();
//			cacct.setAccountName("Personal Savings");
//			cacct.setAccountNumber("577662211-0");
//			//cacct.setAvailableBalance(Double.valueOf("5040.93"));
//			cacct.setCurrentBalance(Double.valueOf("5045.93"));
//			
//			cashAccounts.add(cacct);
//			cashStr = gson.toJson(cashAccounts);
//			
//			System.out.println("Cash String");
//			System.out.println(cashStr);
//			System.out.println("");
//			System.out.println("");
//			
//			
//			List<LoanAccount> loanAccounts = new ArrayList<LoanAccount>();
//			
//			LoanAccount lacct;
//			
//			lacct = new LoanAccount();
//			lacct.setAccountName("Auto Loan");
//			lacct.setAccountNumber("577662255-1");
//			lacct.setPaymentDueAmt(Double.valueOf("683.42"));
//			lacct.setCurrentBalance(Double.valueOf("18345.55"));
//			lacct.setPaymentDueDate("08-23-2020");
//			
//			loanAccounts.add(lacct);
//			
//			lacct = new LoanAccount();
//			lacct.setAccountName("Home Loan");
//			lacct.setAccountNumber("577662256-2");
//			lacct.setPaymentDueAmt(Double.valueOf("2892.00"));
//			lacct.setCurrentBalance(Double.valueOf("243678.41"));
//			lacct.setPaymentDueDate("09-01-2020");
//			
//			loanAccounts.add(lacct);
//			loanStr = gson.toJson(loanAccounts);
//			
//			System.out.println("Loan String");
//			System.out.println(loanStr);
//			System.out.println("");
//			System.out.println("");
//
//			
//			HttpJsonTransport trans = new HttpJsonTransport();
//			Map<String, String> params = new HashMap<String, String>();
//			
//			params.put("memberId", "978365784");
//			params.put("memberLevel", "Gold");
//			
//			trans.setRequestParameters(params);
//			
//			String paramStr = gson.toJson(trans);
//			
//			System.out.println(paramStr);
//			System.out.println("");
//			System.out.println("");
//
//			
//			Map<String, String> payLoads = new HashMap<String, String>();
//			payLoads.put("cashAccounts", cashStr);
//			payLoads.put("loanAccounts", loanStr);
//			
//			trans.setJsonPayloads(payLoads);
//			
//			String output = gson.toJson(trans);
//
//			System.out.println(output);
//			System.out.println("");
//			System.out.println("");
//
//			
//			HttpJsonTransport tans2 = gson.fromJson(output, HttpJsonTransport.class);
//			
//			String jsonCashList = tans2.getJsonPayloads().get("cashAccounts");
//			
//			System.out.println(jsonCashList);
//			
//			List<CashAccount> cashAccts = gson.fromJson(jsonCashList, new TypeToken<ArrayList<CashAccount>>() {}.getType());
//			
//			
//			
//			
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//			
//		}
//
//	}

}
