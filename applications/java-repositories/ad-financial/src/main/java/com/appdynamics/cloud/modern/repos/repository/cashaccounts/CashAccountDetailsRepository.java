/**
 * 
 */
package com.appdynamics.cloud.modern.repos.repository.cashaccounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appdynamics.cloud.modern.repos.model.cashaccounts.CashAccountDetail;


/**
 * @author James Schneider
 *
 */
public interface CashAccountDetailsRepository extends JpaRepository<CashAccountDetail, Long> {

	
	List<CashAccountDetail> findByAccountNumber(String accountNumber);
	
	List<CashAccountDetail> findByAccountNumberOrderByTxnOrderAsc(String accountNumber);

}
