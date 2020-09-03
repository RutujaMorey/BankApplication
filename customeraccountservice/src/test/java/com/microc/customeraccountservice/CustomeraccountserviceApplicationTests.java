package com.microc.customeraccountservice;

//@SpringBootTest
class CustomeraccountserviceApplicationTests {

//	@Test
//	void contextLoads() {
//	}

//	@Test
//	public void testApplyLoan() throws Exception {
//		NewLoanDTO newLoanDTO = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanDTO.class);
//		NewLoanResponse newLoanResponse = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/ApplyLoanSuccessResponse.json")),
//				NewLoanResponse.class);
//		Map<String, String> successResponse = new HashMap<>();
//		successResponse.put("message", "Loan details saved successfully");
//		newLoanResponse.setSuccessResponse(successResponse);
//		when(customerAccountService.applyLoan(newLoanDTO)).thenReturn(newLoanResponse);
//		MvcResult mvcResult = mockMvc
//				.perform(post("/loan/apply").contentType(MediaType.APPLICATION_JSON)
//						.content(ObjectMapperConversion.jsonToString(newLoanDTO)))
//				.andExpect(status().isCreated()).andDo(print()).andReturn();
//		assertNotNull(mvcResult.getResponse());
//		verify(customerAccountService, times(1)).applyLoan(newLoanDTO);
//	}
//	@Test
//	public void testApplyLoan() throws Exception {
//		NewLoanDTO newLoanDTO = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanDTO.class);
//		NewLoanEntity newLoanEntity = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/ApplyLoanRequest.json")), NewLoanEntity.class);
//		NewUserEntity newUserEntity = gson.fromJson(
//				new BufferedReader(new FileReader("src/test/resources/GetCustomerDetailsResponse.json")),
//				NewUserEntity.class);
//		when(customerAccountRepository.save(newLoanEntity)).thenReturn(newLoanEntity);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//		when(restTemplate.exchange("http://localhost:8081/user/id/BNYAT1", HttpMethod.GET, entity, NewUserEntity.class)
//				.getBody()).thenReturn(newUserEntity);
////		Mockito.when(restTemplate.getForEntity(new URI("http://localhost:8081/user/id/BNYAT1"), NewUserEntity.class))
////				.thenReturn(new ResponseEntity(newLoanEntity, HttpStatus.OK));
////		
////		final String baseUrl = "http://localhost:"+ randomServerPort +"/user/id/BNYAT1";
////		URI uri = new URI(baseUrl);
////
////		HttpHeaders headers = new HttpHeaders();
////
////		ResponseEntity<NewLoanEntity> result = this.restTemplate.getForEntity(uri, NewLoanEntity.class);
////
////		NewLoanResponse newLoanResponse = customerAccountService.applyLoan(newLoanDTO);
//		when(customerAccountService.checkCustomerIdIsValid("BNYAT1")).thenReturn(newUserEntity);
////		assertNotNull(newLoanResponse);
//
//		verify(customerAccountRepository, times(1)).save(newLoanEntity);
//	}

}
