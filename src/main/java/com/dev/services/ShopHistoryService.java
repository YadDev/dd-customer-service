package com.dev.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dev.entities.CustomerPayment;
import com.dev.entities.CustomerPurchasedHistory;

@Service
public interface ShopHistoryService {

	public List<CustomerPurchasedHistory> getAllShoppingHistory();
	public Map<String, Object> getCustomerShoppingHistory(String id);
	
	public CustomerPurchasedHistory createCustomerShoppingHistory(CustomerPurchasedHistory customerPurchasedHistory);
	
	public List<CustomerPayment> uploadCustomerShoppingHistory(List<CustomerPayment> customerPayment);

}
