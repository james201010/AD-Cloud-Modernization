/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author James Schneider
 *
 */



public class SQLDataHelper implements Runnable {

	private String cashDatabaseUrl;
	private String cashDatabaseUser;
	private String cashDatabasePwd;
	
	private String loanDatabaseUrl;
	private String loanDatabaseUser;
	private String loanDatabasePwd;
		
	private boolean isBalanceService = false;
	private boolean isLoanService = false;
	
	private int cashInsertOuter = 0;
	private int loanInsertOuter = 0;
	
	private static long START_TIME = 0;
	private static boolean[] QUERIES_RUN = new boolean[300];
	// 7 minutes
	private static long NEXT_QUERY_TIME = 420000;
	// 3 minutes
	private static long QUERY_TIME_INTERVAL = 180000;
	private static int LAST_QUERY_RUN = 0;
	
	private static boolean RUNNING_IN_CLOUD = false;
	
	/**
	 * 
	 */
	public SQLDataHelper() {
		try {
			
			Properties props = new Properties();
			props.load(SQLDataHelper.class.getClassLoader().getResourceAsStream("application.properties"));
			
			this.cashDatabaseUrl = System.getenv(ApplicationConstants.ENV_MYSQL_DB_URL) + "/cashaccounts";
			this.cashDatabaseUser = props.getProperty("app.datasource.cashaccounts.username");
			this.cashDatabasePwd = props.getProperty("app.datasource.cashaccounts.password");
			
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + cashDatabaseUrl);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + cashDatabaseUser);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + cashDatabasePwd);

			this.loanDatabaseUrl = System.getenv(ApplicationConstants.ENV_MYSQL_DB_URL) + "/loanaccounts";
			this.loanDatabaseUser = props.getProperty("app.datasource.loanaccounts.username");
			this.loanDatabasePwd = props.getProperty("app.datasource.loanaccounts.password");

			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + loanDatabaseUrl);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + loanDatabaseUser);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + loanDatabasePwd);
			
			String envTierProp = System.getenv(ApplicationConstants.ENV_PROP_TIER_SRVC_NAME);
			//String sysTierProp = System.getProperty(ApplicationConstants.SYS_PROP_TIER_NAME);
			
			if (envTierProp != null && !envTierProp.equals("")) {
				if (envTierProp.contains(ApplicationConstants.ENV_PROP_CASH_TIER)) {
					this.isBalanceService = true;
				}
				if (envTierProp.contains(ApplicationConstants.ENV_PROP_LOAN_TIER)) {
					this.isLoanService = true;
				}				
			}
			
