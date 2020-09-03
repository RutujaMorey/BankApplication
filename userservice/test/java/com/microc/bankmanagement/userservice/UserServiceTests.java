package com.microc.bankmanagement.userservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.microc.bankmanagement.userservice.dto.NewUserDTO;
import com.microc.bankmanagement.userservice.dto.NewUserResponse;
import com.microc.bankmanagement.userservice.entity.AuthenticatedUsers;
import com.microc.bankmanagement.userservice.entity.NewUserEntity;
import com.microc.bankmanagement.userservice.repositories.AuthenticatedUserRepository;
import com.microc.bankmanagement.userservice.repositories.NewUserRepository;
import com.microc.bankmanagement.userservice.services.CustomerAuthenticationService;

public class UserServiceTests {
	@InjectMocks
	private CustomerAuthenticationService customerAuthenticationService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticatedUserRepository authenticatedUserRepository;

	@Mock
	private NewUserRepository newUserRepository;

	private Gson gson;

	@MockBean
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
		gson = new Gson();

	}

	@Test
	public void testLoadUserByUsername() throws Exception {
		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\"}";

		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
		when(authenticatedUserRepository.findByUsername("BNYAT1")).thenReturn(authenticatedUser);
		UserDetails actual = customerAuthenticationService.loadUserByUsername("BNYAT1");
		assertNotNull(actual);
		verify(authenticatedUserRepository, times(1)).findByUsername("BNYAT1");
	}

	@Test
	public void testLoadUserByUsernameFail() throws Exception {
		when(authenticatedUserRepository.findByUsername("BNYAT1")).thenReturn(null);
		UserDetails actual = customerAuthenticationService.loadUserByUsername("BNYAT1");
		assertNull(actual);
	}

	@Test
	public void testSaveNewUserAlreadyRegistered() throws Exception {
		NewUserDTO newUserDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserInvalidRequest.json")), NewUserDTO.class);
		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johni23\"}";

		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
		when(authenticatedUserRepository.findByUsername("johni23")).thenReturn(authenticatedUser);
		NewUserResponse actual = customerAuthenticationService.saveNewUser(newUserDTO);
		assertNotNull(actual);
		verify(authenticatedUserRepository, times(1)).findByUsername("johni23");
	}

	@Test
	public void testSaveNewUserMandatoryFieldsMissing() throws Exception {
		NewUserDTO newUserDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserDTO.class);
		newUserDTO.setAddress(null);
		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\"}";

		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
		when(authenticatedUserRepository.findByUsername("johnsmi23")).thenReturn(null);
		NewUserResponse actual = customerAuthenticationService.saveNewUser(newUserDTO);
		assertNotNull(actual);
		verify(authenticatedUserRepository, times(1)).findByUsername("johnsmi23");
	}

	@Test
	public void testSaveNewUserInvalidRequest() throws Exception {
		NewUserDTO newUserDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserInvalidRequest.json")), NewUserDTO.class);
		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johni23\"}";

		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
		when(authenticatedUserRepository.findByUsername("johni23")).thenReturn(null);
		NewUserResponse actual = customerAuthenticationService.saveNewUser(newUserDTO);
		assertNotNull(actual);
		verify(authenticatedUserRepository, times(1)).findByUsername("johni23");
	}

	@Test
	public void testSaveNewUserValidRequest() throws Exception {
		NewUserDTO newUserDTO = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserDTO.class);
		String s = "{\"password\":\"$2a$10$YnUP8lUQ7yQqUmfei0b9uOdvR5BdwPND1WQgRQYa9OxeTrQNB3hm\",\"username\":\"johnsmi23\"}";

		AuthenticatedUsers authenticatedUser = gson.fromJson(s, AuthenticatedUsers.class);
		when(authenticatedUserRepository.findByUsername("johnsmi23")).thenReturn(null);
		NewUserEntity newUserEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserEntity.class);
		NewUserEntity savedUser = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserEntity.class);

		when(newUserRepository.save(Mockito.any(NewUserEntity.class))).thenReturn(savedUser);
		NewUserResponse actual = customerAuthenticationService.saveNewUser(newUserDTO);
		assertNotNull(actual);
		verify(authenticatedUserRepository, times(1)).findByUsername("johnsmi23");
	}

	@Test
	public void updateCustomerDetailsNotSuccess() throws Exception {

		NewUserEntity savedEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserEntity.class);
		NewUserEntity deletedEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserEntity.class);

		when(newUserRepository.save(Mockito.any(NewUserEntity.class))).thenReturn(savedEntity);

		NewUserResponse actual = customerAuthenticationService.updateCustomerAccountDetails(savedEntity);
		assertNotNull(actual);
	}

	@Test
	public void testGetCustomerDetails() throws Exception {

		NewUserEntity fetchedUserEntity = gson.fromJson(
				new BufferedReader(new FileReader("src/test/resources/NewUserRequest.json")), NewUserEntity.class);
		when(newUserRepository.findByCustomerId("BNYAT1")).thenReturn(fetchedUserEntity);

		NewUserEntity actual = customerAuthenticationService.getCustomerDetails("BNYAT1");
		assertNotNull(actual);
	}
}
