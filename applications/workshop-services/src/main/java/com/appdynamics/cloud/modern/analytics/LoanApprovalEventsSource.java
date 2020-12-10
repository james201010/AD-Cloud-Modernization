/**
 * 
 */
package com.appdynamics.cloud.modern.analytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.analytics.json.LoanBatchList;
import com.appdynamics.cloud.modern.analytics.json.LoanEvent;
import com.appdynamics.cloud.modern.analytics.json.LoanWithEvents;
import com.appdynamics.cloud.modern.config.AnalyticsEventsSourceConfig;
import com.appdynamics.cloud.modern.config.ConfigProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author James Schneider
 *
 */
public class LoanApprovalEventsSource implements AnalyticsEventsSource {

	private static Logger logr;
	private static String[] LOAN_CATEGORIES = {"business","personal"};
	private static String[] FIRST_NAMES = {"Rob","Eric","Gabriella","Jeff","Alex","Tom","Lori","Michelle","James","Steve","Matt","Jill","Ron","Andy","Michelle","Leslie","Anne","Tom"};
	private static String[] LAST_NAMES = {"Bolton","Querales","Johanson","Morgan","Rabaut","Fedotyev","Swanson","Dwyer","Knope","Haverford","Perkins", "Smith", "Snyder", "Donovan"};
	private static String[] EMAIL_PROVS = {"aol","hotmail","yahoo","gmail","icloud"};
	//private static String[] ALL_LOANS = {"Conventional","FHA","ARM","Home-Equity","Auto","Comm-SBA","Comm-Perm","Comm-Bridge","Comm-Construct","Comm-Conduit"};
	private static String[] COMM_LOANS = {"Comm-SBA","Comm-Perm","Comm-Bridge","Comm-Construct","Comm-Conduit"};
	private static String[] RETAIL_LOANS = {"Conventional","FHA","ARM","Home-Equity","Auto"};
	private static String[] LOCATION_CITIES = {"New York, NY","Chicago, IL","Philadelphia, PA","Columbus, OH","Atlanta, GA","Washington, DC","Boston, MA","Memphis, TN","Louisville, KY","Baltimore, MD"};
	private static String[] CREDIT_CHECKS = {"CC-World", "YourCredit", "Uni-Credit", "CreditExperts"};	
	private static String[] RAN_NUMBERS = {"1","2","3","4","5","6","7","8","9","0"};
	
	private AnalyticsEventsSourceConfig config;
	private String schemaName;
	private int publishEventsInterval;
	private int hourlyEventsBatchSize;
	private int numberOfBatchesPerHour;
	private int intervalEventsBatchSize;
	private int twiceTheIntervalEventsBatchSize;
	
	private int ms1EventsNum, ms2EventsNum, ms3EventsNum, ms4EventsNum, ms5EventsNum;	
	
	private LoanBatchList<LoanWithEvents> HOURLY_BATCH = null;
	
	/**
	 * 
	 */
	public LoanApprovalEventsSource() {

	}

