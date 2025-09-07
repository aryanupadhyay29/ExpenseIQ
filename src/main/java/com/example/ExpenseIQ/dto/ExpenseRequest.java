package com.example.ExpenseIQ.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseRequest {
    private Long userId;        // ID of the user who added expense
    private Long groupId;       // (Optional) if expense belongs to a group
    private String title;       // Expense title (e.g., "Dinner", "Uber Ride")
    private Double amount;      // Expense amount
    private String category;    // Food, Travel, Shopping, etc.
    private LocalDateTime date; // Expense date (default: now)
}