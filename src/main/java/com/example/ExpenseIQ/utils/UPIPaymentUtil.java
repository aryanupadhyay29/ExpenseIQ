package com.example.ExpenseIQ.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UPIPaymentUtil {

    public static String generateUpiUrl(Double amount, String upiId) {
        return "upi://pay?pa=" + upiId + "&pn=ExpenseIQ&am=" + amount + "&cu=INR";
    }

    public String initiatePayment(String payerUpiId, String receiverUpiId, Double amount) {
        // In real-world: call UPI gateway API
        String transactionId = UUID.randomUUID().toString();

        return "Transaction ID: " + transactionId +
                " | Payer: " + payerUpiId +
                " | Receiver: " + receiverUpiId +
                " | Amount: ₹" + amount +
                " | Status: SUCCESS";
    }
}
