package com.dailyCode.expenseTrackerApi.security;

import com.dailyCode.expenseTrackerApi.entity.User;
import com.dailyCode.expenseTrackerApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User newUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with the email: "+ email));
        return new org.springframework.security.core.userdetails.User(newUser.getEmail(), newUser.getPassword(), new ArrayList<>());
    }
}
