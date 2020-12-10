/**
 * 
 */
package com.appdynamics.cloud.modern.repos.repository.loanaccounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanAccount;

/**
 * @author James Schneider
 *
 */
public interface LoanAccountsRepository extends JpaRepository<LoanAccount, Long> {

	List<LoanAccount> findByMemberId(String memberId);
	LoanAccount findByAccountNumber(String accountNumber);
}
