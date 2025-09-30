package com.example.ExpenseIQ.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long payerId;
    private Long receiverId;
    private Long groupId;
    private Double amount;
    private String method;
    private String upiId;
}