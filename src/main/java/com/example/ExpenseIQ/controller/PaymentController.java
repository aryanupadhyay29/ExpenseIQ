package com.example.ExpenseIQ.controller;

import com.example.ExpenseIQ.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/settle/{groupId}")
    public String settleGroupExpenses(@PathVariable Long groupId) {
        return paymentService.settleExpenses(groupId);
    }

    @GetMapping("/upi/{amount}/{upiId}")
    public String generateUpiLink(@PathVariable Double amount, @PathVariable String upiId) {
        return paymentService.generateUpiLink(amount, upiId);
    }
}