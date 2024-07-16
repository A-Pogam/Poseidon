package com.poseidoncapitalsolution.trading.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poseidoncapitalsolution.trading.listener.EndpointListener;

/**
 * Web MVC configuration for the Poseidon Capital Solution trading application.
 * <p>
 * This class configures the interceptors used in the application by
 * registering a specific interceptor {@link EndpointListener}.
 * </p>
 *
 * <p>
 * Annotations used:
 * </p>
 * <ul>
 * <li>{@code @Configuration} : Indicates that the class defines beans and configuration for the Spring application.</li>
 * </ul>
 *
 * <p>
 * Implements the {@link WebMvcConfigurer} interface to provide custom Web MVC configurations.
 * </p>
 *
 * @see EndpointListener
 */
@Configuration
public class WebMvcConfigImpl implements WebMvcConfigurer {

	/**
	 * Adds interceptors to the Web MVC configuration.
	 * <p>
	 * This interceptor is used to listen to and manage specific endpoints of the application.
	 * </p>
	 *
	 * @param registry the {@link InterceptorRegistry} object used to register the interceptors.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new EndpointListener());
	}
}
