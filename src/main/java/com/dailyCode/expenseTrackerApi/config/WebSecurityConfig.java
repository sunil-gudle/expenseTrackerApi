package com.dailyCode.expenseTrackerApi.config;

import com.dailyCode.expenseTrackerApi.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration().getAuthenticationManager();
    }

    @Bean
    public AuthenticationConfiguration authenticationConfiguration() {
        return new AuthenticationConfiguration();
    }

//    @Value("${user1.username}")
//    private String user1Username;
//
//    @Value("${user1.password}")
//    private String user1Password;
//
//    @Value("${user1.authorities}")
//    private String user1Authorities;
//
//    @Value("${user2.username}")
//    private String user2Username;
//
//    @Value("${user2.password}")
//    private String user2Password;
//
//    @Value("${user2.authorities}")
//    private String user2Authorities;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf(csrf -> csrf.disable()) // disable the csrf protection
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/login", "/register").permitAll() // permit these endpoints
//                                .anyRequest().authenticated() // Secure all other endpoints
//                        )
//                .httpBasic(httpBasic -> {}); // Enable HTTP Basic Authentication
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        PasswordEncoder encoder = passwordEncoder();
//
//        UserDetails user1 = User.withUsername(user1Username)
//                .password(encoder.encode(user1Password))
//                .authorities(user1Authorities)
//                .build();
//
//        UserDetails user2 = User.withUsername(user2Username)
//                .password(encoder.encode(user2Password))
//                .authorities(user2Authorities)
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
}

