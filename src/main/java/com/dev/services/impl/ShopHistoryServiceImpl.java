package com.dev.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.constants.CommonConstants;
import com.dev.entities.CustomerPayment;
import com.dev.entities.CustomerPurchasedHistory;
import com.dev.repository.ShopHistoryRepository;
import com.dev.services.ShopHistoryService;

@Service
public class ShopHistoryServiceImpl implements ShopHistoryService {

	@Autowired
	private ShopHistoryRepository shopHistoryRepo;

	@Override
	public List<CustomerPurchasedHistory> getAllShoppingHistory() {
		List<CustomerPurchasedHistory> shopHistory = new ArrayList<>();
		System.out.println("In Shop History Service Impl: getAllShoppingHistory");
		shopHistory = shopHistoryRepo.findAll();
		return shopHistory;
	}

	@Override
	public Map<String, Object> getCustomerShoppingHistory(String id) {
		Collection<CustomerPurchasedHistory> cust = shopHistoryRepo.findShopHistoryByCustomerId(id);
		System.out.println("In Shop History Service Impl: getCustomerShoppingHistory");
		
		Map<String,Object> shopMap = new HashMap<>();
		Map<String, List<CustomerPurchasedHistory>> totalPurchase = cust.stream().filter(code->
			{	
//				String purchaseDate = code.getPurchasedDate();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
//				  String date = "16/08/2016";
//
//				  //convert String to LocalDate
//				  LocalDate localDate = LocalDate.parse(date, formatter);
				return true;
			})
			.collect(Collectors.groupingBy(CustomerPurchasedHistory::getProdCode));
		List<Object> test = new ArrayList<>();
		Double netTotal= 0.0;
		Double restBalance = -1000.0;
		for(String key : totalPurchase.keySet()) {
			Map<String, Object> a = new HashMap<>();
			Double totalQauntity = 0.0;
			Double totalAmount = 0.0;
			Double uniPrice = 0.0;
			List<CustomerPurchasedHistory> shopData = totalPurchase.get(key);
			for (CustomerPurchasedHistory object : shopData) {
				totalQauntity+=object.getQuantity();
				uniPrice += object.getUnitPrice();
				a.put("id",object.getPurchaseId());
			}
			uniPrice = uniPrice/shopData.size();
			totalAmount = totalQauntity*uniPrice;
			System.out.println("Quantity "+totalQauntity+"\t Price "+totalAmount);
			netTotal = netTotal+ totalAmount;
			a.put("name", key);
			a.put("quantity", totalQauntity);
			a.put("price", uniPrice);
			a.put("itemTotal",totalAmount);
			test.add(a);
	    }
		
		//test.add(cust);
		shopMap.put("items",test);
		shopMap.put("netTotal", netTotal);
		shopMap.put("restBalance", restBalance);
		shopMap.put("grandTotal", netTotal+restBalance);
		System.out.println("total "+netTotal+restBalance);
		return shopMap;
	}

	@Override
	public CustomerPurchasedHistory createCustomerShoppingHistory(CustomerPurchasedHistory customerPurchasedHistory) {
		System.out.println("In Shop History Service Impl: createCustomerShoppingHistory");
		CustomerPurchasedHistory shopHistory = null;	
		String shopDate = LocalDate.now().format(DateTimeFormatter
			    .ofLocalizedDate(FormatStyle.MEDIUM));
		
		customerPurchasedHistory.setPurchasedDate(shopDate);
		shopHistory = shopHistoryRepo.save(customerPurchasedHistory);
		return shopHistory;
	}

	@Override
	public List<CustomerPayment> uploadCustomerShoppingHistory(List<CustomerPayment> customerPayment) {
		// TODO Auto-generated method stub
		return null;
	}

	private CustomerPurchasedHistory calculatePurchase(List<CustomerPurchasedHistory> purchaseTotal) {
		CustomerPurchasedHistory temp = new CustomerPurchasedHistory();
		Double sum = purchaseTotal.stream().mapToDouble(CustomerPurchasedHistory::getQuantity).sum();
		Double totalQty = purchaseTotal.stream().mapToDouble(mapper->mapper.getQuantity()*mapper.getUnitPrice()).sum();		
		System.out.println();
		return temp;
		
	}
	
}