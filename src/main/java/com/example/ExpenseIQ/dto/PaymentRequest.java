package com.example.ExpenseIQ.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long payerId;       // Who is making the payment
    private Long receiverId;    // Who is receiving the payment
    private Long groupId;       // If payment is within a group
    private Double amount;      // Payment amount
    private String method;      // UPI, Card, NetBanking, Wallet
    private String upiId;       // If method is UPI
}