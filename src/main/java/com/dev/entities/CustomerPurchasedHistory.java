package com.dev.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dev.constants.CommonConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "customer_purchased_history")
@JsonSerialize
public class CustomerPurchasedHistory {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="id")
    private String purchaseId;
	
	@Column(name="product_code")
	private String prodCode;
	
	@Column(name="quantity")
	private Double quantity;
	
	@Column(name="unitPrice")
	private Double unitPrice;
	
	@Column(name="date",columnDefinition = "text default to_char(now(),'"+ CommonConstants.DATE_FORMAT +"')")
	private String purchasedDate;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "custNumber")
    private Customer customer;
	
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
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
	public String getPurchasedDate() {
		return purchasedDate;
	}
	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
