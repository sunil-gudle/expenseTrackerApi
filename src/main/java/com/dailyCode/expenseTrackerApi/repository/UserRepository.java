package com.dailyCode.expenseTrackerApi.repository;

import com.dailyCode.expenseTrackerApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

}
