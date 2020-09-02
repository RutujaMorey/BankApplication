package com.microc.customeraccountservice.dto;

import java.util.Date;

public class NewLoanDTO {

	public NewLoanDTO() {
		super();
	}

	private String customerId;

	private Double loanAmount;

	private Double interestPercent;

	private Date maturityDate;

	private Double toBePaidOnMaturity;
	private Date dateApplied;

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public Double getToBePaidOnMaturity() {
		return toBePaidOnMaturity;
	}

	public void setToBePaidOnMaturity(Double toBePaidOnMaturity) {
		this.toBePaidOnMaturity = toBePaidOnMaturity;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getInterestPercent() {
		return interestPercent;
	}

	public void setInterestPercent(Double interestPercent) {
		this.interestPercent = interestPercent;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public NewLoanDTO(String customerId, Double loanAmount, Double interestPercent, Date dateApplied,
			Date maturityDate) {
		super();
		this.customerId = customerId;
		this.loanAmount = loanAmount;
		this.interestPercent = interestPercent;
		this.maturityDate = maturityDate;
	}

}
