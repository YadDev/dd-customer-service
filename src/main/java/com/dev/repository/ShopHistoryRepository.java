package com.dev.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.entities.CustomerPayment;
import com.dev.entities.CustomerPurchasedHistory;

@Repository
public interface ShopHistoryRepository extends JpaRepository<CustomerPurchasedHistory,String> {

	String amtPaid = null;


	@Query(value = "SELECT * FROM customer_purchased_history cp WHERE cp.custNumber = ?1", 
			  nativeQuery = true)
	Collection<CustomerPurchasedHistory> findShopHistoryByCustomerId(String custNumber);
	
	@Query(value = "SELECT * FROM customer_payment_detail cp WHERE cp.custNumber = :custNumber and cp.payment_for_month=:forMonth", 
			  nativeQuery = true)
	Optional<CustomerPayment> findPaymentByCustomerIdAndQuery(@Param("custNumber") String payRefId, @Param("forMonth") String forMonth);
	
	
//	@Query(value = "update customer_payment_detail set amount_paid ="+amtPaid+",grand_total ="+grandTotal+" where cust_pay_id ='"payRefId+"'", nativeQuery = true)
//	void updatePaymentByPaymentRefId(String payRefId,Double amtPaid, Double grandTotal);
}