	@Override
	public void initialize(AnalyticsEventsSourceConfig config) throws Throwable {
		
		logr = new Logger(LoanApprovalEventsSource.class.getSimpleName());
		
		this.config = config;
		
		this.schemaName = this.config.getSchemaName();
		
		logr.info("##############################  Initializing Events Source for '" + this.getSchemaName() + "' schema ##############################");
		
		this.publishEventsInterval = Integer.parseInt(this.config.getPublishEventsInterval());
		this.hourlyEventsBatchSize = Integer.parseInt(this.config.getHourlyEventsBatchSize());
		
		this.numberOfBatchesPerHour = 60 / this.publishEventsInterval;
		this.intervalEventsBatchSize = this.hourlyEventsBatchSize / this.numberOfBatchesPerHour;
		this.twiceTheIntervalEventsBatchSize = this.intervalEventsBatchSize * 2;
		
		// calculate the number of loans for each milestone based on the configured percentage
		Double ms1Perc = 1d;
		Double ms2Perc = 1d;
		Double ms3Perc = 1d;
		Double ms4Perc = 1d;
		Double ms5Perc = 1d;
		
		List<ConfigProperty> confProps = this.config.getConfigProperties();
		for (ConfigProperty prop : confProps) {
			switch (prop.getName()) {
			
			case "milestone-1-percent":
				ms1Perc = Double.parseDouble(prop.getValue());
				break;
			case "milestone-2-percent":
				ms2Perc = Double.parseDouble(prop.getValue());
				break;
			case "milestone-3-percent":
				ms3Perc = Double.parseDouble(prop.getValue());
				break;
			case "milestone-4-percent":
				ms4Perc = Double.parseDouble(prop.getValue());
				break;
			case "milestone-5-percent":
				ms5Perc = Double.parseDouble(prop.getValue());
				break;
			}
		}
		
		Double events2Process;
		
		events2Process = this.hourlyEventsBatchSize * ms1Perc;
		this.ms1EventsNum = events2Process.intValue();
		
		logr.info(" - Schema: " + this.schemaName + " : ms1EventsNum = " + this.ms1EventsNum);
		
		events2Process = this.hourlyEventsBatchSize * ms2Perc;
		this.ms2EventsNum = events2Process.intValue();
		
		logr.info(" - Schema: " + this.schemaName + " : ms2EventsNum = " + this.ms2EventsNum);
		
		events2Process = this.hourlyEventsBatchSize * ms3Perc;
		this.ms3EventsNum = events2Process.intValue();
		
		logr.info(" - Schema: " + this.schemaName + " : ms3EventsNum = " + this.ms3EventsNum);
		
		events2Process = this.hourlyEventsBatchSize * ms4Perc;
		this.ms4EventsNum = events2Process.intValue();
		
		logr.info(" - Schema: " + this.schemaName + " : ms4EventsNum = " + this.ms4EventsNum);
		
		events2Process = this.hourlyEventsBatchSize * ms5Perc;
		this.ms5EventsNum = events2Process.intValue();
		
		logr.info(" - Schema: " + this.schemaName + " : ms5EventsNum = " + this.ms5EventsNum);
		
	}	
	
	
	@Override
	public String getSchemaPayloadJson() throws Throwable {
		
		return null;
	}

	@Override
	public String getPublishEventsJson() throws Throwable {
		
		if (HOURLY_BATCH == null || HOURLY_BATCH.size() < 1) {
			HOURLY_BATCH = this.createHourlyBatchOfLoans();
		}
		
		List<LoanWithEvents> nextLoanBatch = getNextIntervalLoanBatchToPublish();
		
		List<LoanEvent> loanEvents = new ArrayList<LoanEvent>();
		
		for (LoanWithEvents loanWEvents : nextLoanBatch) {
			loanEvents.add(loanWEvents.getMs1appsub());
			loanEvents.add(loanWEvents.getMs2docs());
			loanEvents.add(loanWEvents.getMs3credit());
			loanEvents.add(loanWEvents.getMs4under());
			loanEvents.add(loanWEvents.getMs5approval());
		}
		
		
		Gson gson = new Gson();
		String payload = gson.toJson(loanEvents,  new TypeToken<ArrayList<LoanEvent>>() {}.getType());
		
		//logr.info("gson payload: " + payload);
		
		for (LoanWithEvents loan : nextLoanBatch) {
			loan.setPublished(true);
		}
		
		logr.info(" - Schema: " + this.schemaName + " : Next loan batch size = " + nextLoanBatch.size());
		
		return payload;
	}

