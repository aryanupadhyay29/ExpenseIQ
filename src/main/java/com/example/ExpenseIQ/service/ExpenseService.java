package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.model.Expense;
import com.example.ExpenseIQ.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new NoSuchElementException("Expense not found with ID: " + id);
        }
        expenseRepository.deleteById(id);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}