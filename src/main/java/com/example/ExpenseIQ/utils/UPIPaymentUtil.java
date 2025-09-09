package com.example.ExpenseIQ.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UPIPaymentUtil {

    /**
     * Generates a UPI payment URL for a given amount and UPI ID.
     * This can be used to deep-link into a UPI app on a mobile device.
     * @param amount The amount to be paid.
     * @param upiId The UPI ID of the receiver.
     * @return A formatted UPI URL string.
     */
    public static String generateUpiUrl(Double amount, String upiId) {
        return "upi://pay?pa=" + upiId + "&pn=ExpenseIQ&am=" + amount + "&cu=INR";
    }

    /**
     * Placeholder for a real-world UPI payment initiation.
     * In a production environment, this method would call a third-party UPI gateway API
     * to process the transaction, handle authentication, and manage transaction states.
     *
     * @param payerUpiId The UPI ID of the payer.
     * @param receiverUpiId The UPI ID of the receiver.
     * @param amount The payment amount.
     * @return A dummy transaction status string.
     */
    public String initiatePayment(String payerUpiId, String receiverUpiId, Double amount) {
        String transactionId = UUID.randomUUID().toString();

        // TODO: Replace this with a real API call to a UPI gateway
        return "Transaction ID: " + transactionId +
                " | Payer: " + payerUpiId +
                " | Receiver: " + receiverUpiId +
                " | Amount: ₹" + amount +
                " | Status: SUCCESS (dummy)";
    }
}