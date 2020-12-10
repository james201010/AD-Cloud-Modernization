/**
 * 
 */
package com.appdynamics.cloud.modern.repos.repository.loanaccounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanApplication;

/**
 * @author James Schneider
 *
 */
public interface LoanApplicationsRepository extends JpaRepository<LoanApplication, String> {

	List<LoanApplication> findByMsStepCount(String msStepCount);
	
	//LoanApplication save(LoanApplication entity);
}
