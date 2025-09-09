package com.example.ExpenseIQ.repository;

import com.example.ExpenseIQ.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);

    List<Expense> findByGroupId(Long groupId);

    List<Expense> findByUserIdAndGroupId(Long userId, Long groupId);
}