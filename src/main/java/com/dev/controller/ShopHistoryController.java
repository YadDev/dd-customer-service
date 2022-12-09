package com.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.CustomerPurchasedHistory;
import com.dev.services.ShopHistoryService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ShopHistoryController {
	
	@Autowired 
	ShopHistoryService shopHistory;
	
	
	@GetMapping("/customer/shopHistory/{custId}")
	public ResponseEntity<?> getCustPurHistory(@PathVariable String custId) {
		System.out.println("In Get Customer Controller ::getCustPurHistory"+custId);
		return ResponseEntity.ok(shopHistory.getCustomerShoppingHistory(custId));
	}
	
	@PostMapping("/customer/shopHistory")
	public ResponseEntity<?> createCustomerShopHistory(@RequestBody CustomerPurchasedHistory history) {
		System.out.println("In Get Customer Controller ::createCustomerShopHistory"+history.getProdCode());
		return ResponseEntity.ok(shopHistory.createCustomerShoppingHistory(history));
	}
}
