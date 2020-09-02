package com.microc.bankmanagement.userservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MvcResult;

import com.microc.bankmanagement.userservice.dto.AuthenticatedUserResponse;
import com.microc.bankmanagement.userservice.dto.NewUserDTO;
import com.microc.bankmanagement.userservice.dto.NewUserResponse;
import com.microc.bankmanagement.userservice.entity.AuthenticatedUsers;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceApplicationTests {

	@Test
	public void contextLoads() {
	}
	
//	@Test
//	public void testLoginUserSuccess() throws Exception {
//		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\"}";
//
//		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
//		when(authenticatedUserRepository.findByUsername("johnsmi23")).thenReturn(authenticatedUser);
//
//		AuthenticatedUserResponse authenticatedUserResponse = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/AuthorizedUserResponse.json")),
//				AuthenticatedUserResponse.class);
//
//		when(customerAuthenticationService.loadUserByUsername("johnsmi23")).thenReturn(gson.fromJson(
//				"{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\",\"authorities\":[],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true}",
//				UserDetails.class));
//
//		MvcResult mvcResult = mockMvc
//				.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
//						.content(ObjectMapperConversion.jsonToString(authenticatedUserResponse)))
//				.andExpect(status().isOk()).andDo(print()).andReturn();
//		assertEquals(mvcResult.getResponse().getContentAsString(),
//				"{\"authenticationToken\":null,\"message\":\"Logged in successfully\"}");
//	}
	
//	@Test
//	public void testSaveNewUser() throws Exception {
//		NewUserDTO newUserDTO = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserDTO.class);
//		NewUserResponse newUserResponse = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/ApplyLoanSuccessResponse.json")),
//				NewUserResponse.class);
//		Map<String, String> successResponse = new HashMap<>();
//		successResponse.put("message", "Your Customer Id: BNYAT1");
//		newUserResponse.setSuccessResponse(successResponse);
//		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\"}";
//
//		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
//		when(authenticatedUserRepository.findByUsername("johnsmi23")).thenReturn(authenticatedUser);
//
//		when(customerAuthenticationService.saveNewUser(newUserDTO)).thenReturn(newUserResponse);
//		MvcResult mvcResult = mockMvc
//				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
//						.content(ObjectMapperConversion.jsonToString(newUserDTO)))
//				.andExpect(status().isCreated()).andDo(print()).andReturn();
//		assertNotNull(mvcResult.getResponse());
//		verify(customerAuthenticationService, times(1)).saveNewUser(newUserDTO);
//	}


}

