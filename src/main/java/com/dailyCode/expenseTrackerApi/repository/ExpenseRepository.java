package com.dailyCode.expenseTrackerApi.repository;

import com.dailyCode.expenseTrackerApi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    // it will execute the query: Select * from tbl_expenses where category =?;
    Page<Expense> findByCategory(String category, Pageable page);

    // It will execute the query: select * from tbl_expenses where name LIKE '%keyword%'
    Page<Expense> findByNameContaining(String keyword, Pageable page);

    // It will execute the query: select * from tbl_expenses where date BETWEEN 'startDate' and 'endDate';
    Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);
}