//			if (sysTierProp != null && !sysTierProp.equals("")) {
//				if (sysTierProp.contains(ApplicationConstants.ENV_PROP_CASH_TIER)) {
//					this.isBalanceService = true;
//				}
//				if (sysTierProp.contains(ApplicationConstants.ENV_PROP_LOAN_TIER)) {
//					this.isLoanService = true;
//				}				
//			}
			
			String ADWRKSHP_CLOUD_MODE = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_MODE).toLowerCase();
			
			if (ADWRKSHP_CLOUD_MODE.equals(ApplicationConstants.ENV_CLOUD_MODE_NONE)) {
				this.loanInsertOuter = 120;
				this.cashInsertOuter = 80;
			} else {
				RUNNING_IN_CLOUD = true;
				this.loanInsertOuter = 20;
				this.cashInsertOuter = 20;
			}
			
			
			START_TIME = Calendar.getInstance().getTimeInMillis();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void run() {
		
		
		try {
			
			if (this.isLoanService) {
				this.insertLoanRecords();
				this.runLoanQuery();				
			}
			
//			if (this.isBalanceService) {
//				this.insertCashRecords();
//				this.runCashQuery();				
//			}
			
			
//			Thread.currentThread().sleep(720000);
//			if (this.isLoanService) {
//				this.runLoanQuery();
//		
//			}
//
//			Thread.currentThread().sleep(180000);
//			if (this.isLoanService) {
//				this.runLoanQuery();
//			}

			
//			int cntr = 0;
//			
//			while (true) {
//				
//				
//				if (this.isLoanService) {
//					Thread.currentThread().sleep(20000);
//					
//					if (Math.random() < 1.1/100.0) {
//						this.runLoanQuery();
//					}
//				}
//				
//				
//				if (this.isBalanceService) {
//					Thread.currentThread().sleep(20000);
//					
//					if (Math.random() < 1.1/100.0) {
//						this.runCashQuery();
//					}									
//				}				
//				
//				cntr++;
//				
//				System.out.println("[INFO] - Loop Counter : " + cntr);
//			}
			
				
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			
		}
	}

	
	private static boolean shouldRunQuery() {
		
		synchronized(SQLDataHelper.class) {
			
			if (!RUNNING_IN_CLOUD) {
				if (LAST_QUERY_RUN < QUERIES_RUN.length) {
					
					long nextRunTime = START_TIME + NEXT_QUERY_TIME;
					long currentTime = Calendar.getInstance().getTimeInMillis();
					if (currentTime >= nextRunTime) {
						
						NEXT_QUERY_TIME = NEXT_QUERY_TIME + QUERY_TIME_INTERVAL;
						QUERIES_RUN[LAST_QUERY_RUN] = true;
						LAST_QUERY_RUN++;
						
						return true;
					}
					
				}
				
			}
			
			return false;
		}

	}
	
	public void runLoanQuery() throws Throwable {

		if (shouldRunQuery()) {
			Connection conn = null;
			Statement stmt = null;
					
			try {
				
				long startTime = Calendar.getInstance().getTimeInMillis();

				conn = this.getDBConnection(loanDatabaseUrl, loanDatabaseUser, loanDatabasePwd);
				stmt = conn.createStatement();
				
				String selectSql = "SELECT loansummary.memberid, loansummary.accountname, loansummary.accountnumber, loansummary.currentbalance, loansummary.paymentdueamt, loansummary.paymentduedate, loansummary.accounttype, loandtail.memberid, loandtail.accountnumber, loandtail.monthmulti, loandtail.txndate, loandtail.txndescription, loandtail.txnamount, loandtail.runbalance, loandtail.txnorder FROM  loansummary RIGHT OUTER JOIN loandtail ON loansummary.memberid = loandtail.memberid WHERE loandtail.txndescription LIKE '%SHARE2233-5%' AND loandtail.txndescription LIKE '%QY2TRANSFER%' ORDER BY loandtail.id ASC";
				
				stmt.execute(selectSql);
				
				//stmt.getResultSet();
				
				long endTime = Calendar.getInstance().getTimeInMillis();
				long totalTimeSecs = (endTime - startTime) / 1000;
				long totalTimeMins = totalTimeSecs / 60;			
				long minsInSecs = totalTimeMins * 60;
				long remainingSecs = totalTimeSecs - minsInSecs;
				
				System.out.println("");
				System.out.println("[INFO] - Loan Query : Total Elapsed Time = " + totalTimeMins + " minutes : " + remainingSecs + " seconds");
				
				
			} catch (Throwable ex) {
				ex.printStackTrace();
				
			} finally {
				try {
					
					if (stmt != null) {
						stmt.close();
					}				
					if (conn != null) {
						conn.close();
					}				
					
				} catch (Exception e) {
					
				}
			}
			
		}
		
		
	}
	
	public void insertLoanRecords() throws Throwable {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
									
			conn = this.getDBConnection(loanDatabaseUrl, loanDatabaseUser, loanDatabasePwd);
			
			String beginLoanInsert = "INSERT INTO loandtail(id, memberid, accountnumber, monthmulti, txndate, txndescription, txnamount, runbalance, txnorder) VALUES (";
			
			int totalCntr = 0;
			int intIdCntr = 4000;
			stmt = conn.createStatement();
			conn.setAutoCommit(true);
			
			for (int outer = 0; outer < this.loanInsertOuter; outer++) {
				
				for (int inner = 0; inner < 1000; inner++) {
					totalCntr++;
					//System.out.println("[INFO] Inserting Loan Record Number " + intIdCntr);
					
					String sql = beginLoanInsert + intIdCntr + ", '8729173024', '577662276-1', -1, '27', 'Direct Deposit FROM SHARE2233-5 SCHEDULED QY2TRANSFER TO SHARE2234-6 Pending Two Week Clearence Time', -2892.44, 275465.72, 12)";
					
					stmt.executeUpdate(sql);
					intIdCntr++;
				}
				
				//System.out.println("[INFO] Finished Inserting " + totalCntr + " Loan Records");
				
//				if (stmt != null && conn != null) {
//					stmt.close();
//					conn.close();
//					conn = this.getDBConnection(loanDatabaseUrl, loanDatabaseUser, loanDatabasePwd);
//					stmt = conn.createStatement();
//				}
				
			}
						
			System.out.println("[INFO] Finished Inserting " + totalCntr + " Loan Records");
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			
		} finally {
			try {
				
				if (stmt != null) {
					stmt.close();
				}				
				if (conn != null) {
					conn.close();
				}				

			} catch (Exception e) {
				
			}
		}
		
		
	}
	
	
