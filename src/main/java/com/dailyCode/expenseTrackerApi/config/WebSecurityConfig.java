package com.dailyCode.expenseTrackerApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable()) // disable the csrf protection
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/register").permitAll() // permit these endpoints
                                .anyRequest().authenticated() // Secure all other endpoints
                        )
                .httpBasic(httpBasic -> {}); // Enable HTTP Basic Authentication
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        PasswordEncoder encoder = passwordEncoder();

        UserDetails user1 = User.withUsername("sunil")
                .password(encoder.encode("sunil123"))
                .authorities("admin")
                .build();

        UserDetails user2 = User.withUsername("Sunilg")
                .password(encoder.encode("sunilg123"))
                .authorities("user")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
