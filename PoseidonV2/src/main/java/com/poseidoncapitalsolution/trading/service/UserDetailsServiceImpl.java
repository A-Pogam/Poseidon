package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.exception.PasswordNotFoundException;
import com.poseidoncapitalsolution.trading.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, ApplicationContext applicationContext) {
        this.userRepository = userRepository;
        this.applicationContext = applicationContext;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);

        Optional<com.poseidoncapitalsolution.trading.model.User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username " + " does not match any user.");
        }
        // Check if the password is null or empty
        if (user.get().getPassword() == null || user.get().getPassword().isEmpty()) {
            throw new PasswordNotFoundException("Wrong password");
        }

        String encodedPassword = passwordEncoder.encode(user.get().getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), encodedPassword, true, true,
                true, true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    }

