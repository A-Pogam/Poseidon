package com.poseidoncapitalsolution.trading.service.contracts;

import java.util.List;
import java.util.Optional;

import com.poseidoncapitalsolution.trading.model.User;

public interface IUserService {
	User saveUser(User user);

	List<User> getAllUsers();

	Optional<User> findByUsername(String username);

	Optional<User> findById(Integer id);

	void deleteById(Integer user);

	void resetUserTestTable();
}