	@Override
	public String getSchemaName() throws Throwable {
		return this.schemaName;
	}
	@Override
	public int getPublishEventsInterval() throws Throwable {
		return this.publishEventsInterval;
	}
	@Override
	public int getHourlyEventsBatchSize() throws Throwable {
		return this.hourlyEventsBatchSize;
	}

	
	private List<LoanWithEvents> getNextIntervalLoanBatchToPublish() {
		
		int unpublishedCount = HOURLY_BATCH.unpublishedLoansCount();
		
		logr.info(" - Schema: " + this.schemaName + " : Unpublished Count = " + unpublishedCount);
		
		if (unpublishedCount < 1) {
			HOURLY_BATCH = this.createHourlyBatchOfLoans();
			unpublishedCount = HOURLY_BATCH.unpublishedLoansCount();
		}
		
		int numberOfEventsToBatch = this.intervalEventsBatchSize;
		
		//logr.info("numberOfEventsToBatch = " + numberOfEventsToBatch);
		
		List<LoanWithEvents> nextBatch = new ArrayList<LoanWithEvents>();
		LoanWithEvents[] loanArr = HOURLY_BATCH.toArray(new LoanWithEvents[this.hourlyEventsBatchSize]);
		
		int totalMilestonesFound = 0;
		
		if (unpublishedCount < this.twiceTheIntervalEventsBatchSize) {
			
			//logr.info("3: twiceTheIntervalEventsBatchSize = " + twiceTheIntervalEventsBatchSize);
			
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (!loanArr[cntr].isPublished()) {
					nextBatch.add(loanArr[cntr]);
				}
			}
			
		} else {
			
			int eventsPerMileStone = numberOfEventsToBatch / 6;
			
			logr.info(" - Schema: " + this.schemaName + " : Events Per Milestone = " + eventsPerMileStone);
			
			int eventsPerMileStoneCntr = 0;
			
			// add milestone class 0 loans
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (eventsPerMileStoneCntr < eventsPerMileStone) {
					if (loanArr[cntr].getMilestoneClassification() == LoanWithEvents.MILESTONE_CLASS_0 && !loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
						loanArr[cntr].setSelectedToPublish(true);
						nextBatch.add(loanArr[cntr]);
						eventsPerMileStoneCntr++;
						totalMilestonesFound++;
					}
				} else {
					break;
				}
			}
			
