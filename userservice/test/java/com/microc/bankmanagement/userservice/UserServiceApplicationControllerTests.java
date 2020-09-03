package com.microc.bankmanagement.userservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.microc.bankmanagement.userservice.configuration.JWTAuthenticationEntryPoint;
import com.microc.bankmanagement.userservice.controllers.AuthenticateUserController;
import com.microc.bankmanagement.userservice.dto.AuthenticatedUserResponse;
import com.microc.bankmanagement.userservice.dto.NewUserDTO;
import com.microc.bankmanagement.userservice.dto.NewUserResponse;
import com.microc.bankmanagement.userservice.entity.AuthenticatedUsers;
import com.microc.bankmanagement.userservice.entity.NewUserEntity;
import com.microc.bankmanagement.userservice.repositories.AuthenticatedUserRepository;
import com.microc.bankmanagement.userservice.services.CustomerAuthenticationService;
import com.microc.bankmanagement.userservice.services.TokenGeneratorUtil;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserServiceApplication.class)
@WebMvcTest(AuthenticateUserController.class)
public class UserServiceApplicationControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerAuthenticationService customerAuthenticationService;

	private Gson gson;

	@MockBean
	private UserDetails userDetails;

	@MockBean
	private JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint;

	@MockBean
	private TokenGeneratorUtil tokenGeneratorUtil;

	@MockBean
	private AuthenticatedUserRepository authenticatedUserRepository;

	@Before
	public void setUp() throws FileNotFoundException {
		gson = new Gson();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testLoginUserUnauthorized() throws Exception {
		AuthenticatedUserResponse authenticatedUserResponse = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/UnauthorizedUserResponse.json")),
				AuthenticatedUserResponse.class);
		when(customerAuthenticationService.loadUserByUsername("johnsmi23")).thenReturn(userDetails);

		MvcResult mvcResult = mockMvc
				.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
						.content(ObjectMapperConversion.jsonToString(authenticatedUserResponse)))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();
		assertEquals(mvcResult.getResponse().getContentAsString(),
				"{\"authenticationToken\":null,\"message\":\"No user found with this username\"}");
	}

	@Test
	public void testGetDetailsByCustomerId() throws Exception {
		NewUserEntity newUserEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/GetCustomerDetailsResponse.json")),
				NewUserEntity.class);
		when(customerAuthenticationService.getCustomerDetails("BNYAT1")).thenReturn(newUserEntity);
		MvcResult mvcResult = mockMvc.perform(get("/user/id/BNYAT1")).andExpect(status().isOk()).andDo(print())
				.andReturn();
		assertNotNull(mvcResult.getResponse());
		verify(customerAuthenticationService, times(1)).getCustomerDetails("BNYAT1");
	}

	@Test
	public void testGetDetailsByCustomerIdFail() throws Exception {
		when(customerAuthenticationService.getCustomerDetails("BNYAT1")).thenReturn(null);
		MvcResult mvcResult = mockMvc.perform(get("/user/id/BNYAT1")).andExpect(status().isNoContent()).andDo(print())
				.andReturn();
		assertNotNull(mvcResult.getResponse());
		verify(customerAuthenticationService, times(1)).getCustomerDetails("BNYAT1");
	}

	@Test
	public void testSaveNewUserFailure() throws Exception {
		NewUserDTO newUserDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserDTO.class);
		NewUserResponse newUserResponse = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/ApplyLoanSuccessResponse.json")),
				NewUserResponse.class);
		List<String> validationErrors = new ArrayList<>();
		validationErrors.add("Invalid Request");
		newUserResponse.setValidationErrors(validationErrors);
		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\"}";

		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
		when(authenticatedUserRepository.findByUsername("johnsmi23")).thenReturn(authenticatedUser);

		when(customerAuthenticationService.saveNewUser(newUserDTO)).thenReturn(newUserResponse);
		MvcResult mvcResult = mockMvc
				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
						.content(ObjectMapperConversion.jsonToString(newUserDTO)))
				.andExpect(status().isBadRequest()).andDo(print()).andReturn();
		assertEquals(mvcResult.getResponse().getContentAsString(), "[\"Invalid Request\"]");
	}

	@Test
	public void testUpdateUserDetailsFailure() throws Exception {
		NewUserEntity newUserEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserEntity.class);
		when(customerAuthenticationService.updateCustomerAccountDetails(newUserEntity)).thenReturn(null);
		MvcResult mvcResult = mockMvc
				.perform(patch("/user/updatedetails").contentType(MediaType.APPLICATION_JSON)
						.content(ObjectMapperConversion.jsonToString(newUserEntity)))
				.andExpect(status().isBadRequest()).andDo(print()).andReturn();
		assertEquals(mvcResult.getResponse().getContentAsString(), "");
	}

}
