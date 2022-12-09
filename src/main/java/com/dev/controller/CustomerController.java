package com.dev.controller;

import com.dev.entities.Customer;
import com.dev.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired 
	CustomerService customerService;

	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomer(){
		System.out.println("In Get Customer Controller");
		return ResponseEntity.ok(customerService.getAllCustomer());
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable String id){
		System.out.println("Customer id "+id);
		return ResponseEntity.ok(customerService.getCustomer(id));
	}
	
	@PostMapping("/customer")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
		System.out.println("Customer email ");
		return ResponseEntity.ok(customerService.createCustomer(customer));
	}
	
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String id){
		return ResponseEntity.ok(customerService.removeCustomer(id));
	}
	
}