			// add milestone class 1 loans
			eventsPerMileStoneCntr = 0;
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (eventsPerMileStoneCntr < eventsPerMileStone) {
					if (loanArr[cntr].getMilestoneClassification() == LoanWithEvents.MILESTONE_CLASS_1 && !loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
						loanArr[cntr].setSelectedToPublish(true);
						nextBatch.add(loanArr[cntr]);
						eventsPerMileStoneCntr++;
						totalMilestonesFound++;
					}
				} else {
					break;
				}
			}
			
			// add milestone class 2 loans
			eventsPerMileStoneCntr = 0;
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (eventsPerMileStoneCntr < eventsPerMileStone) {
					if (loanArr[cntr].getMilestoneClassification() == LoanWithEvents.MILESTONE_CLASS_2 && !loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
						loanArr[cntr].setSelectedToPublish(true);
						nextBatch.add(loanArr[cntr]);
						eventsPerMileStoneCntr++;
						totalMilestonesFound++;
					}
				} else {
					break;
				}
			}
			
			// add milestone class 3 loans
			eventsPerMileStoneCntr = 0;
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (eventsPerMileStoneCntr < eventsPerMileStone) {
					if (loanArr[cntr].getMilestoneClassification() == LoanWithEvents.MILESTONE_CLASS_3 && !loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
						loanArr[cntr].setSelectedToPublish(true);
						nextBatch.add(loanArr[cntr]);
						eventsPerMileStoneCntr++;
						totalMilestonesFound++;
					}
				} else {
					break;
				}
			}
			
			// add milestone class 4 loans
			eventsPerMileStoneCntr = 0;
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (eventsPerMileStoneCntr < eventsPerMileStone) {
					if (loanArr[cntr].getMilestoneClassification() == LoanWithEvents.MILESTONE_CLASS_4 && !loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
						loanArr[cntr].setSelectedToPublish(true);
						nextBatch.add(loanArr[cntr]);
						eventsPerMileStoneCntr++;
						totalMilestonesFound++;
					}
				} else {
					break;
				}
			}
			
			// add milestone class 5 loans
			eventsPerMileStoneCntr = 0;
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (eventsPerMileStoneCntr < eventsPerMileStone) {
					if (loanArr[cntr].getMilestoneClassification() == LoanWithEvents.MILESTONE_CLASS_5 && !loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
						loanArr[cntr].setSelectedToPublish(true);
						nextBatch.add(loanArr[cntr]);
						eventsPerMileStoneCntr++;
						totalMilestonesFound++;
					}
				} else {
					break;
				}
			}

			
			// randomly select the remaining loans
			int remEvents = numberOfEventsToBatch - (eventsPerMileStone * 6);
			int missingMilestones = (eventsPerMileStone * 6) - totalMilestonesFound;
			int remainderEvents = missingMilestones + remEvents;
			//int remainderEvents = numberOfEventsToBatch - (eventsPerMileStone * 6);
			
			logr.info(" - Schema: " + this.schemaName + " : Remainder Events = " + remainderEvents);
			
			int remainderEventsCntr = 0;
			boolean foundAllRemainders = false;
			List<LoanWithEvents> remainingCandidates = new ArrayList<LoanWithEvents>();
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			
			for (int cntr = 0; cntr < loanArr.length; cntr++) {
				if (!loanArr[cntr].isPublished() && !loanArr[cntr].isSelectedToPublish()) {
					remainingCandidates.add(loanArr[cntr]);
				}
			}
			
			LoanWithEvents[] candidateArr = remainingCandidates.toArray(new LoanWithEvents[remainingCandidates.size()]);
	
			while (!foundAllRemainders) {
				int randomLoan = rand.nextInt(0, candidateArr.length - 1);
				if (!candidateArr[randomLoan].isPublished() && !candidateArr[randomLoan].isSelectedToPublish()) {
					candidateArr[randomLoan].setSelectedToPublish(true);
					nextBatch.add(candidateArr[randomLoan]);
					remainderEventsCntr++;
				}
				
				if (remainderEventsCntr >= remainderEvents) {
					foundAllRemainders = true;
				}
			}
				
			
		}
		
		
		return nextBatch;
		
	}
	
	
	private LoanBatchList<LoanWithEvents> createHourlyBatchOfLoans() {
		LoanBatchList<LoanWithEvents> loanBatch = new LoanBatchList<LoanWithEvents>(this.hourlyEventsBatchSize);
		
		for (int i = 0; i < this.hourlyEventsBatchSize; i++) {
			loanBatch.add(this.createNewLoanWithEvents());
		}
		
		// now we set the milestones for the events of each loan
		LoanWithEvents[] loanArr;
		List<LoanWithEvents> milestoneLoans;
		
		// process 1st milestone loans
		loanArr = loanBatch.toArray(new LoanWithEvents[this.hourlyEventsBatchSize]);
		milestoneLoans = new ArrayList<LoanWithEvents>();
		for (int cntr = 0; cntr < this.ms1EventsNum; cntr++) {
			if (loanArr[cntr].isMs1Candidate()) {
				loanArr[cntr].getMs1appsub().setMs1appsubdone(true);
				milestoneLoans.add(loanArr[cntr]);
			}
		}
			

		// process 2nd milestone loans
		loanArr = milestoneLoans.toArray(new LoanWithEvents[milestoneLoans.size()]);
		milestoneLoans = new ArrayList<LoanWithEvents>();
		for (int cntr = 0; cntr < this.ms2EventsNum; cntr++) {
			if (loanArr[cntr].isMs2Candidate()) {
				loanArr[cntr].getMs2docs().setMs2docsdone(true);
				milestoneLoans.add(loanArr[cntr]);
			}
		}
		
		
		// process 3rd milestone loans
		loanArr = milestoneLoans.toArray(new LoanWithEvents[milestoneLoans.size()]);
		milestoneLoans = new ArrayList<LoanWithEvents>();
		for (int cntr = 0; cntr < this.ms3EventsNum; cntr++) {
			if (loanArr[cntr].isMs3Candidate()) {
				loanArr[cntr].getMs3credit().setMs3creditdone(true);
				milestoneLoans.add(loanArr[cntr]);
			}
		}
		

		// process 4th milestone loans
		loanArr = milestoneLoans.toArray(new LoanWithEvents[milestoneLoans.size()]);
		milestoneLoans = new ArrayList<LoanWithEvents>();
		for (int cntr = 0; cntr < this.ms4EventsNum; cntr++) {
			if (loanArr[cntr].isMs4Candidate()) {
				loanArr[cntr].getMs4under().setMs4underdone(true);
				milestoneLoans.add(loanArr[cntr]);
			}
		}
		

		// process 5th milestone loans
		loanArr = milestoneLoans.toArray(new LoanWithEvents[milestoneLoans.size()]);
		milestoneLoans = new ArrayList<LoanWithEvents>();
		for (int cntr = 0; cntr < this.ms5EventsNum; cntr++) {
			if (loanArr[cntr].isMs5Candidate()) {
				loanArr[cntr].getMs5approval().setMs5approvaldone(true);
				milestoneLoans.add(loanArr[cntr]);
			}
		}
			
//		int class0 = 0;
//		int class1 = 0;
//		int class2 = 0;
//		int class3 = 0;
//		int class4 = 0;
//		int class5 = 0;
//		
//		for (LoanWithEvents loan : loanBatch) {
//			int classi = loan.getMilestoneClassification();
//			
//			switch (classi) {
//			
//			case LoanWithEvents.MILESTONE_CLASS_0:
//				class0++;
//				break;
//
//			case LoanWithEvents.MILESTONE_CLASS_1:
//				class1++;
//				break;
//				
//			case LoanWithEvents.MILESTONE_CLASS_2:
//				class2++;
//				break;
//
//			case LoanWithEvents.MILESTONE_CLASS_3:
//				class3++;
//				break;
//
//			case LoanWithEvents.MILESTONE_CLASS_4:
//				class4++;
//				break;
//
//			case LoanWithEvents.MILESTONE_CLASS_5:
//				class5++;
//				break;
//				
//			default:
//				break;
//			}
//		}
//		
//		logr.info("Class 0 = " + class0);
//		logr.info("Class 1 = " + class1);
//		logr.info("Class 2 = " + class2);
//		logr.info("Class 3 = " + class3);
//		logr.info("Class 4 = " + class4);
//		logr.info("Class 5 = " + class5);
		
		
		return loanBatch;
		
	}
	
	private LoanWithEvents createNewLoanWithEvents() {
		
		LoanWithEvents lwe = new LoanWithEvents();
		
		String loanid = this.getLoanId();
		String acctnumber = this.getAccountNumber();
		String loancategory = this.getLoanCategory();
		String firstname = this.getFirstName();
		String lastname = this.getLastName();
		String email = this.getEmail(firstname, lastname);
		String orgbank = this.getOriginatingBank();
		String loantype = this.getLoanType(loancategory);
		Integer loanamount = this.getLoanAmount(loantype);
		String creditcheckr = this.getCreditCheckProvider();
		String locationcity = this.getLocationCity();
		Boolean ms1appsubdone = false;
		Boolean ms2docsdone = false;
		Boolean ms3creditdone = false;
		Boolean ms4underdone = false;
		Boolean ms5approvaldone = false;		
		
		LoanEvent le = new LoanEvent();

		le = new LoanEvent();
		le.uuid = UUID.randomUUID().toString();
		le.setLoanid(loanid);
		le.setAcctnumber(acctnumber);
		le.setLoancategory(loancategory);
		le.setFirstname(firstname);
		le.setLastname(lastname);
		le.setEmail(email);
		le.setOrgbank(orgbank);
		le.setLoantype(loantype);
		le.setLoanamount(loanamount);
		le.setCreditcheckr(creditcheckr);
		le.setLocationcity(locationcity);
		le.setMs1appsubdone(ms1appsubdone);
		le.setMs2docsdone(ms2docsdone);
		le.setMs3creditdone(ms3creditdone);
		le.setMs4underdone(ms4underdone);
		le.setMs5approvaldone(ms5approvaldone);
		
		lwe.setLoanid(loanid);
		lwe.setMs1appsub(le);
		lwe.setMs2docs(le.cloneLoanEvent(UUID.randomUUID().toString()));
		lwe.setMs3credit(le.cloneLoanEvent(UUID.randomUUID().toString()));
		lwe.setMs4under(le.cloneLoanEvent(UUID.randomUUID().toString()));
		lwe.setMs5approval(le.cloneLoanEvent(UUID.randomUUID().toString()));
		
		return lwe;
	}
	
	
	private int getLoanAmount(String loanType) {
		
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		switch (loanType) {
		
		case "Conventional":
			return rand.nextInt(300, 1200)*1000;

		case "FHA":
			return rand.nextInt(100, 800)*1000;

		case "ARM":
			return rand.nextInt(100, 800)*1000;

		case "Home-Equity":
			return rand.nextInt(14, 100)*1000;

		case "Auto":
			return rand.nextInt(10, 100)*1000;

		case "Comm-SBA":
			return rand.nextInt(400, 1200)*1000;

		case "Comm-Perm":
			return rand.nextInt(500, 1200)*1000;

		case "Comm-Bridge":
			return rand.nextInt(350, 1200)*1000;

		case "Comm-Construct":
			return rand.nextInt(600, 1200)*1000;

		case "Comm-Conduit":
			return rand.nextInt(700, 1200)*1000;
			
		default:
			return rand.nextInt(100, 800)*1000;
		}
	}

	
	private String getLoanId() {
		return UUID.randomUUID().toString();
	}	
	private String getAccountNumber() {
        Random rand = new Random(); 
        String acct = "ACC-"+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)
        +rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)
        +rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length);
		return acct;
	}
	
	private String getFirstName() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(FIRST_NAMES.length); 
		return FIRST_NAMES[randNum];
	}
	private String getLastName() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(LAST_NAMES.length); 
		return LAST_NAMES[randNum];
	}
	private String getEmail(String firstName, String lastName) {
        Random rand = new Random(); 
        int randNum = rand.nextInt(EMAIL_PROVS.length); 
		return firstName + "." + lastName + "@" + EMAIL_PROVS[randNum] + ".com";
	}
	
	private String getLocationCity() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(LOCATION_CITIES.length); 
		return LOCATION_CITIES[randNum];
	}	
	
	private String getCreditCheckProvider() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(CREDIT_CHECKS.length); 
		return CREDIT_CHECKS[randNum];
	}	

	private String getCommercialLoanType() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(COMM_LOANS.length); 
		return COMM_LOANS[randNum];
	}	
	private String getRetailLoanType() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(RETAIL_LOANS.length); 
		return RETAIL_LOANS[randNum];
	}	
	
	private String getOriginatingBank() {
        Random rand = new Random(); 
        String bank = "Bank-"+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)
        +rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length)+rand.nextInt(RAN_NUMBERS.length);
		return bank;
	}	
	private String getLoanCategory() {
        Random rand = new Random(); 
        int randNum = rand.nextInt(LOAN_CATEGORIES.length); 
		return LOAN_CATEGORIES[randNum];
	}	
	
	private String getLoanType(String loanCategory) {
		if (loanCategory.equals("personal")) {
			return this.getRetailLoanType();
		} else {
			return this.getCommercialLoanType();
		}
		
	}



}
