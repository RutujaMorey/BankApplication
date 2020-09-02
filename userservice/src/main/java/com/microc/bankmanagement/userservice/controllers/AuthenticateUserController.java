package com.microc.bankmanagement.userservice.controllers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.microc.bankmanagement.userservice.dto.AuthenticatedUserResponse;
import com.microc.bankmanagement.userservice.dto.NewUserDTO;
import com.microc.bankmanagement.userservice.dto.NewUserResponse;
import com.microc.bankmanagement.userservice.dto.UserDTO;
import com.microc.bankmanagement.userservice.entity.NewUserEntity;
import com.microc.bankmanagement.userservice.services.CustomerAuthenticationService;
import com.microc.bankmanagement.userservice.services.TokenGeneratorUtil;

import io.jsonwebtoken.lang.Collections;

@RequestMapping("/user")
@RestController
public class AuthenticateUserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomerAuthenticationService customerAuthenticationService;

	@Autowired
	private TokenGeneratorUtil tokenGeneratorUtil;

	@Autowired
	private Gson gson;

	@PostMapping(value = "/login")
	public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws Exception {
		boolean isUsernameNotFound = false;
		String token = null;
		final UserDetails userDetails = customerAuthenticationService.loadUserByUsername(userDTO.getUserName());
		if (Objects.isNull(userDetails)) {
			isUsernameNotFound = true;
		} else {
			try {
				authenticate(userDTO.getUserName(), userDTO.getPassword());
			} catch (Exception e) {
				return new ResponseEntity<>(
						new AuthenticatedUserResponse(null,
								"Incorrect credentials entered. Please enter valid crdentials"),
						HttpStatus.UNAUTHORIZED);
			}
			token = tokenGeneratorUtil.generateToken(userDetails);
		}
		return Objects.nonNull(token)
				? new ResponseEntity<>(new AuthenticatedUserResponse(token, "Logged in successfully"), HttpStatus.OK)
				: new ResponseEntity<>(new AuthenticatedUserResponse(null, "No user found with this username"),
						HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/signup")
	public ResponseEntity<?> saveUser(@RequestBody NewUserDTO newUserDTO) throws Exception {
		NewUserResponse newUserResponse = customerAuthenticationService.saveNewUser(newUserDTO);
		return Objects.nonNull(newUserResponse)
				? (Collections.isEmpty(newUserResponse.getValidationErrors())
						? new ResponseEntity<>(newUserResponse.getSuccessResponse(), HttpStatus.OK)
						: new ResponseEntity<>(newUserResponse.getValidationErrors(), HttpStatus.BAD_REQUEST))
				: new ResponseEntity<>(newUserResponse, HttpStatus.BAD_REQUEST);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@GetMapping(value = "/id/{customerId}")
	public ResponseEntity<NewUserEntity> getDetailsByCustomerId(@PathVariable String customerId) throws Exception {
		NewUserEntity userDetails = customerAuthenticationService.getCustomerDetails(customerId);
		return Optional.ofNullable(userDetails).map(response -> new ResponseEntity<>(userDetails, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@PatchMapping(value = "/updatedetails")
	public ResponseEntity<?> updateCustomerAccountDetails(@RequestBody NewUserEntity newUserEntity) throws Exception {
		NewUserResponse newUserResponse = customerAuthenticationService.updateCustomerAccountDetails(newUserEntity);
		return Objects.nonNull(newUserResponse)
				? Objects.nonNull(newUserResponse.getSuccessResponse())
						? new ResponseEntity<>(newUserResponse.getSuccessResponse(), HttpStatus.OK)
						: new ResponseEntity<>(newUserResponse.getValidationErrors(), HttpStatus.BAD_REQUEST)
				: new ResponseEntity<>(newUserResponse, HttpStatus.BAD_REQUEST);
	}

}