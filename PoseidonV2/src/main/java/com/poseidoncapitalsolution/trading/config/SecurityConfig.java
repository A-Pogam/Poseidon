package com.poseidoncapitalsolution.trading.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(new CustomRequestMatcher("/home.html")).permitAll()
                .requestMatchers(new CustomRequestMatcher("/bid/**")).authenticated()
                .requestMatchers(new CustomRequestMatcher("/curvePoint/**")).authenticated()
                .requestMatchers(new CustomRequestMatcher("/rating/**")).authenticated()
                .requestMatchers(new CustomRequestMatcher("/rule/**")).authenticated()
                .requestMatchers(new CustomRequestMatcher("/trade/**")).authenticated()
                .requestMatchers(new CustomRequestMatcher("/user/**")).authenticated()
                .anyRequest().authenticated() // Any other request requires authentication
                .and()
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll() // Custom login page
                        .defaultSuccessUrl("/home.html", true)
                )
                .logout(logout -> logout
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403.html")
                );
    }

    // Custom RequestMatcher to match URLs
    private static class CustomRequestMatcher implements RequestMatcher {
        private final String path;

        public CustomRequestMatcher(String path) {
            this.path = path;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String requestURI = request.getRequestURI();
            return requestURI.matches(path) || requestURI.startsWith(path + "/");
        }
    }
}
