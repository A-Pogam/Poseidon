package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.exception.PasswordNotFoundException;
import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + "not found.");
        }

        if (user.get().getPassword() == null || user.get().getPassword().isEmpty()) {
            throw new PasswordNotFoundException("Wrong password");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.get().getPassword())
                .roles(user.get().getRole())
                .build();

        return userDetails;
    }
}
