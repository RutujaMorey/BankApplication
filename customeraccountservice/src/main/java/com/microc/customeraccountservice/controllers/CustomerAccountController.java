package com.microc.customeraccountservice.controllers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microc.customeraccountservice.dto.NewLoanDTO;
import com.microc.customeraccountservice.dto.NewLoanResponse;
import com.microc.customeraccountservice.services.CustomerAccountService;

@RequestMapping("/loan")
@RestController
public class CustomerAccountController {

	@Autowired
	private CustomerAccountService customerAccountService;

	@GetMapping(value = "/viewdetails/{customerId}")
	public ResponseEntity<NewLoanDTO> getLoanDetails(@PathVariable String customerId) throws Exception {
		NewLoanDTO newLoanDTO = customerAccountService.getLoanDetails(customerId);
		return Optional.ofNullable(newLoanDTO).map(response -> new ResponseEntity<>(newLoanDTO, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@PostMapping(value = "/apply")
	public ResponseEntity<?> applyLoan(@RequestBody NewLoanDTO newLoanDTO) throws Exception {
		NewLoanResponse newLoanResponse = customerAccountService.applyLoan(newLoanDTO);
		return Objects.nonNull(newLoanResponse) ? CollectionUtils.isEmpty(newLoanResponse.getValidationErrors())
				? new ResponseEntity<>(newLoanResponse.getSuccessResponse(), HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
