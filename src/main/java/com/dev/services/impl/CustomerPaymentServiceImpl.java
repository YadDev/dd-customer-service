package com.dev.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.entities.CustomerPayment;
import com.dev.entities.PaymentUpdateRequest;
import com.dev.exception.DevanshException;
import com.dev.repository.CustomerPaymentRepository;
import com.dev.services.CustomerPaymentService;

@Service
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

	@Autowired
	private CustomerPaymentRepository custPayRepo;

	
	@Autowired
	PasswordEncoder encoder;
	
	Collection<CustomerPayment> cust= null;
	
	@Override
	public List<CustomerPayment> getAllCustomerPaymentDetail() {
		List<CustomerPayment> customers = new ArrayList<>();
		System.out.println("In Customer Payment Service Impl: getAllCustomerPaymentDetail");
		customers = custPayRepo.findAll();
		return customers;
	}


	@Override
	public Collection<CustomerPayment> getCustomerPaymentDetail(String id) {
		Collection<CustomerPayment> cust = custPayRepo.findPaymentByCustomerId(id);
		CustomerPayment  customer = getCustomerPaymentDetailWithQuery(id,"Jun-2022");
		System.out.println("In Customer Payment Service Impl: getCustomerPay	mentDetail"+customer.getCustPaymentId());
		return cust;
	}


	@Override
	public CustomerPayment updateCustomerPayment(PaymentUpdateRequest payUpdateReq) throws DevanshException {
		Optional<CustomerPayment> existingPayDetail = custPayRepo.findById(payUpdateReq.getCustPayRef());
		if(existingPayDetail.isPresent()) {
			CustomerPayment retriveCustPayDetail = existingPayDetail.get();
			Double grandTotal = retriveCustPayDetail.getNetTotal() - payUpdateReq.getAmountPaid();
			retriveCustPayDetail.setGrandTotal(grandTotal);
			custPayRepo.updatePaymentByPaymentRefId(payUpdateReq.getCustPayRef(), payUpdateReq.getAmountPaid(), grandTotal);
			return retriveCustPayDetail;
		}
		else {
			throw new DevanshException("insufficient Data","Payment reference is not found !");
		}
		
	}


	@Override
	public CustomerPayment createCustomerPayment(CustomerPayment customerPayment) {
		CustomerPayment customer = null;		
		customerPayment.setNetTotal(customerPayment.getPreviousBalance()+customerPayment.getCurrentMonthBalance());
		System.out.println("In Customer Payment Service Impl: createCustomerPayment");
		customer = custPayRepo.save(customerPayment);
		return customer;
	}


	@Override
	public CustomerPayment getCustomerPaymentDetailWithQuery(String query, String forMonth) {
		Optional<CustomerPayment> cust = custPayRepo.findPaymentByCustomerIdAndQuery(query, forMonth);
		System.out.println("In Customer Payment Service Impl: getCustomerPaymentDetailWithQuery");
		return cust.get();
	}


	@Override
	public List<CustomerPayment> uploadCustomerPayment(List<CustomerPayment> customerPayment) {
		return custPayRepo.saveAll(customerPayment);
	}
}
