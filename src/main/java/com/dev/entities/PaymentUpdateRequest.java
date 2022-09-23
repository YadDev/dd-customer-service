package com.dev.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class PaymentUpdateRequest {

	private String custPayRef;
	private Double amountPaid;
	public String getCustPayRef() {
		return custPayRef;
	}
	public void setCustPayRef(String custPayRef) {
		this.custPayRef = custPayRef;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	
}
