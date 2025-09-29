package com.example.ExpenseIQ.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseRequest {
    private Long userId;
    private Long groupId;
    private String title;
    private Double amount;
    private String category;
    private LocalDateTime date;
    private String description ;


}