package com.microc.customeraccountservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.microc.customeraccountservice.controllers.CustomerAccountController;
import com.microc.customeraccountservice.dto.NewLoanDTO;
import com.microc.customeraccountservice.dto.NewLoanResponse;
import com.microc.customeraccountservice.services.CustomerAccountService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CustomeraccountserviceApplication.class)
@WebMvcTest(CustomerAccountController.class)
public class CustomerAccountServiceControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerAccountService customerAccountService;

	private Gson gson;

	@Before
	public void setUp() throws FileNotFoundException {
		gson = new Gson();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGetLoanDetails() throws Exception {
		NewLoanDTO newLoanDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/GetLoanDetails.json")), NewLoanDTO.class);
		when(customerAccountService.getLoanDetails("BNYAT1")).thenReturn(newLoanDTO);
		MvcResult mvcResult = mockMvc.perform(get("/loan/viewdetails/BNYAT1")).andExpect(status().isOk()).andDo(print())
				.andReturn();
		assertNotNull(mvcResult.getResponse());
		verify(customerAccountService, times(1)).getLoanDetails("BNYAT1");
	}

	@Test
	public void testGetLoanDetailsWhenNoContent() throws Exception {
		when(customerAccountService.getLoanDetails("BNYAT1")).thenReturn(null);
		MvcResult mvcResult = mockMvc.perform(get("/loan/viewdetails/BNYAT1")).andExpect(status().isNoContent())
				.andDo(print()).andReturn();
		assertNotNull(mvcResult.getResponse());
		verify(customerAccountService, times(1)).getLoanDetails("BNYAT1");
	}

	
	
	@Test
	public void testApplyLoanFailure() throws Exception {
		NewLoanDTO newLoanDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanDTO.class);
		newLoanDTO.setInterestPercent(null);
		NewLoanResponse newLoanResponse =  gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanSuccessResponse.json")), NewLoanResponse.class);
		List<String> validationErrors= new ArrayList<>();
		validationErrors.add("Request is not valid");
		newLoanResponse.setValidationErrors(validationErrors);
		when(customerAccountService.applyLoan(newLoanDTO)).thenReturn(newLoanResponse);
		MvcResult mvcResult = mockMvc
				.perform(post("/loan/apply").contentType(MediaType.APPLICATION_JSON)
						.content(ObjectMapperConversion.jsonToString(newLoanDTO)))
				.andExpect(status().isBadRequest()).andDo(print()).andReturn();
		assertEquals(mvcResult.getResponse().getContentAsString(), "");

	}

}
