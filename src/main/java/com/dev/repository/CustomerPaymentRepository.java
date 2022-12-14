package com.dev.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.entities.CustomerPayment;

@Repository
public interface CustomerPaymentRepository extends JpaRepository<CustomerPayment,String> {

	String amtPaid = null;


	@Query(value = "SELECT * FROM customer_payment_detail cp WHERE cp.custNumber = ?1", 
			  nativeQuery = true)
	Collection<CustomerPayment> findPaymentByCustomerId(String custNumber);
	
	@Query(value = "SELECT * FROM customer_payment_detail cp WHERE cp.custNumber =?1 and cp.payment_for_month=?2", 
			  nativeQuery = true)
	Optional<CustomerPayment> findPaymentByCustomerIdAndQuery(String payRefId, String forMonth);
	
	
//	@Query(value = "update customer_payment_detail set amount_paid ="+amtPaid+",grand_total ="+grandTotal+" where cust_pay_id ='"payRefId+"'", nativeQuery = true)
//	void updatePaymentByPaymentRefId(String payRefId,Double amtPaid, Double grandTotal);
}
