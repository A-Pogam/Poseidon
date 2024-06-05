package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.User;

import java.util.List;

public interface IUserService {
    User saveUser(User user);
    List<User> getAllUsers();



}