package com.dailyCode.expenseTrackerApi.service;

import com.dailyCode.expenseTrackerApi.entity.User;
import com.dailyCode.expenseTrackerApi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUser(Long id);

    User updateUser(User user, Long id);

    void deleteUser(Long id);
}
