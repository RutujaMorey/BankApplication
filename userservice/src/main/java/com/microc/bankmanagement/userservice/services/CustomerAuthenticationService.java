package com.microc.bankmanagement.userservice.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microc.bankmanagement.userservice.dto.NewUserDTO;
import com.microc.bankmanagement.userservice.dto.NewUserResponse;
import com.microc.bankmanagement.userservice.entity.AuthenticatedUsers;
import com.microc.bankmanagement.userservice.entity.NewUserEntity;
import com.microc.bankmanagement.userservice.repositories.AuthenticatedUserRepository;
import com.microc.bankmanagement.userservice.repositories.NewUserRepository;

import io.jsonwebtoken.lang.Collections;
import io.micrometer.core.instrument.util.StringUtils;

@Service
public class CustomerAuthenticationService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticatedUserRepository authenticatedUserRepository;

	@Autowired
	private NewUserRepository newUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthenticatedUsers authenticatedUser = authenticatedUserRepository.findByUsername(username);
		if (Objects.isNull(authenticatedUser)) {
			return null;
		}
		return new org.springframework.security.core.userdetails.User(authenticatedUser.getUsername(),
				authenticatedUser.getPassword(), new ArrayList<>());

	}

	public NewUserResponse saveNewUser(NewUserDTO newUserDTO) {
		List<String> validationList = validateMandatoryFields(newUserDTO);
		List<String> validationErrors = new ArrayList<>();
		NewUserResponse newUserResponse = new NewUserResponse();
		AuthenticatedUsers authenticatedUser = authenticatedUserRepository.findByUsername(newUserDTO.getUserName());
		if (!Objects.isNull(authenticatedUser)) {
			Map<String, String> successResponse = new HashMap<>();
			successResponse.put("message", "Username " + newUserDTO.getUserName()
					+ " is already registered. Sign In if already registered or choose different username");
			newUserResponse.setSuccessResponse(successResponse);
			return newUserResponse;
		} else {
			if (Collections.isEmpty(validationList)) {
				if (!validateEmailAddress(newUserDTO.getEmailAddress())) {
					String message = newUserDTO.getEmailAddress() + " is invalid email address";
					validationErrors.add(message);
				}
				if (!validateContactNumber(newUserDTO.getContactNumber())) {
					String message = newUserDTO.getContactNumber() + " is invalid contact number";
					validationErrors.add(message);
				}
				if (!validatePAN(newUserDTO.getPan())) {
					String message = newUserDTO.getPan() + " is invalid PAN number";
					validationErrors.add(message);
				}
				if (!validateDateOfBirth(newUserDTO.getDob())) {
					String message = newUserDTO.getDob() + " is invalid DOB. Please enter in format 'dd/MM/YYYY'";
					validationErrors.add(message);
				}
				if (!validatePasswordCriteria(newUserDTO.getPassword())) {
					String message = "Password dosen't meet the criteria. Password should have atleast 8 characters and at most 20 characters.Should contain at least one digit, one upper case alphabet, one lower case alphabet, one special character";
					validationErrors.add(message);
				}
				if (!validateUsername(newUserDTO.getUserName())) {
					String message = "Username should have atleast 8 characters";
					validationErrors.add(message);
				}

			} else {
				String message = validationList.toString() + " is mandatory";
				validationErrors.add(message);
			}
			if (Collections.isEmpty(validationErrors)) {
				NewUserEntity newUserEntity = new NewUserEntity(newUserDTO.getUserName(),
						passwordEncoder.encode(newUserDTO.getPassword()), newUserDTO.getName(), newUserDTO.getAddress(),
						newUserDTO.getState(), newUserDTO.getCountry(), newUserDTO.getEmailAddress(),
						newUserDTO.getPan(), newUserDTO.getContactNumber(), newUserDTO.getDob(),
						newUserDTO.getAccountType());
				newUserEntity.setDeposit(2000.00);
				newUserEntity.setAccountNumber(setAccountNumber(newUserDTO));
				NewUserEntity savedUser = newUserRepository.save(newUserEntity);
				Map<String, String> successResponse = new HashMap<>();
				String customerId = setCustomerId(newUserDTO) + savedUser.getId();
				savedUser.setCustomerId(setCustomerId(newUserDTO) + savedUser.getId());
				newUserRepository.save(newUserEntity);
				successResponse.put("message", "Your Customer Id: " + customerId);
				newUserResponse.setSuccessResponse(successResponse);
			} else {
				newUserResponse.setValidationErrors(validationErrors);

			}
			return newUserResponse;
		}

	}

	private boolean validateEmailAddress(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		return pat.matcher(email).matches();
	}

	private List<String> validateMandatoryFields(NewUserDTO newUserDTO) {
		List<String> validationList = new ArrayList<>();
		checkIndividualFields(newUserDTO.getName(), "name", validationList);
		checkIndividualFields(newUserDTO.getAccountType(), "accountType", validationList);
		checkIndividualFields(newUserDTO.getAddress(), "address", validationList);
		checkIndividualFields(newUserDTO.getContactNumber(), "contactNumber", validationList);
		checkIndividualFields(newUserDTO.getCountry(), "country", validationList);
		checkIndividualFields(newUserDTO.getState(), "state", validationList);
		checkIndividualFields(newUserDTO.getEmailAddress(), "emailAddress", validationList);
		checkIndividualFields(newUserDTO.getPassword(), "password", validationList);
		checkIndividualFields(newUserDTO.getUserName(), "userName", validationList);
		checkIndividualFields(newUserDTO.getPan(), "pan", validationList);
		return validationList;

	}

	private void checkIndividualFields(String propertyValue, String keyname, List<String> validationList) {
		if (StringUtils.isEmpty(propertyValue)) {
			validationList.add(keyname);
		}
	}

	private boolean validateDateOfBirth(String dob) {
		if (dob == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dob);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean validatePAN(String pan) {
		Pattern p = Pattern.compile("[A-Z]{3}[0-9]{4}[A-Z]{1}");
		Matcher m = p.matcher(pan);
		return (m.find() && m.group().equals(pan));
	}

	private boolean validateContactNumber(String mobileNumber) {
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(mobileNumber);
		return (m.find() && m.group().equals(mobileNumber));
	}

	private String setAccountNumber(NewUserDTO newUserDTO) {
		Random random = new Random();
		int randomNumber = random.nextInt(900) + 100;
		return newUserDTO.getUserName().toUpperCase().substring(0, 3) + randomNumber
				+ newUserDTO.getState().toUpperCase().substring(0, 2)
				+ newUserDTO.getPan().toUpperCase().substring(3, 7);
	}

	private String setCustomerId(NewUserDTO newUserDTO) {
		return "BNY" + newUserDTO.getState().toUpperCase().substring(0, 2);
	}

	private boolean validatePasswordCriteria(String password) {
		Pattern p = Pattern
				.compile("^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	private boolean validateUsername(String username) {
		return username.length() > 7;
	}

	public NewUserEntity getCustomerDetails(String customerId) {
		return newUserRepository.findByCustomerId(customerId);
	}

	public NewUserResponse updateCustomerAccountDetails(NewUserEntity newUserEntity) {
		List<String> validationErrors = new ArrayList<>();
		NewUserResponse newUserResponse = new NewUserResponse();
		Map<String, String> successResponse = new HashMap<>();
		NewUserEntity fetchedUserEntity = getCustomerDetails(newUserEntity.getCustomerId());
		if (Objects.nonNull(fetchedUserEntity)) {
			if (checkIfInvalidFieldsUpdated(newUserEntity.getAccountNumber(), fetchedUserEntity.getAccountNumber())) {
				NewUserEntity finalNewUserEntity = newUserEntity;
				finalNewUserEntity.setPassword(passwordEncoder.encode(fetchedUserEntity.getPassword()));
				newUserRepository.delete(fetchedUserEntity);
				newUserRepository.save(finalNewUserEntity);
				successResponse.put("message", "Account details updated successfully");
				newUserResponse.setSuccessResponse(successResponse);
			} else {
				validationErrors.add("Attempt to update a non editable field. Acocunt Number cannot be updated.");
				newUserResponse.setValidationErrors(validationErrors);
			}
		} else {
			validationErrors.add("Attempt to update non editable field. Customer Id not found in database.");
			newUserResponse.setValidationErrors(validationErrors);
		}
		return newUserResponse;

	}

	private boolean checkIfInvalidFieldsUpdated(String accountNumber, String persistedAccountNumber) {
		return accountNumber.equals(persistedAccountNumber);

	}
}
