package com.microc.bankmanagement.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.microc.bankmanagement.userservice.entity.NewUserEntity;

public interface NewUserRepository extends JpaRepository<NewUserEntity, String> {

	NewUserEntity findByUsername(String username);

	NewUserEntity findByCustomerId(String customerId);
}
