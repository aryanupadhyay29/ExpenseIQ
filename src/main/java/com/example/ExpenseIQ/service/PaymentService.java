package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.model.Expense;
import com.example.ExpenseIQ.model.Group;
import com.example.ExpenseIQ.model.Payment;
import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.ExpenseRepository;
import com.example.ExpenseIQ.repository.GroupRepository;
import com.example.ExpenseIQ.repository.PaymentRepository;
import com.example.ExpenseIQ.repository.UserRepository;
import com.example.ExpenseIQ.utils.UPIPaymentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public String settleExpenses(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found with ID: " + groupId));

        List<Expense> expenses = group.getExpenses();
        if (expenses == null || expenses.isEmpty()) {
            return "No expenses to settle for group: " + group.getName();
        }

        Map<Long, Double> userBalances = new HashMap<>();

        // 1. Calculate net balances for each user
        for (User member : group.getMembers()) {
            userBalances.put(member.getId(), 0.0);
        }

        for (Expense expense : expenses) {
            Double amount = expense.getAmount();
            Long paidByUserId = expense.getUser().getId();
            int numberOfMembers = group.getMembers().size();
            Double sharePerMember = amount / numberOfMembers;

            // Update payer's balance (paid more than their share)
            userBalances.put(paidByUserId, userBalances.get(paidByUserId) + (amount - sharePerMember));

            // Update other members' balances (owe their share)
            for (User member : group.getMembers()) {
                if (!member.getId().equals(paidByUserId)) {
                    userBalances.put(member.getId(), userBalances.get(member.getId()) - sharePerMember);
                }
            }
        }

        // 2. Settle debts: Find who owes whom
        List<User> payers = new ArrayList<>();
        List<User> receivers = new ArrayList<>();

        for (User member : group.getMembers()) {
            if (userBalances.get(member.getId()) < 0) {
                payers.add(member);
            } else if (userBalances.get(member.getId()) > 0) {
                receivers.add(member);
            }
        }

        List<Payment> settlements = new ArrayList<>();
        int i = 0, j = 0;
        while (i < payers.size() && j < receivers.size()) {
            User payer = payers.get(i);
            User receiver = receivers.get(j);

            double payerOwes = Math.abs(userBalances.get(payer.getId()));
            double receiverReceives = userBalances.get(receiver.getId());
            double amountToSettle = Math.min(payerOwes, receiverReceives);

            // Record the payment
            Payment payment = new Payment();
            payment.setPayer(payer);
            payment.setReceiver(receiver);
            payment.setAmount(amountToSettle);
            payment.setGroup(group);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus("PENDING");
            settlements.add(payment);

            // Update balances
            userBalances.put(payer.getId(), userBalances.get(payer.getId()) + amountToSettle);
            userBalances.put(receiver.getId(), userBalances.get(receiver.getId()) - amountToSettle);

            if (userBalances.get(payer.getId()) == 0) {
                i++;
            }
            if (userBalances.get(receiver.getId()) == 0) {
                j++;
            }
        }

        // Save all generated payments
        paymentRepository.saveAll(settlements);

        // Clear all expenses for the group after settlement
        expenseRepository.deleteAll(expenses);

        return "Expenses settled and payments recorded for group: " + group.getName();
    }

    public String generateUpiLink(Double amount, String upiId) {
        return UPIPaymentUtil.generateUpiUrl(amount, upiId);
    }

    public Payment recordPayment(Long payerId, Long receiverId, Double amount, Long groupId) {
        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new NoSuchElementException("Payer not found with ID: " + payerId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new NoSuchElementException("Receiver not found with ID: " + receiverId));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found with ID: " + groupId));

        Payment payment = new Payment();
        payment.setPayer(payer);
        payment.setReceiver(receiver);
        payment.setAmount(amount);
        payment.setGroup(group);
        payment.setUpiId(receiver.getEmail() + "@upi");
        payment.setStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}