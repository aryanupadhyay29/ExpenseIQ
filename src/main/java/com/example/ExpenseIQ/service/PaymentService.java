package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.model.Group;
import com.example.ExpenseIQ.model.Payment;
import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.GroupRepository;
import com.example.ExpenseIQ.repository.PaymentRepository;
import com.example.ExpenseIQ.repository.UserRepository;
import com.example.ExpenseIQ.utils.UPIPaymentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Settle group expenses (dummy logic)
    public String settleExpenses(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // TODO: implement proper splitwise logic later
        return "Expenses settled for group: " + group.getName();
    }

    // ✅ Generate UPI payment link
    public String generateUpiLink(Double amount, String upiId) {
        return UPIPaymentUtil.generateUpiUrl(amount, upiId);
    }

    // ✅ Save payment transaction
    public Payment recordPayment(Long payerId, Long receiverId, Double amount, Long groupId) {
        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new RuntimeException("Payer not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Payment payment = new Payment();
        payment.setPayer(payer);
        payment.setReceiver(receiver);
        payment.setAmount(amount);
        payment.setGroup(group);
        payment.setUpiId(receiver.getEmail() + "@upi"); // dummy
        payment.setStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}