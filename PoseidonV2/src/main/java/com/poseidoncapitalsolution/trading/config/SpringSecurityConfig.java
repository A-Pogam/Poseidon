package com.poseidoncapitalsolution.trading.config;

import com.poseidoncapitalsolution.trading.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Configuration class for Spring Security.
 *
 * <p>
 * This class configures the security settings for the application using Spring Security.
 * It defines beans for the user details service, password encoder, and authentication provider.
 * Additionally, it configures HTTP security settings including URL patterns, access rules,
 * form login, logout behavior, and authentication providers.
 * </p>
 *
 * <p>
 * The class is annotated with {@link Configuration} to indicate that it provides Spring configuration.
 * {@link EnableWebSecurity} enables Spring Security's web security support.
 * </p>
 *
 * @author Alice Pogam
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Provides the user details service implementation.
     *
     * @return an instance of {@link UserDetailsServiceImpl}
     */
    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Provides the BCrypt password encoder.
     *
     * @return an instance of {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the DAO authentication provider with the user details service and password encoder.
     *
     * @param userDetailsService the user details service implementation
     * @param passwordEncoder the BCrypt password encoder
     * @return an instance of {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider daoAuthProvider(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /**
     * Provides a builder for MVC request matchers.
     *
     * @param handlerMappingIntrospector the handler mapping introspector
     * @return a builder for {@link MvcRequestMatcher}
     */
    @Bean
    MvcRequestMatcher.Builder requestMatcher(HandlerMappingIntrospector handlerMappingIntrospector) {
        return new MvcRequestMatcher.Builder(handlerMappingIntrospector);
    }

    /**
     * Configures the security filter chain with HTTP security settings.
     *
     * @param httpSecurity the HTTP security object to configure
     * @param daoAuthProvider the DAO authentication provider
     * @param mvcRequestMatcher the MVC request matcher builder
     * @return a {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, DaoAuthenticationProvider daoAuthProvider, MvcRequestMatcher.Builder mvcRequestMatcher) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(mvcRequestMatcher.pattern("/css/**")).permitAll()
                        .requestMatchers(mvcRequestMatcher.pattern("/user/**")).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/bid/list")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                )
                .authenticationProvider(daoAuthProvider)
                .build();
    }
}
