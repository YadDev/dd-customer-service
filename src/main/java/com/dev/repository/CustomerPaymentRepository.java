package com.dev.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.entities.CustomerPayment;

@Repository
public interface CustomerPaymentRepository extends JpaRepository<CustomerPayment,String> {

	@Query(value = "SELECT * FROM customer_payment_detail cp WHERE cp.custNumber = ?1", 
			  nativeQuery = true)
	Collection<CustomerPayment> findPaymentByCustomerId(String custNumber);
	
	@Query(value = "SELECT * FROM customer_payment_detail cp WHERE cp.custNumber = :custNumber and cp.payment_for_month=:forMonth", 
			  nativeQuery = true)
	Optional<CustomerPayment> findPaymentByCustomerIdAndQuery(@Param("custNumber") String payRefId, @Param("forMonth") String forMonth);
	
	
	@Query(value = "update customer_payment_detail set amount_paid =:amtPaid,grand_total =:grandTotal  where cust_pay_id =:payRefId", nativeQuery = true)
	void updatePaymentByPaymentRefId(@Param("payRefId") String payRefId, @Param("amtPaid") Double amtPaid, @Param("grandTotal") Double grandTotal);
}
