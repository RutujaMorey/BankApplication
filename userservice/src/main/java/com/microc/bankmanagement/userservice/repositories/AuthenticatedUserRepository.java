package com.microc.bankmanagement.userservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microc.bankmanagement.userservice.entity.AuthenticatedUsers;

@Repository
public interface AuthenticatedUserRepository extends CrudRepository<AuthenticatedUsers, Integer>{
	
	AuthenticatedUsers findByUsername(String username);
	

}
