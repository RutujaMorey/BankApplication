package com.microc.customeraccountservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.microc.customeraccountservice.dto.NewLoanDTO;
import com.microc.customeraccountservice.dto.NewLoanResponse;
import com.microc.customeraccountservice.entity.NewLoanEntity;
import com.microc.customeraccountservice.entity.NewUserEntity;
import com.microc.customeraccountservice.repositories.CustomerAccountRepository;

@Service
public class CustomerAccountService {

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public NewLoanResponse applyLoan(NewLoanDTO newLoanDTO) {
		NewLoanResponse newLoanResponse = new NewLoanResponse();
		Map<String, String> successResponse = new HashMap<>();
		if (StringUtils.isEmpty(newLoanDTO.getCustomerId()) || StringUtils.isEmpty(newLoanDTO.getInterestPercent())
				|| StringUtils.isEmpty(newLoanDTO.getLoanAmount())
				|| StringUtils.isEmpty(newLoanDTO.getMaturityDate())) {
			List<String> validationErrors = new ArrayList<>();
			validationErrors.add("Request is not valid");
			newLoanResponse.setValidationErrors(validationErrors);
		} else {

			if (Objects.nonNull(checkCustomerIdIsValid(newLoanDTO.getCustomerId()))) {
				NewLoanEntity newLoanEntity = new NewLoanEntity(newLoanDTO.getCustomerId(), newLoanDTO.getLoanAmount(),
						newLoanDTO.getInterestPercent(), newLoanDTO.getMaturityDate());
				Date date = new Date();
				newLoanEntity.setDateApplied(date);

				customerAccountRepository.save(newLoanEntity);

				successResponse.put("message", "Loan details saved successfully");
				newLoanResponse.setSuccessResponse(successResponse);
			} else {
				successResponse.put("message", "Loan cannot be applied. Customer Id not found.");
				newLoanResponse.setSuccessResponse(successResponse);
			}
		}
		return newLoanResponse;

	}

	public NewLoanDTO getLoanDetails(String customerId) {
		NewLoanDTO newLoanDTO = new NewLoanDTO();
		if (!StringUtils.isEmpty(customerId)) {
			NewLoanEntity newLoanEntity = customerAccountRepository.findByCustomerId(customerId);
			if (Objects.nonNull(newLoanEntity)) {
				newLoanDTO.setCustomerId(newLoanEntity.getCustomerId());
				newLoanDTO.setDateApplied(newLoanEntity.getDateApplied());
				newLoanDTO.setInterestPercent(newLoanEntity.getInterestPercent());
				newLoanDTO.setLoanAmount(newLoanEntity.getLoanAmount());
				newLoanDTO.setMaturityDate(newLoanEntity.getMaturityDate());
				Double paidOnMaturity = newLoanEntity.getLoanAmount() + (newLoanEntity.getInterestPercent() * newLoanEntity.getLoanAmount()) / 100;
				newLoanDTO.setToBePaidOnMaturity(paidOnMaturity);
			} else {
				return null;
			}
		}
		return newLoanDTO;
	}

	public NewUserEntity checkCustomerIdIsValid(String customerId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate
				.exchange("http://localhost:8081/user/id/" + customerId, HttpMethod.GET, entity, NewUserEntity.class)
				.getBody();
	}

}