//	public void insertCashRecords() throws Throwable {
//		
//		Connection conn = null;
//		Statement stmt = null;
//		
//		try {
//			
//			conn = this.getDBConnection(cashDatabaseUrl, cashDatabaseUser, cashDatabasePwd);
//			
//			String beginCashInsert = "INSERT INTO cashdetail(id, memberid, accountnumber, monthmulti, txndate, txndescription, txnamount, runbalance, txnorder) VALUES (";
//			
//			int totalCntr = 0;
//			int intIdCntr = 4000;
//			stmt = conn.createStatement();
//			conn.setAutoCommit(true);
//			
//			for (int outer = 0; outer < this.cashInsertOuter; outer++) {
//				
//				for (int inner = 0; inner < 1000; inner++) {
//					totalCntr++;
//					//System.out.println("[INFO] Inserting Cash Record Number " + intIdCntr);
//					
//					String sql = beginCashInsert + intIdCntr + ", '8729173024', '577662224-1', 0, '1', 'Point Of Sale Withdrawal - FRINEDLYS QUICKSTOP LOCAL SUNDRIES STORE', -7.00, 7974.13, 135)";
//					
//					stmt.executeUpdate(sql);
//					intIdCntr++;
//				}
//				
//				System.out.println("[INFO] Finished Inserting " + totalCntr + " Cash Records");
//				
////				if (stmt != null && conn != null) {
////					stmt.close();
////					conn.close();
////					conn = this.getDBConnection(cashDatabaseUrl, cashDatabaseUser, cashDatabasePwd);
////					stmt = conn.createStatement();
////				}
//				
//			}
//						
//			
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//			
//		} finally {
//			try {
//				
//				if (stmt != null) {
//					stmt.close();
//				}				
//				if (conn != null) {
//					conn.close();
//				}				
//
//			} catch (Exception e) {
//				
//			}
//		}
//		
//		
//	}


//	public void runCashQuery() throws Throwable {
//		
//		Connection conn = null;
//		Statement stmt = null;
//				
//		try {
//			
//			
//			long startTime = Calendar.getInstance().getTimeInMillis();
//
//			conn = this.getDBConnection(cashDatabaseUrl, cashDatabaseUser, cashDatabasePwd);
//			stmt = conn.createStatement();
//			
//			String selectSql = "SELECT cashsummary.memberid, cashsummary.accountname, cashsummary.accountnumber, cashsummary.avalbalance, cashsummary.currentbalance, cashdetail.memberid, cashdetail.accountnumber, cashdetail.monthmulti, cashdetail.txndate, cashdetail.txndescription, cashdetail.txnamount, cashdetail.runbalance, cashdetail.txnorder FROM cashsummary RIGHT OUTER JOIN cashdetail ON cashsummary.memberid = cashdetail.memberid WHERE cashdetail.txndescription LIKE '%SUNDRIES%' AND cashdetail.txndescription LIKE '%QUICKSTOP%' ORDER BY cashdetail.id ASC";
//			
//			stmt.execute(selectSql);
//			
//			//stmt.getResultSet();
//			
//			long endTime = Calendar.getInstance().getTimeInMillis();
//			long totalTimeSecs = (endTime - startTime) / 1000;
//			long totalTimeMins = totalTimeSecs / 60;			
//			long minsInSecs = totalTimeMins * 60;
//			long remainingSecs = totalTimeSecs - minsInSecs;
//			
//			System.out.println("");
//			System.out.println("[INFO] - Cash Query : Total Elapsed Time = " + totalTimeMins + " minutes : " + remainingSecs + " seconds");
//			
//			
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//			
//		} finally {
//			try {
//				
//				if (stmt != null) {
//					stmt.close();
//				}				
//				if (conn != null) {
//					conn.close();
//				}				
//			} catch (Exception e) {
//				
//			}
//		}
//		
//		
//	}
//

    public Connection getDBConnection(String databaseUrl, String databaseUser, String databasePwd) throws Throwable {

        Class.forName("com.mysql.jdbc.Driver");
        //Class.forName("org.mariadb.jdbc.Driver");
        
        Connection dbCon = DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
        return dbCon;

    }

	public boolean isBalanceService() {
		return isBalanceService;
	}

	public boolean isLoanService() {
		return isLoanService;
	}	



}
