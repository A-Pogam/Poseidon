package com.poseidoncapitalsolution.trading.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Actuator-related beans.
 * This configuration class is responsible for creating and managing
 * the bean for HTTP trace repository which is used for tracing HTTP requests.
 */

@Configuration
public class ActuatorConfig {

	/**
	 * Creates an in-memory HTTP exchange repository bean.
	 * This repository stores HTTP request and response exchanges in memory.
	 *
	 * @return an instance of {@link InMemoryHttpExchangeRepository}
	 */


	@Bean
	HttpExchangeRepository httpTraceRepository() {
		return new InMemoryHttpExchangeRepository();
	}
}