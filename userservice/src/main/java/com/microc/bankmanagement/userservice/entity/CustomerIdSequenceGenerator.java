package com.microc.bankmanagement.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "customerNoSeq", initialValue = 70000000, allocationSize = 100)
public class CustomerIdSequenceGenerator {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Id
	long customerId;
}