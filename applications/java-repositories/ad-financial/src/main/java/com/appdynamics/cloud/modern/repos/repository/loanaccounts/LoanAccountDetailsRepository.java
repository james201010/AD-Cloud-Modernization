/**
 * 
 */
package com.appdynamics.cloud.modern.repos.repository.loanaccounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanAccountDetail;


/**
 * @author James Schneider
 *
 */
public interface LoanAccountDetailsRepository extends JpaRepository<LoanAccountDetail, Long> {

	
	List<LoanAccountDetail> findByAccountNumber(String accountNumber);
	
	List<LoanAccountDetail> findByAccountNumberOrderByTxnOrderAsc(String accountNumber);

}
