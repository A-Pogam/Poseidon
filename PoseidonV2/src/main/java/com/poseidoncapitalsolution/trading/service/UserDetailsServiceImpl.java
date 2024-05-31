package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.repository.contracts.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final ApplicationContext applicationContext;



}
