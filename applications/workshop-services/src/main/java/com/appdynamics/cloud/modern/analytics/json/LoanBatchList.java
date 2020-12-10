/**
 * 
 */
package com.appdynamics.cloud.modern.analytics.json;

import java.util.ArrayList;

/**
 * @author James Schneider
 *
 */
public class LoanBatchList<E> extends ArrayList<E> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8869536550303099639L;

	/**
	 * 
	 */
	public LoanBatchList(int initialCapacity) {
		super(initialCapacity);
	}

	public int unpublishedLoansCount() {
		int cntr = 0;
		
		LoanWithEvents loan;
		for (Object obj : this) {
			
			loan = (LoanWithEvents)obj;
			if (!loan.isPublished()) {
				cntr++;
			}
			
		}
		
		return cntr;
	}
	
	
}
