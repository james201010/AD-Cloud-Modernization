/**
 * 
 */
package com.appdynamics.cloud.modern.repos.repository.cashaccounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appdynamics.cloud.modern.repos.model.cashaccounts.CashAccount;


/**
 * @author James Schneider
 *
 */
public interface CashAccountsRepository extends JpaRepository<CashAccount, Long> {

	List<CashAccount> findByMemberId(String memberId);
	CashAccount findByAccountNumber(String accountNumber);

}
