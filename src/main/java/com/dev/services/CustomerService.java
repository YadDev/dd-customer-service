package com.dev.services;

import java.util.List;

import com.dev.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dev.entities.Customer;

@Service
public interface CustomerService {

	public List<Customer> getAllCustomer();
	public Customer getCustomer(String id);
	public Customer createCustomer(Customer customer);
	public Customer removeCustomer(String id);

	public ResponseEntity<?> customerPasswordAuthentication(User user);

}
