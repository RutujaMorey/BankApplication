package com.microc.customeraccountservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.microc.customeraccountservice.dto.NewLoanDTO;
import com.microc.customeraccountservice.dto.NewLoanResponse;
import com.microc.customeraccountservice.entity.NewLoanEntity;
import com.microc.customeraccountservice.entity.NewUserEntity;
import com.microc.customeraccountservice.repositories.CustomerAccountRepository;
import com.microc.customeraccountservice.services.CustomerAccountService;

public class CustomerAccountServicesTests {

	@InjectMocks
	private CustomerAccountService customerAccountService;

	@Mock
	private CustomerAccountRepository customerAccountRepository;

	@LocalServerPort
	int randomServerPort;

	private Gson gson;

	@MockBean
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
		gson = new Gson();

//		userDetailsCollections = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/java/resources/UserDetails.json")),
//				new TypeToken<ArrayList<UserDetailsCollections>>() {
//				}.getType());
//		userCredentialsCollection = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/java/resources/UserCredentials.json")),
//				new TypeToken<ArrayList<UserCredentialsCollections>>() {
//				}.getType());

	}

	

	@Test
	public void testApplyLoanFailure() throws Exception {
		NewLoanDTO newLoanDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanDTO.class);
		NewLoanEntity newLoanEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanEntity.class);
		newLoanDTO.setInterestPercent(null);
		NewLoanResponse newLoanResponse = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanSuccessResponse.json")),
				NewLoanResponse.class);
		List<String> validationErrors = new ArrayList<>();
		validationErrors.add("Request is not valid");
		newLoanResponse.setValidationErrors(validationErrors);

		verify(customerAccountRepository, times(0)).save(newLoanEntity);
	}

	@Test
	public void testGetLoanDetails() throws Exception {
		NewLoanEntity newLoanEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanEntity.class);
		when(customerAccountRepository.findByCustomerId("BNYAT1")).thenReturn(newLoanEntity);

		NewLoanDTO actual = customerAccountService.getLoanDetails("BNYAT1");
		assertNotNull(actual);

		verify(customerAccountRepository, times(1)).findByCustomerId("BNYAT1");
	}
	@Test
	public void testGetLoanDetailsFailure() throws Exception {
		when(customerAccountRepository.findByCustomerId("BNYAT1")).thenReturn(null);

		NewLoanDTO actual = customerAccountService.getLoanDetails("BNYAT1");
		assertNull(actual);

		verify(customerAccountRepository, times(1)).findByCustomerId("BNYAT1");
	}


}
