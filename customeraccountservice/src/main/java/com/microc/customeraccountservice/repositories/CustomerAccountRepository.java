package com.microc.customeraccountservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microc.customeraccountservice.entity.NewLoanEntity;

@Repository
public interface CustomerAccountRepository extends CrudRepository<NewLoanEntity, String>{

	public NewLoanEntity findByCustomerId(String customerId);
}
