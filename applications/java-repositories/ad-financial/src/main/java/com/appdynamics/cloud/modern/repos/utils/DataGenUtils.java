/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author James Schneider
 *
 */
public class DataGenUtils {

	
	private static String[] FIRST_NAMES = {"Rob","Eric","Gabriella","Jeff","Alex","Tom","Lori","Michelle","James","Steve","Matt","Jill","Ron","Andy","Michelle","Leslie","Anne","Tom"};
	
	private static String[] LAST_NAMES = {"Bolton","Querales","Johanson","Morgan","Rabaut","Fedotyev","Swanson","Dwyer","Knope","Haverford","Perkins", "Smith", "Snyder", "Donovan"};
	
	private static String[] EMAIL_PROVS = {"aol","hotmail","yahoo","gmail","icloud"};
	
	private static String[] ALL_LOANS = {"Conventional","FHA","ARM","Home-Equity","Auto","Comm-SBA","Comm-Perm","Comm-Bridge","Comm-Construct","Comm-Conduit"};

	private static String[] COMM_LOANS = {"Comm-SBA","Comm-Perm","Comm-Bridge","Comm-Construct","Comm-Conduit"};
	
	private static String[] RETAIL_LOANS = {"Conventional","FHA","ARM","Home-Equity","Auto"};
	
	private static String[] LOCATION_CITIES = {"New York, NY","Chicago, IL","Philadelphia, PA","Columbus, OH","Atlanta, GA","Washington, DC","Boston, MA","Memphis, TN","Louisville, KY","Baltimore, MD"};
	
	private static String[] CREDIT_CHECKS = {"CC-World", "YourCredit", "Uni-Credit", "CreditExperts"};
	
	private static String[] RAN_NUMBERS = {"1","2","3","4","5","6","7","8","9","0"};
	
	/**
	 * 
	 */
	public DataGenUtils() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {	
			

			
			generateDDL("business");
			generateDDL("personal");
			
//			String fName;
//			String lName;
//			String loanType;
//			StringBuffer buf;
//			for (int i = 0; i < 100; i++) {
//				
//				fName = getFirstName();
//				lName = getLastName();
//				loanType = getAnyLoanType();
//				
//				buf = new StringBuffer();
//				
//				buf.append(padString( getLoanId() , 40));
//				buf.append(padString( getAccountNumber() , 40));
//				buf.append(padString( getEmail(fName, lName) , 40));
//				buf.append(padString( fName , 40));
//				buf.append(padString( lName , 40));
//				buf.append(padString( getOriginatingBank() , 40));
//				buf.append(padString( loanType , 40));
//				buf.append(padString( getLoanAmount(loanType) , 40));
//				buf.append(padString( getCreditCheckProvider() , 40));
//				buf.append(padString( getLocationCity() , 40));
//				
//				System.out.println(buf.toString());
//				
//			}
			
			
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			
		}

	}

	protected static void generateDDL(String loanCat) {
		
		StringBuffer buff = new StringBuffer();
		
		String fName;
		String lName;
		String loanType;		
		
		for (int cntr = 0; cntr < 100; cntr++) {
			
			fName = getFirstName();
			lName = getLastName();	
			if (loanCat.equalsIgnoreCase("personal")) {
				loanType = getRetailLoanType();
			} else {
				loanType = getCommercialLoanType();
			}
			
			buff.append("(");
			
			buff.append("'" + getLoanId() + "', "); // loanid
			buff.append("'" + getAccountNumber() + "', "); // accountnumber
			buff.append("'" + loanCat + "', "); // loancategory
			buff.append("'" + getEmail(fName, lName) + "', "); // email
			buff.append("'" + fName + "', "); // firstname
			buff.append("'" + lName + "', "); // lastname
			buff.append("'" + getOriginatingBank() + "', "); // orgbank
			buff.append("'" + loanType + "', "); // loantype
			buff.append("'" + getLoanAmount(loanType) + "', "); // loanamount
			buff.append("'" + getCreditCheckProvider() + "', "); // creditcheck
			buff.append("'" + getLocationCity() + "', "); // locationcity
			buff.append("'" + "true" + "', "); // msappsubdone
			buff.append("'" + "false" + "', "); // msdocsdone
			buff.append("'" + "false" + "', "); // mscreditdone
			buff.append("'" + "false" + "', "); // msunderdone
			buff.append("'" + "false" + "', "); // msapprovaldone
			buff.append("'" + "1" + "'"); // msstepcount
			
			buff.append("),");
			buff.append(System.getProperty("line.separator"));			
		}
		
		System.out.println(buff.toString());
	}
	
	protected static String padString(String strToPad, int totalLength) {

		int added = totalLength - strToPad.length();
		StringBuffer buf = new StringBuffer();
		buf.append(strToPad);
		for (int i = 0; i < added; i++) {
			buf.append(" ");
		}
		buf.append(" | ");
		return buf.toString();
	}
	
	public static String getLoanAmount(String loanType) {
	
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		switch (loanType) {
		
		case "Conventional":
			return "" + rand.nextInt(300, 1200)*1000;

		case "FHA":
			return "" + rand.nextInt(100, 800)*1000;

		case "ARM":
			return "" + rand.nextInt(100, 800)*1000;

		case "Home-Equity":
			return "" + rand.nextInt(14, 100)*1000;

		case "Auto":
			return "" + rand.nextInt(10, 100)*1000;

		case "Comm-SBA":
			return "" + rand.nextInt(400, 1200)*1000;

		case "Comm-Perm":
			return "" + rand.nextInt(500, 1200)*1000;

		case "Comm-Bridge":
			return "" + rand.nextInt(350, 1200)*1000;

		case "Comm-Construct":
			return "" + rand.nextInt(600, 1200)*1000;

		case "Comm-Conduit":
			return "" + rand.nextInt(700, 1200)*1000;
			
		default:
			return "" + rand.nextInt(100, 800)*1000;
		}
	}
	
	public static String getLoanId() {
		return UUID.randomUUID().toString();
	}	
	public static String getAccountNumber() {
        Random rand = new Random(); 
        String acct = "ACC-"+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)
        +rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)
        +rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length);
		return acct;
	}
	
	public static String getFirstName() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(FIRST_NAMES.length); 
		return FIRST_NAMES[randNum];
	}
	public static String getLastName() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(LAST_NAMES.length); 
		return LAST_NAMES[randNum];
	}
	public static String getEmail(String firstName, String lastName) {
        Random rand = new Random(); 
        int randNum = rand.nextInt(EMAIL_PROVS.length); 
		return firstName + "." + lastName + "@" + EMAIL_PROVS[randNum] + ".com";
	}
	
	public static String getLocationCity() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(LOCATION_CITIES.length); 
		return LOCATION_CITIES[randNum];
	}	
	
	public static String getCreditCheckProvider() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(CREDIT_CHECKS.length); 
		return CREDIT_CHECKS[randNum];
	}	

	
	public static String getAnyLoanType() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(ALL_LOANS.length); 
		return ALL_LOANS[randNum];
	}	
	public static String getCommercialLoanType() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(COMM_LOANS.length); 
		return COMM_LOANS[randNum];
	}	
	public static String getRetailLoanType() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(RETAIL_LOANS.length); 
		return RETAIL_LOANS[randNum];
	}	
	
	public static String getOriginatingBank() {
        Random rand = new Random(); 
        String bank = "Bank-"+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)
        +rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length);
		return bank;
	}	
	
	
}
