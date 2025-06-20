package com.dailyCode.expenseTrackerApi.service;

import com.dailyCode.expenseTrackerApi.entity.User;
import com.dailyCode.expenseTrackerApi.entity.UserModel;
import com.dailyCode.expenseTrackerApi.exceptions.ItemAlreadyExistException;
import com.dailyCode.expenseTrackerApi.exceptions.ResourceNotFoundException;
import com.dailyCode.expenseTrackerApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User createUser(UserModel user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExistException("User is already registered with this email: "+ user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for the id: "+ id));
    }

    @Override
    public User updateUser(User user, Long id) throws ResourceNotFoundException {
        User eUser = readUser(id);
        eUser.setName(user.getName() !=null ? user.getName() : eUser.getName());
        eUser.setEmail(user.getEmail() != null ? user.getEmail() : eUser.getEmail());
        eUser.setPassword(user.getPassword() !=null ? passwordEncoder.encode(eUser.getPassword()) : eUser.getPassword());
        eUser.setAge(user.getAge() != null ? user.getAge() : eUser.getAge());
        return userRepository.save(eUser);
    }

    @Override
    public void deleteUser(Long id) {
        User eUser = readUser(id);
        userRepository.delete(eUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email "+ email));
    }
}
