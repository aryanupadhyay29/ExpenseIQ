package com.example.ExpenseIQ.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;  // Unique reference for payment

    private Double amount;

    private String upiId; // e.g., "user@upi"

    private LocalDateTime paymentDate;

    private String status; // SUCCESS, PENDING, FAILED

    // Who paid
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    // Who received
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    // Payment may relate to a group settlement
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
