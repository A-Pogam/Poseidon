package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    List<User> getAllUsers();

    Optional<User> findByUsername(String username);







}