package com.microc.bankmanagement.userservice;

public class UserServiceApplicationTests {

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
