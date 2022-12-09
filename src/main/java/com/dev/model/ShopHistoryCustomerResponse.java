package com.dev.model;

public class ShopHistoryCustomerResponse {
	private String productName;
	private Double quantity;
	private Double unitPrice;
	private Double itemTotal;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}
	
	
}
