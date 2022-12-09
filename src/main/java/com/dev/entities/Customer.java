package com.dev.entities;

import com.dev.constants.CommonConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Customer")
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Customer {

	@Id
	@Column(name="custNumber")
	private String custNumber;

	@Column(name="custName")
	private String customerName;

	@Column(name="custEmail")
	private String customerEmail;

	@Column(name="custMobile")
	private String customerMobile;

	@Column(name="custPriceMode")
	private String custPriceMode;

	@Column(name="custStatus", columnDefinition = "boolean default false")
	private Boolean customerStatus;

	@Column(name="custAddress")
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(name = "customer_addresses",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "address_id")
	)
	private Set<Address> customerAddres;
	
	
	

	@Column(name="createdAt",columnDefinition = "text default to_char(now(),'"+ CommonConstants.DATE_TIME_FORMAT +"')")
	private String createdAt;

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustPriceMode() {
		return custPriceMode;
	}

	public void setCustPriceMode(String custPriceMode) {
		this.custPriceMode = custPriceMode;
	}

	public Boolean getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(Boolean customerStatus) {
		this.customerStatus = customerStatus;
	}

	public Set<Address> getCustomerAddres() {
		return customerAddres;
	}

	public void setCustomerAddres(Set<Address> customerAddres) {
		this.customerAddres = customerAddres;
	}
	

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}