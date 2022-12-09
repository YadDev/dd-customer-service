package com.dev.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer_bill_mapping")
public class BillSequenceTracking {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custNumber")
    private Customer customer;

	public long getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(long billNumber) {
		this.billNumber = billNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	
	

}
