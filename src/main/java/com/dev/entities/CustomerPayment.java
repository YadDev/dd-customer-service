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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="customer_payment_detail")
@JsonSerialize
public class CustomerPayment {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="cust_pay_id")
	private String custPaymentId;
	
	@Column(name="previous_balance")
	private Double previousBalance;
		
	@Column(name="currant_month_balance")
	private Double currentMonthBalance;
	
	@Column(name="payment_for_month")
	private String paymentForMonth;
	
	@Column(name="net_total")
	private Double netTotal;
	
	@Column(name="amount_paid")
	private Double amountPaid;
	
	@Column(name="grand_total")
	private Double grandTotal;
	
	@Column(name="status",columnDefinition = "varchar(25) default 'Pending'")
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "custNumber")
    private Customer customer;

	public String getCustPaymentId() {
		return custPaymentId;
	}

	public void setCustPaymentId(String custPaymentId) {
		this.custPaymentId = custPaymentId;
	}

	public Double getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(Double previousBalance) {
		this.previousBalance = previousBalance;
	}

	public Double getCurrentMonthBalance() {
		return currentMonthBalance;
	}

	public void setCurrentMonthBalance(Double currentMonthBalance) {
		this.currentMonthBalance = currentMonthBalance;
	}

	public String getPaymentForMonth() {
		return paymentForMonth;
	}

	public void setPaymentForMonth(String paymentForMonth) {
		this.paymentForMonth = paymentForMonth;
	}

	public Double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
	
}
