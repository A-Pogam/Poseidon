package com.poseidoncapitalsolution.trading.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.service.contracts.IUserService;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceIT {

	@Autowired
	private IUserService iUserService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@BeforeAll
	public void fillUserTable() {
		for (int i = 1; i <= 3; i++) {
			iUserService.saveUser(new User(null, "Username" + i, bCryptPasswordEncoder.encode("Azerty59!"+i), "Fullname" + i, "ADMIN"));
		}
	}
	
	@AfterAll
	public void resetUserTable() {
		iUserService.resetUserTestTable();
	}
	
	@Test
	public void getUsers_returnUsers() {
		List<User> users = iUserService.getAllUsers();
		
		assertThat(users).isNotEmpty();
		assertThat(users.getFirst().getId()).isEqualTo(1);
		assertThat(users.getFirst().getUsername()).isEqualTo("Username1");
		assertThat(users.getFirst().getFullname()).isEqualTo("Fullname1");
		assertThat(users.getFirst().getRole()).isEqualTo("ADMIN");
	}

	@Test
	public void getUserById_returnUser() {
		User user = iUserService.findById(1).get();
		
		assertThat(user.getId()).isEqualTo(1);
		assertThat(user.getUsername()).isEqualTo("Username1");
		assertThat(user.getFullname()).isEqualTo("Fullname1");
		assertThat(user.getRole()).isEqualTo("ADMIN");
	}
	
	@Test
	public void getUserByUsername_returnUser() {
		User user = iUserService.findByUsername("Username1").get();
		
		assertThat(user.getId()).isEqualTo(1);
		assertThat(user.getUsername()).isEqualTo("Username1");
		assertThat(user.getFullname()).isEqualTo("Fullname1");
		assertThat(user.getRole()).isEqualTo("ADMIN");
	}

	@Test
	public void addOrUpdateUser_returnUser() {
		User newUser = new User(null, "NewUsername", "NewAzertu59!", "NewFullname", "ADMIN");
		
		User userAdded = iUserService.saveUser(newUser);
		
		assertThat(userAdded.getUsername()).isEqualTo(newUser.getUsername());
		assertThat(userAdded.getFullname()).isEqualTo(newUser.getFullname());
		assertThat(userAdded.getRole()).isEqualTo(newUser.getRole());
	}
	
	@Test
	public void deleteUser_deleteUser() {
		User userToDelete = iUserService.findById(2).get();
		
		iUserService.deleteById(2);
		
		assertThat(!iUserService.getAllUsers().contains(userToDelete));
	}
}
