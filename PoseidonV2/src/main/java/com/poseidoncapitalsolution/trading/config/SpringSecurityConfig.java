package com.poseidoncapitalsolution.trading.config;

import com.poseidoncapitalsolution.trading.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(new CustomRequestMatcher("/home.html")).permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true))
                .logout(logout -> logout
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403.html")
                );
    }

    protected UserDetailsServiceImpl userDetailsService() {
        return userDetailsService;
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
