package com.microc.bankmanagement.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "accountNumberSeq", initialValue = 70000000, allocationSize = 100)
public class AccountNumberSequenceGenerator {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Id
	long accountNumber;
}