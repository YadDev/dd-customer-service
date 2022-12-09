package com.dev.services;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.entities.CustomerPayment;
import com.dev.exception.DevanshException;
import com.dev.model.PaymentUpdateRequest;

@Service
public interface CustomerPaymentService {

	public List<CustomerPayment> getAllCustomerPaymentDetail();
	public Collection<CustomerPayment> getCustomerPaymentDetail(String id);
	public CustomerPayment getCustomerPaymentDetailWithQuery(String query,String forMonth);
	public CustomerPayment updateCustomerPayment(PaymentUpdateRequest payUpdateReq) throws DevanshException;
	public CustomerPayment createCustomerPayment(CustomerPayment customerPayment);
	
	public List<CustomerPayment> uploadCustomerPayment(List<CustomerPayment> customerPayment);



}
