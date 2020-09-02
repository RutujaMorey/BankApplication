package com.microc.customeraccountservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "CustomerLoan")
public class NewLoanEntity {

	public NewLoanEntity() {
		super();
	}

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public NewLoanEntity(String customerId, Double loanAmount, Double interestPercent, Date maturityDate) {
		super();
		this.customerId = customerId;
		this.loanAmount = loanAmount;
		this.interestPercent = interestPercent;
		this.maturityDate = maturityDate;
	}

	public Integer getId() {
		return id;
	}

	public void setTransactionId(Integer id) {
		this.id = id;
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

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	@Column
	private String customerId;

	@Column
	private Double loanAmount;

	@Column
	private Double interestPercent;

	@Column
	private Date dateApplied;

	@Column
	private Date maturityDate;
}
